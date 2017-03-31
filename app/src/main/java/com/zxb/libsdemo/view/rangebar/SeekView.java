package com.zxb.libsdemo.view.rangebar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by mrzhou on 2017/3/30.
 */
public class SeekView extends View {

    private Context mContext;

    private boolean isLeft;

    // seek 相关
    // 原始图片
    private Bitmap mBitmap;
    // 缩放过后的图片
    private Bitmap mEndBitmap;
    private Paint mBitmapPaint;
    private BitmapShader mBitmapShader;
    // 起始位置
    private int startX;
    private int startY;
    private int centerX;
    // 缩放过后的图片宽度
    private int mBitmapWidth;
    private int mBitmapHeight;

    private int mTotalWidth;

    private boolean isPressed;

    private int totalScrollWidth;
    private int innerMargin;

    private Paint textPaint;

    // 当前滑动条在进度条上的百分比
    int initialPercentage;
    // 用于缩放的矩阵
    Matrix matrix;

    public SeekView(Context context) {
        this(context, null);
    }

    public SeekView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SeekView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public void init(int resId, boolean isLeft, int totalWidth, int margin) {
        mTotalWidth = totalWidth;
        Drawable drawable = ContextCompat.getDrawable(mContext, resId);
        if (drawable == null) {
            return;
        }

        isPressed = false;

        mBitmap = drawableToBitamp(drawable);
        // 将bmp作为着色器，就是在指定区域内绘制bmp
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        float mScale = 2.0f;
        matrix = new Matrix();
        matrix.postScale(mScale, mScale / 2);
        mEndBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);

        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        // 设置shader
        mBitmapPaint.setShader(mBitmapShader);
        mBitmapWidth = mEndBitmap.getWidth();
        mBitmapHeight = mEndBitmap.getHeight();

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.parseColor("#ffffff"));
        textPaint.setTextSize(35);

        this.isLeft = isLeft;
        if (isLeft) {
            centerX = startX + mBitmapWidth / 2;
            startY = 0;
        } else {
            if (startX == 0) {
                startX = totalWidth - mBitmapWidth;
            }
            startY = 0;
            centerX = startX + mBitmapWidth / 2;
        }

        this.innerMargin = margin;
        totalScrollWidth = totalWidth - 2 * getRealWidth() - 2 * innerMargin;

        if (initialPercentage != 0) {
            setPercentage(initialPercentage);
        }
    }

    // 得到滑动条图片的右边距离, 用于计算进度条的起始位置
    public int getEndX() {
        return startX + mBitmapWidth;
    }

    public int getStartX() {
        return startX;
    }

    /**
     * 最重要的方法, 根据触摸时的 x 坐标, 计算滑动条的起始绘图坐标和中心坐标
     * 由于设定了触摸的 x 坐标一直位于滑动条的中心位置, 所以 centerX = x
     * 当超出屏幕边界时, 取 centerX 为临界值, 分左边界和右边界两种情况
     * @param x
     */
    public void setCenterX(int x) {
        this.centerX = x;
        // 超出左边界
        if (x < mEndBitmap.getWidth() / 2) {
            centerX = mEndBitmap.getWidth() / 2;
        }
        // 超出右边界
        if (x > mTotalWidth - mBitmapWidth / 2) {
            centerX = mTotalWidth - mBitmapWidth / 2;
        }
        // 中心点位置确定后,即可确定起始位置, 中心点 - 宽度/2 即可
        this.startX = centerX - mEndBitmap.getWidth() / 2;
    }

    public void setPressed(boolean pressed) {
        this.isPressed = pressed;
    }

    public boolean isPressed() {
        return isPressed;
    }

    /**
     * 计算滑动的有效距离, 体现在进度条上的实际距离, 用于计算滑动百分比
     * @return
     */
    private int getRealScrollX() {
        if (isLeft) {
            int initX = getRealWidth() + innerMargin;
            int realX = centerX + getRealWidth() / 2 + innerMargin;
            return realX - initX;
        } else {
            int initX = getRealWidth() + innerMargin;
            int realX = centerX - getRealWidth() / 2 - innerMargin;
            return realX - initX;
        }
    }

    public void setPercentage(int percentage) {
        this.initialPercentage = percentage;
        int initX = getRealWidth() + innerMargin;
        int realX = percentage * totalScrollWidth / 100 + initX;
        if (isLeft) {
            centerX = realX - getRealWidth() / 2 - innerMargin;
        } else {
            centerX = realX + getRealWidth() / 2 - innerMargin;
        }
        this.startX = centerX - getRealWidth() / 2;
    }

    public int getPercentage() {
        return getRealScrollX() * 100 / totalScrollWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mEndBitmap, startX, startY, mBitmapPaint);
    }

    private Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    public int getRealWidth() {
        return mBitmapWidth;
    }

    /**
     * 重置
     * 分左右两个滑动条, 对应两种情况
     * 分别计算重绘时左侧起始位置, 中心位置
     * 重置百分比
     */
    public void reset() {
        isPressed = false;
        if (isLeft) {
            startX = 0;
            centerX = startX + mBitmapWidth / 2;
            startY = 0;
        } else {
            startX = mTotalWidth - mBitmapWidth;
            startY = 0;
            centerX = startX + mBitmapWidth / 2;
        }
        initialPercentage = 0;
    }
}
