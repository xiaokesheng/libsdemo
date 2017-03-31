package com.zxb.libsdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by mrzhou on 16/5/31.
 */
public class HorizontalScrollViewX extends HorizontalScrollView {

    private boolean mCurrentlyTouching;

    private boolean isScroll;

    private int oldX = 0;
    private int currentX = 0;

    private ScrollViewListener scrollViewListener = null;

    public interface ScrollViewListener {
        void onScrollChanged(HorizontalScrollViewX scrollView, int x, int y, int oldx, int oldy);

        void onEndScroll(int x);

        void onScrollX(int x);
    }

    public HorizontalScrollViewX(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (null != scrollViewListener) {
            if (Math.abs(oldX - getScrollX()) < 10) {
//                if (isScroll) {
                    if (!mCurrentlyTouching) {
                        if (scrollViewListener != null) {
                            scrollViewListener.onEndScroll(getScrollX());
                        }
                    }
                    isScroll = false;
                    Log.e("scrollX", "变false");
//                }
            } else {
                isScroll = true;
                Log.e("scrollX", "变true");
            }
            currentX = oldX;
            oldX = getScrollX();
            scrollViewListener.onScrollX(getScrollX());
        }
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mCurrentlyTouching = true;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mCurrentlyTouching = false;
                if (!isScroll) {
                    if (scrollViewListener != null) {
                        scrollViewListener.onEndScroll(getScrollX());
                    }
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mCurrentlyTouching = true;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mCurrentlyTouching = true;
                break;
            default:
                break;
        }
        boolean result = super.onInterceptTouchEvent(ev);
        Log.e("intercept", result ? "true" : "false");
        if (null != mOnInterceptChangedListener) {
            mOnInterceptChangedListener.onInterceptChanged(result);
        }
        return result;
//        return super.onInterceptTouchEvent(ev);
    }

    OnInterceptChangedListener mOnInterceptChangedListener;

    public void setOnInterceptChangedListener(OnInterceptChangedListener listener) {
        this.mOnInterceptChangedListener = listener;
    }

    public interface OnInterceptChangedListener {
        void onInterceptChanged(boolean value);
    }
}
