package com.zxb.libsdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * Created by mrzhou on 16/5/19.
 */
public class TouchTestView extends View {

    private int mColor = Color.BLUE;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int paddingLeft;
    private int paddingTop;
    private int paddingRight;
    private int paddingBottom;

    private int mInitialWidth = 400;
    private int mInitialHeight = 400;

    Scroller mScroller;

    Context mContext;

    ScrollView sv;

    public TouchTestView(Context context) {
        this(context, null);
    }

    public TouchTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public TouchTestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void init() {
        mPaint.setColor(mColor);
        mScroller = new Scroller(mContext);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mInitialWidth, mInitialHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mInitialWidth, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, mInitialHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        Rect r = new Rect();
        r.top = paddingTop;
        r.left = paddingLeft;
        r.right = r.left + width;
        r.bottom = r.top + height;

        canvas.drawRect(r, mPaint);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        mScroller.startScroll(scrollX, 0, delta, 0, 2000);
        invalidate();
    }

    int lastX, lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("motionEvent", "Down");
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("touchEvent", "touching");
                int offsetX = x - lastX;
                int offsetY = y - lastY;
//                layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
                break;
            case MotionEvent.ACTION_UP:
                Log.e("motionEvent", "UP");
                break;
        }
//        return true;
        boolean result = super.onTouchEvent(event);
        Log.i("motionEventResult", String.valueOf(result));
        return result;
    }
}
