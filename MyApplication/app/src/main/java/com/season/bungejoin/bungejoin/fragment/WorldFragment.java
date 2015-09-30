package com.season.bungejoin.bungejoin.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.season.bungejoin.bungejoin.R;
import com.season.bungejoin.bungejoin.sina.AuthListener;
import com.season.bungejoin.bungejoin.sina.SinaHelper;
import com.season.bungejoin.bungejoin.sina.SinaParams;
import com.season.bungejoin.bungejoin.utils.HttpHelpers.QiniuHelper;
import com.season.bungejoin.bungejoin.widget.CircleImageView;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Administrator on 2015/9/19.
 */
public class WorldFragment extends BaseFragment {
    CircleImageView mCirweibo;
    AuthInfo mWeiboAuth;
    SsoHandler mWeiboSso;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         LinearLayout linearLayout=(LinearLayout)inflater
                .inflate(R.layout.worldpage_fragment,null);
        mCirweibo=(CircleImageView)linearLayout.findViewById(R.id.btn_weibo);
        initListener();
        return linearLayout;
    }

    protected void initListener(){
        mCirweibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWeibo();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mWeiboSso!=null){
            mWeiboSso.authorizeCallBack(requestCode,resultCode,data);
        }
    }

    public static WorldFragment newInstance(){
        WorldFragment fragment =new WorldFragment();
        return fragment;
    }

    protected void loginWeibo(){
        mWeiboAuth=new AuthInfo(getActivity(), SinaParams.APP_KEY,SinaParams.CALLBACK_RUL,SinaParams.SCOPE);
        mWeiboSso=new SsoHandler(getActivity(),mWeiboAuth);
        mWeiboSso.authorize(new AuthListener(){
            @Override
            public void onComplete(Oauth2AccessToken tokendata) {
                super.onComplete(tokendata);
                SinaHelper.saveTokenData(tokendata, getContext());
            }
        });
    }

}
