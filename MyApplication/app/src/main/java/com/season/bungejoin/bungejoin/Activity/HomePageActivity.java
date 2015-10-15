package com.season.bungejoin.bungejoin.activity;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.loopj.android.http.RequestParams;
import com.season.bungejoin.bungejoin.R;
import com.season.bungejoin.bungejoin.utils.DensityUtils;
import com.season.bungejoin.bungejoin.utils.HttpHelpers.HttpClient;
import com.season.bungejoin.bungejoin.utils.HttpHelpers.JsonResponseHandler;
import com.season.bungejoin.bungejoin.fragment.HobbyFragment;
import com.season.bungejoin.bungejoin.fragment.HomeFragment;
import com.season.bungejoin.bungejoin.fragment.WorldFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2015/9/7.
 */
public class HomePageActivity extends BaseActivity {
    int DIRECTION_LEFT = 0;
    int DIRECTION_RIGHT = 1;
    int DIRECTION_UNDEFINED = 2;
    String[] titles;
    ViewPager viewPager;
    ImageButton[] imagebuttons = new ImageButton[3];
    int[] color = new int[5];
    int curColor;
    int nextColor;
    int screenWidth;
    int direction;
    int lastOffset = -1;
    int scrollCounter = 0;//记录回调次数，帮助识别左右滑动
    int backgroundColor;
    int curIndex;
    boolean isCheckNeeded=true;
    LinearLayout linearLayout;
    HobbyFragment mHobbyFragment;
    HomeFragment mHomeFragment;
    WorldFragment mWorldFragment;
    List<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_layout);
        initData();
        ini();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ViewGroup.LayoutParams params =linearLayout.getLayoutParams();
        int h =DensityUtils.getWindowHeight(HomePageActivity.this)
                -params.height-DensityUtils.getStatusBarHei(this);
        int w=DensityUtils.getWindowWidth(HomePageActivity.this);
        mHomeFragment.setWidth(w);
        mHomeFragment.setHeight(h);
        if(isCheckNeeded&&hasFocus){
            checkAccount();
        }
    }

    protected void ini() {
        fragments=new ArrayList<>();
        mWorldFragment=WorldFragment.newInstance();
        mHomeFragment=HomeFragment.newInstance();
        mHobbyFragment=HobbyFragment.newInstance();
        fragments.add(mWorldFragment);
        fragments.add(mHomeFragment);
        fragments.add(mHobbyFragment);
        int[] btn_id = {R.id.btn_world, R.id.btn_home, R.id.btn_hobby};
        titles = this.getResources().getStringArray(R.array.home_part);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        linearLayout=(LinearLayout)findViewById(R.id.bottom_bar);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(1, true);
        curIndex = 1;
        viewPager.addOnPageChangeListener(new MyOnPageChangedListener());
        for (int i = 0; i < titles.length; i++) {
            imagebuttons[i] = (ImageButton) findViewById(btn_id[i]);
            imagebuttons[i].setOnClickListener(new MyOnClickListener(i));
        }
        imagebuttons[1].setBackgroundColor(curColor);
    }

    protected void initData() {
        color[0] = getResources().getColor(R.color.happiness0);
        color[1] = getResources().getColor(R.color.happiness1);
        color[2] = getResources().getColor(R.color.happiness5);
        color[3] = getResources().getColor(R.color.happiness4);
        color[4]=getResources().getColor(R.color.happiness3);
        backgroundColor = getResources().getColor(R.color.happiness2);
        screenWidth = DensityUtils.getWindowWidth(this);
        direction = DIRECTION_UNDEFINED;
        curColor = getNextColor();
        nextColor = getNextColor();
    }

    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    class MyOnPageChangedListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (positionOffsetPixels == 0) {
                if (curIndex != position) {
                    curColor = nextColor;
                    nextColor = getNextColor();
                    direction = DIRECTION_UNDEFINED;
                    lastOffset = -1;//初始化
                    scrollCounter = 0;
                    curIndex = position;
                    for (int i = 0; i < 3; i++) {
                        if (i != curIndex)
                            imagebuttons[i].setBackgroundColor(backgroundColor);
                    }
                    imagebuttons[curIndex].setBackgroundColor(curColor);
                }
                return;
            }
            //识别左右滑动
            if (lastOffset == -1) {
                lastOffset = positionOffsetPixels;
                scrollCounter++;
            } else if (lastOffset > positionOffsetPixels && scrollCounter != 0) {
                direction = DIRECTION_RIGHT;
                scrollCounter = 0;
            } else if (lastOffset < positionOffsetPixels && scrollCounter != 0) {
                direction = DIRECTION_LEFT;
                scrollCounter = 0;
            }
            if (direction != DIRECTION_UNDEFINED) {
//                如果向左滑动，则下一个view变化
                if (direction == DIRECTION_LEFT) {
                    if (curIndex != 2) {
                        imagebuttons[curIndex + 1].
                                setBackgroundColor(getGradientColor(backgroundColor, nextColor, positionOffset));
                        imagebuttons[curIndex].
                                setBackgroundColor(getGradientColor(curColor, backgroundColor, positionOffset));
                    }
                } else {
                    if (curIndex != 0) {
                        float percent = 1 - positionOffset;//因为从1->0，所以取相反的
                        imagebuttons[curIndex].
                                setBackgroundColor(getGradientColor(curColor, backgroundColor, percent));
                        imagebuttons[curIndex - 1].
                                setBackgroundColor(getGradientColor(backgroundColor, nextColor, percent));
                    }
                }
            }
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    class MyOnClickListener implements View.OnClickListener {
        private int index;

        public MyOnClickListener(int pos) {
            index = pos;
        }

        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(index, true);
        }
    }

    protected int getGradientColor(int color1, int color2, float percent) {
        ArgbEvaluator evaluator = new ArgbEvaluator();
        int color = (int) evaluator.evaluate(percent, color1, color2);
        return color;
    }

    protected int getNextColor() {
        Random random = new Random();
        int index = random.nextInt(color.length);
        return color[index];
    }

    protected void checkAccount(){
        RequestParams params=new RequestParams();
        HttpClient.post(this,"user/checktoken",params,new JsonResponseHandler(HomePageActivity.this){
            @Override
            public void onSuccess(JSONObject object) {
                super.onSuccess(object);
                isCheckNeeded=false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
