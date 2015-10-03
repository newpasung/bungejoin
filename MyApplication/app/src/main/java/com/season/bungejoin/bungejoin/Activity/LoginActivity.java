package com.season.bungejoin.bungejoin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.loopj.android.http.RequestParams;
import com.season.bungejoin.bungejoin.Constant.SharedPrefParameter;
import com.season.bungejoin.bungejoin.JoinApplication;
import com.season.bungejoin.bungejoin.model.User;
import com.season.bungejoin.bungejoin.R;
import com.season.bungejoin.bungejoin.storage.SharedPreferManager;
import com.season.bungejoin.bungejoin.utils.AnimationHelper;
import com.season.bungejoin.bungejoin.utils.DensityUtils;
import com.season.bungejoin.bungejoin.utils.HttpHelpers.HttpClient;
import com.season.bungejoin.bungejoin.utils.HttpHelpers.JsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Administrator on 2015/9/7.
 */
public class LoginActivity extends BaseActivity {
    Button mBtnbottom_chooser;
    Button mBtnlogin_register;
    LinearLayout mLltop;
    RelativeLayout mRlglobal;
    EditText mEtaccount;
    EditText mEtpassword;
    String LOGIN;
    String REGISTER;
    Button mBtnTopChooser;
    boolean isOn;
    int backColorId[] = new int[3];
    Runnable runForText = new Runnable() {
        @Override
        public void run() {
            if (isOn) {
                if (mBtnlogin_register.getText().toString().equals(LOGIN)) {
                    mBtnlogin_register.setText(REGISTER);
                } else {
                    mBtnlogin_register.setText(LOGIN);
                }
            }
        }
    };

    Runnable runForColor = new Runnable() {
        @Override
        public void run() {
            if (isOn) {
                if (mBtnlogin_register.getText().toString().equals(LOGIN)) {
                    mBtnbottom_chooser.setText(REGISTER);
                    mBtnTopChooser.setText("");
                    mRlglobal.setBackgroundColor(backColorId[1]);
                    mLltop.setBackgroundColor(backColorId[0]);
                    mBtnTopChooser.setBackgroundColor(backColorId[1]);
                    mBtnbottom_chooser.setBackgroundColor(backColorId[2]);
                } else {
                    mBtnTopChooser.setText(LOGIN);
                    mBtnbottom_chooser.setText("");
                    mRlglobal.setBackgroundColor(backColorId[0]);
                    mLltop.setBackgroundColor(backColorId[1]);
                    mBtnTopChooser.setBackgroundColor(backColorId[2]);
                    mBtnbottom_chooser.setBackgroundColor(backColorId[0]);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isOn = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isOn = true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        iniUI();
    }

    private void iniUI() {
        backColorId[0] = getResources().getColor(R.color.summerparty2);
        backColorId[1] = getResources().getColor(R.color.summerparty4);
        backColorId[2] = getResources().getColor(R.color.summerparty3);
        LOGIN = getResources().getString(R.string.login);
        REGISTER = getResources().getString(R.string.register);
        mBtnbottom_chooser = (Button) findViewById(R.id.btn_bottom_chooser);
        mBtnlogin_register = (Button) findViewById(R.id.btn_login_register);
        mBtnTopChooser = (Button) findViewById(R.id.btn_top_chooser);
        mRlglobal = (RelativeLayout) findViewById(R.id.rl_global);
        mLltop = (LinearLayout) findViewById(R.id.ll_top);
        mEtaccount=(EditText)findViewById(R.id.et_account);
        mEtpassword=(EditText)findViewById(R.id.et_password);

        mEtpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtpassword.setError(null);
            }
        });
        mBtnTopChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int distance = DensityUtils.getWindowHeight(LoginActivity.this)
                        - mBtnbottom_chooser.getHeight() - DensityUtils.getStatusBarHei(LoginActivity.this);
                mBtnTopChooser.setClickable(false);
                mBtnbottom_chooser.setClickable(true);
                mBtnTopChooser.startAnimation(AnimationHelper.moveDown(distance));
                mBtnlogin_register.startAnimation(AnimationHelper.upsideDown());
                new Handler().postDelayed(runForText, 500);
                new Handler().postDelayed(runForColor, 1100);
            }
        });
        mBtnbottom_chooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int distance = DensityUtils.getWindowHeight(LoginActivity.this)
                        - mBtnbottom_chooser.getHeight() - DensityUtils.getStatusBarHei(LoginActivity.this);
                mBtnbottom_chooser.setClickable(false);
                mBtnTopChooser.setClickable(true);
                mBtnbottom_chooser.startAnimation(AnimationHelper.moveUp(distance));
                mBtnlogin_register.startAnimation(AnimationHelper.upsideDown());
                new Handler().postDelayed(runForText, 500);
                new Handler().postDelayed(runForColor, 1000);
            }
        });

        mBtnlogin_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBtnlogin_register.getText().toString().equals(LOGIN)) {
                    login();
                } else {
                    register();
                }
            }
        });
    }

    private void login() {
        String account=mEtaccount.getText().toString();
        String password=mEtpassword.getText().toString();
        if(TextUtils.isEmpty(account)||TextUtils.isEmpty(password)){
            mEtpassword.setText("");
            mEtpassword.setError("请正确填写");
            mEtpassword.requestFocus();
            return ;
        }
        RequestParams params =new RequestParams();
        params.put("account",account);
        params.put("password",password);
        HttpClient.post(LoginActivity.this,"user/login",params,new JsonResponseHandler(LoginActivity.this){
            @Override
            public void onSuccess(JSONObject object) {
                super.onSuccess(object);
                try {
                    JSONObject data =object.getJSONObject("data");
                    if(data.has("userinfo")){
                        JSONObject user=data.getJSONObject("userinfo");
                        JoinApplication.getInstance().setToken(user.getString("token"), LoginActivity.this);
                        JoinApplication.getInstance().setUid(user.getInt("userid"), LoginActivity.this);
                        User.insertOrUpdate(user);
                        SharedPreferManager.getInstance(LoginActivity.this)
                                .setSystemManager().setInt(SharedPrefParameter.ISFIRSTLOGING,1);
                        Intent intent=new Intent(LoginActivity.this,HomePageActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void register() {
        String account=mEtaccount.getText().toString();
        String passwd=mEtpassword.getText().toString();
        if(TextUtils.isEmpty(account)||TextUtils.isEmpty(passwd)){
            mEtpassword.setError("未填写");
            mEtpassword.requestFocus();
            return;
        }
        if(account.length()<6||passwd.length()<6||account.length()>16||passwd.length()>16){
            mEtpassword.setError("账号密码长度[6,16]");
            mEtpassword.requestFocus();
            return;
        }
        RequestParams params =new RequestParams();
        params.put("account",account);
        params.put("password",passwd);
        HttpClient.post(LoginActivity.this,"user/register",params,new JsonResponseHandler(LoginActivity.this){
            @Override
            public void onSuccess(JSONObject object) {
                super.onSuccess(object);
                toast("注册成功");
            }
        });
    }


}
