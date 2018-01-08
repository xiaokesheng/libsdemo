package com.zxb.libsdemo.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.zxb.libsdemo.model.PointC;
import com.zxb.libsdemo.view.points.MinMax;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

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

    public static double formatDouble(double d, int len) {
        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(len, BigDecimal.ROUND_HALF_UP);
        double result = bd.doubleValue();
        bd = null;
        return result;
    }

    public static void handleValues(float totalHeight, int itemWidth, ArrayList<PointC>... values) {
        MinMax minMax = new MinMax();
        for (ArrayList<PointC> list : values) {
            for (PointC item : list) {
                if (minMax.min >= item.yValue) {
                    minMax.min = item.yValue;
                }
                if (minMax.max <= item.yValue) {
                    minMax.max = item.yValue;
                }
            }
        }
        float scale = (minMax.max - minMax.min) / totalHeight;
        for (ArrayList<PointC> list : values) {
            int i = 0;
            for (PointC item : list) {
                item.x = i++ * itemWidth;
                item.yPixels = (minMax.max - item.yValue) / scale;
            }
        }
    }

    public static String readStrFromAssets(Context context, String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = context.getResources().getAssets().open(fileName);
            int size = inputStream.available();
            int len = -1;
            byte[] bytes = new byte[size];
            inputStream.read(bytes);
            inputStream.close();
            String string = new String(bytes);
            return string;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void toast(Context c, String title, String content) {
        toast(c, title, content, true);
    }

    /**
     * toast （默认 时间Toast.LENGTH_SHORT）
     */
    public static void toast(Context c, String msg) {
        toast(c, msg, true);
    }

    public static void toast(Context c, String msg, int duration) {
        toast(c, msg, true, duration);
    }


    public static void toast(Context c, String msg, boolean show) {
        toast(c, msg, show, Toast.LENGTH_SHORT);
    }

    public static void toast(Context c, String title, String content, boolean show) {
        toast(c, title, content, show, Toast.LENGTH_SHORT);
    }

    public static void toast(Context c, boolean hasIcon, String title, String content) {
        ToastMgr.builder.display(title, content, Toast.LENGTH_SHORT, hasIcon);
    }


    public static void toast(Context c, String msg, boolean show, int duration) {
        if (!show)
            return;
        ToastMgr.builder.display(msg, duration);
    }


    public static void toast(Context c, String title, String content, boolean show, int duration) {
        if (!show)
            return;
        ToastMgr.builder.display(title, content, duration);
    }


    public static void toast(Context c, int resid) {
        toast(c, resid, true);
    }

    /**
     * toast
     *
     * @param c
     * @param resid    内容资源id
     * @param duration 时间
     */
    public static void toast(Context c, int resid, int duration) {
        toast(c, resid, true, duration);
    }

    /**
     * toast 时间（默认 时间Toast.LENGTH_LONG）
     *
     * @param c
     * @param resid 内容资源id
     * @param show  是否显示
     */
    public static void toast(Context c, int resid, boolean show) {
        toast(c, resid, show, Toast.LENGTH_LONG);
    }

    /**
     * toast
     *
     * @param c
     * @param resid    内容资源id
     * @param show     是否显示
     * @param duration 时间
     */
    public static void toast(Context c, int resid, boolean show, int duration) {
        if (!show)
            return;
        ToastMgr.builder.display(resid, duration);
    }

    public static void toast(Context c, String msg, boolean show, int duration, int position,
                             int yOffset) {
        if (!show)
            return;
        ToastMgr.builder.display(msg, duration, position, yOffset);
    }
}
