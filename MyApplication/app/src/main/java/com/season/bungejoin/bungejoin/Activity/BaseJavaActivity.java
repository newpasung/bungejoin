package com.season.bungejoin.bungejoin.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.season.bungejoin.bungejoin.R;
import com.season.bungejoin.bungejoin.libs.me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2015/9/29.
 */
public class BaseJavaActivity extends SwipeBackActivity {
    Spinner mSpinner;
    EditText mEtinput;
    TextView mTvdisplay;
    Button mBtnensure;
    Button mBtnfinish;
    String [] array;
    int mint=0;
    short mshort=0;
    byte mbyte=0;
    long mlong=0;
    double mdouble=0;
    float mfloat=0;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basejava_layout);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    protected void init(){
        //0byte 1short 2int  3long 4float 5double
        array=getResources().getStringArray(R.array.base_type);
        mSpinner=(Spinner)findViewById(R.id.spinner);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mEtinput=(EditText)findViewById(R.id.edittext);
        mTvdisplay=(TextView)findViewById(R.id.textview);
        mBtnensure=(Button)findViewById(R.id.btn_ensure);
        mBtnfinish=(Button)findViewById(R.id.btn_finish);
        mBtnfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBtnensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEtinput.getText().toString();
                try{
                    switch (index) {
                        case 0: {
                            calculate(Byte.parseByte(text));
                        }
                        break;
                        case 1: {
                            calculate(Short.parseShort(text));
                        }
                        break;
                        case 2: {
                            calculate(Integer.parseInt(text));
                        }
                        break;
                        case 3: {
                            calculate(Long.parseLong(text));
                        }
                        break;
                        case 4: {
                            calculate(Float.parseFloat(text));
                        }
                        break;
                        case 5: {
                            calculate(Double.parseDouble(text));
                        }
                        break;
                        default:
                            break;
                    }
                }catch(NumberFormatException e){
                    toast(e.toString());
                    e.printStackTrace();}
                display();
            }
        });
        mBtnfinish.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mBtnfinish.startDrag(null, new View.DragShadowBuilder(mBtnfinish), null, 0);
                return true;
            }
        });
        mBtnfinish.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                return true;
            }
        });
    }

    protected  void calculate(int data){
        mint=data;
        mbyte=(byte)data;
        mshort=(short)data;
        mlong=(long)data;
        mdouble=(double)data;
        mfloat=(float)data;
    }

    protected  void calculate(byte data){
        mint=data;
        mbyte=data;
        mshort=(short)data;
        mlong=(long)data;
        mdouble=(double)data;
        mfloat=(float)data;
    }

    protected  void calculate(short data){
        mint=data;
        mbyte=(byte)data;
        mshort=data;
        mlong=(long)data;
        mdouble=(double)data;
        mfloat=(float)data;
    }

    protected  void calculate(long data){
        mint=(int)data;
        mbyte=(byte)data;
        mshort=(short)data;
        mlong=data;
        mdouble=(double)data;
        mfloat=(float)data;
    }

    protected  void calculate(float data){
        mint=(int)data;
        mbyte=(byte)data;
        mshort=(short)data;
        mlong=(long)data;
        mdouble=(double)data;
        mfloat=data;
    }

    protected  void calculate(double data){
        mint=(int)data;
        mbyte=(byte)data;
        mshort=(short)data;
        mlong=(long)data;
        mdouble=data;
        mfloat=(float)data;
    }

    protected void display(){
        StringBuilder stringBuilder=new StringBuilder(16);
        stringBuilder.append("int:");
        stringBuilder.append(mint);
        stringBuilder.append('\n');
        stringBuilder.append("short:");
        stringBuilder.append(mshort);
        stringBuilder.append('\n');
        stringBuilder.append("byte");
        stringBuilder.append(mbyte);
        stringBuilder.append('\n');
        stringBuilder.append("long");
        stringBuilder.append(mlong);
        stringBuilder.append('\n');
        stringBuilder.append("float");
        stringBuilder.append(mfloat);
        stringBuilder.append('\n');
        stringBuilder.append("double");
        stringBuilder.append(mdouble);
        stringBuilder.append('\n');
        mTvdisplay.setText(stringBuilder.toString());
    }

}
