package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.view.HistogramAndLineView;

/**
 * Created by yufangyuan on 2017/8/21.
 */

public class TestHistsogramViewActivity extends Activity {

    HistogramAndLineView hlvTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_test_histogram);

        hlvTest = (HistogramAndLineView) findViewById(R.id.hlvTest);
    }
}
