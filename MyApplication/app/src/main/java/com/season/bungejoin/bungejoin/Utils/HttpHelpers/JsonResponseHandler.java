package com.season.bungejoin.bungejoin.Utils.HttpHelpers;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/9/8.
 */
public class JsonResponseHandler extends JsonHttpResponseHandler {
    @Override
    public void onFailure(int statusCode, org.apache.http.Header[] headers, String responseString, Throwable throwable) {
        super.onFailure(statusCode, headers, responseString, throwable);
    }

    @Override
    public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, JSONArray errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
    }

    @Override
    public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
    }

    @Override
    public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONArray response) {
        super.onSuccess(statusCode, headers, response);
    }

    @Override
    public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONObject response) {
        try {
            if(response.getInt("status")==1){
                onSuccess(response);
            }
            else{
                onFailure();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        onSuccess();
    }

    @Override
    public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString) {
        super.onSuccess(statusCode, headers, responseString);
    }

    public void onSuccess(JSONObject object){
        onSuccess();
    }

    public void onSuccess(){
        Log.i("HttpClinet", "onSuccess");
    }

    public void onFailure(){
        Log.i("HttpClient","onFailure");
    }

}
