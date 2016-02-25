package com.zjk.util;

import com.zjk.wlo.R;

import android.R.integer;
import android.R.string;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class CustomDialog extends Dialog {

	public CustomDialog(Context context) {
		super(context);
	}

	public CustomDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private String EditText1Hint,EditText2Hint;
		private String text1,text2;
		private EditText editText1,editText2;
		private int et1_visibility_state,et2_visibility_state;
		private TextView messageTextView;
		private View contentView;
		private DialogInterface.OnClickListener positiveButtonClickListener;
		private DialogInterface.OnClickListener negativeButtonClickListener;

		public Builder(Context context) {
			this.context = context;
		}

		public Builder() {
			// TODO Auto-generated constructor stub
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		/**
		 * Set the Dialog message from resource
		 * 
		 * @param title
		 * @return
		 */
		//0-invisible,1-visible,2-gone
		public Builder setEt1Visibility(int state){
			this.et1_visibility_state = state;
			return this;
		}
//		public Builder setEt2Visibility(int state){
//			this.et2_visibility_state = state;
//			return this;
//		}
		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}

		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}
        

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}
		public Builder setEditText1(String text){
			this.EditText1Hint = text;
			return this;
		}
		public Builder setEditText2(String text){
			this.EditText2Hint = text;
			return this;
		}
 		public String getEditText1(){
			text1 = editText1.getText().toString();
			return text1;
		}
		public String getEditText2(){
			text2 = editText2.getText().toString();
			return text2;
		}
		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}
        
		/**
		 * Set the positive button resource and it's listener
		 * 
		 * @param positiveButtonText
		 * @return
		 */
		public Builder setPositiveButton(int positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(int negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(String negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}
        
	
		
		public CustomDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final CustomDialog dialog = new CustomDialog(context,R.style.Dialog);
			View layout = inflater.inflate(R.layout.dialog_layout, null);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			// set the dialog title
			((TextView) layout.findViewById(R.id.title)).setText(title);
			   editText1 = (EditText)layout.findViewById(R.id.changeorder_number);
			   editText1.setHint(EditText1Hint);
			   
			   editText1.setVisibility(View.INVISIBLE);
			   editText2 = (EditText)layout.findViewById(R.id.changetable_number);
			   editText2.setHint(EditText2Hint);
			  
			   editText2.setVisibility(View.INVISIBLE);
			   messageTextView = (TextView)layout.findViewById(R.id.message);
			   
			if(et1_visibility_state == 1){
				editText1.setVisibility(View.VISIBLE);
				editText2.setVisibility(View.VISIBLE);
			}else if(et1_visibility_state == 2){
				editText1.setVisibility(View.GONE);
				editText2.setVisibility(View.GONE);
			}
			else{
				editText1.setVisibility(View.INVISIBLE);
				editText2.setVisibility(View.INVISIBLE);
			}
			// set the confirm button
			if (positiveButtonText != null) {
				((Button) layout.findViewById(R.id.positiveButton))
						.setText(positiveButtonText);
				if (positiveButtonClickListener != null) {
					((Button) layout.findViewById(R.id.positiveButton))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									positiveButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.positiveButton).setVisibility(
						View.GONE);
			}
			//设置取消按钮
			if (negativeButtonText != null) {
				((Button) layout.findViewById(R.id.negativeButton))
						.setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					((Button) layout.findViewById(R.id.negativeButton))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									negativeButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.negativeButton).setVisibility(
						View.GONE);
			}
			
			// set the content message
			if (message != null) {
				messageTextView.setText(message);
			}
			else if (contentView != null) {
				messageTextView.setVisibility(View.INVISIBLE);
				((LinearLayout) layout.findViewById(R.id.content))
						.removeAllViews();
				((LinearLayout) layout.findViewById(R.id.content)).addView(
						contentView, new LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.FILL_PARENT));
			}
			dialog.setContentView(layout);
			return dialog;
		}
	}

}
