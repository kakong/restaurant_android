package com.zjk.wlo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.zjk.wlo.R;
import com.zjk.util.CustomDialog;
import com.zjk.util.HttpUtil;

public class LoginActivity extends Activity {
	// 锟斤拷锟斤拷锟斤拷录锟斤拷取锟斤拷锟斤拷钮
	private Button cancelBtn;
	private Button loginBtn;
 	private String  ip;
 	//private CustomDialog cd;
	// 锟斤拷锟斤拷锟矫伙拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
	private EditText userEditText,pwdEditText,ipEditText;
	private Animation anim;
	private RelativeLayout login_rLayout;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 锟斤拷锟矫憋拷锟斤拷
		setTitle("锟矫伙拷锟斤拷录");
		// 锟斤拷锟矫碉拷前Activity锟斤拷锟芥布锟斤拷
		if (android.os.Build.VERSION.SDK_INT > 9) {
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	    }
		setContentView(R.layout.login_system);
		// 通锟斤拷findViewById锟斤拷锟斤拷实锟斤拷锟斤拷锟斤拷锟�
		cancelBtn = (Button)findViewById(R.id.cancelButton);
		// 通锟斤拷findViewById锟斤拷锟斤拷实锟斤拷锟斤拷锟斤拷锟�
		loginBtn = (Button)findViewById(R.id.loginButton);
		// 通锟斤拷findViewById锟斤拷锟斤拷实锟斤拷锟斤拷锟斤拷锟�
		userEditText = (EditText)findViewById(R.id.userEditText);
		// 通锟斤拷findViewById锟斤拷锟斤拷实锟斤拷锟斤拷锟斤拷锟�
		pwdEditText = (EditText)findViewById(R.id.pwdEditText);
		// 通锟斤拷findViewById锟斤拷锟斤拷实锟斤拷锟斤拷锟斤拷锟�
		ipEditText = (EditText)findViewById(R.id.ipEditText);
		//锟今动凤拷锟斤拷实锟斤拷锟斤拷
	    anim = AnimationUtils.loadAnimation(LoginActivity.this,R.anim.myanim);
	    //
	    login_rLayout = (RelativeLayout)findViewById(R.id.login_relativelayout_div);
		
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				//userEditText.startAnimation(anim);
			}
		});
		
		loginBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
