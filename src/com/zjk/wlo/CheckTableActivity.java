package com.zjk.wlo;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zjk.wlo.R;
import com.zjk.provider.CheckTable;
import com.zjk.util.CustomDialog;
import com.zjk.util.HttpUtil;


public class CheckTableActivity extends Activity{
	// 锟斤拷示锟斤拷锟斤拷状态锟斤拷GridView
	private GridView gv;
	// 锟斤拷锟斤拷锟斤拷锟斤拷
	private int count;
	// 锟斤拷锟斤拷锟斤拷锟斤拷锟较拷锟斤拷斜锟�
	private List list = new ArrayList();
	//锟斤拷锟斤拷锟阶诧拷锟斤拷钮
	private Button changetable_btn,opentable_btn,quit_btn;
	//public View gv;
	//锟斤拷台锟斤拷锟斤拷id
	private String orderId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 锟斤拷锟矫憋拷锟斤拷
		setTitle("锟斤拷台");
		// 锟斤拷锟矫碉拷前Activity锟侥斤拷锟芥布锟斤拷
		setContentView(R.layout.check_table);
		// 实锟斤拷锟斤拷
        gv = (GridView) findViewById(R.id.check_table_gridview);
        //锟斤拷貌锟斤拷锟斤拷斜锟�
        getTableList();
        //为GridView锟斤拷锟斤拷锟斤拷
        gv.setAdapter(new ImageAdapter(this));
        //实锟斤拷锟斤拷锟斤拷钮
        changetable_btn = (Button)findViewById(R.id.changetable);
        opentable_btn = (Button)findViewById(R.id.opentable_btn);
        quit_btn = (Button)findViewById(R.id.quit_btn);
        
        
        changetable_btn.setOnClickListener(new OnClickListener() {
			
   			@Override
   			public void onClick(View arg0) {
   				changeTable();
   				
   			}
   		});
        
