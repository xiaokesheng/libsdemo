package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.app.IntentService;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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

    private static final String TAG = "TestVolleyActivity";
    TextView tvRequest;
    TextView tvResult;

    IntentService service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_test_res);

        tvRequest = (TextView) findViewById(R.id.tvRequest);
        tvResult = (TextView) findViewById(R.id.tvResult);

        Util.setClickListener(this, tvRequest);

//        new MyAsyncTask().execute();
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

    private class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            Log.e(TAG, Thread.currentThread().getName() + " onPreExecute ");
        }

        @Override
        protected Void doInBackground(Void... params) {

            // 模拟数据的加载,耗时的任务
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }

            Log.e(TAG, Thread.currentThread().getName() + " doInBackground ");
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.e(TAG, Thread.currentThread().getName() + " onProgressUpdate ");
        }

        @Override
        protected void onPostExecute(Void result) {
            // 进行数据加载完成后的UI操作
            Log.e(TAG, Thread.currentThread().getName() + " onPostExecute ");
        }
    }
}
