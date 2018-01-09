package com.zxb.libsdemo.util;

import android.util.Log;

/**
 * Created by mrzhou on 2017/4/12.
 */
public class J {
    public static void j(String tag, String str) {
        Log.e(tag, str);
    }
    public static void j(float[] pts) {
        for (float i : pts) {
            Log.e("Points_Log", String.valueOf(i));
        }
    }
}
