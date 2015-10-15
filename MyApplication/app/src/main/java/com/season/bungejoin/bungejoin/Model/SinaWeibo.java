package com.season.bungejoin.bungejoin.model;

import android.content.Context;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.season.bungejoin.bungejoin.Constant.SharedPrefParameter;
import com.season.bungejoin.bungejoin.storage.SharedPreferManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Administrator on 2015/10/1.
 */
@Table(name = "sinaweibo")
public class SinaWeibo extends Model {

    @Column(name = "create_at")
    public String create_at;

    @Column(name = "weiboid")
    public long id;

    @Column(name = "mid")
    public long mid;

    @Column(name = "has_unread")
    public int has_unread;

    @Column(name = "idstr")
    public String idstr;

    @Column(name = "text")
    public String text;

    //来源
    @Column(name = "source")
    public String source;

    //收藏了？
    @Column(name = "favorited")
    public boolean favorited;

    //被截断？
    @Column(name = "truncted")
    public boolean truncted;

    //缩略图
    @Column(name = "thumbnail_pic")
    public String thumbnail_pic;

    @Column(name = "bmiddle_pic")
    public String bmiddle_pic;

    @Column(name = "original_pic")
    public String original_pic;

    @Column(name = "userid")
    public long userid;

    //被转发微博原内容
    @Column(name = "retweeted_status")
    public SinaWeibo retweeted_status;

    @Column(name = "reposts_count")
    public int reposts_count;

    @Column(name = "comments_count")
    public int comments_count;

    @Column(name = "attitudes_count")
    public int attitudes_count;

    //该object中type取值，0：普通微博，1：私密微博，3：指定分组微博，4：密友微博；list_id为分组的组号
    @Column(name = "visibletype")
    public int visibletype;

    @Column(name = "visiblelistid")
    public int visiblelistid;

    @Column(name = "piccount")
    public int piccount;

    @Column(name = "picurl1")
    public String picurl1;

    @Column(name = "picurl2")
    public String picurl2;

    @Column(name = "picurl3")
    public String picurl3;

    @Column(name = "picurl4")
    public String picurl4;

    @Column(name = "picurl5")
    public String picurl5;

    @Column(name = "picurl6")
    public String picurl6;

    @Column(name = "picurl7")
    public String picurl7;

    @Column(name = "picurl8")
    public String picurl8;

    @Column(name = "picurl9")
    public String picurl9;

