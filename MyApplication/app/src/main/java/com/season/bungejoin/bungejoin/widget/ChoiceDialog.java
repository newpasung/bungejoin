package com.season.bungejoin.bungejoin.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.season.bungejoin.bungejoin.R;


/**
 * Created by admin on 14/11/13.
 */
public class ChoiceDialog extends Dialog {
    private ListView listView;
    private ActionListAdapter adapter;
    private TextView titleView;
    private String title;

    public ChoiceDialog(Context context) {
        super(context, R.style.commondialog);
        adapter = new ActionListAdapter(context,R.layout.textview);
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choicedialog_layout);
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

        titleView = (TextView)findViewById(R.id.title_view);
        if (title != null && title.length() > 0) {
            titleView.setText(title);
            titleView.setVisibility(View.VISIBLE);
        } else {
            titleView.setVisibility(View.GONE);
        }
    }

    public ChoiceDialog addTitle(String title) {
        this.title = title;
        return this;
    }

    public ChoiceDialog addItem(String title, OnClickListener listener) {
        ActionItem item = new ActionItem(title, listener);
        adapter.add(item);
        return this;
    }

    public  interface OnClickListener {
        void didClick(ChoiceDialog dialog, String itemTitle);
    }

    public static class ActionItem {
        private String title;
        private OnClickListener listener;

        private ActionItem(String title, OnClickListener listener) {
            this.title = title;
            this.listener = listener;
        }
    }

    class ActionListAdapter extends ArrayAdapter<ActionItem> {
        LayoutInflater inflater;

        ActionListAdapter(Context context, int resource) {
            super(context, resource);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=inflater.inflate(R.layout.textview,null);
            }
            TextView textView=(TextView)convertView;
            ActionItem item = getItem(position);
            textView.setText(item.title);
            textView.setTextSize(15);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActionItem item = getItem(position);
                    if (item.listener != null)
                        item.listener.didClick(ChoiceDialog.this, item.title);
                }
            });
            return convertView;
        }
    }

}
