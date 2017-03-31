package com.zxb.libsdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by mrzhou on 16/11/2.
 */
public class SyncLinearLayout extends LinearLayout {

    private boolean canIntercept = true;

    public SyncLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    public void setCanIntercept(boolean canIntercept) {
        this.canIntercept = canIntercept;
    }

    public boolean onTouchEvent(MotionEvent event) {
        //获得listview的个数
        int count = getChildCount();

        HorizontalScrollViewX view = (HorizontalScrollViewX) getChildAt(1);
        LinearLayout llLeft = (LinearLayout) getChildAt(0);
//        if () {
//            return true;
//        } else {
////            view.dispatchTouchEvent(event);
//            llLeft.dispatchTouchEvent(event);
//            return false;
//        }
////        return true;

        if (canIntercept) {
            view.dispatchTouchEvent(event);

        } else {
            view.dispatchTouchEvent(event);
            llLeft.dispatchTouchEvent(event);
        }
        return true;





//        for (int i = 0; i < count; i++) {
//            View child = getChildAt(i);
//            try {
//                child.dispatchTouchEvent(event);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return true;
    }
}
