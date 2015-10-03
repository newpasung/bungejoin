package com.season.bungejoin.bungejoin.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.season.bungejoin.bungejoin.R;
import com.season.bungejoin.bungejoin.model.SinaUser;
import com.season.bungejoin.bungejoin.model.SinaWeibo;
import com.season.bungejoin.bungejoin.utils.DensityUtils;
import com.season.bungejoin.bungejoin.utils.HttpHelpers.ImageLoaderHelper;
import com.season.bungejoin.bungejoin.widget.ChoiceDialog;
import com.season.bungejoin.bungejoin.widget.CircleImageView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2015/10/1.
 */
public class SinaWbSumAdapter extends RecyclerView.Adapter {
    int curIndex=0;
    Context mContext;
    final int TYPE_COMMON =0;
    SinaWeibo weibo;
    SinaUser sinauser;
    ArrayList<SinaWeibo> weiboList;
    onClickListener listener;
    int []colorids ;
    int colorindex =0;
    public SinaWbSumAdapter(Context mContext,SinaWbSumAdapter.onClickListener listener) {
        this.mContext = mContext;
        this.listener=listener;
        weiboList=new ArrayList<>();
        colorids=mContext.getResources().getIntArray(R.array.weibocardcolor);
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_COMMON;
    }

    @Override
    public int getItemCount() {
        return weiboList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(mContext).inflate(R.layout.weibo_sum_item,null);
        if(viewType==TYPE_COMMON){
            return new CommonHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        curIndex=position;
        weibo =weiboList.get(position);
        sinauser=SinaUser.getUser(weibo.userid);
        ((CommonHolder)holder).mCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v,onClickListener.TYPE_CARDVIEW,position);
            }
        });
        switch (holder.getItemViewType()){
            case TYPE_COMMON:{
                ((CommonHolder)holder).mTvcontent.setText(weibo.text);
                ((CommonHolder)holder).mTvtime.setText(weibo.getTime());
                if(weibo.retweeted_status==null){
                    if(weibo.piccount==0){
                        ((CommonHolder)holder).mTvtype.setText(mContext.getResources().getString(R.string.weibotype_text));
                    }
                    else{
                        ((CommonHolder)holder).mTvtype
                                .setText(mContext.getResources().getString(R.string.weibotype_sharepics));
                    }
                }else{
                    SinaWeibo reweibo =weibo.retweeted_status;
                    if(reweibo.piccount==0){
                        ((CommonHolder)holder).mTvtype.setText(mContext.getResources().getString(R.string.retweet_text));
                    }
                    else{
                        ((CommonHolder)holder).mTvtype
                                .setText(mContext.getResources().getString(R.string.retweet_sharepics));
                    }
                }

            }break;
        }
    }

    class CommonHolder extends RecyclerView.ViewHolder{
        CardView mCardview;
        TextView mTvcontent;
        TextView mTvtime;
        TextView mTvtype;
        public CommonHolder(View itemView) {
            super(itemView);
            mCardview=(CardView)itemView;
            mTvcontent=(TextView)mCardview.findViewById(R.id.tv_text);
            mTvtime=(TextView)mCardview.findViewById(R.id.tv_time);
            mTvtype =(TextView)mCardview.findViewById(R.id.tv_type);
            mCardview.setCardBackgroundColor(nextColor());
            mTvcontent.setMaxWidth(DensityUtils.getWindowWidth((Activity)mContext)*4/5);
            mTvcontent.setMinWidth(DensityUtils.getWindowWidth((Activity)mContext)/2);
        }
    }

    public void refreshData(ArrayList<SinaWeibo> list){
        weiboList.clear();
        weiboList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<SinaWeibo> list){
        weiboList.addAll(list);
        notifyDataSetChanged();
    }

    public interface onClickListener{
        int TYPE_CARDVIEW=1;
        void onClick(View view,int type,int position);
    }

    public SinaWeibo getItem(int position){
        return weiboList.get(position);
    }

    protected int nextColor(){
        if(colorindex>=colorids.length){
            colorindex=0;
        }
        return colorids[colorindex++];
    }

    public int getCurIndex(){
        return curIndex;
    }

}
