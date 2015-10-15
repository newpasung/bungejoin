package com.season.bungejoin.bungejoin;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.activeandroid.ActiveAndroid;
import com.season.bungejoin.bungejoin.Constant.SharedPrefParameter;
import com.season.bungejoin.bungejoin.storage.SharedPreferManager;

/**
 * Created by Administrator on 2015/9/7.
 */
public class JoinApplication extends Application {
    public static JoinApplication mJoinApp;
    public static String token = "";
    public static int uid = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    public JoinApplication() {
    }

    public static JoinApplication getInstance() {
        if (mJoinApp == null) {
            mJoinApp = new JoinApplication();
        }
        return mJoinApp;
    }

    public String getToken(Context context) {
        if (token.equals("")) {
            token = SharedPreferManager.getInstance(context).setUserInfoManager()
                    .getString(SharedPrefParameter.TOKEN);
        }
        return token;
    }

    public void setToken(String tok,Context context) {
        if (!TextUtils.isEmpty(tok)) {
            SharedPreferManager.getInstance( context).setUserInfoManager().
                    setString(SharedPrefParameter.TOKEN, tok);
            token = tok;
        }
    }

    public void setUid(int id,Context context) {
        SharedPreferManager.getInstance(context).setUserInfoManager().setInt(SharedPrefParameter.CURUID, id);
        uid = id;
    }

    public int getUid(Context context) {
        if (uid == 0) {
            uid = SharedPreferManager.getInstance(context).setUserInfoManager().getInt(SharedPrefParameter.CURUID);
        }
        return uid;
    }

    public boolean getExWritePermission(Context context){
        PackageManager manager=context.getPackageManager();
        boolean permission =(PackageManager.PERMISSION_GRANTED==manager
                .checkPermission("android.permission.WRITE_EXTERNAL_STORAGE",
                        "com.season.bungejoin.bungejoin"));
        return permission;
    }

}
