package com.season.bungejoin.bungejoin.sina;

import android.content.Context;
import android.os.Bundle;

import com.season.bungejoin.bungejoin.storage.SharedPreferManager;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * Created by Administrator on 2015/10/1.
 */
public class SinaHelper {

    public static void saveTokenData(Oauth2AccessToken data,Context context){
        SharedPreferManager.getInstance(context).setSinaInfoManager()
                .setString(SinaParams.SINATOKEN,data.getToken())
                .setString(SinaParams.SINAREFRESHTOKEN,data.getRefreshToken())
                .setString(SinaParams.SINAPHONENUM,data.getPhoneNum())
                .setString(SinaParams.SINAPHONENUM,data.getPhoneNum())
                .setLong(SinaParams.SINAEXPIREIN, data.getExpiresTime());
    }

    public static String getToken(Context context){
        return SharedPreferManager.getInstance(context).setSinaInfoManager()
                .getString(SinaParams.SINATOKEN);
    }

    public static long getExpire(Context context){
        return SharedPreferManager.getInstance(context).setSinaInfoManager()
                .getLong(SinaParams.SINAEXPIREIN);
    }

}
