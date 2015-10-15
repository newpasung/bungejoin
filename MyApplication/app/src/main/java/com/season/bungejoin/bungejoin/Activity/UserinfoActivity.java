package com.season.bungejoin.bungejoin.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.season.bungejoin.bungejoin.Constant.HttpParameter;
import com.season.bungejoin.bungejoin.Constant.MRequestCode;
import com.season.bungejoin.bungejoin.Constant.SharedPrefParameter;
import com.season.bungejoin.bungejoin.JoinApplication;
import com.season.bungejoin.bungejoin.R;
import com.season.bungejoin.bungejoin.libs.me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import com.season.bungejoin.bungejoin.task.PicUploadExecutor;
import com.season.bungejoin.bungejoin.model.User;
import com.season.bungejoin.bungejoin.storage.SharedPreferManager;
import com.season.bungejoin.bungejoin.utils.HttpHelpers.HttpClient;
import com.season.bungejoin.bungejoin.utils.HttpHelpers.ImageLoaderHelper;
import com.season.bungejoin.bungejoin.utils.HttpHelpers.JsonResponseHandler;
import com.season.bungejoin.bungejoin.widget.ChoiceDialog;
import com.season.bungejoin.bungejoin.widget.InputDialog;
import com.season.bungejoin.bungejoin.widget.TextDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/9/24.
 */
public class UserinfoActivity  extends SwipeBackActivity {
    int UPLOAD_CATAGORY;
    RecyclerView mRecavatar;
    TextView mTvnickname;
    TextView mTvemail;
    TextView mTvphone;
    TextView mTvmotto;
    TextView mTvsex;
    ImageButton mBtncontroller;
    View mFiller;
    User mUser;
    InputDialog mInputDialog;
    TextDialog waitdialog;
    boolean isUIUp=true;
    String [] avatarUrls={"http://7xlra1.com1.z0.glb.clouddn.com/image/avataradd_avatar.png"
          };
    MyAdapter adapter;
    OnItemClickedListener listener;

    GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_layout);
        iniData();
        iniUI();
        iniListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==MRequestCode.GETAPICTURE&&resultCode==RESULT_OK){
            Uri uri=data.getData();
            String []column={MediaStore.Images.Media.DATA};
            Cursor cursor=getContentResolver().query(uri, column, null, null, null);
            cursor.moveToFirst();
            String path=cursor.getString(cursor.getColumnIndex(column[0]));
            cursor.close();
            PicUploadExecutor taskExcutor=new PicUploadExecutor(this, UPLOAD_CATAGORY);
            taskExcutor.execute(path);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    protected void iniUI(){
        mRecavatar=(RecyclerView)findViewById(R.id.rec_avatar);
        mRecavatar.setHasFixedSize(true);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecavatar.setLayoutManager(manager);
        mRecavatar.setAdapter(adapter);
        mTvemail=(TextView)findViewById(R.id.tv_email);
        mTvmotto=(TextView)findViewById(R.id.tv_motto);
        mTvphone=(TextView)findViewById(R.id.tv_phone);
        mTvsex=(TextView)findViewById(R.id.tv_sex);
        mBtncontroller=(ImageButton)findViewById(R.id.btn_controller);
        mTvnickname=(TextView)findViewById(R.id.tv_nickname);
        mFiller=findViewById(R.id.filler);

        mTvnickname.setText("昵称:"+mUser.nickname);
        mTvemail.setText("邮箱:" + mUser.email);
        mTvphone.setText("手机:"+mUser.phonenumber);
        mTvmotto.setText("座右铭:"+mUser.motto);
        mTvsex.setText("性别:"+(mUser.sex==1?"男":"女"));
    }

    protected void iniData(){
        getAvatars();
        mUser=User.getUser(JoinApplication.getInstance().getUid(this));
        adapter=new MyAdapter();
    }

    protected void iniListener(){
        detector =new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                float y1=e1.getY();
                float y2=e2.getY();
                if(isUIUp&&y2-y1>200){
                    downUI();
                }
                else if(y1-y2>200&&!isUIUp){
                    upUI();
                }
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        });
        mTvsex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mInputDialog=new InputDialog(UserinfoActivity.this,R.style.inputdialog);
                mInputDialog.show(new InputDialog.Listener() {
                    @Override
                    public void onDismiss(String text) {
                        ((TextView)v).setText("性别:"+text);
                    }
                },"修改性别");
            }
        });

        mTvnickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mInputDialog=new InputDialog(UserinfoActivity.this,R.style.inputdialog);
                mInputDialog.show(new InputDialog.Listener() {
                    @Override
                    public void onDismiss(String text) {
                        ((TextView)v).setText("昵称:"+text);
                    }
                },"修改昵称");
            }
        });
        mTvemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mInputDialog=new InputDialog(UserinfoActivity.this,R.style.inputdialog);
                mInputDialog.show(new InputDialog.Listener() {
                    @Override
                    public void onDismiss(String text) {
                        ((TextView)v).setText("邮箱:"+text);
                    }
                },"修改邮箱");
            }
        });
        mTvmotto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mInputDialog=new InputDialog(UserinfoActivity.this,R.style.inputdialog);
                mInputDialog.show(new InputDialog.Listener() {
                    @Override
                    public void onDismiss(String text) {
                        ((TextView) v).setText("座右铭:"+text);
                    }
                }, "修改座右铭");
            }
        });
        mTvphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mInputDialog=new InputDialog(UserinfoActivity.this,R.style.inputdialog);
                mInputDialog.show(new InputDialog.Listener() {
                    @Override
                    public void onDismiss(String text) {
                        ((TextView)v).setText("手机:"+text);
                    }
                },"修改手机号码");
            }
        });

        listener =new OnItemClickedListener() {
            @Override
            public void onItemClick(View v,int pos) {
                if(pos==0){
                    new ChoiceDialog(UserinfoActivity.this)
                            .addItem("修改头像",new ChoiceDialog.OnClickListener(){
                                @Override
                                public void didClick(ChoiceDialog dialog, String itemTitle) {
                                    Intent intent =new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivity(intent);
                                    UPLOAD_CATAGORY = PicUploadExecutor.UPLOAD_CATAGORY_AVATAR;
                                    dialog.dismiss();
                                }
                            })
                            .addItem("修改封面", new ChoiceDialog.OnClickListener() {
                                @Override
                                public void didClick(ChoiceDialog dialog, String itemTitle) {
                                    Intent intent =new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivity(intent);
                                    UPLOAD_CATAGORY = PicUploadExecutor.UPLOAD_CATAGORY_COVER;
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
                else{
                    Intent intent =new Intent(UserinfoActivity.this,ImageBrowserActivity.class);
                    intent.putExtra(ImageBrowserActivity.INTENT_PARAMETER_PICURL,avatarUrls[pos]);
                    intent.putExtra(ImageBrowserActivity.INTENT_PARAMETER_PICCOUNT,1);
                    startActivity(intent);
                }
            }
        };

        mBtncontroller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog=new AlertDialog.Builder(UserinfoActivity.this)
                        .setTitle("确定保存修改")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                uploadInfo();
                                dialog.dismiss();
                                waitdialog =new TextDialog(UserinfoActivity.this);
                                waitdialog.show();

                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNeutralButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                UserinfoActivity.this.finish();
                            }
                        })
                        .show();
            }
        });

    }

    protected void getAvatars(){
        int count=SharedPreferManager.getInstance(this).setUserInfoManager().getInt(SharedPrefParameter.AVATARCOUNT);
        if(count!=0){
            avatarUrls=new String[count];
            for(int i=0;i<count;i++){
                avatarUrls[i]=SharedPreferManager.getInstance(this).setUserInfoManager()
                        .getString(SharedPrefParameter.AVATARURLS + i);
            }
        }
        RequestParams requestParams=new RequestParams();
        HttpClient.post(this, "user/avatarurls", requestParams, new JsonResponseHandler(this) {
            @Override
            public void onSuccess(JSONObject object) {
                super.onSuccess(object);
                try {
                    JSONObject data = object.getJSONObject("data");
                    avatarUrls = new String[data.length()];
                    SharedPreferManager.getInstance(UserinfoActivity.this)
                            .setUserInfoManager().setInt(SharedPrefParameter.AVATARCOUNT, data.length());
                    for (int i = 0; i < data.length(); i++) {
                        avatarUrls[i] = data.getString("avatar" + i);
                        SharedPreferManager.getInstance(UserinfoActivity.this)
                                .setUserInfoManager()
                                .setString(SharedPrefParameter.AVATARURLS + i, avatarUrls[i]);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class MyAdapter extends RecyclerView.Adapter{
        @Override
        public int getItemCount() {
            return avatarUrls.length;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CardView cardView= (CardView)LayoutInflater
                    .from(UserinfoActivity.this).inflate(R.layout.picpreview_card,parent,false);
            return new MyHolder(cardView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final int pos=holder.getLayoutPosition();
            ((MyHolder)holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v,pos);
                }
            });
                ImageLoaderHelper.load(UserinfoActivity.this,avatarUrls[position]
                        ,((MyHolder) holder).imageView,ImageLoaderHelper.avataroption());
        }

        class MyHolder extends RecyclerView.ViewHolder{
            CardView cardView;
            ImageView imageView;
            public MyHolder(View itemView)
            {
                super(itemView);
                cardView=(CardView)itemView;
                imageView=(ImageView)cardView.findViewById(R.id.iv_ssqualpic);
            }
        }
    }

    protected void downUI(){
        mFiller.setVisibility(View.VISIBLE);
        isUIUp=false;
    }

    protected void upUI(){
        mFiller.setVisibility(View.GONE);
        isUIUp=true;
    }

    interface OnItemClickedListener {
        void onItemClick(View v,int pos);
    }

    protected void uploadInfo(){
        String nickname=mTvnickname.getText().toString();
        nickname=nickname.replace("昵称:","");
        String email =mTvemail.getText().toString();
        email=email.replace("邮箱:","");
        String phone =mTvphone.getText().toString();
        phone=phone.replace("手机:","");
        String motto=mTvmotto.getText().toString();
        motto=motto.replace("座右铭:","");
        String sex=mTvsex.getText().toString();
        sex=sex.replace("性别:","");
        RequestParams params=new RequestParams();
        params.put(HttpParameter.EMAIL,email);
        params.put(HttpParameter.NICKNAME,nickname);
        params.put(HttpParameter.PHONENUMBER,phone);
        params.put(HttpParameter.MOTTO,motto);
        params.put(HttpParameter.SEX,sex.equals("男")?1:0);
        HttpClient.post(this,"user/modify_userinfo",params,new JsonResponseHandler(this){
            @Override
            public void onSuccess(JSONObject object) {
                super.onSuccess(object);
                try {
                    JSONObject data =object.getJSONObject("data");
                    User.insertOrUpdate(data);
                    if(waitdialog!=null){
                        waitdialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
