package com.season.bungejoin.bungejoin.Utils.HttpHelpers;

import android.content.Context;

import com.loopj.android.http.RequestParams;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.season.bungejoin.bungejoin.Constant.HttpParameter;
import com.season.bungejoin.bungejoin.JoinApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Administrator on 2015/9/21.
 */
public class QiniuHelper {

    public static final int FILETYPE_DEFAULT=0;
    public static final int FILETYPE_AVATAR=1;

    public static void uploadFile(Context mContext,int filetype,final byte[] file, final String filename,final UpCompletionHandler handler){
        final UploadManager manager=new UploadManager();
        final String name ;
        switch (filetype){
            case FILETYPE_AVATAR :{name="image/avatar"+filename;}break;
            default:{name=filename;}
        }
        RequestParams params =new RequestParams();
        params.put("catagory",filetype);
        HttpClient.post(mContext,"qiniu/token",params,new JsonResponseHandler(mContext){
            @Override
            public void onSuccess(JSONObject object) {
                super.onSuccess(object);
                try {
                    JSONObject data=object.getJSONObject("data");
                    String token=data.getString(HttpParameter.QINIUTOKEN);
                    manager.put(file,name,token,handler,null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
