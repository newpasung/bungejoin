package com.season.bungejoin.bungejoin.activity;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.util.Linkify;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.season.bungejoin.bungejoin.R;
import com.season.bungejoin.bungejoin.adapter.PreviewAdapter;
import com.season.bungejoin.bungejoin.adapter.SinaWbSumAdapter;
import com.season.bungejoin.bungejoin.model.SinaUser;
import com.season.bungejoin.bungejoin.model.SinaWeibo;
import com.season.bungejoin.bungejoin.sina.SinaHttpClient;
import com.season.bungejoin.bungejoin.sina.SinaResHandler;
import com.season.bungejoin.bungejoin.task.TaskExecutor;
import com.season.bungejoin.bungejoin.utils.DensityUtils;
import com.season.bungejoin.bungejoin.utils.HttpHelpers.ImageLoaderHelper;
import com.season.bungejoin.bungejoin.widget.CircleImageView;
import com.season.bungejoin.bungejoin.widget.HLinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/1.
 */
public class SinaWeiboActivity extends BaseActivity {
    RecyclerView mSumrec;
    LinearLayout mLlprepiccontainer;
    ArrayList<SinaWeibo> weiboList;
    SinaWbSumAdapter sumAdapter;
    CircleImageView mCiravatar;
    TextView mTvcontent;
    TextView mTvname;
    TextView mTvmsg;
    RecyclerView mRecpic;
    PreviewAdapter prepicAdapter;
    HLinearLayoutManager picmanager;
    LinearLayoutManager sumManager;
    View headerview;
    View footerview;
    int curClickedIndex = -1;
    long loadmoreIndex ;
    SinaWbSumAdapter.onClickListener sumListener = new SinaWbSumAdapter.onClickListener() {
        @Override
        public void onClick(View view, int type, int position) {
            if (curClickedIndex == position) return;
            switch (type) {
                case SinaWbSumAdapter.onClickListener.TYPE_CARDVIEW: {
                    showDetail(position);
                }
                break;
            }
            curClickedIndex = position;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sinaweibo_layout);
        initdata();
        initUI();
    }

