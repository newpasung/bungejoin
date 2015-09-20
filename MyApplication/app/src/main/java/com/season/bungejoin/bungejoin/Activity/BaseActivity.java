package com.season.bungejoin.bungejoin.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.season.bungejoin.bungejoin.Utils.CodeHelper;

/**
 * Created by Administrator on 2015/9/7.
 */
public class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CodeHelper.logCurretMethod(getClass().getName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        CodeHelper.logCurretMethod(getClass().getName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        CodeHelper.logCurretMethod(getClass().getName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        CodeHelper.logCurretMethod(getClass().getName());
    }

    public void toast(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}
