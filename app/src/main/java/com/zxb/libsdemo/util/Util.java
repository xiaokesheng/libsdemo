package com.zxb.libsdemo.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by mrzhou on 16/5/25.
 */
public class Util {

    private static int screenHeight = 0;
    private static int screenWidth = 0;

    public static void setVis(View... views) {
        for (View view : views) {
            if (view != null)
                view.setVisibility(View.VISIBLE);
        }
    }

    public static void setInvis(View... views) {
        for (View view : views) {
            if (view != null)
                view.setVisibility(View.INVISIBLE);
        }
    }

    public static void setGone(View... views) {
        for (View view : views) {
            if (view != null)
                view.setVisibility(View.GONE);
        }
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            screenHeight = display.getHeight();
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            screenWidth = display.getWidth();
        }

        return screenWidth;
    }

    public static void setClickListener(View.OnClickListener onClickListener, View... views) {
        if (null != views && views.length > 0) {
            for (View view : views) {
                Log.e("setClick", "setClick");
                view.setOnClickListener(onClickListener);
            }
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
