package com.season.bungejoin.bungejoin.utils.HttpHelpers;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;
import com.season.bungejoin.bungejoin.JoinApplication;

import org.apache.http.Header;

/**
 * Created by Administrator on 2015/9/8.
 */
public class HttpClient {

    public static String IPOBUNGEJOIN="http://bungejoin.sinaapp.com";
    static AsyncHttpClient client =new AsyncHttpClient();
    static SyncHttpClient sClient=new SyncHttpClient();
    public static void get(Context context,String url,RequestParams params,JsonResponseHandler handler){
        if(params==null){
            params=new RequestParams();
        }
        params.put("r",url);
        String finalurl=getIPUrl();
        params.put("token", JoinApplication.getInstance().getToken(context));
        params.put("userid", JoinApplication.getInstance().getUid(context));
        client.get(finalurl,params,handler);
    }

    public static void post(Context context,String url,RequestParams params ,JsonResponseHandler handler){
        if(params==null){
            params=new RequestParams();
        }
        params.put("r",url);
        String finalurl=getIPUrl();
        params.put("token", JoinApplication.getInstance().getToken(context));
        params.put("userid",JoinApplication.getInstance().getUid(context));
        client.post(finalurl, params, handler);
    }

    public static String getIPUrl(){
        return IPOBUNGEJOIN;
    }

    public static void syncPost(Context context,String url,RequestParams params ,JsonResponseHandler handler){
        if(params==null){
            params=new RequestParams();
        }
        params.put("r",url);
        String finalurl=getIPUrl();
        params.put("token", JoinApplication.getInstance().getToken(context));
        params.put("userid",JoinApplication.getInstance().getUid(context));
        sClient.post(finalurl, params, handler);
    }

    public static void getByte(Context context,String url,AsyncHttpResponseHandler handler){
        client.get(context, url,handler);
    }

}
