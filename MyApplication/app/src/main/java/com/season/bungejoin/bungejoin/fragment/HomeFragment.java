package com.season.bungejoin.bungejoin.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.season.bungejoin.bungejoin.JoinApplication;
import com.season.bungejoin.bungejoin.activity.ImageBrowserActivity;
import com.season.bungejoin.bungejoin.model.User;
import com.season.bungejoin.bungejoin.R;
import com.season.bungejoin.bungejoin.utils.DensityUtils;
import com.season.bungejoin.bungejoin.utils.HttpHelpers.ImageLoaderHelper;
import com.season.bungejoin.bungejoin.widget.CircleImageView;
import com.season.bungejoin.bungejoin.widget.MovingSurfaceView;
import com.season.bungejoin.bungejoin.widget.SelfInfoView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/19.
 */
public class HomeFragment extends BaseFragment {
    final int ANIMATION_OFFSET=10;
    RelativeLayout rlboard;
    ImageButton mBtnDirc;
    SelfInfoView infoView;
    RelativeLayout rlshowboard;
    CircleImageView mIvavatar;
    MovingSurfaceView surfaceView;
    RelativeLayout mRlachieve;
    ImageView mIvgoldegg;
    User mUser;
    ViewStub viewStub;
    boolean isHide = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isHide = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        showBoard();
        if(surfaceView!=null){
            surfaceView.destroy();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(surfaceView!=null){
            surfaceView.destroy();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(
                R.layout.homepage_fragment, null);
        iniUI(relativeLayout);
        return relativeLayout;
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    protected void iniUI(RelativeLayout relativeLayout) {
        mUser=User.getUser(JoinApplication.getInstance().getUid(getContext()));
        viewStub=(ViewStub)relativeLayout.findViewById(R.id.surfacestub);
        mIvgoldegg=(ImageView)relativeLayout.findViewById(R.id.iv_goldegg);
        mIvgoldegg.setImageLevel(8000);
        mRlachieve=(RelativeLayout)relativeLayout.findViewById(R.id.bottom_achieve);
        rlboard = (RelativeLayout) relativeLayout.findViewById(R.id.rl_showboard);
        mIvavatar=(CircleImageView)relativeLayout.findViewById(R.id.circleview);
        mIvavatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), ImageBrowserActivity.class);
                intent.putExtra(ImageBrowserActivity.INTENT_PARAMETER_PICCOUNT,1);
                intent.putExtra(ImageBrowserActivity.INTENT_PARAMETER_PICURL,mUser.avatar);
                startActivity(intent);
            }
        });

        ImageLoaderHelper.load(getContext(),mUser.cover,new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                BitmapDrawable drawable=new BitmapDrawable(getResources(),loadedImage);
                if(rlboard!=null)
                rlboard.setBackground(drawable);
            }
        });
        ImageLoaderHelper.load(getContext(), mUser.avatar, mIvavatar, ImageLoaderHelper.avataroption());
        rlboard.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                infoView.setUser(User.getUser(JoinApplication.getInstance().getUid(getContext())));
                infoView.setCenter(rlboard.getWidth() / 2
                        , (rlboard.getHeight() / 2) + DensityUtils.getStatusBarHei(getActivity()));
                rlboard.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        rlshowboard=(RelativeLayout)rlboard.findViewById(R.id.rl_showboard);

        mBtnDirc = (ImageButton) relativeLayout.findViewById(R.id.btn_up);
        infoView = (SelfInfoView) relativeLayout.findViewById(R.id.infoview);
        Button btn_down = (Button) relativeLayout.findViewById(R.id.btn_down);
        mBtnDirc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideBoard();
            }
        });

        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBoard();
            }
        });

    }

    protected AnimationSet getHideAnim(Animation.AnimationListener listener) {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation =
                new TranslateAnimation(0, 0, 0, -(rlboard.getHeight() - mBtnDirc.getHeight() - ANIMATION_OFFSET));
        animationSet.addAnimation(translateAnimation);
        animationSet.setDuration(500);
        animationSet.setFillAfter(true);
        if(listener!=null) animationSet.setAnimationListener(listener);
        return animationSet;
    }

    protected AnimationSet getShowAnimation(Animation.AnimationListener listener) {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation =
                new TranslateAnimation(0, 0, -(rlboard.getHeight() - mBtnDirc.getHeight() - ANIMATION_OFFSET), 0);
        animationSet.addAnimation(translateAnimation);
        animationSet.setDuration(500);
        animationSet.setFillAfter(true);
        if(listener!=null) animationSet.setAnimationListener(listener);
        return animationSet;
    }

    protected void setSurVisibility(boolean visibility){
        if(visibility){
            if(surfaceView==null){
                surfaceView=(MovingSurfaceView)viewStub.inflate();
                ArrayList<String> list=new ArrayList<>();
                list.add("hello");
                list.add("helle");
                list.add("helle");
                list.add("helle");
                ArrayList<Point> points=new ArrayList<>();
                points.add(new Point(100,300));
                points.add(new Point(100, 400));
                points.add(new Point(300,378));
                points.add(new Point(300, 500));
                surfaceView.init(list, points);
                surfaceView.setDisplayW(getFWidth());
                surfaceView.setDisplayH(getFHeight() - 2*(mBtnDirc.getHeight() + ANIMATION_OFFSET)-mRlachieve.getHeight());
                surfaceView.setUpbound(mBtnDirc.getHeight() + ANIMATION_OFFSET);
            }
            else{
                surfaceView.setVisibility(View.VISIBLE);
            }
        }
        else{
            surfaceView.destroy();
            surfaceView.setVisibility(View.INVISIBLE);
        }
    }

    protected void showBoard(){
        if (isHide) {
            rlboard.startAnimation(getShowAnimation(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    setSurVisibility(false);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            }));
            mBtnDirc.setImageResource(R.drawable.uptip);
            isHide = false;
        }
    }

    protected void hideBoard(){
        if(!isHide){
            rlboard.startAnimation(getHideAnim(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    setSurVisibility(true);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            }));
            mBtnDirc.setImageResource(R.drawable.downtip);
            isHide = true;
        }
    }

}
