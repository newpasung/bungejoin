package com.season.bungejoin.bungejoin.storage;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.season.bungejoin.bungejoin.JoinApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Administrator on 2015/10/4.
 */
public class FileStorage {
    public static final String  FOLDER_SINA_GIF="/sinagif";
    public static final String FOLDER_IMAGE="/bungejoin_img";

    public static byte[] getSinaGif(Context context,String url){
        byte [] result =null;
        int lastcharindex = url.lastIndexOf("/");
        String name = url.substring(lastcharindex+1,url.length());
        String path =context.getCacheDir().getPath()+FOLDER_SINA_GIF+"/"+name;
        try {
            File file =new File(path);
            int length=(int)file.length();
            if(file.exists()){
                result =new byte[length];
                FileInputStream inputStream =new FileInputStream(file);
                inputStream.read(result);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void saveSinaGif(Context context,byte [] data ,String url){
        int lastcharindex = url.lastIndexOf("/");
        String name = url.substring(lastcharindex + 1, url.length());
        String path =context.getCacheDir().getPath()+FOLDER_SINA_GIF+"/"+name;
        ensureSinaGifFolder(context);
        File file =new File(path);
        if(!file.exists()){
            try {
                file.createNewFile();
                FileOutputStream outputStream =new FileOutputStream(file);
                outputStream.write(data);
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void ensureSinaGifFolder(Context context){
        String folder = context.getCacheDir()+FOLDER_SINA_GIF;
        File file = new File(folder);
        if(!file.exists()){
                file.mkdir();
        }
    }

    public static void saveImage(Context context ,byte [] data){
        String path=null;
        boolean permission = JoinApplication.getInstance().getExWritePermission(context);
        /*PackageManager manager=context.getPackageManager();
        boolean permission =(PackageManager.PERMISSION_GRANTED==manager
                .checkPermission("android.permission.WRITE_EXTERNAL_STORAGE",
                        "com.season.bungejoin.bungejoin"));*/
        if(!permission&&Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            path = Environment.getExternalStorageDirectory()+FOLDER_IMAGE;
            File file =new File(path);
            if(!file.exists()){
                if(!file.mkdirs()){
                    if(file.isDirectory()){
                        Log.i("","");
                    }
                }
            }
        }
        else{
            path = context.getFilesDir()+FOLDER_IMAGE;
            File file =new File(path);
            if(!file.exists()){
                if(!file.mkdirs()){
                    if(file.isDirectory()){
                        Log.i("","");
                    }
                }
            }
        }
        path = path+"/"+System.currentTimeMillis()+".png";
        try {
            File file =new File(path);
            file.createNewFile();
            FileOutputStream outputStream =new FileOutputStream(file);
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
