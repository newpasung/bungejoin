package com.season.bungejoin.bungejoin.model;

import android.content.Context;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.season.bungejoin.bungejoin.JoinApplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/9/12.
 */
@Table(name = "user")
public class User extends Model {

    @Column(name="userid")
    public int userid;

    @Column(name = "account")
    public String account;

    @Column(name = "nickname")
    public String nickname;

    @Column(name = "motto")
    public String motto;

    @Column(name = "email")
    public String email;

    @Column(name = "phonenumber")
    public String phonenumber;

    @Column(name = "sex")
    public int sex;

    @Column(name = "avatar")
    public String avatar;

    @Column(name ="cover")
    public String cover;
    public User() {
        super();
    }

    public static void insertOrUpdate(JSONObject jsonObject) {
        User user=null;
        try {
            int uid=jsonObject.getInt("userid");
            user=getUser(uid);
            if(user==null){
                user=new User();
                user.userid=uid;
            }
            if (jsonObject.has("account")) {
                user.account = jsonObject.getString("account");
            }
            if(jsonObject.has("nickname")){
                user.nickname=jsonObject.getString("nickname");
            }
            if(jsonObject.has("motto")){
                user.motto=jsonObject.getString("motto");
            }
            if(jsonObject.has("email")){
                user.email=jsonObject.getString("email");
            }
            if(jsonObject.has("phonenumber")){
                user.phonenumber=jsonObject.getString("phonenumber");
            }
            if(jsonObject.has("sex")){
                user.sex=jsonObject.getInt("sex");
            }
            if(jsonObject.has("avatar")){
                user.avatar=jsonObject.getString("avatar");
            }
            if(jsonObject.has("cover")){
                user.cover=jsonObject.getString("cover");
            }
            user.save();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static User getUser(int id){
        return new Select().from(User.class).where("userid="+id).executeSingle();
    }

    public static void  modify_avatar(Context context,String url){
        User user=getUser(JoinApplication.getInstance().getUid(context));
        user.avatar=url;
        user.save();
    }

}
