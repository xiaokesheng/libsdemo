package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.activity.view.TestScrollerActivity;
import com.zxb.libsdemo.util.Util;
import com.zxb.libsdemo.view.VerticalProgressView;

import java.util.Random;

/**
 * Created by mrzhou on 17/2/10.
 */
public class TestViewActivity extends Activity implements View.OnClickListener {

    TextView tvTestScroller;

    TextView tvCommit;
    VerticalProgressView vpv;
    TextView tvValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_view);
        tvTestScroller = (TextView) findViewById(R.id.tvTestScroller);
        tvTestScroller.setOnClickListener(this);
        tvCommit = (TextView) findViewById(R.id.tvCommit);
        tvValue = (TextView) findViewById(R.id.tvValue);
        vpv = (VerticalProgressView) findViewById(R.id.vpv);

        Util.setClickListener(this, tvCommit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTestScroller:
                startActivity(new Intent(this, TestScrollerActivity.class));
                break;
            case R.id.tvCommit:
                float value = new Random().nextInt(100);
                tvValue.setText(String.valueOf(value));
                vpv.setProgress(value);
                break;
        }
    }
}
