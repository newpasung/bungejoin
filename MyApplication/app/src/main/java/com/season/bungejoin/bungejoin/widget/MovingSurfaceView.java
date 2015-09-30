package com.season.bungejoin.bungejoin.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.season.bungejoin.bungejoin.activity.MessageActivity;
import com.season.bungejoin.bungejoin.activity.SecurityActivity;
import com.season.bungejoin.bungejoin.activity.SystemSettingActivity;
import com.season.bungejoin.bungejoin.activity.UserinfoActivity;
import com.season.bungejoin.bungejoin.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/9/23.
 */
public class MovingSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    final int CHOOSERCOUNT = 4;
    Canvas mCanvas;
    SurfaceHolder mHolder;
    List<String> selections = new ArrayList<>();
    List<Point> pointList = new ArrayList<>();
    TimerTask task;
    Timer timer;
    float radius = 70;
    float[] xmove = {10, 10, 10, 10};
    float[] ymove = {5, 10, 8, 4};
    int upbound;
    int displayW;
    int displayH;
    int[] color = new int[CHOOSERCOUNT];
    Bitmap[] bitmaps = new Bitmap[CHOOSERCOUNT];
    Class [] clses=new Class[CHOOSERCOUNT];

    public MovingSurfaceView(Context context) {
        super(context);
        init();
    }

    public MovingSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MovingSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        color[0] = getResources().getColor(R.color.gradient0);
        color[1] = getResources().getColor(R.color.gradient1);
        color[2] = getResources().getColor(R.color.gradient2);
        color[3] = getResources().getColor(R.color.gradient3);
        bitmaps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.usersetting);
        bitmaps[1] = BitmapFactory.decodeResource(getResources(), R.drawable.setting);
        bitmaps[2] = BitmapFactory.decodeResource(getResources(), R.drawable.message);
        bitmaps[3] = BitmapFactory.decodeResource(getResources(), R.drawable.security);
        clses[0]= UserinfoActivity.class;
        clses[1]= SystemSettingActivity.class;
        clses[2]= MessageActivity.class;
        clses[3]= SecurityActivity.class;
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        //消去黑色背景
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int counter = 0;
            int index = -1;
            float x = event.getX();
            float y = event.getY();
            for (int i = 0; i < pointList.size(); i++) {
                Point point = pointList.get(i);
                if (calDistance(point.x, x, point.y, y) <= radius) {
                    counter++;
                    index = i;
                }
            }
            if (counter == 1) {
                Intent intent=new Intent(getContext(),clses[index]);
                getContext().startActivity(intent);
                return true;
            } else {
                return false;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawPic();
        mCanvas = mHolder.lockCanvas(new Rect(0, 0, 0, 0));
        mHolder.unlockCanvasAndPost(mCanvas);
        task = new TimerTask() {
            @Override
            public void run() {
                movePoints();
                drawPic();
            }
        };
        timer = new Timer();
        timer.schedule(task, 0, 50);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        destroy();
    }

    public void init(List<String> s, List<Point> points) {
        selections = s;
        pointList = points;
    }

    protected void drawPic() {
        Paint cPaint = new Paint();
        cPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mCanvas = mHolder.lockCanvas();
        mCanvas.drawPaint(cPaint);
        Paint paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < pointList.size(); i++) {
            Point point = pointList.get(i);
            ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
            drawable.getPaint().setShader(new BitmapShader(bitmaps[i], Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            drawable.setBounds(point.x - (int) radius, point.y - (int) radius
                    , point.x + (int) radius, point.y + (int) radius);
            drawable.draw(mCanvas);
            paint.setColor(color[i]);
            mCanvas.drawCircle(point.x, point.y, radius, paint);
        }
        mHolder.unlockCanvasAndPost(mCanvas);
    }

    protected void movePoints() {
        for (int i = 0; i < pointList.size(); i++) {
            Point point = pointList.get(i);
            if (point.x + xmove[i] - radius < 0 || point.x + xmove[i] + radius > getDisplayW()) {
                xmove[i] = -xmove[i];
                point.x = point.x + (int) xmove[i];
                point.y = point.y + (int) ymove[i];
                continue;
            }
            if (point.y + ymove[i] - radius < upbound || point.y + ymove[i] + radius > getDisplayH()) {
                ymove[i] = -ymove[i];
                point.x = point.x + (int) xmove[i];
                point.y = point.y + (int) ymove[i];
                continue;
            }
            for (int j = i + 1; j < pointList.size(); j++) {
                Point point1 = pointList.get(j);
                if (calDistance(point.x + xmove[i], point1.x, point.y + xmove[i], point1.y) <= 2 * radius) {
                    xmove[i] = -xmove[i];
                    ymove[i] = -ymove[i];
                    break;
                }
            }
            point.x = point.x + (int) xmove[i];
            point.y = point.y + (int) ymove[i];
        }
    }

    public void destroy() {
        timer.cancel();
        timer.purge();
    }

    protected float calDistance(float x1, float x2, float y1, float y2) {
        float dis;
        dis = Math.round(Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)));
        return dis;
    }

    public void setDisplayH(int displayH) {
        this.displayH = displayH;
    }

    public void setDisplayW(int displayW) {
        this.displayW = displayW;
    }

    public void setUpbound(int upbound) {
        this.upbound = upbound;
    }

    public float getRadius() {
        return radius;
    }

    public int getDisplayW() {
        return displayW;
    }

    public int getDisplayH() {
        return displayH;
    }
}