//				if(validate()){
//				if(ipIsChange()){
//					if(login()==1){
						if(true){
							if(true){
								if(true){
						//Intent intent = new Intent(LoginActivity.this,CheckTableActivity.class);
						Intent intent = new Intent(LoginActivity.this,MainMenuActivity.class);
						startActivity(intent);
					}else if(login() == 0){
					   // showAlertDialog sad = new showAlertDialog("锟矫伙拷锟斤拷锟狡伙拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷耄�");
						showAlertDialog("锟矫伙拷锟斤拷锟狡伙拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷耄�");
					}else{
						showAlertDialogNetError("锟斤拷锟斤拷锟届常!锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟街凤拷锟�");
					}
				}
			}
			}
		});
	}
	
	private boolean ipIsChange(){
		if(ipEditText.getText().toString().equals("")){
			return true;
		}
		else{
			//public static final String BASE_URL="http://10.251.83.139:8080/WirelessOrder_Server/";
			ip = ipEditText.getText().toString();
			HttpUtil.BASE_URL ="http://"+ip+":8080/WirelessOrder_Server/" ;
			return true;
		}
	}
	// 锟斤拷录锟斤拷锟斤拷
	private int login(){
		// 锟斤拷锟斤拷没锟斤拷锟斤拷锟�
		String username = userEditText.getText().toString();
		// 锟斤拷锟斤拷锟斤拷锟�
		String pwd = pwdEditText.getText().toString();
		// 锟斤拷玫锟铰硷拷锟斤拷
		String result=query(username,pwd);
//		if(result!=null&&result.equals("0")){
//			return false;
//		}else{
//			saveUserMsg(result);
//			return true;
//		}
		if(result!=null&&result.equals("0")){
			return 0;
		}
		else if(result.length() == 5){
				return 2;
			}
		else{
			saveUserMsg(result);
			return 1;
		}
	}
	
	// 锟斤拷锟矫伙拷锟斤拷息锟斤拷锟芥到锟斤拷锟斤拷锟侥硷拷
	private void saveUserMsg(String msg){
		// 锟矫伙拷锟斤拷锟�
		String id = "";
		// 锟矫伙拷锟斤拷锟斤拷
		String name = "";
		// 锟斤拷锟斤拷锟较拷锟斤拷锟�
		String[] msgs = msg.split(";");
		int idx = msgs[0].indexOf("=");
		id = msgs[0].substring(idx+1);
		idx = msgs[1].indexOf("=");
		name = msgs[1].substring(idx+1);
		// 锟斤拷锟斤拷锟斤拷息
		SharedPreferences pre = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = pre.edit();
		editor.putString("id", id);
		editor.putString("name", name);
		editor.commit();
	}
	
	// 锟斤拷证锟斤拷锟斤拷
	private boolean validate(){
		String username = userEditText.getText().toString();
		if(username.equals("")){
			//showDialog("锟矫伙拷锟斤拷锟斤拷锟角憋拷锟斤拷锟筋！");
			//Animation anim = AnimationUtils.loadAnimation(LoginActivity.this,R.id.userEditText);
			//实锟斤拷锟斤拷锟斤拷示效锟斤拷
			 userEditText.startAnimation(anim);
			//showAlertDialog("锟矫伙拷锟斤拷锟斤拷锟角憋拷锟斤拷锟筋！");
			return false;
		}
		String pwd = pwdEditText.getText().toString();
		if(pwd.equals("")){
			pwdEditText.startAnimation(anim);
		//  Animation anim = AnimationUtils.loadAnimation(this,R.id.pwdEditText);			
		//  showDialog("锟矫伙拷锟斤拷锟斤拷锟角憋拷锟斤拷锟筋！");
		//	showAlertDialog("锟矫伙拷锟斤拷锟斤拷锟角憋拷锟斤拷锟筋！");
			return false;
		}
		return true;
	}
	
	//锟斤拷锟斤拷锟斤拷息锟津，达拷锟斤拷锟斤拷息锟斤拷锟斤拷
	public void showAlertDialog(String msg) {

		CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setMessage(msg);
		builder.setTitle("锟斤拷息锟斤拷示");
		builder.setPositiveButton("确锟斤拷", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.setNegativeButton("取锟斤拷",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();

	}
	//
	public void showAlertDialogNetError(String msg) {

		CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setMessage(msg);
		builder.setTitle("锟斤拷息锟斤拷示");
		builder.setPositiveButton("确锟斤拷", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
//				LayoutParams param = new RelativeLayout.LayoutParams(
//                        200,
//                        150);
				LayoutParams lp = (LayoutParams)login_rLayout.getLayoutParams();
				lp.height = 470;
				//lp.width = 
//				//lp
//				int i = login_rLayout.getHeight();
				login_rLayout.setLayoutParams(lp);
//				int j = login_rLayout.getLayoutParams().height;
//				login_rLayout.setLayoutParams(lp);
//				
				ipEditText.setVisibility(View.VISIBLE);
				ipEditText.startAnimation(anim);
				dialog.dismiss();
			}
		});
		builder.create().show();

	}
	
	
	// 锟斤拷锟斤拷锟矫伙拷锟斤拷锟斤拷锟斤拷锟斤拷锟窖�
	private String query(String account,String password){
		// 锟斤拷询锟斤拷锟斤拷
		String queryString = "account="+account+"&password="+password;
		// url
		String url = HttpUtil.BASE_URL+"servlet/LoginServlet?"+queryString;
		// 锟斤拷询锟斤拷锟截斤拷锟�
		return HttpUtil.queryStringForPost(url);
    }
}