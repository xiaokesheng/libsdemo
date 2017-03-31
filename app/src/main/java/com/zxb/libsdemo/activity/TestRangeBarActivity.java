package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.util.Util;
import com.zxb.libsdemo.view.ImageCaptureView;
import com.zxb.libsdemo.view.rangebar.RangeBar;

/**
 * Created by mrzhou on 2017/3/30.
 */
public class TestRangeBarActivity extends Activity implements View.OnClickListener {

    RangeBar rbRangeBar;
    TextView tvLeft;
    TextView tvRight;

    TextView tvReset;
    TextView tvCommit;
    EditText etMargin;

    LinearLayout llImg;
    HorizontalScrollView hsvImg;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_test_rangebar);
        mContext = this;

        rbRangeBar = (RangeBar) findViewById(R.id.rbRangeBar);
        tvLeft = (TextView) findViewById(R.id.tvLeft);
        tvRight = (TextView) findViewById(R.id.tvRight);
        tvReset = (TextView) findViewById(R.id.tvReset);
        tvCommit = (TextView) findViewById(R.id.tvCommit);
        etMargin = (EditText) findViewById(R.id.etMargin);

        llImg = (LinearLayout) findViewById(R.id.llImg);
        hsvImg = (HorizontalScrollView) findViewById(R.id.hsvImg);

        rbRangeBar.setOnRangeScrollListener(new RangeBar.OnRangeScrollListener() {
            @Override
            public void onScrollPercentage(int leftPercentage, int rightPercentage) {
                tvLeft.setText(String.valueOf(leftPercentage));
                tvRight.setText(String.valueOf(rightPercentage));
            }
        });
        rbRangeBar.setLeftPercentage(50);

        Util.setClickListener(this, tvCommit, tvReset);

        rbRangeBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rbRangeBar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) rbRangeBar.getLayoutParams();
                addVideoImage(lp.leftMargin + rbRangeBar.getLeftSeekBarWidth(), lp.rightMargin + rbRangeBar.getRightSeekBarWidth());
            }
        });
    }

    private void addVideoImage(int leftMargin, int rightMargin) {
        int totalVideoWidth = 2040;     // 设定宽度为 2020px
        int totalHeight = Util.dip2px(50);      // 设定高度为 50dp

        ImageCaptureView view = new ImageCaptureView(this, totalVideoWidth, totalHeight, leftMargin, rightMargin, Util.getScreenWidth(mContext));
        llImg.addView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvReset:
                rbRangeBar.resetRangeBar();
                break;
            case R.id.tvCommit:
                rbRangeBar.setMargin(Integer.valueOf(etMargin.getText().toString()));
                break;
        }
    }
}
