package com.season.bungejoin.bungejoin.activity;
import android.content.Intent;
import android.os.Bundle;

import com.season.bungejoin.bungejoin.Constant.SharedPrefParameter;
import com.season.bungejoin.bungejoin.storage.SharedPreferManager;

public class LeadActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lead();
    }

    private void lead(){
        int loginCounter;
        loginCounter=SharedPreferManager.getInstance(LeadActivity.this).setSystemManager().getInt(SharedPrefParameter.ISFIRSTLOGING);
        if(loginCounter==0){
            startActivity(new Intent(this,WelcomeActivity.class));
            finish();
        }else{
            startActivity(new Intent(this,HomePageActivity.class));
            finish();
        }
    }
}
