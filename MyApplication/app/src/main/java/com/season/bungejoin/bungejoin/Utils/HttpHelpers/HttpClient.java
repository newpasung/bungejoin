package com.season.bungejoin.bungejoin.Utils.HttpHelpers;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.season.bungejoin.bungejoin.JoinApplication;

/**
 * Created by Administrator on 2015/9/8.
 */
public class HttpClient {

    public static String IPOBUNGEJOIN="http://bungejoin.sinaapp.com";
    static AsyncHttpClient client =new AsyncHttpClient();

    public static void get(String url,RequestParams params,JsonResponseHandler handler){
        client.get(url,params,handler);
    }

    public static void post(Context context,String url,RequestParams params ,JsonResponseHandler handler){
        if(params==null){
            params=new RequestParams();
        }
        params.put("r",url);
        String finalurl=getIPUrl();
        params.put("token", JoinApplication.getInstance().getToken(context));
        params.put("userid",JoinApplication.getInstance().getUid(context));
        client.post(finalurl,params,handler);
    }

    public static String getIPUrl(){
        return IPOBUNGEJOIN;
    }

}
