package com.season.bungejoin.bungejoin.Utils;

import android.util.Log;

/**
 * Created by Administrator on 2015/9/7.
 */
public class CodeHelper {
    public static void logCurretMethod(String className){
        Log.i(className,Thread.currentThread().getStackTrace()[3].getMethodName());
    }



}
