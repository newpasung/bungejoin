package com.season.bungejoin.bungejoin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.season.bungejoin.bungejoin.Activity.FillterGameActivity;
import com.season.bungejoin.bungejoin.Adapter.HobbyLeaderAdapter;
import com.season.bungejoin.bungejoin.Listener.ClickListener;
import com.season.bungejoin.bungejoin.R;

/**
 * Created by Administrator on 2015/9/19.
 */
public class HobbyFragment extends BaseFragment {

    String[] catagory = {"美术", "音乐", "阅读", "摄影", "图片", "游戏"};
    int[] colors;
    String[][] text;
    Class[][] clses;
    ClickListener listener = new ClickListener() {
        @Override
        public void onClick(View v, Class cls) {
            if (cls != null) {
                startActivity(new Intent(getContext(), cls));
            } else {
                Toast.makeText(getContext(), "现在没做好", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        colors = getColors();
        clses = getClasses();
        text = getTextDate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.hobbypage_fragment, null);
        RecyclerView recyclerView = (RecyclerView) linearLayout.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new MyAdapter(catagory));
        return linearLayout;
    }

    public static HobbyFragment newInstance() {
        HobbyFragment fragment = new HobbyFragment();
        return fragment;
    }

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        String[] dataset;

        public MyAdapter(String[] data) {
            dataset = data;
        }

        @Override
        public int getItemCount() {
            return dataset.length;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview, viewGroup, false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            MyViewHolder holder = (MyViewHolder) viewHolder;
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.recyclerView.setLayoutManager(manager);
            HobbyLeaderAdapter adapter = new HobbyLeaderAdapter(text[i], colors, clses[i]);
            adapter.setListener(listener);
            holder.recyclerView.setAdapter(adapter);
            holder.recyclerView.setHasFixedSize(true);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public RecyclerView recyclerView;
            public LinearLayout linearLayout;

            public MyViewHolder(View itemView) {
                super(itemView);
                linearLayout = (LinearLayout) itemView;
                recyclerView = (RecyclerView) linearLayout.findViewById(R.id.recyclerview);
            }
        }

    }

    protected int[] getColors() {
        int[] c = {R.color.beauty0, R.color.beauty1, R.color.beauty2,
                R.color.beauty3, R.color.beauty4, R.color.beauty5,
                R.color.beauty6, R.color.beauty7,};
        int[] copy = c;//避免提示错误的强迫症
        for (int i = 0; i < c.length; i++) {
            copy[i] = getResources().getColor(c[i]);
        }
        return copy;
    }

    protected Class[][] getClasses() {
        Class[][] cls = {
                {FillterGameActivity.class},
                {},
                {},
                {},
                {},
                {}
        };
        return cls;
    }

    protected String[][] getTextDate() {
        String[][] text = {{"填色"}
                , {"asd", "asdf", "wla;sdj", "aspoes"}
                , {",d.tuo", "aseyras", "23gys", "asdqwghsd", "asdascx"}
                , {"xxcgsjj", "aseyras", "789787", "aseyras"}
                , {"werw", "aseyras", "646546", "aseyras", "aseyras"}
                , {"46546", "hoyue", "aseyras", "aseyras", "aseyras"}};
        return text;
    }
}
