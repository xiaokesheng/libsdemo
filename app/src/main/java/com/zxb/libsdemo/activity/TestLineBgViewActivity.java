package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.view.LineWithBgView;

/**
 * Created by mrzhou on 16/11/23.
 */
public class TestLineBgViewActivity extends BaseActivity {

    LinearLayout llContainer;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_test_linebgview);

        llContainer = (LinearLayout) findViewById(R.id.llContainer);
        llContainer.addView(new LineWithBgView(this));
        tv = (TextView) findViewById(R.id.tv);
    }
}
