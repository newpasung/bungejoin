package com.season.bungejoin.bungejoin.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.season.bungejoin.bungejoin.JoinApplication;

/**
 * Created by Administrator on 2015/9/7.
 */
public class SharedPreferManager {
    public String FILENAME_SYSTEMINFO="system_info";
    public String FILENAME_USERINFO="user_info";
    public static SharedPreferManager mSPManager=new SharedPreferManager();
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEditor;
    private static Context mContext;
    //to get a single instance
    public static SharedPreferManager getInstance(Context context){
        mContext=context;
        if(mSPManager==null){
            mSPManager=new SharedPreferManager();
        }
        return mSPManager;
    }

    public SharedPreferManager setSystemManager(){
         mSharedPref= mContext.getSharedPreferences(FILENAME_SYSTEMINFO,Context.MODE_PRIVATE);
        mEditor=mSharedPref.edit();
        return mSPManager;
    }

    public SharedPreferManager setUserInfoManager(){
        mSharedPref =mContext.getSharedPreferences(FILENAME_USERINFO,Context.MODE_PRIVATE);
        mEditor=mSharedPref.edit();
        return mSPManager;
    }

    public int getInt(String key){
        return mSharedPref.getInt(key, 0);
    }

    public int getInt(String key,int defaultvalue){return mSharedPref.getInt(key,defaultvalue);}

    public String getString(String key){
        return mSharedPref.getString(key, "");
    }

    public double getFloat(String key){
        return mSharedPref.getFloat(key, 0);
    }

    public long getLong(String key){
        return mSharedPref.getLong(key, 0);
    }

    public boolean getBoolean(String key){
        return mSharedPref.getBoolean(key,false);
    }

    public void setInt(String key,int value){
        mEditor.putInt(key,value);
        mEditor.commit();
    }

    public void setString(String key,String value){
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public void setLong(String key ,long value){
        mEditor.putLong(key,value);
        mEditor.commit();
    }

    public void setFloat(String key,float value){
        mEditor.putFloat(key,value);
        mEditor.commit();
    }

    public void setBoolean(String key,boolean value){
        mEditor.putBoolean(key,value);
    }

}
