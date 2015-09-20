package com.season.bungejoin.bungejoin.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.season.bungejoin.bungejoin.R;
import com.season.bungejoin.bungejoin.widget.FillterView;

/**
 * Created by Administrator on 2015/9/11.
 */
public class FillterGameActivity extends BaseActivity {
    FillterView fillterView;
    boolean isNeededInit = true;
    ProgressDialog dialog;
    Button[] mBtnChooser = new Button[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filltergame_layout);
        iniUI();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void iniUI() {
        dialog = new ProgressDialog(this);
        fillterView = (FillterView) findViewById(R.id.fillterview);
        int btn_id[] = {R.id.btn_colorchooser1, R.id.btn_colorchooser2,
                R.id.btn_colorchooser3, R.id.btn_colorchooser4, R.id.btn_colorchooser5,
                R.id.btn_colorchooser6};
        final int color_id[] = {R.color.summerparty1, R.color.summerparty2, R.color.summerparty3,
                R.color.summerparty4, R.color.summerparty5};
        final int length = btn_id.length;
        for (int i = 0; i < btn_id.length; i++) {
            final int j = i;
            mBtnChooser[i] = (Button) findViewById(btn_id[i]);
            mBtnChooser[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toast("choosed");
                    if (j != length - 1)
                        fillterView.setPaintColor(FillterGameActivity.this.getResources().getColor(color_id[j]));
                    else
                        fillterView.setIsRandom(true);
                }
            });
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isNeededInit) {
            FillterView.Job job = new FillterView.Job() {
                @Override
                public void onWorking() {
                    dialog.show();
                    fillterView.setFocusable(false);
                }

                @Override
                public void postworking() {
                    dialog.dismiss();
                    fillterView.setFocusable(true);
                }
            };
            fillterView.setListener(job);
            fillterView.preProc();
            fillterView.setFocusable(true);
            isNeededInit = false;
        }
    }
}
