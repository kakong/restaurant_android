package com.zjk.re;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zjk.provider.OrderDetail;
import com.zjk.util.AddMealDialog;
import com.zjk.util.CustomDialog;
import com.zjk.util.HttpUtil;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends Activity {

	// 结算、点菜和下单按钮
	private Button payBtn, addfoodBtn, submitOrder;
	// 点菜列表
	private ListView orderdetail_lv,choice_lv;
	// 开桌生成的订单Id
	private String orderId;
	// 点菜列表中绑定的数据
	private List data = new ArrayList();
	//已点的菜列表
	private  List odlist = new ArrayList();
	// 点菜列表中具体的数据项
	private Map map,map1;
	// ListView 的 Adapter
	private SimpleAdapter sa,sa1;
	// ListView 中显示的数据项
	private String[] from = { "id", "name","num", "price", "remark" };
	// 引用的TextView Drawable ID
	private int[] to = new int[5];
	//顶部信息
	private int orderDetailNumber=0,orderDetailTotal=0;
    //顶部textview
	ArrayList<Map> list = null;
	private TextView orderDetailNumber_tv,orderDetailTotal_tv;
	//private  EditText
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 为Activity设置界面布局
		setContentView(R.layout.order);
        Intent getintent = getIntent();
		// int tableId = getintent.getIntExtra("tableId",-1);
		 orderId = ""+getintent.getIntExtra("orderId",-1);
		// 实例化下单按钮
		submitOrder = (Button) findViewById(R.id.submitorder_btn);
		submitOrder.setOnClickListener(orderListener);
		// 实例化点菜按钮
		addfoodBtn = (Button) findViewById(R.id.addfood_btn);
		addfoodBtn.setOnClickListener(addListener);
		// 实例化支付按钮
		payBtn = (Button) findViewById(R.id.pay_btn);
		payBtn.setOnClickListener(payListener);
		//实例化顶部信息栏
		//已点数目
		orderDetailNumber_tv = (TextView)findViewById(R.id.orderDetailNumber_textView);
		//已点总价
		orderDetailTotal_tv = (TextView)findViewById(R.id.orderDetailTotal_textView);
        //菜单详情
		orderdetail_lv = (ListView)findViewById(R.id.orderDetailListView);
		//声明adapter
		SimpleAdapter adapter = new SimpleAdapter(this,getOrderDetailList(),R.layout.orderdetail_listview,
				new String[]{"name","num","price","remark"},
				new int[]{R.id.name_list,R.id.num_list,R.id.price_list,R.id.remark_list});
		//设置菜单adapter
		orderdetail_lv.setAdapter(adapter);
		//设置顶部信息数据
		orderDetailNumber_tv.setText(orderDetailNumber_tv.getText().toString()+""+orderDetailNumber+"个");
		orderDetailTotal_tv.setText(orderDetailTotal_tv.getText().toString() + "" + orderDetailTotal + "元");
		// 实例化ListView
		choice_lv = (ListView) findViewById(R.id.orderDetailListView01);
		// 为点菜列表ListView绑定数据
		setMenusAdapter();
	}

	// 为点菜列表ListView绑定数据
	private void setMenusAdapter(){
		// 显示点菜项的TextView引用
		to[0] = R.id.id_ListView;
		to[1] = R.id.name_ListView;
		to[2] = R.id.num_ListView;
		to[3] = R.id.price_ListView;
		to[4] = R.id.remark_ListView;
		// 实例化点菜列表ListView Adapter
		sa = new SimpleAdapter(this, data, R.layout.orderdetail_listview2, from, to);
		// 为ListView绑定数据
		choice_lv.setAdapter(sa);
	}

	private List<Map<String, Object>> getOrderDetailList(){
		// 访问服务器url
		String url = HttpUtil.BASE_URL+"servlet/PayServlet?id="+orderId;
		// 查询返回结果
		String result = HttpUtil.queryStringForPost(url);
		// 拆分字符串，转换成对象，添加到列表
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
        if (!result.equals("")) {
			String[] strs = result.split(";");
			for (int i = 0; i < strs.length; i++) {
				map = new HashMap<String, Object>();
				int idx = strs[i].indexOf(",");
				int idx1 = strs[i].indexOf(".");
				int idx2 = strs[i].indexOf("'");
				int idx3 = strs[i].indexOf("*");
				int j = strs[i].length();
				String name = strs[i].substring(0, idx);
				int price = Integer.parseInt(strs[i].substring(idx + 1, idx1));
				int num = Integer.parseInt(strs[i].substring(idx1 + 1, idx2));
				int total = Integer.parseInt(strs[i].substring(idx2 + 1, idx3));
				String remark = strs[i].substring(idx3 + 1);
				OrderDetail od = new OrderDetail();
				od.setName(name);
				od.setPrice(price);
				od.setNum(num);
				od.setTotal(total);
				od.setRemark(remark);
				map.put("name", name);
				map.put("num", num);
				map.put("price", price);
				map.put("remark", remark);
				odlist.add(od);
				list.add(map);
				orderDetailNumber++;
				orderDetailTotal+=total;
			}
		}else {
			map = new HashMap<String, Object>();
			map.put("name","");
			map.put("num", "");
			map.put("price", "");
			map.put("remark", "");
			list.add(map);
		}
		return list;
	}

	// 添菜监听器
	private OnClickListener addListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// 调用点菜方法
			addMeal();
		}
	};
	//结算方法
	private OnClickListener payListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				final CustomDialog.Builder builder = new CustomDialog.Builder(OrderActivity.this);
				builder.setTitle("结算")
						//other-invisible,1-visible,2-gone
						.setEt1Visibility(0)
						.setMessage("总共消费："+orderDetailTotal+"元")
						.setdialogvisibility(1)
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								String url = HttpUtil.BASE_URL+"servlet/PayMoneyServlet?id="+orderId;
								// 获得查询结果
								String result = HttpUtil.queryStringForPost(url);
                                //弹出toast
								Toast.makeText(OrderActivity.this, "已结算", Toast.LENGTH_LONG).show();
								dialog.cancel();
								//builder.setdialogvisibility(0);
								finish();
							}
						})
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
				builder.create().show();
			}
		};

	//添菜方法
	private void addMeal() {
		// 获得AlertDialog.Builder实例
		final AddMealDialog.Builder addbuilder = new AddMealDialog.Builder(this);
		addbuilder
				// 设置标题
				.setTitle("请点菜：")

						// 设置确定按钮
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					// 确定按钮事件
					public void onClick(DialogInterface dialog, int id) {

						LinearLayout v1 = addbuilder.getSelectedItemPosition();

						// 获得TextView，菜编号
						TextView id_tv = (TextView) v1
								.findViewById(R.id.spinner_id);
						// 获得TextView，菜价格
						TextView price_tv = (TextView) v1
								.findViewById(R.id.spinner_price);
						// 获得TextView，菜名称
						TextView name_tv = (TextView) v1
								.findViewById(R.id.spinner_name);

 						// 菜编号值
						String idStr = id_tv.getText().toString();
						// 菜价格值
						String priceStr = price_tv.getText().toString();
						// 菜名称值
						String nameStr = name_tv.getText().toString();
    					// 菜数量值
						String numStr = addbuilder.getEditText1();
						// 菜备注值
						String remarkStr = addbuilder.getEditText2();

						// 封装到Map中
						map = new HashMap();

						map.put("id", idStr);
						map.put("name", nameStr);
						map.put("num", numStr);
						map.put("price", priceStr);
						map.put("remark", remarkStr);

						// 添加到ListView
						data.add(map);

						// 关联的TextView
						to[0] = R.id.id_ListView;
						to[1] = R.id.name_ListView;
						to[2] = R.id.num_ListView;
						to[3] = R.id.price_ListView;
						to[4] = R.id.remark_ListView;

						// 实例化SimpleAdapter
						sa = new SimpleAdapter(OrderActivity.this, data,
								R.layout.orderdetail_listview2, from, to);
						// 为ListView绑定数据
						choice_lv.setAdapter(sa);
						Toast.
								makeText(OrderActivity.this, "增加："+nameStr, Toast.LENGTH_LONG).show();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					// 确定按钮事件
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
			});
		addbuilder.create().show();
	}

	// 下单监听器
	private OnClickListener orderListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// 遍历点菜列表
			for (int i = 0; i < data.size(); i++) {
				// 获得其中点菜map
				Map map = (Map)data.get(i);
				// 获得点菜项
				String menuId = (String) map.get("id");
				String num = (String)map.get("num");
				String remark = (String)map.get("remark");
				String myOrderId = orderId;
				// 封装到请求参数中
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// 添加到请求参数中
				params.add(new BasicNameValuePair("menuId", menuId));
				params.add(new BasicNameValuePair("orderId", myOrderId));
				params.add(new BasicNameValuePair("num", num));
				params.add(new BasicNameValuePair("remark", remark));
				UrlEncodedFormEntity entity1=null;
				try {
					entity1 =  new UrlEncodedFormEntity(params,HTTP.UTF_8);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				// 请求服务器Servlet的url
				String url = HttpUtil.BASE_URL+"servlet/OrderDetailServlet";
				// 获得HttpPost对象
				HttpPost request = HttpUtil.getHttpPost(url);
				// 为请求设置参数
				request.setEntity(entity1);
				// 获得返回结果
				String result= HttpUtil.queryStringForPost(request);

			}
		}
	};
}

