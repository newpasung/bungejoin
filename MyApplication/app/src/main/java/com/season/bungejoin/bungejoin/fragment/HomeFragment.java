package com.season.bungejoin.bungejoin.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.season.bungejoin.bungejoin.R;
import com.season.bungejoin.bungejoin.widget.TextDialog;

/**
 * Created by Administrator on 2015/9/19.
 */
public class HomeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout linearLayout=(LinearLayout)inflater.inflate(
                R.layout.homepage_fragment,null);
        Button button=(Button)linearLayout.findViewById(R.id.btn_test);
        final TextDialog dialog=new TextDialog(getContext(),R.style.textdialog);
        dialog.setCanceledOnTouchOutside(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        return linearLayout;
    }

    public static HomeFragment newInstance(){
        HomeFragment fragment=new HomeFragment();
        return fragment;
    }
}
