package com.season.bungejoin.bungejoin.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.season.bungejoin.bungejoin.utils.CodeHelper;

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
    protected void onStart() {
        super.onStart();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CodeHelper.logCurretMethod(getClass().getName());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        CodeHelper.logCurretMethod(getClass().getName());
    }

    public void toast(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

}
