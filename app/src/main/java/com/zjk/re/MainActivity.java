package com.zjk.re;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zjk.provider.CheckTable;
import com.zjk.provider.Menus;
import com.zjk.util.CustomDialog;
import com.zjk.util.HttpUtil;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MainActivity extends Activity{
	// 显示餐桌状态的GridView
	private GridView gv;
	// 餐桌数量
	private int count;
	// 保存餐桌信息的列表
	private List list = new ArrayList();
	// 设置底部按钮，换桌，开桌，退出
	private Button changetable_btn,opentable_btn,quit_btn,update_btn;
	// 开桌订单id
	private String orderId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		JPushInterface.setDebugMode(true);
//		JPushInterface.init(this);
		// 设置当前Activity的界面布局
		setContentView(R.layout.check_table);
		// 实例化
        gv = (GridView) findViewById(R.id.check_table_gridview);
        //获得餐桌列表
        getTableList();
        //为GridView绑定数据
        gv.setAdapter(new ImageAdapter(this));
        //实例化底部按钮，
        changetable_btn = (Button)findViewById(R.id.changetable);
        opentable_btn = (Button)findViewById(R.id.opentable_btn);
		update_btn = (Button)findViewById(R.id.update_btn);
        quit_btn = (Button)findViewById(R.id.quit_btn);
        
        
        changetable_btn.setOnClickListener(new OnClickListener() {
			
   			@Override
   			public void onClick(View arg0) {
   				changeTable();
   				
   			}
   		});
		//设置按钮响应事件
        update_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
                updateMenu();
			}
		});
        opentable_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {				
				openTable(null);
			}
		});
        quit_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				quit();			
			}
		});
   
	}
	
	// 获得餐桌信息列表，信息包括桌号和状态
	private void getTableList(){
		// 访问服务器url
		String url = HttpUtil.BASE_URL+"servlet/CheckTableServlet";
		// 查询返回结果
		String result = HttpUtil.queryStringForPost(url);
		// 拆分字符串，转换成对象，添加到列表
		String[] strs = result.split(";");
		for (int i = 0; i < strs.length; i++) {
			int idx = strs[i].indexOf(",");
			int idx1 = strs[i].indexOf(".");
			int idx2 = strs[i].indexOf("'");
			int j = strs[i].length();
			int num = Integer.parseInt(strs[i].substring(0, idx));
			int flag = Integer.parseInt(strs[i].substring(idx+1,idx1));
			int people = Integer.parseInt(strs[i].substring(idx1+1,idx2));
			int orderid = Integer.parseInt(strs[i].substring(idx2+1));
			CheckTable ct = new CheckTable();
			ct.setFlag(flag);
			ct.setNum(num);
			ct.setPeople(people);
			ct.setOrderId(orderid);
			list.add(ct);
		}
	}
	// 继承BaseAdapter
    public class ImageAdapter extends BaseAdapter {
    	//上下文
        private Context mContext;
        // 构造方法
        public ImageAdapter(Context c) {
            mContext = c;
        }
        // 组件个数
        public int getCount() {
            return list.size();
        }
        // 当前组件id
        public Object getItem(int position) {
            return null;
        }
        // 获得当前视图
        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
        	// 声明图片视图
        	LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        	View v = null;
        	ImageView imageView =null;
        	TextView tv =null;
			TextView tv2 =null;
            if (convertView == null) {
            	// // 实例化图片视图
            	v = inflater.inflate(R.layout.check_table_view,null);
            	// 设置图片视图属性
                v.setPadding(9, 9, 9, 9);
            } else {
                v = (View) convertView;
            }
            
            
            // 获得ImageView对象
            imageView = (ImageView) v.findViewById(R.id.check_table_ImageView01);
       	 	// 获得TextView对象
            tv = (TextView) v.findViewById(R.id.check_table_TextView01);
			tv2 = (TextView) v.findViewById(R.id.check_table_TextView02);
            // 获得CheckTable对象
            final CheckTable ct = (CheckTable) list.get(position);
            if(ct.getFlag()==1){
            	// 设置ImageView图片为有人
            	imageView.setImageResource(R.drawable.table_icon2);
            	tv.setText("餐桌号：" + ct.getNum());
				tv2.setText("订单号："+ct.getOrderId());
				imageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent();
						intent.putExtra("tableId",ct.getNum());
						intent.putExtra("orderId",ct.getOrderId());
						intent.setClass( MainActivity.this,OrderActivity.class);
						startActivity(intent);
					}
				});
            }else{
            	// 设置ImageView图片为空位
            	imageView.setImageResource(R.drawable.table_icon1);
            	tv.setText("餐桌号：" + ct.getNum());
				tv2.setText("座位数："+ct.getPeople());
				imageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						openTable(""+ct.getNum());
					}
				});
            }
            return v;
        }
    }

    //能否换桌
	//验证用户输入规则
	private boolean isChangeTable(String OrderId,String changeToTableId) {
		boolean res=false;
		//订单号和更换的桌号不能为空
		if(!OrderId.equals("")&&changeToTableId.equals("")){
			//字符格式转换
			int orderid=Integer.parseInt(OrderId);
			int tableid = Integer.parseInt(changeToTableId);
			//判断能否换桌
		for (int i = 0; i < list.size(); i++) {
			CheckTable ct = (CheckTable) list.get(i);
			if (orderid == ct.getOrderId() && ct.getFlag() == 0 && orderid != 0) {
				for (int j = 0; j < list.size(); j++) {
					CheckTable ct1 = (CheckTable) list.get(j);
					if (ct.getFlag() == 0 && tableid == ct1.getNum()) {
						res = true;
						break;
					}
				}
			}
		}
		}
		return res;
	}


	// 换桌方法
	private void changeTable() {
		LayoutInflater inflater = LayoutInflater.from(this);
		//声明弹窗
		final CustomDialog.Builder builder = new CustomDialog.Builder(this);
		//设置弹窗内容
		builder.setEditText1("请输入订到号");
		builder.setEditText2("更换到的桌号");
		builder.setInputType(InputType.TYPE_CLASS_NUMBER);
			builder.setTitle("更换桌号")

					//设置visibility other-invisible,1-visible,2-gone
					.setEt1Visibility(1)
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// 获得第edittext内容，从builder传进来
							String orderId = builder.getEditText1();
							String tableId = builder.getEditText2();
							if (isChangeTable(orderId, tableId)) {

								// 查询语句
								String queryString = "orderId=" + orderId + "&tableId=" + tableId;
								// url
								String url = HttpUtil.BASE_URL + "servlet/ChangeTableServlet?" + queryString;
								// 调用http服务器查询方法获得返回结果
								String result = HttpUtil.queryStringForPost(url);
								// 换桌成功，弹出toast
								Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
								//清空餐桌list
								list.clear();
								//重新获得餐桌list
								getTableList();
								//重绘当前gvview
								gv.setAdapter(new ImageAdapter(MainActivity.this));
								dialog.cancel();
							} else {
								Toast.
										makeText(MainActivity.this, "输入有误！请重新输入", Toast.LENGTH_LONG).show();

//
							}
						}
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			builder.create().show();

	}
        
	// 开桌方法
		private void openTable(final  String tableId){
			//声明弹出框并设置参数
			final CustomDialog.Builder builder = new CustomDialog.Builder(this);
			builder.setEditText1(tableId);
			builder.setEditText1Hint("请输入桌号");
			builder.setEditText2Hint("请输入人数");
			//builder.setEditText2("");
			builder.setInputType(InputType.TYPE_CLASS_NUMBER);
			builder.setTitle("开桌")
			//设置输入框visibility，other-invisible,1-visible,2-gone
			       .setEt1Visibility(1)
			       .setPositiveButton("确定", new DialogInterface.OnClickListener() {
					   public void onClick(DialogInterface dialog, int id) {
						   String personNum = builder.getEditText2();
						   //设置下单时间
						   Date date = new Date();
						   SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm");
						   String orderTime = sdf.format(date);
						   // 传入操作员id，从登陆后保存的信息上获取
						   SharedPreferences pres = getSharedPreferences("user_msg",
								   MODE_WORLD_READABLE);
						   String userId = pres.getString("id", "");
						   // 封装到请求参数中
						   List<NameValuePair> params = new ArrayList<NameValuePair>();
						   // 添加到请求参数中
						   params.add(new BasicNameValuePair("orderTime", orderTime));
						   params.add(new BasicNameValuePair("userId", userId));
						   params.add(new BasicNameValuePair("tableId", tableId));
						   params.add(new BasicNameValuePair("personNum", personNum));
						   UrlEncodedFormEntity entity1 = null;
						   try {
							   entity1 = new UrlEncodedFormEntity(params, HTTP.UTF_8);
						   } catch (UnsupportedEncodingException e) {
							   e.printStackTrace();
						   }
						   // 请求服务器Servlet的url
						   String url = HttpUtil.BASE_URL + "servlet/StartTableServlet";
						   // 获得HttpPost对象
						   HttpPost request = HttpUtil.getHttpPost(url);
						   // 为请求设置参数
						   request.setEntity(entity1);
						   // 获得返回结果
						   String result = HttpUtil.queryStringForPost(request);
						   // 获得系统给的订单id
						   orderId = result;
						   Toast.
								   makeText(MainActivity.this, "开桌成功！你的订单号：" + result, Toast.LENGTH_LONG).show();
						   //重绘girdview
						   //清空tablelist
						   list.clear();
						   //table再次获取list
						   getTableList();
						   //设置gridviewAdapter
						   gv.setAdapter(new ImageAdapter(MainActivity.this));
						   dialog.cancel();
					   }
				   })
			       .setNegativeButton("取消", new DialogInterface.OnClickListener() {
					   public void onClick(DialogInterface dialog, int id) {
						   dialog.cancel();
					   }
				   });
			 builder.create().show();
		}

	// 更新菜谱和餐桌表方法
	private void updateMenu() {
		// 访问服务器url
		String urlStr = HttpUtil.BASE_URL + "servlet/UpdateServlet";
		try {
			// 实例化URL对象
			URL url = new URL(urlStr);
			// 打开连接
			URLConnection conn = url.openConnection();
			// 获得输入流
			InputStream in = conn.getInputStream();
			// 实例化DocumentBuilderFactory
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			// 实例化DocumentBuilder
			DocumentBuilder builder = factory.newDocumentBuilder();
			// 获得Document
			Document doc = builder.parse(in);
			// 获得节点列表
			NodeList nl = doc.getElementsByTagName("menu");
			// 获得访问数据接口ContentResolver
			ContentResolver cr = getContentResolver();
			// 访问数据的Uri
			Uri uri1 = Menus.CONTENT_URI;
			// 删除本地SQLite数据库中菜谱表中的数据
			cr.delete(uri1, null, null);
			nl.getLength();
			// 循环将数据保存到菜谱表
			for (int i = 0; i < nl.getLength(); i++) {
				// 实例化ContentValues
				ContentValues values = new ContentValues();
				// 解析XML文件获得菜单id
				int id = Integer.parseInt(doc.getElementsByTagName("id")
						.item(i).getFirstChild().getNodeValue());
				// 名称
				String name = doc.getElementsByTagName("name").item(i)
						.getFirstChild().getNodeValue();
				// 图片路径
				String pic = doc.getElementsByTagName("pic").item(i)
						.getFirstChild().getNodeValue();
				// 价格
				int price = Integer.parseInt(doc.getElementsByTagName("price")
						.item(i).getFirstChild().getNodeValue());
				// 分类编号
				int typeId = Integer.parseInt(doc
						.getElementsByTagName("typeId").item(i).getFirstChild()
						.getNodeValue());
				// 备注
				String remark = doc.getElementsByTagName("remark").item(i)
						.getFirstChild().getNodeValue();
				// 添加到ContenValues对象
				values.put("_id", id);
				values.put("name", name);
				values.put("price", price);
				values.put("pic", pic);
				values.put("typeId", typeId);
				values.put("remark", remark);
				// 插入到数据库
				cr.insert(uri1, values);
				//重绘gridview
				//清空tablelist
				list.clear();
				//重新获取list
				getTableList();
				//设置gridviewAdapter
				gv.setAdapter(new ImageAdapter(MainActivity.this));
               //弹出toast
				Toast
						.makeText(MainActivity.this, "更新成功", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// 退出方法
	private void quit(){
		//声明弹出框
		CustomDialog.Builder builder = new CustomDialog.Builder(this);
		//设置弹出框参数
		builder.setMessage("你确定要退出系统吗？");
		builder.setTitle("退出系统");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}
	
	
}
