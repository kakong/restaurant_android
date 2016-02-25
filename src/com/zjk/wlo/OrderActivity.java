package com.zjk.wlo;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zjk.wlo.R;
import com.zjk.util.HttpUtil;

public class OrderActivity extends Activity {
	// 锟斤拷锟斤拷锟斤拷锟斤拷锟叫憋拷
	private Spinner tableNoSpinner;
	// 锟斤拷锟斤拷锟斤拷锟斤拷撕锟斤拷碌锟斤拷锟脚�
	private Button orderBtn, addBtn, startBtn;
	// 锟斤拷锟斤拷锟洁辑锟斤拷
	private EditText personNumEt;
	// 锟斤拷锟斤拷斜锟�
	private ListView lv;
	// 锟斤拷锟斤拷锟斤拷锟缴的讹拷锟斤拷Id
	private String orderId;
	// 锟斤拷锟斤拷斜锟斤拷邪蠖ǖ锟斤拷锟斤拷锟�
	private List data = new ArrayList();
	// 锟斤拷锟斤拷斜锟斤拷芯锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
	private Map map;
	// ListView 锟斤拷 Adapter
	private SimpleAdapter sa;
	// ListView 锟斤拷锟斤拷示锟斤拷锟斤拷锟斤拷锟斤拷
	private String[] from = { "id", "name","num", "price", "remark" };
	// 锟斤拷锟矫碉拷TextView Drawable ID
	private int[] to = new int[5];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 为Activity锟斤拷锟矫斤拷锟芥布锟斤拷
		setContentView(R.layout.order);
		
		// 实锟斤拷锟斤拷Spinner
		tableNoSpinner = (Spinner) findViewById(R.id.tableNoSpinner);
		// 为锟斤拷锟斤拷锟斤拷锟斤拷锟叫憋拷Spinner锟斤拷锟斤拷锟斤拷
		setTableAdapter();
		
		// 实锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷钮
		startBtn = (Button) findViewById(R.id.startButton02);
		// 为锟斤拷锟斤拷锟斤拷钮锟斤拷蛹锟斤拷锟斤拷锟�
		startBtn.setOnClickListener(startListener);
		
		// 实锟斤拷锟斤拷锟斤拷税锟脚�
		addBtn = (Button) findViewById(R.id.addMealButton01);
		// 为锟斤拷税锟脚ワ拷锟接硷拷锟斤拷锟斤拷
		addBtn.setOnClickListener(addListener);
			
		// 实锟斤拷锟斤拷锟铰碉拷锟斤拷钮
		orderBtn = (Button) findViewById(R.id.orderButton02);
		// 为锟铰碉拷锟斤拷钮锟斤拷蛹锟斤拷锟斤拷锟�
		orderBtn.setOnClickListener(orderListener);
		
		// 实锟斤拷锟斤拷锟斤拷锟斤拷锟洁辑锟斤拷
		personNumEt = (EditText) findViewById(R.id.personNumEditText02);
		
