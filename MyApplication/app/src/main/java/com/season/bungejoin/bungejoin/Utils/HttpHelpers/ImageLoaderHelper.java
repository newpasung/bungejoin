package com.season.bungejoin.bungejoin.Utils.HttpHelpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.season.bungejoin.bungejoin.R;

import java.util.concurrent.Executor;

/**
 * Created by Administrator on 2015/9/23.
 */
/*DisplayImageOptions options = new DisplayImageOptions.Builder()
        .showImageOnLoading(R.drawable.ic_stub) // resource or drawable
        .showImageForEmptyUri(R.drawable.ic_empty) // resource or drawable
        .showImageOnFail(R.drawable.ic_error) // resource or drawable
        .resetViewBeforeLoading(false)  // default
        .delayBeforeLoading(1000)
        .cacheInMemory(false) // default
        .cacheOnDisk(false) // default
        .preProcessor(...)
        .postProcessor(...)
        .extraForDownloader(...)
        .considerExifParams(false) // default
        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
        .bitmapConfig(Bitmap.Config.ARGB_8888) // default
        .decodingOptions(...)
        .displayer(new SimpleBitmapDisplayer()) // default
        .handler(new Handler()) // default
        .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
        .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
        .diskCacheExtraOptions(480, 800, null)
        .taskExecutor(...)
        .taskExecutorForCachedImages(...)
        .threadPoolSize(3) // default
        .threadPriority(Thread.NORM_PRIORITY - 1) // default
        .tasksProcessingOrder(QueueProcessingType.FIFO) // default
        .denyCacheImageMultipleSizesInMemory()
        .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
        .memoryCacheSize(2 * 1024 * 1024)
        .memoryCacheSizePercentage(13) // default
        .diskCache(new UnlimitedDiscCache(cacheDir)) // default
        .diskCacheSize(50 * 1024 * 1024)
        .diskCacheFileCount(100)
        .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
        .imageDownloader(new BaseImageDownloader(context)) // default
        .imageDecoder(new BaseImageDecoder()) // default
        .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
        .writeDebugLogs()
        .build();*/
public class ImageLoaderHelper {


    public static void load(Context context,String url,ImageView imageView,DisplayImageOptions options
    ,ImageLoadingListener listener){
        if(ImageLoader.getInstance().isInited()){
            ImageLoader.getInstance().displayImage(url, imageView, options, listener);
        }else{
            ImageLoader.getInstance().init(getImageLoaderConfi(context));
            ImageLoader.getInstance().displayImage(url, imageView, options,listener);
        }
    }

    public static void load(Context context,String url,ImageView imageView,DisplayImageOptions options){
        load(context,url,imageView,options,null);
    }

    public static void load(Context context,String url,ImageSize imageSize,ImageLoadingListener listener){
        if(ImageLoader.getInstance().isInited()){
            ImageLoader.getInstance().loadImage(url,imageSize,listener);
        }else{
            ImageLoader.getInstance().init(getImageLoaderConfi(context));
            ImageLoader.getInstance().loadImage(url, imageSize, listener);
        }
    }

    public static void load(Context context,String url,ImageLoadingListener listener){
        if(ImageLoader.getInstance().isInited()){
            ImageLoader.getInstance().loadImage(url,listener);
        }else{
            ImageLoader.getInstance().init(getImageLoaderConfi(context));
            ImageLoader.getInstance().loadImage(url, listener);
        }
    }

    public static ImageLoaderConfiguration getImageLoaderConfi(Context context){
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(context)
                .writeDebugLogs()
                .build();
        return configuration;
    }

    public static DisplayImageOptions avataroption(){
        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.whitepic)
                .showImageForEmptyUri(R.drawable.whitepic)
                .showImageOnFail(R.drawable.whitepic)
                .resetViewBeforeLoading(false)
                .delayBeforeLoading(100)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(100))
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();
        return options;
    }

    public static DisplayImageOptions testoption(){
        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.redpic)
                .showImageForEmptyUri(R.drawable.kitty)
                .showImageOnFail(R.drawable.blackpic)
                .resetViewBeforeLoading(false)
                .delayBeforeLoading(100)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .postProcessor(new BitmapProcessor() {
                    @Override
                    public Bitmap process(Bitmap bitmap) {
                        return bitmap;
                    }
                })
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(100))
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();
        return options;
    }

}
