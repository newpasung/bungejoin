package com.season.bungejoin.bungejoin.activity;

import android.graphics.Bitmap;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.View;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.season.bungejoin.bungejoin.R;
import com.season.bungejoin.bungejoin.libs.me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import com.season.bungejoin.bungejoin.storage.FileStorage;
import com.season.bungejoin.bungejoin.utils.HttpHelpers.HttpClient;
import com.season.bungejoin.bungejoin.utils.HttpHelpers.ImageLoaderHelper;
import com.season.bungejoin.bungejoin.widget.GifView;
import com.season.bungejoin.bungejoin.widget.TextDialog;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2015/10/4.
 */
public class GifActivity extends SwipeBackActivity {
    final String INTENT_PARAMETER_PICCOUNT = "piccount";
    final String INTENT_PARAMETER_PICURL = "picurl";
    GifView mGifView;
    int piccount = 0;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gif_layout);
        mGifView = (GifView) findViewById(R.id.gifview);
        if (getIntent() != null) {
            piccount = getIntent().getIntExtra(INTENT_PARAMETER_PICCOUNT, 0);
            url = getIntent().getStringExtra(INTENT_PARAMETER_PICURL);
        }
        if(url!=null){
            byte [] data ;
            if((data=FileStorage.getSinaGif(this,url))!=null){
                mGifView.setMovie(Movie.decodeByteArray(data,0,data.length));
            }else{
                HttpClient.getByte(this, url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        mGifView.setMovie(Movie.decodeByteArray(bytes,0,bytes.length));
                        FileStorage.saveSinaGif(GifActivity.this,bytes,url);
                    }
                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                    }
                });
            }

        }
        /*if (url != null) {
            final TextDialog dialog = new TextDialog(this);
            dialog.show();
            ImageLoaderHelper.load(this, url, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    try {
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        loadedImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                        outputStream.close();
                        loadedImage.recycle();
                        mGifView.setMovie(Movie.decodeStream(inputStream));
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();

                }
            });
        }*/
    }
}
