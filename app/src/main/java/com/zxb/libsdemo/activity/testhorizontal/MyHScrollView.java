package com.zxb.libsdemo.activity.testhorizontal;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class MyHScrollView extends HorizontalScrollView {

    ScrollViewObserver mScrollViewObserver = new ScrollViewObserver();

    public MyHScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyHScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyHScrollView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (mScrollViewObserver != null) {
            mScrollViewObserver.notifyOnScrollChanged(l, t, oldl, oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void addOnScrollChangedListener(OnScrollChangedListener listener) {
        mScrollViewObserver.addOnScrollChangedListener(listener);
    }

    public void removeOnScrollChangedListener(OnScrollChangedListener listener) {
        mScrollViewObserver.removeOnScrollChangedListener(listener);
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    public static class ScrollViewObserver {
        List<OnScrollChangedListener> mList;

        public ScrollViewObserver() {
            super();
            mList = new ArrayList<OnScrollChangedListener>();
        }

        public void addOnScrollChangedListener(OnScrollChangedListener listener) {
            mList.add(listener);
        }

        public void removeOnScrollChangedListener(OnScrollChangedListener listener) {
            mList.remove(listener);
        }

        public void notifyOnScrollChanged(int l, int t, int oldl, int oldt) {
            if (mList == null || mList.size() == 0) {
                return;
            }
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i) != null) {
                    mList.get(i).onScrollChanged(l, t, oldl, oldt);
                }
            }
        }
    }
}
