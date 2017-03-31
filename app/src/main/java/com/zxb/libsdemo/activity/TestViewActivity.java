package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.activity.view.TestScrollerActivity;

/**
 * Created by mrzhou on 17/2/10.
 */
public class TestViewActivity extends Activity implements View.OnClickListener {

    TextView tvTestScroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_view);
        tvTestScroller = (TextView) findViewById(R.id.tvTestScroller);
        tvTestScroller.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTestScroller:
                startActivity(new Intent(this, TestScrollerActivity.class));
                break;
        }
    }
}
