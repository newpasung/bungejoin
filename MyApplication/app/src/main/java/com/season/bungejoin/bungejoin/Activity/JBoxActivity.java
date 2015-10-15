package com.season.bungejoin.bungejoin.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.season.bungejoin.bungejoin.widget.JBoxView;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.pooling.IWorldPool;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/10/12.
 */
public class JBoxActivity extends BaseActivity {
    JBoxView jBoxView;
    Timer timer;
    TimerTask task;
    Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            jBoxView.refresh();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jBoxView=new JBoxView(this);
        setContentView(jBoxView);
        task =new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(1);
            }
        };
        timer =new Timer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
        timer.purge();
    }
}
