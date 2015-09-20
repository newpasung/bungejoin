package com.season.bungejoin.bungejoin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.season.bungejoin.bungejoin.R;

/**
 * Created by Administrator on 2015/9/19.
 */
public class WorldFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout linearLayout=(LinearLayout)inflater
                .inflate(R.layout.worldpage_fragment,null);
        return linearLayout;
    }

    public static WorldFragment newInstance(){
        WorldFragment fragment =new WorldFragment();
        return fragment;
    }
}
