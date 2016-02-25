package com.zjk.re;

import android.app.Activity;
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

import com.zjk.util.CustomDialog;
import com.zjk.util.HttpUtil;

public class LoginActivity extends Activity {
	// // 声明登录、取消按钮、服务器地址
	private Button cancelBtn;
	private Button loginBtn;
 	private String  ip;
	// 声明用户名、密码输入框
	private EditText userEditText,pwdEditText,ipEditText;
	private Animation anim;
	private RelativeLayout login_rLayout;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		JPushInterface.setDebugMode(true);
//		JPushInterface.init(this);
//		Context context = getApplicationContext();
//		XGPushManager.registerPush(context);
//
// //2.36（不包括）之前的版本需要调用以下2行代码
//		Intent service = new Intent(context, XGPushService.class);
//		context.startService(service);

		// 设置标题
		setTitle("餐厅");
		// sdk9以上要在方法中访问服务器需要加上这个方法
		if (android.os.Build.VERSION.SDK_INT > 9) {
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	    }
		//设置登陆layout
		setContentView(R.layout.login_system);

		//实例化组件
		//取消按钮，登录按钮，用户名输入框，密码登录框，ip地址输入框
		cancelBtn = (Button)findViewById(R.id.cancelButton);
		loginBtn = (Button)findViewById(R.id.loginButton);
		userEditText = (EditText)findViewById(R.id.userEditText);
		pwdEditText = (EditText)findViewById(R.id.pwdEditText);
		ipEditText = (EditText)findViewById(R.id.ipEditText);
		//设置动画layout
	    anim = AnimationUtils.loadAnimation(LoginActivity.this,R.anim.myanim);
	    //实例化登录界面
	    login_rLayout = (RelativeLayout)findViewById(R.id.login_relativelayout_div);
		//取消按钮，注册监听器
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LoginActivity.this.finish();
			}
		});
		loginBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				//判断用户密码输入是否为空
				if(validate()){
			   //判断ip地址是否改变
				if(ipIsChange()){
			  //访问后台验证用户和密码
					if(login()==1){
//						if(true){
//							if(true){
//								if(true){
						//Intent intent = new Intent(LoginActivity.this,MainActivity.class);
						Intent intent = new Intent(LoginActivity.this,MainActivity.class);
						startActivity(intent);
					}else if(login() == 0){
					   // showAlertDialog sad = new showAlertDialog("用户名称或者密码错误，请重新输入");
						showAlertDialog("用户名称或者密码错误，请重新输入！");
					}else{
						showAlertDialogNetError("网络异常！请重新输入服务器地址");
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
			ip = ipEditText.getText().toString();
			HttpUtil.BASE_URL ="http://"+ip+":8080/WirelessOrder_Server/" ;
			return true;
		}
	}
	// 登录方法
	private int login(){
		// 获得用户名称
		String username = userEditText.getText().toString();
		// 获得密码
		String pwd = pwdEditText.getText().toString();
		// 获得登录结果
		String result=query(username,pwd);
		//判断获得的结果
		//0表示账号密码不对，1表示登录成功，2表示网络错误，
		if(result!=null&&result.equals("0")){
			return 0;
		}
		//网络错误result = error
		else if(result.length() == 5){
				return 2;
			}
		else{
			//登录正常，保存登录信息
			saveUserMsg(result);
			return 1;
		}
	}
	
	// 将用户信息保存到配置文件
	private void saveUserMsg(String msg){
		// 用户编号
		String id = "";
		// 用户名称
		String name = "";
		// 获得信息数组
		//result：id=1；name=admin；
		//字符串截取
		String[] msgs = msg.split(";");
		int idx = msgs[0].indexOf("=");
		id = msgs[0].substring(idx+1);
		idx = msgs[1].indexOf("=");
		name = msgs[1].substring(idx+1);
		// 共享信息
		SharedPreferences pre = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = pre.edit();
		editor.putString("id", id);
		editor.putString("name", name);
		editor.commit();
	}
	
	// 验证方法
	private boolean validate(){
		String username = userEditText.getText().toString();
		if(username.equals("")){
			//如果输入框空会震动提示
			userEditText.startAnimation(anim);
			return false;
		}
		String pwd = pwdEditText.getText().toString();
		if(pwd.equals("")){
			//如果输入框空会振动提示
			pwdEditText.startAnimation(anim);
			return false;
		}
		return true;
	}
	
	//弹出普通提示框方法
	public void showAlertDialog(String msg) {
        //声明对象，设置参数
		CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setMessage(msg);
		builder.setTitle("消息提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		//弹出框创建
		builder.create().show();

	}
	//设置网络错误提示框
	public void showAlertDialogNetError(String msg) {
        //声明对象，并设置参数
		CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setMessage(msg);
		builder.setTitle("网络错误提示");
		builder.setPositiveButton("提示", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//网络错误，改变div布局，增加高度，把ipedittext控件变成可视
				LayoutParams lp = (LayoutParams)login_rLayout.getLayoutParams();
				lp.height = 470;
				login_rLayout.setLayoutParams(lp);
				ipEditText.setVisibility(View.VISIBLE);
				ipEditText.startAnimation(anim);
				dialog.dismiss();
			}
		});
		builder.create().show();

	}
	
	
	//根据用户名称密码查询
	private String query(String account,String password){
		// 查询参数
		String queryString = "account="+account+"&password="+password;
		// url
		String url = HttpUtil.BASE_URL+"servlet/LoginServlet?"+queryString;
		// 查询返回结果
		return HttpUtil.queryStringForPost(url);
    }
}