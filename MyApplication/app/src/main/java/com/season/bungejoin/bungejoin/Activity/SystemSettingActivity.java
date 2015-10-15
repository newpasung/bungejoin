package com.season.bungejoin.bungejoin.activity;

import android.os.Bundle;

import com.season.bungejoin.bungejoin.R;
import com.season.bungejoin.bungejoin.libs.me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by Administrator on 2015/9/24.
 */
public class SystemSettingActivity extends SwipeBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.systemsetting_layout);
    }
}
