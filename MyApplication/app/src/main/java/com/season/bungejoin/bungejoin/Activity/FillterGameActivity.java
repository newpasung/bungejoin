package com.season.bungejoin.bungejoin.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.season.bungejoin.bungejoin.Constant.MRequestCode;
import com.season.bungejoin.bungejoin.R;
import com.season.bungejoin.bungejoin.libs.com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.season.bungejoin.bungejoin.libs.me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import com.season.bungejoin.bungejoin.widget.FillerView;

/**
 * Created by Administrator on 2015/9/11.
 */
public class FillterGameActivity extends SwipeBackActivity {
    FillerView fillerView;
    ProgressDialog dialog;
    Button[] mBtnChooser = new Button[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filltergame_layout);
        iniUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==MRequestCode.RESULTCODE_COLOR&&requestCode==MRequestCode.GETCOLOR){
            int color =data.getIntExtra("data",-1);
            if(color!=-1){
                fillerView.setPaintColor(color);
            }
        }
    }

    protected void iniUI() {
        dialog = new ProgressDialog(this);
        fillerView = (FillerView) findViewById(R.id.fillerview);
        int btn_id[] = {R.id.btn_colorchooser1, R.id.btn_colorchooser2,
                R.id.btn_colorchooser3, R.id.btn_colorchooser4, R.id.btn_colorchooser5,
                R.id.btn_colorchooser6,R.id.btn_colorchooser7};
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
                    if(j<length-2){
                        fillerView.setPaintColor(FillterGameActivity.this.getResources().getColor(color_id[j]));
                    }
                    else if(j == length-2){
                        fillerView.setRandomColor();
                    }
                    else{
                        startActivityForResult(new Intent(FillterGameActivity.this,SelectColorActivity.class), MRequestCode.GETCOLOR);
                    }
                }
            });
        }
    }

}