		// 实锟斤拷锟斤拷ListView
		lv = (ListView) findViewById(R.id.orderDetailListView01);
		// 为锟斤拷锟斤拷斜锟絃istView锟斤拷锟斤拷锟斤拷
		setMenusAdapter();
	}
	
	
	// 为锟斤拷锟斤拷斜锟絃istView锟斤拷锟斤拷锟斤拷
	private void setMenusAdapter(){
		// 锟斤拷示锟斤拷锟斤拷锟斤拷TextView锟斤拷锟斤拷
		to[0] = R.id.id_ListView;
		to[1] = R.id.name_ListView;
		to[2] = R.id.num_ListView;
		to[3] = R.id.price_ListView;
		to[4] = R.id.remark_ListView;
		// 实锟斤拷锟斤拷锟斤拷锟斤拷斜锟絃istView Adapter
		sa = new SimpleAdapter(this, data, R.layout.listview, from, to);
		// 为ListView锟斤拷锟斤拷锟斤拷
		lv.setAdapter(sa);
	}
	
	
	// 为锟斤拷锟斤拷锟斤拷锟斤拷锟叫憋拷Spinner锟斤拷锟斤拷锟斤拷
	private void setTableAdapter(){
		// 锟斤拷锟绞憋拷锟斤拷SQLite锟斤拷锟捷匡拷锟斤拷锟斤拷锟脚憋拷锟経ri
	    Uri uri = Uri.parse("content://com.amaker.provider.TABLES/table");
		// 要选锟斤拷锟斤拷锟脚憋拷锟叫碉拷锟斤拷
		String[] projection = { "_id", "num", "description" };
		// 锟斤拷询锟脚伙拷锟轿憋拷
		Cursor c = managedQuery(uri, projection, null, null, null);
		// 实锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟叫憋拷Spinner锟斤拷Adapter
		SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, c,
				new String[] { "_id" }, new int[] { android.R.id.text1 });
		// 为锟斤拷锟斤拷Spinner锟斤拷锟斤拷锟斤拷
		tableNoSpinner.setAdapter(adapter2);
	}
	
	
	// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
	private OnClickListener startListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm");
			// 锟斤拷锟斤拷时锟斤拷
			String orderTime = sdf.format(date);
			// 锟斤拷锟斤拷锟矫伙拷锟斤拷锟接碉拷陆锟斤拷锟斤拷锟侥硷拷锟叫伙拷锟�
			SharedPreferences pres = getSharedPreferences("user_msg",
					MODE_WORLD_READABLE);
			String userId = pres.getString("id", "");
			// 锟斤拷锟斤拷
			TextView tv = (TextView) tableNoSpinner.getSelectedView();
			String tableId = tv.getText().toString();
			// 锟斤拷锟斤拷
			String personNum = personNumEt.getText().toString();
			// 锟斤拷锟斤拷锟斤拷锟斤拷斜锟�
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
			params.add(new BasicNameValuePair("orderTime", orderTime));
			params.add(new BasicNameValuePair("userId", userId));
			params.add(new BasicNameValuePair("tableId", tableId));
			params.add(new BasicNameValuePair("personNum", personNum));
			UrlEncodedFormEntity entity1=null;
			try {
				 entity1 =  new UrlEncodedFormEntity(params,HTTP.UTF_8);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			// 锟斤拷锟斤拷锟斤拷锟斤拷锟絬rl
			String url = HttpUtil.BASE_URL+"servlet/StartTableServlet";
			// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷HttpPost
			HttpPost request = HttpUtil.getHttpPost(url);
			// 锟斤拷锟矫诧拷询锟斤拷锟斤拷
			request.setEntity(entity1);
			// 锟斤拷锟斤拷锟接︼拷锟斤拷
			String result= HttpUtil.queryStringForPost(request);
			// 锟斤拷锟斤拷锟斤拷锟斤拷锟缴的讹拷锟斤拷Id锟斤拷锟斤拷值锟斤拷全锟斤拷orderId
			orderId = result;
			Toast.
			makeText(OrderActivity.this, "锟斤拷锟侥讹拷锟斤拷锟斤拷锟角ｏ拷"+result, Toast.LENGTH_LONG).show();
		}
	};
	

	
	// 锟斤拷思锟斤拷锟斤拷锟�
	private OnClickListener addListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// 锟斤拷锟矫碉拷朔锟斤拷锟�
			addMeal();
		}
	};
	
	//锟斤拷朔锟斤拷锟�
	private void addMeal() {
		// 锟斤拷锟絃ayoutInflater实锟斤拷
		LayoutInflater inflater = LayoutInflater.from(this);
		// 实锟斤拷锟斤拷锟节碉拷锟斤拷锟皆伙拷锟斤拷锟斤拷锟斤拷拥锟斤拷锟酵�
		final View v = inflater.inflate(R.layout.order_detail, null);
		// 锟斤拷锟斤拷锟酵硷拷械锟絊pinner锟斤拷锟襟，菜碉拷锟斤拷锟斤拷锟叫憋拷
		final Spinner menuSpinner = (Spinner) v.findViewById(R.id.menuSpinner);
		// 锟斤拷锟斤拷锟酵硷拷械锟紼ditText锟斤拷锟斤拷,锟斤拷锟斤拷
		EditText numEt = (EditText) v.findViewById(R.id.numEditText);
		// 锟斤拷锟斤拷锟酵硷拷械锟紼ditText实锟斤拷锟斤拷锟斤拷注
		EditText remarkEt = (EditText) v.findViewById(R.id.add_remarkEditText);
		
		// 锟斤拷锟绞憋拷锟斤拷SQLite锟斤拷锟捷匡拷锟斤拷MenuTbl锟斤拷锟経ri
		Uri uri = Uri.parse("content://com.amaker.provider.MENUS/menu1");
		// 锟斤拷询锟斤拷
		String[] projection = { "_id", "name", "price" };
		// 锟斤拷锟紺ontentResolver实锟斤拷
		ContentResolver cr = getContentResolver();
		// 锟斤拷询锟脚伙拷锟轿憋拷
		Cursor c = cr.query(uri, projection, null, null, null);
		// 实锟斤拷锟斤拷SimpleCursorAdapter
		SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(this,
				R.layout.spinner_lo, c,
				new String[] { "_id", "price", "name" }, new int[] {
						R.id.id_TextView01, R.id.price_TextView02,
						R.id.name_TextView03, });
		// 为锟斤拷锟斤拷锟斤拷锟斤拷斜锟絊pinner锟斤拷锟斤拷锟斤拷
		menuSpinner.setAdapter(adapter2);
		
		// 锟斤拷锟紸lertDialog.Builder实锟斤拷
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder
		// 锟斤拷锟矫憋拷锟斤拷
		.setMessage("锟斤拷锟剿ｏ拷")
		// 锟斤拷锟斤拷锟皆讹拷锟斤拷锟斤拷图
		.setView(v)
		// 锟斤拷锟斤拷确锟斤拷锟斤拷钮
		.setPositiveButton("确锟斤拷", new DialogInterface.OnClickListener() {
					// 确锟斤拷锟斤拷钮锟铰硷拷
					public void onClick(DialogInterface dialog, int id) {
						
						// 锟斤拷锟絃istView锟叫碉拷锟皆讹拷锟斤拷锟斤拷图LinearLayout
						LinearLayout v1 = (LinearLayout) menuSpinner
								.getSelectedView();
						
						// 锟斤拷锟絋extView锟斤拷锟剿憋拷锟�
						TextView id_tv = (TextView) v1
								.findViewById(R.id.id_TextView01);
						// 锟斤拷锟絋extView锟斤拷锟剿价革拷
						TextView price_tv = (TextView) v1
								.findViewById(R.id.price_TextView02);
						// 锟斤拷锟絋extView锟斤拷锟斤拷锟斤拷锟斤拷
						TextView name_tv = (TextView) v1
								.findViewById(R.id.name_TextView03);
						// 锟斤拷锟紼ditText锟斤拷锟斤拷锟斤拷锟斤拷
						EditText num_et = (EditText) v
								.findViewById(R.id.numEditText);
						// 锟斤拷锟紼ditText锟斤拷锟剿憋拷注
						EditText remark_et = (EditText) v
								.findViewById(R.id.add_remarkEditText);
						// 锟剿憋拷锟街�
						String idStr = id_tv.getText().toString();
						// 锟剿价革拷值
						String priceStr = price_tv.getText().toString()+"元";
						// 锟斤拷锟斤拷锟斤拷值
						String nameStr = name_tv.getText().toString();
						// 锟斤拷锟斤拷锟斤拷值
						String numStr = num_et.getText().toString();
						// 锟剿憋拷注值
						String remarkStr = remark_et.getText().toString();
						
						// 锟斤拷装锟斤拷Map锟斤拷
						map = new HashMap();

						map.put("id", idStr);
						map.put("name", nameStr);
						map.put("num", numStr);
						map.put("price", priceStr);
						map.put("remark", remarkStr);
						
						// 锟斤拷拥锟絃istView
						data.add(map);
						
						// 锟斤拷锟斤拷锟斤拷TextView
						to[0] = R.id.id_ListView;
						to[1] = R.id.name_ListView;
						to[2] = R.id.num_ListView;
						to[3] = R.id.price_ListView;
						to[4] = R.id.remark_ListView;
						
						// 实锟斤拷锟斤拷SimpleAdapter
						sa = new SimpleAdapter(OrderActivity.this, data,
								R.layout.listview, from, to);
						// 为ListView锟斤拷锟斤拷锟斤拷
						lv.setAdapter(sa);

					}
				}).setNegativeButton("取锟斤拷", null);
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	
	
	// 锟铰碉拷锟斤拷锟斤拷锟斤拷
	private OnClickListener orderListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// 锟斤拷锟斤拷锟斤拷锟斤拷斜锟�
			for (int i = 0; i < data.size(); i++) {
				// 锟斤拷锟斤拷锟斤拷械锟斤拷map
				Map map = (Map)data.get(i);
				// 锟斤拷玫锟斤拷锟斤拷
				String menuId = (String) map.get("id");
				String num = (String)map.get("num");
				String remark = (String)map.get("remark");
				String myOrderId = orderId;
				
				// 锟斤拷装锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// 锟斤拷拥锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
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
				
				// 锟斤拷锟斤拷锟斤拷锟斤拷锟絊ervlet锟斤拷url
				String url = HttpUtil.BASE_URL+"servlet/OrderDetailServlet";
				// 锟斤拷锟紿ttpPost锟斤拷锟斤拷
				HttpPost request = HttpUtil.getHttpPost(url);
				// 为锟斤拷锟斤拷锟斤拷锟矫诧拷锟斤拷
				request.setEntity(entity1);
				// 锟斤拷梅锟斤拷亟锟斤拷
				String result= HttpUtil.queryStringForPost(request);
				
				//Toast.
				//makeText(OrderActivity.this, "锟斤拷统晒锟�", Toast.LENGTH_LONG).show();
				
				finish();
			}
		}
	};
}
