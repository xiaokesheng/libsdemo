package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.zxb.libsdemo.R;

/**
 * Created by yufangyuan on 2017/6/20.
 */

public class TestWifiActivity extends BaseActivity {

    Handler mHandler;
    ScrollView sv;
    Scroller scroller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offload_layout);

        mHandler = new Handler();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
        mHandler = new Handler();

        mHandler.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        Message msg = new Message();
        msg.what = 0;
        msg.obj = new String("hahaah");
        mHandler.sendMessage(msg);
    }
}
