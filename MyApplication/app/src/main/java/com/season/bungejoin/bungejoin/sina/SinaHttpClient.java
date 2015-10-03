package com.season.bungejoin.bungejoin.sina;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.season.bungejoin.bungejoin.JoinApplication;
import com.season.bungejoin.bungejoin.utils.HttpHelpers.HttpClient;
import com.season.bungejoin.bungejoin.utils.HttpHelpers.JsonResponseHandler;

/**
 * Created by Administrator on 2015/10/1.
 */
public class SinaHttpClient {

    public static AsyncHttpClient client =new AsyncHttpClient();
    public static SyncHttpClient sClient =new SyncHttpClient();
    public static final String WEIBOURL="https://api.weibo.com/2/";

    public static void get(Context context,String url,RequestParams params,SinaResHandler handler){
        if(params==null){
            params=new RequestParams();
        }
        params.put("access_token",SinaHelper.getToken(context));
        client.get(WEIBOURL+url,params,handler);
    }

    public static void syncget(Context context,String url,RequestParams params,SinaResHandler handler){
        if(params==null){
            params=new RequestParams();
        }
        params.put("access_token",SinaHelper.getToken(context));
        sClient.get(WEIBOURL+url,params,handler);
    }

    public static void getLastestFrn(Context context,SinaResHandler handler){
        RequestParams params =new RequestParams();
        params.put("count",10);//默认获取十条0
        syncget(context, "statuses/friends_timeline.json", params, handler);
    }

    public static void loadmoreFrn(Context context ,long lastid ,SinaResHandler handler){
        RequestParams params =new RequestParams();
        params.put("count",10);
        params.put("max_id",lastid-1);
        syncget(context,"statuses/friends_timeline.json",params,handler);
    }

}
