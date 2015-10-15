package com.season.bungejoin.bungejoin.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.season.bungejoin.bungejoin.model.User;
import com.season.bungejoin.bungejoin.utils.HttpHelpers.QiniuHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Administrator on 2015/9/26.
 */
public class PicUploadExecutor extends AsyncTask<String,Integer,Nullable> {

    Context  mContext;
    public static final int UPLOAD_CATAGORY_AVATAR=QiniuHelper.FILETYPE_AVATAR;
    public static final int UPLOAD_CATAGORY_COVER=QiniuHelper.FILETYPE_COVER;
    int catagory;
    int SCALE_STANDAR=200;
    public PicUploadExecutor(Context context, int catagory) {
        super();
        mContext=context;
        this.catagory=catagory;
    }

    @Override
    protected Nullable doInBackground(String... params) {
        byte[] bytedata;
        try {
            BitmapFactory.Options options =new BitmapFactory.Options();
            options.inJustDecodeBounds=true;
            Bitmap bitmap =BitmapFactory.decodeFile(params[0],options);
            options.inJustDecodeBounds=false;
            int scale;
            if(options.outHeight<=options.outWidth){
                scale =(options.outHeight)/SCALE_STANDAR;
            }else{
                scale=(options.outWidth)/SCALE_STANDAR;
            }
            options.inSampleSize=scale<1?1:scale;
            bitmap=BitmapFactory.decodeFile(params[0],options);
            ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            QiniuHelper.uploadFile(mContext, catagory, outputStream.toByteArray(), System.currentTimeMillis() + ".jpg",
                    new UpCompletionHandler() {
                        @Override
                        public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {
                            try {
                                JSONObject data=jsonObject.getJSONObject("data");
                                String url=data.getString("image");
                                if(catagory==UPLOAD_CATAGORY_AVATAR){
                                    User.modify_avatar(mContext,url);
                                }
                                if(catagory==UPLOAD_CATAGORY_COVER){
                                    User.modify_cover(mContext,url);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
        }  catch (OutOfMemoryError e){
            e.printStackTrace();
        }
        return null;
    }

}