        opentable_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {				
				openTable();
			}
		});
        quit_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				quit();			
			}
		});
   
	}
	
	// 锟斤拷貌锟斤拷锟斤拷锟较拷斜锟斤拷锟较拷锟斤拷锟斤拷锟斤拷藕锟阶刺�
	private void getTableList(){
		// 锟斤拷锟绞凤拷锟斤拷锟斤拷url
		String url = HttpUtil.BASE_URL+"servlet/CheckTableServlet";
		// 锟斤拷询锟斤拷锟截斤拷锟�
		String result = HttpUtil.queryStringForPost(url);
		// 锟斤拷锟斤拷址锟斤拷锟斤拷锟阶拷锟斤拷啥锟斤拷锟斤拷锟接碉拷锟叫憋拷
		String[] strs = result.split(";");
		for (int i = 0; i < strs.length; i++) {
			int idx = strs[i].indexOf(",");
			int idx1 = strs[i].indexOf(".");
			int j = strs[i].length();
			int num = Integer.parseInt(strs[i].substring(0,idx));
			int flag = Integer.parseInt(strs[i].substring(idx+1,idx1));
			int people = Integer.parseInt(strs[i].substring(idx1+1));
			CheckTable ct = new CheckTable();
			ct.setFlag(flag);
			ct.setNum(num);
			ct.setPeople(people);
			list.add(ct);
		}
	}
	// 锟教筹拷BaseAdapter
    public class ImageAdapter extends BaseAdapter {
    	// 锟斤拷锟斤拷锟斤拷
        private Context mContext;
        // 锟斤拷锟届方锟斤拷
        public ImageAdapter(Context c) {
            mContext = c;
        }
        // 锟斤拷锟斤拷锟斤拷锟�
        public int getCount() {
            return list.size();
        }
        // 锟斤拷前锟斤拷锟�
        public Object getItem(int position) {
            return null;
        }
        // 锟斤拷前锟斤拷锟絠d
        public long getItemId(int position) {
            return 0;
        }
        
        // 锟斤拷玫锟角帮拷锟酵�,锟斤拷锟絠mage+text
        public View getView(int position, View convertView, ViewGroup parent) {
        	// 锟斤拷锟斤拷图片锟斤拷图
        	LayoutInflater inflater = 
        		LayoutInflater.from(CheckTableActivity.this);
        	View v = null;
        	ImageView imageView =null;
        	TextView tv =null;
            if (convertView == null) {
            	// 实锟斤拷锟斤拷图片锟斤拷图
            	v = inflater.inflate(R.layout.check_table_view,null);
            	// 锟斤拷锟斤拷图片锟斤拷图锟斤拷锟斤拷
                v.setPadding(9, 9, 9, 9);
            } else {
                v = (View) convertView;
            }
            
            
            // 锟斤拷锟絀mageView锟斤拷锟斤拷
            imageView = (ImageView) v.findViewById(R.id.check_table_ImageView01);
       	 	// 锟斤拷锟絋extView锟斤拷锟斤拷
            tv = (TextView) v.findViewById(R.id.check_tableTextView01);
            // 锟斤拷锟紺heckTable锟斤拷锟斤拷
            CheckTable ct = (CheckTable) list.get(position);
            if(ct.getFlag()==1){
            	// 锟斤拷锟斤拷ImageView图片为 锟斤拷锟斤拷
            	imageView.setImageResource(R.drawable.table_icon2);
            	tv.setText("No."+ct.getNum()+" "+ct.getPeople()+"锟斤拷锟斤拷");
            }else{
            	// 锟斤拷锟斤拷ImageView图片为 锟斤拷位
            	imageView.setImageResource(R.drawable.table_icon1);
            	tv.setText("No."+ct.getNum()+" "+ct.getPeople()+"锟斤拷锟斤拷");
            }
            // 为TextView锟斤拷锟斤拷锟斤拷锟斤拷
          //  tv.setText("锟斤拷锟斤拷"+ct.getNum());
            return v;
        }
    }
    
	// 锟斤拷台系统
	private void changeTable(){
		// 锟斤拷锟絃ayoutInflater实锟斤拷
		LayoutInflater inflater = LayoutInflater.from(this);
		// 锟斤拷锟絃inearLayout锟斤拷图实锟斤拷
		//View v =inflater.inflate(R.layout.change_table, null);
//		// 锟斤拷LinearLayout锟叫伙拷锟紼ditText实锟斤拷
//		final EditText et1 = (EditText) findViewById(R.id.changeorder_number);
//		final EditText et2 = (EditText) findViewById(R.id.changetable_number);
		
		final CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setEditText1("锟斤拷锟斤拷锟斤拷亩锟斤拷锟斤拷锟�");
		builder.setEditText2("锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟脚ｏ拷");
		builder.setTitle("锟斤拷锟斤拷锟斤拷锟斤拷")
		//other-invisible,1-visible,2-gone
		       .setEt1Visibility(1)
		       .setPositiveButton("确锟斤拷", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	// 锟斤拷枚锟斤拷锟斤拷锟�
		        	 String orderId = builder.getEditText1();
		        //	String orderId = et1.getText().toString();
		        	// 锟斤拷锟斤拷锟斤拷锟�
		        	   String tableId = builder.getEditText2();
		       	//	String tableId = et2.getText().toString();
		       		// 锟斤拷询锟斤拷锟斤拷
		       		String queryString = "orderId="+orderId+"&tableId="+tableId;
		       		// url
		       		String url = HttpUtil.BASE_URL+"servlet/ChangeTableServlet?"+queryString;
		       		// 锟斤拷询锟斤拷锟截斤拷锟�
		       		String result = HttpUtil.queryStringForPost(url);
		       		// 锟斤拷示锟斤拷锟�
		       		Toast.makeText(CheckTableActivity.this,result,Toast.LENGTH_LONG).show();
		       		
		       		//锟斤拷锟絚hecklist锟斤拷锟斤拷锟铰硷拷锟斤拷tablelist锟斤拷然锟斤拷锟截伙拷gridview
		       		list.clear();
		       		getTableList();
		       		gv.setAdapter(new ImageAdapter(CheckTableActivity.this));
		       		dialog.cancel();
		       		//Adapter adapter = 
//		       		GameThread gtGameThread = new GameThread();
//		       		gtGameThread.run();
		       		
		           }
		       })
		       .setNegativeButton("取锟斤拷", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		 builder.create().show();
	}
	
