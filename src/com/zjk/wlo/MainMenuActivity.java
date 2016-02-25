package com.zjk.wlo;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

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
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zjk.wlo.R;
import com.zjk.util.CustomDialog;
import com.zjk.util.HttpUtil;

public class MainMenuActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("锟斤拷锟剿碉拷");
        setContentView(R.layout.main_menu);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
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
            return mThumbIds.length;
        }
        // 锟斤拷前锟斤拷锟�
        public Object getItem(int position) {
            return null;
        }
        // 锟斤拷前锟斤拷锟絠d
        public long getItemId(int position) {
            return 0;
        }
        // 锟斤拷玫锟角帮拷锟酵�
        public View getView(int position, View convertView, ViewGroup parent) {
        	// 锟斤拷锟斤拷图片锟斤拷图
            ImageView imageView;
            if (convertView == null) {
            	// 实锟斤拷锟斤拷图片锟斤拷图
                imageView = new ImageView(mContext);
                // 锟斤拷锟斤拷图片锟斤拷图锟斤拷锟斤拷
                imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
                imageView.setPadding(15,15,15,15);
            } else {
                imageView = (ImageView) convertView;
            }
            // 锟斤拷锟斤拷图片锟斤拷图图片锟斤拷源
            imageView.setImageResource(mThumbIds[position]);
            // 为锟斤拷前锟斤拷图锟斤拷蛹锟斤拷锟斤拷锟�
            switch (position) {
			case 0:
				// 锟斤拷拥锟酵硷拷锟斤拷锟斤拷
				imageView.setOnClickListener(orderLinstener);
				break;
			case 1:
				// 锟斤拷锟阶拷锟斤拷锟斤拷锟�
				imageView.setOnClickListener(changeTableLinstener);
				break;
			case 2:
				// 锟斤拷硬锟教拷锟斤拷锟斤拷锟�
				imageView.setOnClickListener(checkTableLinstener);
				break;
			case 3:
				// 锟斤拷痈锟斤拷录锟斤拷锟斤拷锟�
				imageView.setOnClickListener(updateLinstener);
				break;
			case 4:
				// 锟斤拷锟阶拷锟斤拷锟斤拷锟斤拷锟�
				imageView.setOnClickListener(exitLinstener);
				break;
			case 5:
				// 锟斤拷咏锟斤拷锟斤拷锟斤拷锟斤拷
				imageView.setOnClickListener(payLinstener);
				break;
			
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
			default:
			}
            
            return imageView;
        }
        // 图片锟斤拷源锟斤拷锟斤拷
        private Integer[] mThumbIds = {
                R.drawable.diancai,R.drawable.zhuantai, R.drawable.chatai,
                R.drawable.gengxin, R.drawable.zhuxiao, R.drawable.jietai,
                R.drawable.yijian,R.drawable.tuijian,R.drawable.shezhi
        };
    }
    
    // 锟斤拷锟铰硷拷锟斤拷锟斤拷
    OnClickListener updateLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			// 锟斤拷锟斤拷锟斤拷锟斤拷Activity
			intent.setClass(MainMenuActivity.this, UpdateActivity.class);
			startActivity(intent);
		}
	};
    
    // 锟斤拷台锟斤拷锟斤拷锟斤拷
    OnClickListener checkTableLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			// 锟斤拷锟斤拷锟斤拷台Activity
			intent.setClass(MainMenuActivity.this, CheckTableActivity.class);
			startActivity(intent);
		}
	};
    
    // 锟斤拷锟斤拷锟斤拷锟斤拷锟�
    OnClickListener payLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			// 锟斤拷锟斤拷锟斤拷锟斤拷Activity
			intent.setClass(MainMenuActivity.this, PayActivity.class);
			startActivity(intent);
		}
	};
    
    // 锟斤拷锟酵硷拷锟斤拷锟斤拷
    OnClickListener orderLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			// 锟斤拷锟斤拷锟斤拷锟斤拷Activity
			intent.setClass(MainMenuActivity.this, OrderActivity.class);
			startActivity(intent);
		}
	};
	
	// 注锟斤拷锟斤拷锟斤拷锟斤拷
    OnClickListener exitLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			logout();
		}
	};
	
	// 转台锟斤拷锟斤拷锟斤拷
    OnClickListener changeTableLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			changeTable();
		}
	};
	
	
	
	
	// 锟斤拷台系统
	private void changeTable(){
		// 锟斤拷锟絃ayoutInflater实锟斤拷
		LayoutInflater inflater = LayoutInflater.from(this);
		// 锟斤拷锟絃inearLayout锟斤拷图实锟斤拷
		View v =inflater.inflate(R.layout.change_table, null);
		// 锟斤拷LinearLayout锟叫伙拷锟紼ditText实锟斤拷
		final EditText et1 = (EditText) v.findViewById(R.id.change_table_order_number_EditText);
		final EditText et2 = (EditText) v.findViewById(R.id.change_table_no_EditText);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(" 锟斤拷锟揭拷锟斤拷锟轿伙拷锟�")
		       .setCancelable(false)
		       .setView(v)
		       .setPositiveButton("确锟斤拷", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	// 锟斤拷枚锟斤拷锟斤拷锟�
		        	String orderId = et1.getText().toString();
		        	// 锟斤拷锟斤拷锟斤拷锟�
		       		String tableId = et2.getText().toString();
		       		// 锟斤拷询锟斤拷锟斤拷
		       		String queryString = "orderId="+orderId+"&tableId="+tableId;
		       		// url
		       		String url = HttpUtil.BASE_URL+"servlet/ChangeTableServlet?"+queryString;
		       		// 锟斤拷询锟斤拷锟截斤拷锟�
		       		String result = HttpUtil.queryStringForPost(url);
		       		// 锟斤拷示锟斤拷锟�
		       		Toast.makeText(MainMenuActivity.this,result,Toast.LENGTH_LONG).show();
		           }
		       })
		       .setNegativeButton("取锟斤拷", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	
	
	// 锟剿筹拷系统
	private void logout(){
		CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setMessage("锟斤拷锟斤拷锟揭拷顺锟较低筹拷锟�");
		builder.setTitle("锟斤拷息锟斤拷示");
		builder.setPositiveButton("确锟斤拷", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				 Intent intent = new Intent();
	        	  intent.setClass(MainMenuActivity.this, LoginActivity.class);
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