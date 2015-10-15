package com.season.bungejoin.bungejoin.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.season.bungejoin.bungejoin.R;
import com.season.bungejoin.bungejoin.utils.HttpHelpers.ImageLoaderHelper;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Administrator on 2015/10/2.
 */
public class PreviewAdapter extends RecyclerView.Adapter {
    ArrayList<String> urlList=new ArrayList<>();
    Context mContext;
    OnItemClickedListener listener ;
    public PreviewAdapter(Context context) {
        mContext=context;
    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.picpreview_card,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ImageLoaderHelper.load(mContext, urlList.get(position), ((MyHolder) holder).mImageview,
                    ImageLoaderHelper.avataroption());
        ((MyHolder)holder).mImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(position);
            }
        });
    }

    class MyHolder extends RecyclerView.ViewHolder{
        CardView mCardview;
        ImageView mImageview;
        public MyHolder(View itemView) {
            super(itemView);
            mCardview=(CardView)itemView;
            mImageview=(ImageView)itemView.findViewById(R.id.iv_ssqualpic);
        }
    }

    public void setData(ArrayList<String> list){
        urlList =list;
        notifyDataSetChanged();
    }

    public void setData(String[] data){
        if(data==null){urlList.clear();notifyDataSetChanged();return ;}
        urlList.clear();
        Collections.addAll(urlList,data);
        notifyDataSetChanged();
    }

    public String getData(int position){
        return urlList.get(position);
    }

    public interface OnItemClickedListener{
        void onItemClicked(int pos);
    }

    public void setListener(OnItemClickedListener listener) {
        this.listener = listener;
    }
}
