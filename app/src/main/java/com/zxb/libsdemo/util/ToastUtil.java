package com.zxb.libsdemo.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by mrzhou on 16/7/25.
 */
public class ToastUtil {
    public static void toast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
