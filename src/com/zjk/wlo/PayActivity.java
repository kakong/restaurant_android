package com.zjk.wlo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zjk.wlo.R;
import com.zjk.util.HttpUtil;

public class PayActivity extends Activity{
	// 锟斤拷示锟斤拷锟斤拷锟较ebView
	private WebView wv;
	// 锟斤拷询锟斤拷锟斤拷锟较拷锟脚ワ拷徒锟斤拷惆磁�
	private Button queryBtn,payBtn;
	// 锟斤拷锟斤拷锟斤拷锟�
	private EditText orderIdEt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 锟斤拷锟矫碉拷前Activity锟侥斤拷锟芥布锟斤拷
		setContentView(R.layout.pay);
		// 锟斤拷锟絎ebView实锟斤拷
		wv = (WebView)findViewById(R.id.pay_webview);
		// 实锟斤拷锟斤拷锟斤拷询锟斤拷钮
		queryBtn = (Button)findViewById(R.id.pay_query_Button01);
		// 实锟斤拷锟斤拷锟斤拷锟姐按钮
		payBtn = (Button)findViewById(R.id.pay_Button01);
		// 实锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷疟嗉拷锟�
		orderIdEt = (EditText)findViewById(R.id.pay_order_number_EditText01);
		
		// 锟斤拷硬锟窖拷锟斤拷锟斤拷息锟斤拷锟斤拷锟斤拷
		queryBtn.setOnClickListener(queryListener);
		// 锟斤拷咏锟斤拷锟斤拷锟较拷锟斤拷锟斤拷锟�
		payBtn.setOnClickListener(payListener);
	}
	

	// 锟斤拷询锟斤拷锟斤拷锟较拷锟斤拷锟斤拷锟�
	OnClickListener queryListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// 锟斤拷枚锟斤拷锟斤拷锟斤拷
			String orderId = orderIdEt.getText().toString();
			// 锟斤拷锟斤拷锟斤拷锟斤拷锟絬rl
			String url = HttpUtil.BASE_URL+"servlet/PayServlet?id="+orderId;
			// 锟斤拷锟斤拷锟斤拷锟斤拷息锟斤拷WebView锟斤拷锟斤拷示
			wv.loadUrl(url);
		}
	};
	
	// 锟斤拷锟斤拷锟斤拷锟斤拷锟�
	OnClickListener payListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// 锟斤拷枚锟斤拷锟斤拷锟斤拷
			String orderId = orderIdEt.getText().toString();
			// 锟斤拷锟斤拷锟斤拷锟斤拷锟絬rl
			String url = HttpUtil.BASE_URL+"servlet/PayMoneyServlet?id="+orderId;
			// 锟斤拷貌锟窖拷锟斤拷
			String result = HttpUtil.queryStringForPost(url);
			// 锟斤拷示锟斤拷锟斤拷锟斤拷
			Toast.makeText(PayActivity.this, result, Toast.LENGTH_LONG).show();
			// 使锟斤拷锟姐按钮失效
			payBtn.setEnabled(false);
		}
	};

}
