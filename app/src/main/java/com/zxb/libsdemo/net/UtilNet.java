package com.zxb.libsdemo.net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by yufangyuan on 2017/8/14.
 */

public class UtilNet {

    public volatile static RequestQueue requestQueue;

    public static RequestQueue getQueue(Context context) {
        if (requestQueue == null) {
            synchronized (UtilNet.class) {
                if (requestQueue == null) {
                    requestQueue = Volley.newRequestQueue(context);
                }
            }
        }
        return requestQueue;
    }
}
