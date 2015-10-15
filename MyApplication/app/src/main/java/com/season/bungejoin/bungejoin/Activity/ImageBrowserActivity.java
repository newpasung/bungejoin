package com.season.bungejoin.bungejoin.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.season.bungejoin.bungejoin.R;
import com.season.bungejoin.bungejoin.libs.com.davemorrissey.labs.subscaleview.ImageSource;
import com.season.bungejoin.bungejoin.libs.com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.season.bungejoin.bungejoin.libs.me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import com.season.bungejoin.bungejoin.storage.FileStorage;
import com.season.bungejoin.bungejoin.utils.HttpHelpers.HttpClient;
import com.season.bungejoin.bungejoin.utils.HttpHelpers.ImageLoaderHelper;
import com.season.bungejoin.bungejoin.widget.ChoiceDialog;
import com.season.bungejoin.bungejoin.widget.TextDialog;

import org.apache.http.Header;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2015/10/3.
 */
public class ImageBrowserActivity extends SwipeBackActivity {
    SubsamplingScaleImageView scaleView;
    int piccount = 0;
    String url;
    byte [] source;
    public final static String INTENT_PARAMETER_PICCOUNT = "piccount";
    public final static String INTENT_PARAMETER_PICURL = "picurl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagebrowser_layout);
        scaleView = (SubsamplingScaleImageView) findViewById(R.id.subscaleview);
        scaleView.setMinimumDpi(80);
        if (getIntent() != null) {
            piccount = getIntent().getIntExtra(INTENT_PARAMETER_PICCOUNT, 0);
            url = getIntent().getStringExtra(INTENT_PARAMETER_PICURL);
        }
        if (url != null) {
            final TextDialog dialog = new TextDialog(this);
            dialog.show();
            /*ImageLoaderHelper.load(this, url, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);

                }
            });*/
            HttpClient.getByte(ImageBrowserActivity.this, url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    scaleView.setImage(ImageSource.bitmap(BitmapFactory.decodeByteArray(bytes,0,bytes.length)));
                    source = bytes;
                    dialog.dismiss();
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    dialog.dismiss();
                }
            });

        }
        iniListener();
    }

    protected void iniListener(){
        scaleView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(source!=null){
                    new ChoiceDialog(ImageBrowserActivity.this)
                            .addItem("保存图片", new ChoiceDialog.OnClickListener() {
                                @Override
                                public void didClick(ChoiceDialog dialog, String itemTitle) {
                                    FileStorage.saveImage(ImageBrowserActivity.this,source);
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

}
