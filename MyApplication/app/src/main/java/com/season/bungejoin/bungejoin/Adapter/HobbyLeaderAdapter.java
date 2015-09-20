package com.season.bungejoin.bungejoin.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.season.bungejoin.bungejoin.Listener.ClickListener;
import com.season.bungejoin.bungejoin.R;

/**
 * Created by Administrator on 2015/9/20.
 */
public class HobbyLeaderAdapter extends RecyclerView.Adapter {
    String [] text;
    int [] colors;
    Class [] clses;
    ClickListener listener;
    public HobbyLeaderAdapter( String[] text,int []colors,Class [] cls) {
        this.text = text;
        this.colors=colors;
        if(cls!=null){
            this.clses=cls;
        }
    }

    @Override
    public int getItemCount() {
        return text.length;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.square_leader,parent,false);
        Holder mHolder=new Holder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder mHolder=(Holder)holder;
        mHolder.cardView.setCardBackgroundColor(colors[position]);
        mHolder.textView.setText(text[position]);
        final int index=position;
        mHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clses!=null&&index<clses.length&&clses[index]!=null){
                    listener.onClick(v,clses[index]);
                }
                else{
                    listener.onClick(v,null);
                }
            }
        });
    }

    protected class Holder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView textView;
        public Holder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView;
            textView=(TextView)cardView.findViewById(R.id.tv_text);
        }
    }

    public void setListener(ClickListener listener){
        this.listener=listener;
    }

}
