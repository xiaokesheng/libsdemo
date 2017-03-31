package com.zxb.libsdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by mrzhou on 2017/3/29.
 */
public class TestTouchView extends View {

    Context mContext;

    private int currentX;
    private int currentY;

    private int mRadius;

    private Paint mPaint;

    private int mWidth;
    private int mHeight;

    RectF rect = new RectF();

    public TestTouchView(Context context) {
        this(context, null);
    }

    public TestTouchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestTouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        currentX = 0;
        currentY = 0;
        mRadius = 30;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#389cff"));
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int height = getHeight();
//        int width = getWidth();
//        Log.e("height", height + ", " + width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentX = (int) event.getX();
                currentY = (int) event.getY();
                Log.e("move", currentX + ", " + currentY);
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                currentX = (int) event.getX();
                currentY = (int) event.getY();
                Log.e("move", currentX + ", " + currentY);
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.parseColor("#ff0000"));
        mPaint.setTextSize(40);
//        canvas.drawText("哈哈哈你好啊哈哈哈你好啊", 100, mHeight / 2, mPaint);
        if (currentX != 0 || currentY != 0) {
//            canvas.drawCircle(currentX, currentY, mRadius, mPaint);
            canvas.drawLine(currentX, 0, currentX, mHeight, mPaint);
            canvas.drawLine(0, mHeight / 2, mWidth, mHeight / 2, mPaint);
            rect.top = mHeight / 2 - 100;
            rect.bottom = mHeight / 2 + 100;
            if (currentX > mWidth / 2) {
                rect.left = currentX - 50 - 300;
                rect.right = rect.left + 300;
            } else {
                rect.left = currentX + 50;
                rect.right = rect.left + 300;
            }
            mPaint.setColor(Color.parseColor("#66389cff"));
            canvas.drawRoundRect(rect, 10, 10, mPaint);

            mPaint.setTextSize(30);
            mPaint.setColor(Color.parseColor("#ffffff"));
            canvas.drawText("2017-21-24", rect.left + 25, rect.top + 40, mPaint);
            canvas.drawText("鼎泰新材", rect.left + 60, rect.top + 100, mPaint);
            canvas.drawText("交通运输", rect.left + 60, rect.top + 140, mPaint);
            canvas.drawText("沪深", rect.left + 60, rect.top + 180, mPaint);

            mPaint.setColor(Color.parseColor("#389cff"));
            canvas.drawCircle(rect.left + 25 + 15, rect.top + 100 - 15, 15, mPaint);
            mPaint.setColor(Color.parseColor("#f25f5c"));
            canvas.drawCircle(rect.left + 25 + 15, rect.top + 140 - 15, 15, mPaint);
            mPaint.setColor(Color.parseColor("#38bd7f"));
            canvas.drawCircle(rect.left + 25 + 15, rect.top + 180 - 15, 15, mPaint);
        }
    }
}
