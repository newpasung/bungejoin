package com.season.bungejoin.bungejoin.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.season.bungejoin.bungejoin.R;
import com.season.bungejoin.bungejoin.Utils.HttpHelpers.QiniuHelper;
import com.season.bungejoin.bungejoin.widget.MovingSurfaceView;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/19.
 */
public class WorldFragment extends BaseFragment {
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
        final LinearLayout linearLayout=(LinearLayout)inflater
                .inflate(R.layout.worldpage_fragment,null);
       /* Button button=(Button)linearLayout.findViewById(R.id.btn_qiniutest);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这个从文库获取一张图片
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
            }
        });*/
        return linearLayout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            Uri imageuri=data.getData();
            String [] column={MediaStore.Images.Media.DATA};
            Cursor cursor=getContext().getContentResolver().query(imageuri, column, null, null, null);
            cursor.moveToFirst();
            String filePath=cursor.getString(cursor.getColumnIndex(column[0]));
            try {
                FileInputStream inputStream=new FileInputStream(filePath);
                byte []bytedata=new byte[inputStream.available()];
                inputStream.read(bytedata);
                inputStream.close();
                ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
                Bitmap bm=BitmapFactory.decodeByteArray(bytedata,0,bytedata.length);
                if(bytedata.length>1024*1024){
                    bm.compress(Bitmap.CompressFormat.JPEG,50,outputStream);
                }
                ByteArrayInputStream byteinput=new ByteArrayInputStream(outputStream.toByteArray());
                BitmapFactory.Options options=new BitmapFactory.Options();
                options.inJustDecodeBounds=true;
                bm=BitmapFactory.decodeStream(byteinput,null,options);
                options.inJustDecodeBounds=false;
                int width=options.outWidth;
                int height=options.outHeight;
                float commonH=800f;
                float commonW=480f;
                int scale=1;
                if(width>height&&width>commonW){
                    scale=(int)(width/commonW);
                }
                else{
                    scale=(int)(height/commonH);
                }
                if(scale<1)scale=1;
                options.inPreferredConfig= Bitmap.Config.RGB_565;
                options.inSampleSize=scale;
                byteinput=new ByteArrayInputStream(outputStream.toByteArray());
                bm=BitmapFactory.decodeStream(byteinput,null,options);
                bm.compress(Bitmap.CompressFormat.PNG,100,outputStream);
                bm.recycle();
                byteinput.close();
                cursor.close();
                QiniuHelper.uploadFile(getActivity(), QiniuHelper.FILETYPE_AVATAR, outputStream.toByteArray(), System.currentTimeMillis() + ".jpg",
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {
                                Toast.makeText(getContext(),
                                        "success upload", Toast.LENGTH_SHORT).show();
                            }
                        });
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (OutOfMemoryError e){
                e.printStackTrace();
            }
        }
    }

    public static WorldFragment newInstance(){
        WorldFragment fragment =new WorldFragment();
        return fragment;
    }

}
