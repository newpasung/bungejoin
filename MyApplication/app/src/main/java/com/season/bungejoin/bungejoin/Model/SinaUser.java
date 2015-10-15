package com.season.bungejoin.bungejoin.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/10/1.
 */
@Table(name = "sinauser")
public class SinaUser extends Model {

    @Column(name = "sinauserid")
    public long id;

    //友好显示名称
    @Column(name="name")
    public String name;

    //用户昵称
    @Column(name="screen_name")
    public String screen_name;

    @Column(name="created_at")
    public String created_at;

    @Column(name = "remark")
    public String remark;

    @Column(name="location")
    public String location;

    @Column(name = "verified_contact_email")
    public String verified_email;

    @Column(name = "verified_reason")
    public String verfied_reason;

    @Column(name = "statuses_count")
    public int statuses_count;

    @Column(name = "city")
    public int city;

    @Column(name = "favourities_count")
    public int favourities_count;

    @Column(name = "verified")
    public boolean verified;

    @Column(name = "description")
    public String description;

    @Column(name = "verified_contact_name")
    public String verified_contact_name;

    @Column(name = "verified_level")
    public int verified_level;

    @Column(name = "province")
    public int province;

    @Column(name = "gender")
    public String gender;

    @Column(name = "cover_image")
    public String cover_image;

    @Column(name = "friend_count")
    public int friend_count;

    @Column(name = "profile_image_url")
    public String profile_image_url;

    @Column(name = "profile_url")
    public String profile_url;

    @Column(name = "avatar_large")
    public String avatar_large;

    @Column(name = "avatar_hd")
    public String avatar_hd;

    @Column(name="follow_me")
    public boolean follow_me;

    @Column(name="following")
    public boolean following;

    @Column(name = "bi_followers_count")
    public int bi_followers_count;

    @Column(name = "followers_count")
    public int followers_count;

    public static SinaUser updateUser(JSONObject jsonObject){
        SinaUser user =null;
        try {
        if(jsonObject.has("id")){
            user=getUser(jsonObject.getLong("id"));
            if(user==null) user=new SinaUser();
            user.id=jsonObject.getLong("id");

            if(jsonObject.has("remark")){
                user.remark=jsonObject.getString("remark");
            }

            if(jsonObject.has("location")){
                user.location=jsonObject.getString("location");
            }

            if (jsonObject.has("verified_contact_email")){
                user.verified_email=jsonObject.getString("verified_contact_email");
            }

            if(jsonObject.has("verified_reason")){
                user.verfied_reason=jsonObject.getString("verified_reason");
            }

            if(jsonObject.has("statuses_count")){
                user.statuses_count=jsonObject.getInt("statuses_count");
            }

            if(jsonObject.has("city")){
                user.city=jsonObject.getInt("city");
            }

            if (jsonObject.has("favourites_count"))
                user.favourities_count=jsonObject.getInt("favourites_count");

            if (jsonObject.has("verified"))
                user.verified=jsonObject.getBoolean("verified");

            if (jsonObject.has("description"))
                user.description=jsonObject.getString("description");

            if (jsonObject.has("verified_contact_name"))
                user.verified_contact_name=jsonObject.getString("verified_contact_name");

            if (jsonObject.has("province"))
                user.province=jsonObject.getInt("province");

            if(jsonObject.has("gender"))
                user.gender=jsonObject.getString("gender");

            if(jsonObject.has("cover_image"))
                user.cover_image=jsonObject.getString("cover_image");

            if (jsonObject.has("verified_level"))
                user.verified_level=jsonObject.getInt("verified_level");

            if(jsonObject.has("friends_count"))
                user.friend_count=jsonObject.getInt("friends_count");

            //粉丝
            if (jsonObject.has("followers_count"))
                user.followers_count=jsonObject.getInt("followers_count");

            if(jsonObject.has("follow_me"))
                user.follow_me=jsonObject.getBoolean("follow_me");

            if (jsonObject.has("following"))
                user.following=jsonObject.getBoolean("following");

            if (jsonObject.has("profile_image_url"))
                user.profile_image_url=jsonObject.getString("profile_image_url");

            if(jsonObject.has("name"))
                user.name=jsonObject.getString("name");

            if(jsonObject.has("created_at"))
                user.created_at=jsonObject.getString("created_at");

            if (jsonObject.has("bi_followers_count"))
                user.bi_followers_count=jsonObject.getInt("bi_followers_count");

            if(jsonObject.has("avatar_hd"))
                user.avatar_hd=jsonObject.getString("avatar_hd");

            if (jsonObject.has("avatar_large"))
                user.avatar_large=jsonObject.getString("avatar_large");

            if(jsonObject.has("screen_name"))
                user.screen_name=jsonObject.getString("screen_name");

            user.save();
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static SinaUser getUser(long id){
        return new Select().from(SinaUser.class).where("sinauserid="+id).executeSingle();
    }

    public String getHdAvatar(){
        return this.avatar_hd;
    }

}
