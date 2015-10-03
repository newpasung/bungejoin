package com.season.bungejoin.bungejoin.utils;

import android.view.animation.TranslateAnimation;

import com.season.bungejoin.bungejoin.animation.XRotationAnimation;

/**
 * Created by Administrator on 2015/9/8.
 */
public class AnimationHelper  {
    public static TranslateAnimation moveToLeft(int x){
        x*=-1;
        TranslateAnimation animation=new TranslateAnimation(0,x,0,0);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        return animation;
    }

    public static TranslateAnimation moveToRight(int x){
        TranslateAnimation animation=new TranslateAnimation(0,x,0,0);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        return animation;
    }

    public static TranslateAnimation moveUp(int y){
        y*=-1;
        TranslateAnimation animation=new TranslateAnimation(0,0,0,y);
        animation.setDuration(1000);
        return animation;
    }

    public static TranslateAnimation moveDown(int y){
        TranslateAnimation animation=new TranslateAnimation(0,0,0,y);
        animation.setDuration(3000);
        return animation;
    }

    public static XRotationAnimation upsideDown(){
        XRotationAnimation animation=new XRotationAnimation();
        return animation;
    }

}
