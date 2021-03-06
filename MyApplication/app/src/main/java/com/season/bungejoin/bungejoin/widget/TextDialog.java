package com.season.bungejoin.bungejoin.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.season.bungejoin.bungejoin.R;

/**
 * Created by Administrator on 2015/9/20.
 */
public class TextDialog extends Dialog {
    ImageView imageView;
    TextView textView;
    public TextDialog(Context context) {
        super(context,R.style.textdialog);
    }

    public TextDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textdialog_layout);
        imageView=(ImageView)findViewById(R.id.image);
        textView=(TextView)findViewById(R.id.tv_text);
        RotateAnimation animation=new RotateAnimation(0,359,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(1500);
        animation.setRepeatCount(Animation.INFINITE);
        imageView.startAnimation(animation);
    }

    public void setTextView(String text){
        textView.setText(text);
    }
}