//	class GameThread implements Runnable {
//		 public void run() {
//		  while (!Thread.currentThread().isInterrupted()) {
//		   try {
//		    Thread.sleep(100);
//		   }
//		            catch (InterruptedException e) {
//		    Thread.currentThread().interrupt();
//		   }
//		   // 使锟斤拷postInvalidate锟斤拷锟斤拷直锟斤拷锟斤拷锟竭筹拷锟叫革拷锟铰斤拷锟斤拷
//		   gv.postInvalidate();
//		  }
//		 }
//		}
//	public void postInvalidate() {
//        postInvalidateDelayed(0);
//}
//public void postInvalidateDelayed(long delayMilliseconds) {
//        // We try only with the AttachInfo because there's no point in invalidating
//        // if we are not attached to our window
//        if (mAttachInfo != null) {
//            Message msg = Message.obtain();
//            msg.what = AttachInfo.INVALIDATE_MSG;
//            msg.obj = this;
//            mAttachInfo.mHandler.sendMessageDelayed(msg, delayMilliseconds);
//        }
//        
        
	// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
		private void openTable(){
			LayoutInflater inflater = LayoutInflater.from(this);
			// 锟斤拷锟絃inearLayout锟斤拷图实锟斤拷
			//View v =inflater.inflate(R.layout.change_table, null);
//			// 锟斤拷LinearLayout锟叫伙拷锟紼ditText实锟斤拷
//			final EditText et1 = (EditText) findViewById(R.id.changeorder_number);
//			final EditText et2 = (EditText) findViewById(R.id.changetable_number);
			
			final CustomDialog.Builder builder = new CustomDialog.Builder(this);
			builder.setEditText1("锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟脚ｏ拷");
			builder.setEditText2("锟斤拷锟斤拷锟矫诧拷锟斤拷锟斤拷锟斤拷");
			builder.setTitle("锟斤拷锟斤拷锟斤拷锟斤拷")
			//other-invisible,1-visible,2-gone
			       .setEt1Visibility(1)
			       .setPositiveButton("确锟斤拷", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
			        	   String tableId = builder.getEditText1();
			        	// 锟斤拷锟斤拷貌锟斤拷锟斤拷锟�
			        	   String personNum = builder.getEditText2();
			        		Date date = new Date();
							SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm");
						// 锟斤拷锟斤拷时锟斤拷
							String orderTime = sdf.format(date);
							// 锟斤拷锟斤拷锟矫伙拷锟斤拷锟接碉拷陆锟斤拷锟斤拷锟侥硷拷锟叫伙拷锟�
							SharedPreferences pres = getSharedPreferences("user_msg",
									MODE_WORLD_READABLE);
							String userId = pres.getString("id", "");
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
							makeText(CheckTableActivity.this, "锟斤拷锟侥讹拷锟斤拷锟斤拷锟角ｏ拷"+result, Toast.LENGTH_LONG).show();
			       		
			       		//锟斤拷锟絚hecklist锟斤拷锟斤拷锟铰硷拷锟斤拷tablelist锟斤拷然锟斤拷锟截伙拷gridview
			       		list.clear();
			       		getTableList();
			       		gv.setAdapter(new ImageAdapter(CheckTableActivity.this));
			       		dialog.cancel();
			           }
			       })
			       .setNegativeButton("取锟斤拷", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			 builder.create().show();
		}
		
		
	// 锟剿筹拷系统
	private void quit(){
		CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setMessage("锟斤拷锟斤拷锟揭拷顺锟较低筹拷锟�?");
		builder.setTitle("锟斤拷息锟斤拷示");
		builder.setPositiveButton("确锟斤拷", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				 Intent intent = new Intent();
	        	  intent.setClass(CheckTableActivity.this, LoginActivity.class);
	        	  startActivity(intent);
			}
		});

		builder.setNegativeButton("取锟斤拷",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		builder.create().show();
	}
	
	
}
