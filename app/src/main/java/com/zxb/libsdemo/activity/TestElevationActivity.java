package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.util.Util;

/**
 * Created by yufangyuan on 2017/7/26.
 */

public class TestElevationActivity extends BaseActivity {

    TextView tvTest;
    LinearLayout llContainer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_test_elevation);
        tvTest = (TextView) findViewById(R.id.tvTest);
        llContainer = (LinearLayout) findViewById(R.id.llContainer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tvTest.setElevation(10);
        }

        addViewToLl();
    }

    private void addViewToLl() {
        for (int i = 0; i < 15; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.cardview_test, null, false);
            CardView cardView = (CardView) view.findViewById(R.id.cardView);
            cardView.setMaxCardElevation(Util.dip2px(20));
            cardView.setCardElevation(Util.dip2px(20));
            llContainer.addView(view);
        }
    }
}
