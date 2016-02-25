package com.zjk.util;
/**
 * Created by zhongjiakang on 16/1/27.
 */

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.zjk.re.R;


public class AddMealDialog extends Dialog {

    public AddMealDialog(Context context) {
        super(context);
    }

    public AddMealDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        //上下文
        private Context context;
        private String title;
        //确定按钮字符
        private String positiveButtonText;
        //取消按钮字符
        private String negativeButtonText;
       private String spinnerId,spinnerPrice,spinnerName,text1,text2;
        //edittext
        private EditText editText1,editText2;
        //spinner
        private Spinner spinner;
     private SimpleCursorAdapter spinnerAdapter;
        private TextView id_tv,price_tv,name_tv;
        LinearLayout v1;
        int i;
        private TextView messageTextView;
        private View contentView,menuview;
        private int numberType;
        //设置按钮监听事件
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder() {
        }
        //edittext1 get、set方法
        public Builder geteditText1(){
            this.text1 = editText1.getText().toString();
            return  this;
        }
        //edittext2 get、set方法
        public Builder geteditText2(){
            this.text2 = editText2.getText().toString();
            return  this;
        }
        //设置adapter
        public Builder setAdapter(SimpleCursorAdapter adapter){
            this.spinnerAdapter = adapter;
            return this;
        }
        //设置标题
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }
        //获取spinner中选择的view
        public LinearLayout getSelectedItemPosition(){
            this.v1 = (LinearLayout) spinner
                    .getSelectedView();
            return v1;
        }
        //设置下拉框选择项目
        public Builder setgetSelectedItemPosition(int i) {
            this.i = i;
            return this;
        }
       //设置标题
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

//        public String getSprinnerId(){
//
//            return id_tv.getText().toString();
//        }
//        public String getSprinnerPrice(){
////            this.spinnerPrice =  "df";
//            return price_tv.getText().toString();
//        }
//        public String getSprinnerName(){
////            this.spinnerName = "23";
//            return name_tv.getText().toString();
//        }
        public String getEditText1(){
           // this.text1 = editText1.getText().toString();
            return editText1.getText().toString();
        }
        public String getEditText2(){
//            this.text2 = editText2.getText().toString();
            return editText2.getText().toString();
        }


//        public Builder setContentView(View v) {
//            this.contentView = v;
//            return this;
//        }

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



        public AddMealDialog create() {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
             View layout = inflater.inflate(R.layout.addmeal_dialog_layout, null);

            Uri uri = Uri.parse("content://com.zjk.provider.MENUS/menu1");
            // 查询列  // set the dialog title
             spinner = (Spinner) layout.findViewById(R.id.spinner2);

            // spinner.setSelection(1);


            String[] projection = { "_id", "price", "name" };
            // 获得ContentResolver实例
            ContentResolver cr = this.context.getContentResolver();
            // 查询放回游标
            Cursor c = cr.query(uri, projection, null, null, null);
            // 实例化SimpleCursorAdapter
            SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(context,
                    R.layout.spinner_lo, c,
                    new String[] { "_id", "price", "name" }, new int[] {
                    R.id.spinner_id, R.id.spinner_price,
                    R.id.spinner_name, });

            // 为点菜下拉列表Spinner绑定数据
            spinner.setAdapter(adapter2);
            //adapter.notifyDataSetChanged();       //通知spinner刷新数据
           // int index =spinner.getSelectedItemPosition();
            spinner.setSelection(i,true);


            final AddMealDialog dialog = new AddMealDialog(context, R.style.Dialog);

            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

           // spinner.setSelected(0,true);
            // 获得ListView中的自定义视图LinearLayout
             v1 = (LinearLayout) spinner
                    .getSelectedView();
            id_tv = (TextView) v1.findViewById(R.id.spinner_id);
           // String id12 =id_tv.getText().toString();
            // 获得TextView，菜价格
            price_tv = (TextView) v1.findViewById(R.id.spinner_price);
            // 获得TextView，菜名称
            name_tv = (TextView) v1.findViewById(R.id.spinner_name);

            editText1 = (EditText)layout.findViewById(R.id.editText);
            editText2 = (EditText)layout.findViewById(R.id.editText2);
////

            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton1))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.positiveButton1))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                layout.findViewById(R.id.positiveButton1).setVisibility(
                        View.GONE);
            }
            //设置取消按钮
            if (negativeButtonText != null)
                ((Button) layout.findViewById(R.id.negativeButton1))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.negativeButton1))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }

            dialog.setContentView(layout);
            return dialog;
        }
    }

}
