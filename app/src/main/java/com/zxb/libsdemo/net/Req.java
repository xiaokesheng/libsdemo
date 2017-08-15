package com.zxb.libsdemo.net;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.zxb.libsdemo.App;

/**
 * Created by yufangyuan on 2017/8/14.
 */

public class Req {

    private static RequestQueue requestQueue = UtilNet.getQueue(App.getApp());

    public static void getUrlRequest(String url, Response.Listener listener, Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(url, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
}
