package com.zxb.libsdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * Created by mrzhou on 16/5/30.
 */
public class GalleryX extends Gallery {

    public GalleryX(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.e("galleryX", String.valueOf(distanceX));

        return super.onScroll(e1, e2, distanceX, distanceY);
    }

//    @Override
//    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        Log.e("galleryXVelocity", String.valueOf(velocityX));
//        return super.onFling(e1, e2, velocityX, velocityY);
//    }
//
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        Log.e("galleryXScrollChange", String.valueOf(oldl) + ":" + String.valueOf(l));
//        Log.e("galleryXScrollChange", String.valueOf(oldt) + ":" + String.valueOf(t));
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