    protected void initUI() {
        mCiravatar = (CircleImageView) findViewById(R.id.cir_avatar);
        headerview=findViewById(R.id.headerview);
        footerview=findViewById(R.id.footerview);
        mTvmsg =(TextView)findViewById(R.id.tv_msg);
        mLlprepiccontainer = (LinearLayout) findViewById(R.id.ll_prepiccontainer);
        mTvname = (TextView) findViewById(R.id.tv_name);
        mTvcontent = (TextView) findViewById(R.id.tv_content);
        mRecpic = (RecyclerView) findViewById(R.id.rec_pics);
        prepicAdapter = new PreviewAdapter(this);
        prepicAdapter.setData(new String[]{"http://7xlra1.com1.z0.glb.clouddn.com/systemweibopic1.png"
                , "http://7xlra1.com1.z0.glb.clouddn.com/systemweibopic2.png",
                "http://7xlra1.com1.z0.glb.clouddn.com/systemweibopic3.png",
                "http://7xlra1.com1.z0.glb.clouddn.com/systemweibopic4.png",
                "http://7xlra1.com1.z0.glb.clouddn.com/systemweibopic5.png",
                "http://7xlra1.com1.z0.glb.clouddn.com/systemweibopic6.png",
                "http://7xlra1.com1.z0.glb.clouddn.com/systemweibopic7.png",
                "http://7xlra1.com1.z0.glb.clouddn.com/systemweibopic8.png",
                "http://7xlra1.com1.z0.glb.clouddn.com/systemweibopic9.png"});
        picmanager = new HLinearLayoutManager(this);
        mRecpic.setLayoutManager(picmanager);
        mRecpic.setHasFixedSize(true);
        mRecpic.setAdapter(prepicAdapter);

        sumAdapter = new SinaWbSumAdapter(this, sumListener);
        sumAdapter.refreshData(weiboList);
        mSumrec = (RecyclerView) findViewById(R.id.rec_bottomsum);
        sumManager = new LinearLayoutManager(this);
        sumManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mSumrec.setLayoutManager(sumManager);
        mSumrec.setHasFixedSize(true);
        mSumrec.setItemAnimator(new DefaultItemAnimator());
        mSumrec.setAdapter(sumAdapter);
        mSumrec.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(sumManager.findLastCompletelyVisibleItemPosition()==0&&newState==2){
                    showSumHeader();//hide also
                }
                else if(sumManager.findLastCompletelyVisibleItemPosition()==sumManager.getItemCount()-1&&newState==2){
                    showSumFooter();//hide also
                }
            }
        });

    }

    protected void initdata() {
        weiboList = SinaWeibo.getCached(this);
        httpRefresh();
    }

    protected void showDetail(int pos) {
        SinaWeibo weibo = sumAdapter.getItem(pos);
        SinaUser user = SinaUser.getUser(weibo.userid);
        ImageLoaderHelper.load(this, user.avatar_large, mCiravatar, ImageLoaderHelper.fadeavataropt());
        mTvcontent.setAutoLinkMask(Linkify.WEB_URLS);
        mTvmsg.setText(getWeiboMsg(weibo));
        mTvname.setText(user.name);
        if (weibo.retweeted_status == null) {
            mTvcontent.setText(weibo.text);
            if (weibo.piccount != 0) {
                prepicAdapter.setData(weibo.getThumbnailPics());
                mLlprepiccontainer.setVisibility(View.VISIBLE);
                mRecpic.smoothScrollToPosition(0);
            } else {
                mLlprepiccontainer.setVisibility(View.GONE);
            }
        } else {
            SinaWeibo reweibo = weibo.retweeted_status;
            mTvcontent.setText(weibo.text + "==>" + SinaUser.getUser(reweibo.userid).name + "==>" + reweibo.text);
            if (reweibo.piccount != 0) {
                prepicAdapter.setData(reweibo.getThumbnailPics());
                mLlprepiccontainer.setVisibility(View.VISIBLE);
                mRecpic.smoothScrollToPosition(0);
            } else {
                mLlprepiccontainer.setVisibility(View.GONE);
            }
        }

    }

    protected SpannableStringBuilder getWeiboMsg(SinaWeibo weibo){
        int lastLength;
        SpannableStringBuilder spanbuilder =new SpannableStringBuilder();
        spanbuilder.append("评论:");
        lastLength =spanbuilder.length();
        spanbuilder.append(String.valueOf(weibo.comments_count));
        spanbuilder.setSpan(new ForegroundColorSpan(Color.MAGENTA), lastLength, spanbuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanbuilder.append(" 点赞:");
        lastLength =spanbuilder.length();
        spanbuilder.append(String.valueOf(weibo.attitudes_count));
        spanbuilder.setSpan(new ForegroundColorSpan(Color.MAGENTA), lastLength, spanbuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanbuilder.append(" ");
        lastLength =spanbuilder.length();
        if(weibo.favorited) spanbuilder.append("已收藏");
        else spanbuilder.append("未收藏");
        spanbuilder.setSpan(new ForegroundColorSpan(Color.MAGENTA),lastLength,spanbuilder.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanbuilder;
    }

    //show header之后加载数据之后隐藏header
    protected void showSumHeader(){
        final ViewGroup.LayoutParams params =headerview.getLayoutParams();
        if(params.width==0){
            ValueAnimator animator =ValueAnimator.ofInt(0,DensityUtils.dip2px(this,80f));
            animator.setDuration(500);
            animator.start();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    params.width=(int)animation.getAnimatedValue();
                    headerview.setLayoutParams(params);
                    if((int)animation.getAnimatedValue()==DensityUtils.dip2px(SinaWeiboActivity.this, 80f)){
                        httpRefresh();
                    }
                }
            });
        }
    }

    protected void hideSumHeader(){
        final ViewGroup.LayoutParams params =headerview.getLayoutParams();
        if(params.width!=0){
            ValueAnimator animator =ValueAnimator.ofInt(params.width,0);
            animator.setDuration(500);
            animator.start();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    params.width = (int) animation.getAnimatedValue();
                    headerview.setLayoutParams(params);
                    if((int)animation.getAnimatedValue()==0){
                        mSumrec.smoothScrollToPosition(0);
                    }
                }
            });
        }
    }

    protected void showSumFooter(){
        final ViewGroup.LayoutParams params =footerview.getLayoutParams();
        if(params.width==0){
            ValueAnimator animator =ValueAnimator.ofInt(0,DensityUtils.dip2px(this,80f));
            animator.setDuration(500);
            animator.setInterpolator(new LinearInterpolator());
            animator.start();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    params.width = (int) animation.getAnimatedValue();
                    footerview.setLayoutParams(params);
                    mSumrec.scrollToPosition(sumManager.getItemCount() - 1);
                    if((int)animation.getAnimatedValue()==DensityUtils.dip2px(SinaWeiboActivity.this, 80f)){
                        httpLoadMore();
                    }
                }
            });
        }
    }

    protected void hideSumFooter(){
        final ViewGroup.LayoutParams params =footerview.getLayoutParams();
        if(params.width!=0){
            ValueAnimator animator =ValueAnimator.ofInt(params.width,0);
            animator.setDuration(500);
            animator.start();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    params.width = (int) animation.getAnimatedValue();
                    footerview.setLayoutParams(params);
                    if((int)animation.getAnimatedValue()==0){
                        mSumrec.smoothScrollToPosition(sumManager.findLastCompletelyVisibleItemPosition());
                    }
                }
            });
        }
    }

    protected void httpRefresh(){
        TaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                SinaHttpClient.getLastestFrn(SinaWeiboActivity.this, new SinaResHandler(SinaWeiboActivity.this) {
                    @Override
                    public void onSuccess(JSONObject response) {
                        super.onSuccess(response);
                        if (response.has("statuses")) {
                            try {
                                weiboList = SinaWeibo.updateWeibos(response.getJSONArray("statuses"));
                                loadmoreIndex = weiboList.get(weiboList.size()-1).id;
                                SinaWeibo.setCache(SinaWeiboActivity.this, response.getJSONArray("statuses"));
                                TaskExecutor.executeUI(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (sumAdapter != null) {
                                            sumAdapter.refreshData(weiboList);
                                            hideSumHeader();
                                            weiboList.clear();
                                        }
                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

    protected void httpLoadMore(){
        TaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                SinaHttpClient.loadmoreFrn(SinaWeiboActivity.this,loadmoreIndex,new SinaResHandler(SinaWeiboActivity.this){
                    @Override
                    public void onSuccess(JSONObject response) {
                        super.onSuccess(response);
                        try {
                            JSONArray jsonObject =response.getJSONArray("statuses");
                            weiboList = SinaWeibo.updateWeibos(jsonObject);
                            loadmoreIndex =weiboList.get(weiboList.size()-1).id;
                            TaskExecutor.executeUI(new Runnable() {
                                @Override
                                public void run() {
                                    if(sumAdapter!=null){
                                        sumAdapter.addData(weiboList);
                                        hideSumFooter();
                                        weiboList.clear();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

}
