package com.season.bungejoin.bungejoin.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.season.bungejoin.bungejoin.R;

/**
 * Created by Administrator on 2015/9/24.
 */
public class InputDialog extends Dialog {
    EditText mEtinput;
    TextView mTvtitle;
    Button mBtnensure;
    Button mBtncancel;
    Listener listener;
    String TITLE="";
    public InputDialog(Context context) {
        super(context);
    }

    public InputDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public InputDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputdialog_layout);
        iniUI();
        iniListener();
    }

    protected void iniUI(){
        mEtinput=(EditText)findViewById(R.id.edittext);
        mTvtitle=(TextView)findViewById(R.id.tv_title);
        mBtnensure=(Button)findViewById(R.id.btn_ensure);
        mBtncancel=(Button)findViewById(R.id.btn_cancel);
        mTvtitle.setText(TITLE);
    }

    protected void iniListener(){
        mBtnensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDismiss(mEtinput.getText().toString());
                dismiss();
            }
        });

        mBtncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public interface Listener{
        void onDismiss(String text);
    }

    public void show(Listener listener,String title){
        this.listener=listener;
        show();
        setmTitle(title);
    }

    public void setmTitle(String title){
        mTvtitle.setText(title);
    }

}
