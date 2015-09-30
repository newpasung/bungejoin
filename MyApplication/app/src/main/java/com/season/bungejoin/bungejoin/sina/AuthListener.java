package com.season.bungejoin.bungejoin.sina;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * Created by Administrator on 2015/9/30.
 */
public class AuthListener implements WeiboAuthListener {
    Oauth2AccessToken tokenData;
    @Override
    public void onCancel() {

    }

    @Override
    public void onComplete(Bundle bundle) {
        tokenData=Oauth2AccessToken.parseAccessToken(bundle);
        if(tokenData.isSessionValid())
        onComplete(tokenData);
        else return;
    }

    @Override
    public void onWeiboException(WeiboException e) {
        e.printStackTrace();
    }

    public void onComplete( Oauth2AccessToken tokendata){

    }
}
