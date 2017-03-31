package com.zxb.libsdemo.activity.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxb.libsdemo.R;

/**
 * Created by mrzhou on 17/2/10.
 */
public class TestScrollerActivity extends Activity implements View.OnClickListener {

    View vTest;
    TextView tvStart;
    LinearLayout llParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_test_scroller);

        vTest = findViewById(R.id.vTest);
        tvStart = (TextView) findViewById(R.id.tvStart);
        llParent = (LinearLayout) findViewById(R.id.llParent);

        tvStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvStart:
                startScroll(llParent);
                break;
        }
    }

    private void startScroll(View view) {
        view.scrollBy(-30, -60);
    }
}
