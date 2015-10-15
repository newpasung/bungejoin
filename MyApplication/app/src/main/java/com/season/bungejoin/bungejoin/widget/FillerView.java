package com.season.bungejoin.bungejoin.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.season.bungejoin.bungejoin.R;
import com.season.bungejoin.bungejoin.libs.com.davemorrissey.labs.subscaleview.ImageSource;
import com.season.bungejoin.bungejoin.libs.com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.Random;
import java.util.Stack;

/**
 * Created by Administrator on 2015/10/8.
 */
public class FillerView extends SubsamplingScaleImageView {
    private boolean isRanColor = true;
    int paintColor;
    boolean needExeBm = true;
    private Bitmap mBitmap;
    TextDialog dialog;
    GestureDetector detector = new GestureDetector(getContext(),
            new GestureDetector.OnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {
                    return false;
                }

                @Override
                public void onShowPress(MotionEvent e) {

                }
                @Override
                public boolean onSingleTapUp(MotionEvent event) {
                    float x = event.getX();
                    float y = event.getY();
                    x = viewToSourceX(x);
                    y = viewToSourceY(y);
                    fillColorToSameArea((int) x, (int) y);
                    return false;
                }

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    return false;
                }

                @Override
                public void onLongPress(MotionEvent e) {

                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    return false;
                }
            });
    private Stack<Point> mStacks = new Stack<Point>();

    public FillerView(Context context) {
        super(context);
    }

    public FillerView(Context context, AttributeSet attr) {
        super(context, attr);
        TypedArray array = context.obtainStyledAttributes(attr, R.styleable.fillerview);
        BitmapDrawable drawable = (BitmapDrawable) array.getDrawable(0);
        if (drawable != null) {
            mBitmap = drawable.getBitmap();
            setBitmap(mBitmap);
        }
        setMinimumDpi(80);
        dialog = new TextDialog(getContext());
        dialog.show();
    }

    public void setBitmap(Bitmap bm) {
        animateScaleAndCenter(getScale(),getCenter());
        setImage(ImageSource.bitmap(bm));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(needExeBm){
            BitmapExecutor executor =new BitmapExecutor();
            executor.execute(getCurBitmap());
        }
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * 根据x,y获得改点颜色，进行填充
     *
     * @param x
     * @param y
     */
    private void fillColorToSameArea(int x, int y) {
        Bitmap bm = getCurBitmap().copy(Bitmap.Config.RGB_565, true);
        int pixel = bm.getPixel(x, y);
        int newColor = getColor();

        int w = bm.getWidth();
        int h = bm.getHeight();
        //拿到该bitmap的颜色数组
        int[] pixels = new int[w * h];
        bm.getPixels(pixels, 0, w, 0, 0, w, h);
        //填色
        fillColor(pixels, w, h, pixel, newColor, x, y);
        //重新设置bitmap
        bm.setPixels(pixels, 0, w, 0, 0, w, h);
        setBitmap(bm);
    }

    /**
     * @param pixels   像素数组
     * @param w        宽度
     * @param h        高度
     * @param pixel    当前点的颜色
     * @param newColor 填充色
     * @param i        横坐标
     * @param j        纵坐标
     */
    private void fillColor(int[] pixels, int w, int h, int pixel, int newColor, int i, int j) {
        //步骤1：将种子点(x, y)入栈；
        mStacks.push(new Point(i, j));

        //步骤2：判断栈是否为空，
        // 如果栈为空则结束算法，否则取出栈顶元素作为当前扫描线的种子点(x, y)，
        // y是当前的扫描线；
        while (!mStacks.isEmpty()) {
            /**
             * 步骤3：从种子点(x, y)出发，沿当前扫描线向左、右两个方向填充，
             * 直到边界。分别标记区段的左、右端点坐标为xLeft和xRight；
             */
            Point seed = mStacks.pop();
            //L.e("seed = " + seed.x + " , seed = " + seed.y);
            int count = fillLineLeft(pixels, pixel, w, h, newColor, seed.x, seed.y);
            int left = seed.x - count + 1;
            count = fillLineRight(pixels, pixel, w, h, newColor, seed.x + 1, seed.y);
            int right = seed.x + count;


            /**
             * 步骤4：
             * 分别检查与当前扫描线相邻的y - 1和y + 1两条扫描线在区间[xLeft, xRight]中的像素，
             * 从xRight开始向xLeft方向搜索，假设扫描的区间为AAABAAC（A为种子点颜色），
             * 那么将B和C前面的A作为种子点压入栈中，然后返回第（2）步；
             */
            //从y-1找种子
            if (seed.y - 1 >= 0)
                findSeedInNewLine(pixels, pixel, w, h, seed.y - 1, left, right);
            //从y+1找种子
            if (seed.y + 1 < h)
                findSeedInNewLine(pixels, pixel, w, h, seed.y + 1, left, right);
        }


    }

    /**
     * 在新行找种子节点
     *
     * @param pixels
     * @param pixel
     * @param w
     * @param h
     * @param i
     * @param left
     * @param right
     */
    private void findSeedInNewLine(int[] pixels, int pixel, int w, int h, int i, int left, int right) {
        /**
         * 获得该行的开始索引
         */
        int begin = i * w + left;
        /**
         * 获得该行的结束索引
         */
        int end = i * w + right;

        boolean hasSeed = false;

        int rx = -1, ry = -1;

        ry = i;

        /**
         * 从end到begin，找到种子节点入栈（AAABAAAB，则B前的A为种子节点）
         */
        while (end >= begin) {
            if (pixels[end] == pixel) {
                if (!hasSeed) {
                    rx = end % w;
                    mStacks.push(new Point(rx, ry));
                    hasSeed = true;
                }
            } else {
                hasSeed = false;
            }
            end--;
        }
    }

    /**
     * 往右填色，返回填充的个数
     *
     * @return
     */
    private int fillLineRight(int[] pixels, int pixel, int w, int h, int newColor, int x, int y) {
        int count = 0;

        while (x < w) {
            //拿到索引
//            int index = y * w + x;
            int index = y * w + x;
            if (needFillPixel(pixels, pixel, index)) {
                pixels[index] = newColor;
                count++;
                x++;
            } else {
                break;
            }

        }

        return count;
    }


    /**
     * 往左填色，返回填色的数量值
     *
     * @return
     */
    private int fillLineLeft(int[] pixels, int pixel, int w, int h, int newColor, int x, int y) {
        int count = 0;
        while (x >= 0) {
            //计算出索引
            int index = y * w + x;
            if (needFillPixel(pixels, pixel, index)) {
                pixels[index] = newColor;
                count++;
                x--;
            } else {
                break;
            }

        }
        return count;
    }

    /**
    * 是否需要填色
    * @param pixels bitmap中像素数组*
     *              @param pixel  点击处像素
     *                            @param index 点击处坐标
    * @return true or false
    */
    private boolean needFillPixel(int[] pixels, int pixel, int index) {
        return pixels[index]==pixel;//不等于边界颜色且同于点击颜色
    }

    /**
     * 返回一个随机颜色
     *
     * @return
     */
    private int getColor() {
        int color;
        if (isRanColor) {
            Random random = new Random();
            color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        } else {
            color = paintColor;
        }
        return color;
    }

    public void setPaintColor(int color) {
        paintColor = color;
        isRanColor = false;
    }

    public void setRandomColor() {
        isRanColor = true;
    }

    public Bitmap getCurBitmap() {
        mBitmap = getBitmap();
        return mBitmap;
    }

    //考虑到subview的机制，需要对新bitmap进行优化
    class BitmapExecutor extends AsyncTask<Bitmap ,Nullable,Bitmap>{
        @Override
        protected Bitmap doInBackground(Bitmap... params) {
            //得到图像的宽度和长度
            int width = params[0].getWidth();
            int height = params[0].getHeight();
            //创建线性拉升灰度图像
            Bitmap result = null;
            result = params[0].copy(Bitmap.Config.ARGB_8888, true);
            //依次循环对图像的像素进行处理
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    //得到每点的像素值
                    int col = params[0].getPixel(i, j);
                    int alpha = col & 0xFF000000;
                    int red = (col & 0x00FF0000) >> 16;
                    int green = (col & 0x0000FF00) >> 8;
                    int blue = (col & 0x000000FF);
                    // 增加了图像的亮度
                    red = (int) (1.1 * red + 30);
                    green = (int) (1.1 * green + 30);
                    blue = (int) (1.1 * blue + 30);
                    int gray = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                    //对图像进行二值化处理
                    if (gray <= 220) {
                        gray = 0;
                    } else {
                        gray = 255;
                    }
                    // 新的ARGB
                    int newColor = alpha | (gray << 16) | (gray << 8) | gray;
                    //设置新图像的RGB值
                    result.setPixel(i, j, newColor);
                }
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            dialog.show();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            needExeBm = false;
            setBitmap(bitmap);
            dialog.dismiss();
        }
    }

}
