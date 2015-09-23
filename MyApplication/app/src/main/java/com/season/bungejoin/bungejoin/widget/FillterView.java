package com.season.bungejoin.bungejoin.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.Random;
import java.util.Stack;

/**
 * Created by Administrator on 2015/9/11.
 */
public class FillterView extends ImageView {
    Job job;
    Bitmap mBitmap;
    int paintColor ;//当前画笔颜色
    int curColor;//点击处颜色
    int borderColor = Color.BLACK;//边界颜色
    int iniPixels[];
    Stack<Point> mStack = new Stack<>();
    boolean isRandom=true;
    public FillterView(Context context) {
        super(context);
    }

    public FillterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FillterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setListener(Job j) {
        job = j;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, getDrawable().getIntrinsicHeight() * width / getDrawable()
                .getIntrinsicWidth());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int posX = (int) event.getX();
            int posY = (int) event.getY();
            Log.i("COORDINATE_VIEW","X->"+posX+"Y->"+posY);
            iniPixels = new int[mBitmap.getWidth() * mBitmap.getHeight()];
            mBitmap.getPixels(iniPixels, 0, mBitmap.getWidth(), 0, 0, mBitmap.getWidth(), mBitmap.getHeight());
            curColor = mBitmap.getPixel(posX, posY);
            job.onWorking();
            if(isRandom){
                randomColor();
            }
            areaFilling(posX, posY);
            job.postworking();
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    //填充一块区域
    protected void areaFilling(int x, int y) {
        mStack.push(new Point(x, y));
        while (!mStack.isEmpty()) {
            Point point = mStack.pop();
            int leftIndex = lineLeftFilling(point.x, point.y) - 1;
            int rightIndex = lineRightFilling(point.x, point.y) - 1;
            leftIndex = point.x - leftIndex;
            rightIndex = point.x + rightIndex;
            //分别扫描上或下一行
            if (point.y >= y) {
                if (point.y + 1 < mBitmap.getHeight()) {
                    findSeedHor(leftIndex, rightIndex, point.y + 1, true);
                }
            }
            if (point.y <= y) {
                if (point.y - 1 > 0) {
                    findSeedHor(leftIndex, rightIndex, point.y - 1, false);
                }
            }

        }
        mStack.push(new Point(x, y));
        while (!mStack.isEmpty()) {
            Point point = mStack.pop();
            int upIndex = lineUpFilling(point.x, point.y) - 1;
            int downIndex = lineDownFilling(point.x, point.y) - 1;
            upIndex = point.y - upIndex;
            downIndex = point.y + downIndex;
            //分别扫描上或下一列
            if (point.x >= x) {
                if (point.x + 1 < mBitmap.getHeight()) {
                    findSeedVer(upIndex, downIndex, point.x + 1, true);
                }
            }
            if (point.x <= x) {
                if (point.x - 1 > 0) {
                    findSeedVer(upIndex, downIndex, point.x - 1, false);
                }
            }
        }
        mBitmap.setPixels(iniPixels, 0, mBitmap.getWidth(), 0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        setImageDrawable(new BitmapDrawable(getResources(), mBitmap));
    }

    //填充线左边,及当前点
    protected int lineLeftFilling(int x, int y) {
        int counter = 0;
        while (!isBorder(x, y) || isTouchingColor(x, y)) {
            iniPixels[point2Index(x, y)] = paintColor;
            x--;
            counter++;
        }
        return counter;
    }

    //填充线右边
    protected int lineRightFilling(int x, int y) {
        int counter = 0;
        while (!isBorder(x, y) || isTouchingColor(x, y)) {
            iniPixels[point2Index(x, y)] =paintColor;
            x++;
            counter++;
        }
        return counter;
    }

    protected int lineUpFilling(int x,int y){
        int counter = 0;
        while (!isBorder(x, y) || isTouchingColor(x, y)) {
            iniPixels[point2Index(x, y)] =paintColor;
            y--;
            counter++;
        }
        return counter;
    }

    protected int lineDownFilling(int x,int y){
        int counter = 0;
        while (!isBorder(x, y) || isTouchingColor(x, y)) {
            iniPixels[point2Index(x, y)] =paintColor;
            y++;
            counter++;
        }
        return counter;
    }

    protected void findSeedVer(int upBoundary, int downboundary, int x, boolean isUp) {
        boolean isFoundU2D = false;
        int up = upBoundary;
        int down = downboundary;
        while (downboundary >= upBoundary) {
            //找到非点击颜色
            if (!isTouchingColor(x, downboundary)) {
                //上边是不是点击颜色
                    if (isTouchingColor(x, downboundary-1)) {
                        mStack.push(new Point(x, downboundary-1));
                        isFoundU2D = true;
                        break;
                    }
            }
            downboundary--;
        }
        if (!isFoundU2D ) {
            if (!isBorder(x, (up+down)/2)
                    && isTouchingColor(x, (up+down)/2)) {
                mStack.push(new Point(x, (up+down)/2));
            }
        }
    }

    //寻找种子点
    protected void findSeedHor(int leftBoundary, int rightBoundary, int y, boolean isUp) {
        boolean isFoundR2L = false;
        boolean isFoundL2R = false;
        int left = leftBoundary;
        int right = rightBoundary;
        while (rightBoundary >= leftBoundary) {
            //找到非点击颜色
            if (!isTouchingColor(rightBoundary, y)) {
                //左边是不是点击颜色
                if(!isFoundL2R){
                    if (isTouchingColor(rightBoundary +1, y)) {
                        mStack.push(new Point(rightBoundary + 1, y));
                        isFoundL2R = true;
                    }
                }
                if(isFoundL2R){
                    if (isTouchingColor(rightBoundary -1, y)) {
                        mStack.push(new Point(rightBoundary -1, y));
                        isFoundR2L = true;
                        break;
                    }
                }
            }
            rightBoundary--;
        }
        if (!isFoundR2L && !isFoundL2R) {
            if (!isBorder((left + right) / 2, y)
                    && isTouchingColor((left + right) / 2, y)) {
                mStack.push(new Point((left + right) / 2, y));
            }
//            else if (!isBorder((left + right) / 2 + 10, y)
//                    && isTouchingColor((left + right) / 2 + 10, y)) {
//                mStack.push(new Point((left + right) / 2 + 10, y));
//            }
        }
    }

    //判断是否点击处颜色
    protected Boolean isTouchingColor(int x, int y) {
        boolean result = false;
        if (!isBorder(x, y)) {
            int color = mBitmap.getPixel(x, y);
            if (curColor == color) {
                result = true;
            }
        }
        return result;
    }

    protected Boolean isBorder(int x, int y) {
        if (x <= 0 || x >= mBitmap.getWidth() || y <= 0 || y >= mBitmap.getHeight()) {
            return true;
        }
        else {
            return mBitmap.getPixel(x, y) == borderColor;
        }
    }

    protected int point2Index(int x, int y) {
        return y * mBitmap.getWidth() + x;
    }

    protected void randomColor() {
        Random random=new Random();
        paintColor = Color.argb(255,random.nextInt(256),random.nextInt(256),random.nextInt(256));
    }

    public void preProc(Bitmap bitmap) {
        Bitmap bm = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        int w = bm.getWidth();
        int h = bm.getHeight();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                //得到当前像素的值
                int col = bm.getPixel(i, j);
                //得到alpha通道的值
                int alpha = col & 0xFF000000;
                //得到图像的像素RGB的值
                int red = (col & 0x00FF0000) >> 16;
                int green = (col & 0x0000FF00) >> 8;
                int blue = (col & 0x000000FF);
                // 用公式X = 0.3×R+0.59×G+0.11×B计算出X代替原来的RGB
                int gray = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                //对图像进行二值化处理
                if (gray <= 95) {
                    gray = 0;
                } else {
                    gray = 255;
                }
                // 新的ARGB
                int newColor = alpha | (gray << 16) | (gray << 8) | gray;
                //设置新图像的当前像素值
                bm.setPixel(i, j, newColor);
            }
        }
        mBitmap = Bitmap.createScaledBitmap(bm, getMeasuredWidth(), getMeasuredHeight(), false);
        setImageBitmap(bm);
        bitmap.recycle();
    }

    public void preProc() {
        BitmapDrawable drawable = (BitmapDrawable) getDrawable();
        preProc(drawable.getBitmap());
    }

    public interface Job {
        void onWorking();
        void postworking();
    }

    public void setPaintColor(int color){
        paintColor=color;
        isRandom=false;
    }

    public void setIsRandom(boolean random){
        isRandom=random;
    }

}
