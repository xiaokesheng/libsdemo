package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zxb.libsdemo.R;
import com.zxb.libsdemo.net.Req;
import com.zxb.libsdemo.util.Util;

/**
 * Created by yufangyuan on 2017/8/14.
 */

public class TestVolleyActivity extends Activity implements View.OnClickListener {

    TextView tvRequest;
    TextView tvResult;

    AsyncTask asyncTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_test_res);

        tvRequest = (TextView) findViewById(R.id.tvRequest);
        tvResult = (TextView) findViewById(R.id.tvResult);

        Util.setClickListener(this, tvRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRequest:
                startRequest();
                break;
        }
    }

    private void startRequest() {
        Response.Listener listener = new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                if (null != response) {
                    String str = (String) response;
                    tvResult.setText(str);
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
        Req.getUrlRequest("http://138.68.2.222", listener, errorListener);
    }
}
