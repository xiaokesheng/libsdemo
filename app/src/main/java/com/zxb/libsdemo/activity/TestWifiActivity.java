package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zxb.libsdemo.R;

/**
 * Created by yufangyuan on 2017/6/20.
 */

public class TestWifiActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offload_layout);
    }
}