    public static SinaWeibo updateWeibo(JSONObject jsonObject) {
        SinaWeibo weibo=null;
        if (jsonObject.has("id")) {
            try {
                weibo = getWeibo(jsonObject.getLong("id"));
                if (weibo == null) weibo = new SinaWeibo();

                if (jsonObject.has("pic_urls")) {
                    JSONArray array = jsonObject.getJSONArray("pic_urls");
                    for (int i = 0; ; i++) {
                        if (!array.isNull(i)) {
                            JSONObject object = array.getJSONObject(i);
                            switch (i) {
                                case 0: {
                                    weibo.picurl1 = object.getString("thumbnail_pic");
                                }
                                break;
                                case 1: {
                                    weibo.picurl2 = object.getString("thumbnail_pic");
                                }
                                break;
                                case 2: {
                                    weibo.picurl3 = object.getString("thumbnail_pic");
                                }
                                break;
                                case 3: {
                                    weibo.picurl4 = object.getString("thumbnail_pic");
                                }
                                break;
                                case 4: {
                                    weibo.picurl5 = object.getString("thumbnail_pic");
                                }
                                break;
                                case 5: {
                                    weibo.picurl6 = object.getString("thumbnail_pic");
                                }
                                break;
                                case 6: {
                                    weibo.picurl7 = object.getString("thumbnail_pic");
                                }
                                break;
                                case 7: {
                                    weibo.picurl8 = object.getString("thumbnail_pic");
                                }
                                break;
                                case 8: {
                                    weibo.picurl9 = object.getString("thumbnail_pic");
                                }
                                break;
                            }
                        } else {
                            weibo.piccount = i;
                            break;
                        }
                    }
                }
                else weibo.piccount=0;

                if (jsonObject.has("visible")) {
                    JSONObject visible = jsonObject.getJSONObject("visible");
                    if (visible.has("type"))
                        weibo.visibletype = visible.getInt("type");

                    if (visible.has("list_id"))
                        weibo.visiblelistid = visible.getInt("list_id");
                }
                if (jsonObject.has("attitudes_count"))
                    weibo.attitudes_count = jsonObject.getInt("attitudes_count");

                if (jsonObject.has("truncated"))
                    weibo.truncted = jsonObject.getBoolean("truncated");

                if (jsonObject.has("thumbnail_pic")){
                    weibo.thumbnail_pic = jsonObject.getString("thumbnail_pic");
                    weibo.piccount=weibo.piccount==0?1:weibo.piccount;
                }

                if (jsonObject.has("id"))
                    weibo.id = jsonObject.getLong("id");

                if (jsonObject.has("original_pic"))
                    weibo.original_pic = jsonObject.getString("original_pic");

                if (jsonObject.has("reposts_count"))
                    weibo.reposts_count = jsonObject.getInt("reposts_count");

                if (jsonObject.has("created_at"))
                    weibo.create_at = jsonObject.getString("created_at");

                if (jsonObject.has("comments_count"))
                    weibo.comments_count = jsonObject.getInt("comments_count");

                if (jsonObject.has("text"))
                    weibo.text = jsonObject.getString("text");

                if (jsonObject.has("bmiddle_pic"))
                    weibo.bmiddle_pic = jsonObject.getString("bmiddle_pic");

                if (jsonObject.has("favorited"))
                    weibo.favorited = jsonObject.getBoolean("favorited");

                if (jsonObject.has("user")) {
                    JSONObject userdata = jsonObject.getJSONObject("user");
                    SinaUser user=SinaUser.updateUser(userdata);
                    if (user!=null)
                    weibo.userid=user.id;
                }

                if (jsonObject.has("retweeted_status")) {
                    JSONObject retweeted = jsonObject.getJSONObject("retweeted_status");
                    weibo.retweeted_status=updateWeibo(retweeted);
                }
                weibo.save();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return weibo;
    }

    public static ArrayList<SinaWeibo> updateWeibos(JSONArray array) {
        ArrayList<SinaWeibo> list = new ArrayList<>();
        for (int i = 0; ; i++) {
            if (!array.isNull(i)) {
                try {
                    SinaWeibo weibo = updateWeibo(array.getJSONObject(i));
                    if (weibo != null)
                        list.add(weibo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else break;
        }
        return list;
    }

    public static SinaWeibo getWeibo(long id) {
        return new Select().from(SinaWeibo.class).where("weiboid=" + id).executeSingle();
    }

    public static ArrayList<SinaWeibo> getCached(Context context) {
        ArrayList<SinaWeibo> list = new ArrayList<>();
        String data=SharedPreferManager.getInstance(context).setSinaInfoManager()
                .getString(SharedPrefParameter.SINAWEIBOCACHE);
        String [] ids=data.split(";");
        for (String id:ids){
            SinaWeibo weibo =getWeibo(Long.parseLong(id));
            if(weibo!=null)
            list.add(weibo);
        }
        return list;
    }

    public static void setCache(Context context,JSONArray data) {
        StringBuilder text=new StringBuilder();
        //缓存id，格式为id;id;id...
        try {
            for (int i = 0; ; i++) {
                if (!data.isNull(i)) {
                    JSONObject object = data.getJSONObject(i);
                    text.append(object.getLong("id"));
                    text.append(";");
                }else break;
            }
            SharedPreferManager.getInstance(context).setSinaInfoManager()
                    .setString(SharedPrefParameter.SINAWEIBOCACHE,text.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTime(){
        Date date=null;
        String time =null;
        SimpleDateFormat sdf =new SimpleDateFormat("EEE MMM dd hh:mm:ss zzzzz yyyy", Locale.US);
        try {
            date=sdf.parse(this.create_at);
            long dis=date.getTime()-System.currentTimeMillis();
            if(dis>1000*60*60*48){
                sdf=new SimpleDateFormat("yyyy-MM-dd");
                text=sdf.format(date);
            }
            if (dis>1000*60*60*24&&dis<1000*60*60*48){
                text="前天";
            }
            else if(dis>1000*60*60*24){
                text="一天内";
            }
            else if(dis>1000*60*60*12){
                text="半天内";
            }
            else if(dis<1000*60){
                time="一分钟之内";
            }else if(dis<1000*60*5) {
                time = "五分钟之内";
            }else if(dis<1000*60*30){
                time="半小时内";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public String[] getThumbnailPics(){
        if(piccount==0) return null;
        String []urls =new String[piccount];
        switch (piccount){
            case 9:urls[8]=picurl9;
            case 8:urls[7]=picurl8;
            case 7:urls[6]=picurl7;
            case 6:urls[5]=picurl6;
            case 5:urls[4]=picurl5;
            case 4:urls[3]=picurl4;
            case 3:urls[2]=picurl3;
            case 2:urls[1]=picurl2;
            case 1:urls[0]=picurl1;
                default:break;
        }
        return urls;
    }

    public static String getLargePic(String url){
        String result =url;
        if(url.contains("thumbnail")){
            result=url.replace("thumbnail","large");
        }
        return result;
    }

}
