package com.season.bungejoin.bungejoin.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.EmbossMaskFilter;
import android.graphics.Path;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.season.bungejoin.bungejoin.model.User;
import com.season.bungejoin.bungejoin.R;
import com.season.bungejoin.bungejoin.utils.DensityUtils;

import java.util.Random;

/**
 * Created by Administrator on 2015/9/20.
 */
public class SelfInfoView extends View {
    int centerX;
    int centerY;
    float imgRadius;
    //下面参数用于maskfilter，表示光源方向，强度，反射等级,模糊等级
    float []lightDirection ={1,1,2};
    float ambient=0.6f;
    float specular=6;
    float blur=3.5f;
    TextPaint paint;
    User user;
    EmbossMaskFilter embossFilter;
    public SelfInfoView(Context context) {
        super(context);
        ini();
    }

    public SelfInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ini();
    }

    public SelfInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ini();
    }

    protected void ini(){
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        imgRadius= 0.5f*getContext().getResources().getDimension(R.dimen.cover_image_radius);
        paint =new TextPaint();
        //浮雕效果
        embossFilter=new EmbossMaskFilter(lightDirection, ambient, specular, blur);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCenter(int x,int y){
        this.centerX=x;
        this.centerY=y;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radius=imgRadius;
        paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.textsize_big));
        paint.setColor(getResources().getColor(R.color.covertext0));
        radius=imgRadius + DensityUtils.dip2px(getContext(), 50);
        canvas.drawTextOnPath("个性签名>>" + user.motto, getCirclePath
                (radius), radius * 3, 1, paint);
        radius+=DensityUtils.dip2px(getContext(),20);
        paint.setColor(getResources().getColor(R.color.covertext3));
        canvas.drawTextOnPath("账号>>" + user.account, getCirclePath(radius), 0, 1, paint);
        paint.setColor(getResources().getColor(R.color.covertext4));
        canvas.drawTextOnPath("邮箱>" + user.email, getRandomPath(), 0, 1, paint);

        paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.textsize_large));
        paint.setMaskFilter(embossFilter);
        paint.setColor(getResources().getColor(R.color.covertext2));
        canvas.drawText(user.nickname, centerX - paint.measureText(user.nickname) / 2, centerY + imgRadius + 10, paint);
    }

    protected Path getCirclePath(float radius){
        Path path=new Path();
        path.addCircle(centerX, centerY, radius, Path.Direction.CW);
        return path;
    }

    protected Path getRandomPath(){
        Path path=new Path();
        int x=DensityUtils.dip2px(getContext(), 50);
        int y=DensityUtils.dip2px(getContext(), 50);
        path.moveTo(x, y);
        Random random =new Random();
        for(int i=0 ;i<12;i++){
            x+=50;
            if(random.nextInt(11)>=8){
                y+=random.nextInt(15);
            }
            else{
                y-=random.nextInt(15);
            }
            path.lineTo(x, y);
        }
        return path;
    }

}
