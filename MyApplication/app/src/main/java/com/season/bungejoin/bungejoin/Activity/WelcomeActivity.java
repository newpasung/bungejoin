package com.season.bungejoin.bungejoin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.season.bungejoin.bungejoin.R;

/**
 * Created by Administrator on 2015/9/7.
 */
public class WelcomeActivity extends BaseActivity {
    private ImageSwitcher mImageSwitcher;
    private int currentIndex=0;
    private int drawableId[] ={R.drawable.welcomepage1,
            R.drawable.welcomepage2,R.drawable.welcomepage3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomepage_layout);
        iniUI();
    }

    private void iniUI(){
        mImageSwitcher=(ImageSwitcher)findViewById(R.id.is_welcomepage);
        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView image =new ImageView(WelcomeActivity.this);
                image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                image.setLayoutParams(new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                ));
                return image;
            }
        });
        mImageSwitcher.setImageResource(drawableId[currentIndex]);
    }

    GestureDetector gestureDetector =new GestureDetector(
            new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            if(motionEvent.getX()-motionEvent1.getX()>100){
                //左滑
                if(currentIndex<2){
                    mImageSwitcher.setOutAnimation(WelcomeActivity.this,R.anim.slide_out_left);
                    mImageSwitcher.setInAnimation(WelcomeActivity.this,R.anim.slide_in_right);
                    mImageSwitcher.setImageResource(drawableId[currentIndex+1]);
                    currentIndex++;
                }
                else{
                    startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                    finish();
                }
            }
            else if(motionEvent.getX()-motionEvent1.getX()<-100){
                if(currentIndex>0){
                    mImageSwitcher.setOutAnimation(WelcomeActivity.this,R.anim.slide_out_right);
                    mImageSwitcher.setInAnimation(WelcomeActivity.this,R.anim.slide_in_left);
                    mImageSwitcher.setImageResource(drawableId[currentIndex-1]);
                    currentIndex--;
                }
            }
            return true;
        }
    });

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }
}
