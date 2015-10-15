package com.season.bungejoin.bungejoin.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.season.bungejoin.bungejoin.Constant.MRequestCode;
import com.season.bungejoin.bungejoin.R;
import com.season.bungejoin.bungejoin.libs.me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import com.season.bungejoin.bungejoin.widget.CircleImageView;

/**
 * Created by Administrator on 2015/10/9.
 */
public class SelectColorActivity extends SwipeBackActivity {
    RecyclerView mRecpick;
    int colorId [] ;
    CircleImageView mCirpick1;
    CircleImageView mCirpick2;
    CircleImageView mCirresult;
    RadioGroup mRgmode;
    final int PICKMODE_SINGLE =0;
    final int PICKMODE_COMPOUND_1=1;
    final int PICKMODE_COMPOUND_2=2;
    final int PICKMODE_COMPOUND_FIN=3;
    int pickmode=PICKMODE_SINGLE;
    int color1=-1;
    int color2=-1;
    int color3=-1;
    final int BOARDWIDTH =3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectcolor_layout);
        iniData();
        iniUI();
        iniListener();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent =new Intent();
            if(mRgmode.getCheckedRadioButtonId()==R.id.radio_single){
                intent.putExtra("data",color1);
            }else{
                if(color3==-1){
                    new AlertDialog.Builder(this)
                            .setTitle("未合成颜色")
                            .show();
                    return true;
                }
                else{
                    intent.putExtra("data",color3);
                }
            }
            setResult(MRequestCode.RESULTCODE_COLOR,intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void iniUI(){
        mRgmode =(RadioGroup)findViewById(R.id.rg_choice);
        mCirpick1=(CircleImageView)findViewById(R.id.cir_pick1);
        mCirpick2=(CircleImageView)findViewById(R.id.cir_pick2);
        mCirresult=(CircleImageView)findViewById(R.id.cir_result);
        mRecpick=(RecyclerView)findViewById(R.id.rec_pick);
        GridLayoutManager manager =new GridLayoutManager(this,4);
        mRecpick.setLayoutManager(manager);
        mRecpick.setAdapter(new MyAdapter(this));
        mCirpick1.setBorderColor(Color.BLACK);
        mCirpick2.setBorderColor(Color.BLACK);
        mCirpick1.setBorderWidth(0);
        mCirpick2.setBorderColor(0);
    }

    protected void iniData(){
        colorId = getResources().getIntArray(R.array.pickboard);
    }

    protected void iniListener(){
        mCirpick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pickmode==PICKMODE_COMPOUND_2||pickmode==PICKMODE_COMPOUND_FIN){
                    pickmode=PICKMODE_COMPOUND_1;
                }
                setViewOnChoosing();
            }
        });
        mCirpick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pickmode==PICKMODE_COMPOUND_1||pickmode==PICKMODE_COMPOUND_FIN){
                    pickmode=PICKMODE_COMPOUND_2;
                }
                setViewOnChoosing();
            }
        });
        mRgmode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radio_single){
                    mCirpick2.setVisibility(View.INVISIBLE);
                    mCirresult.setVisibility(View.INVISIBLE);
                    pickmode=PICKMODE_SINGLE;
                    setViewOnChoosing();
                }
                if(checkedId==R.id.radio_compound){
                    mCirpick2.setVisibility(View.VISIBLE);
                    mCirresult.setVisibility(View.VISIBLE);
                    pickmode=PICKMODE_COMPOUND_1;
                    setViewOnChoosing();
                }
            }
        });
    }

    protected void setPickers(int color){
        switch (pickmode){
            case PICKMODE_SINGLE:{
                color1=color;
                mCirpick1.setImageDrawable(new ColorDrawable(color1));
            }break;
            case PICKMODE_COMPOUND_1:{
                color1=color;
                mCirpick1.setImageDrawable(new ColorDrawable(color1));
            }break;
            case PICKMODE_COMPOUND_2:{
                color2=color;
                mCirpick2.setImageDrawable(new ColorDrawable(color2));
                color3 = color1|color2;
                mCirresult.setImageDrawable(new ColorDrawable(color3));
                pickmode=PICKMODE_COMPOUND_FIN;
            }break;
            case PICKMODE_COMPOUND_FIN:{
            }break;
            default:break;
        }

    }

    class MyAdapter extends RecyclerView.Adapter{
        Context mContext;
        public MyAdapter(Context context) {
            mContext=context;
        }

        @Override
        public int getItemCount() {
            return colorId.length;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view =LayoutInflater.from(mContext).inflate(R.layout.pickboard_item_layout,null);
            MyHolder holder =new MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final int pos =position;
            ((MyHolder)holder).mCir.setImageDrawable(new ColorDrawable(colorId[pos]));
            ((MyHolder)holder).mCir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPickers(colorId[pos]);
                    setViewOnChoosing();
                }
            });
        }

        class MyHolder extends RecyclerView.ViewHolder{
            CircleImageView mCir;
            public MyHolder(View itemView) {
                super(itemView);
                mCir=(CircleImageView)itemView.findViewById(R.id.cir_color);
            }
        }

    }

    protected void setViewOnChoosing(){
        mCirpick1.setBorderColor(Color.BLACK);
        mCirpick2.setBorderColor(Color.BLACK);
        if(pickmode==PICKMODE_COMPOUND_1){
            mCirpick2.setBorderWidth(0);
            mCirpick1.setBorderWidth(BOARDWIDTH);
        }
        if(pickmode==PICKMODE_COMPOUND_2){
            mCirpick1.setBorderWidth(0);
            mCirpick2.setBorderWidth(BOARDWIDTH);
        }
        if(pickmode==PICKMODE_COMPOUND_FIN||pickmode==PICKMODE_SINGLE){
            mCirpick1.setBorderWidth(0);
            mCirpick2.setBorderWidth(0);
        }
        mCirpick1.invalidate();
        mCirpick2.invalidate();
    }

}
