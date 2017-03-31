package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.line.LinePointUtil;
import com.zxb.libsdemo.line.NewPoint;
import com.zxb.libsdemo.util.Util;
import com.zxb.libsdemo.view.HorizontalScrollViewX;
import com.zxb.libsdemo.view.NewLineView;

import java.util.ArrayList;

/**
 * Created by mrzhou on 16/5/31.
 */
public class TestLineViewActivity extends Activity implements View.OnClickListener {

    private NewLineView nlvTest;
    private TextView tvZoomLittle;
    private TextView tvZoomLarge;
    private TextView tvZoomMedium;
    private View vPointCircle;

    private HorizontalScrollViewX hsvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_lineview);

        hsvTest = (HorizontalScrollViewX) findViewById(R.id.hsvTest);
        nlvTest = (NewLineView) findViewById(R.id.nlvTest);
        tvZoomLarge = (TextView) findViewById(R.id.tvZoomLarge);
        tvZoomLittle = (TextView) findViewById(R.id.tvZoomLittle);
        tvZoomMedium = (TextView) findViewById(R.id.tvZoomMedium);

        vPointCircle = findViewById(R.id.vPointCircle);

        setListen();

        ArrayList<NewPoint> pointList = new ArrayList<>();

        LinePointUtil.initNewLinePoints(pointList);
        nlvTest.setHorizontalScrollViewX(hsvTest);
        nlvTest.setPointList(pointList);
    }

    private void setListen() {
        Util.setClickListener(this, tvZoomLarge, tvZoomMedium, tvZoomLittle);

        hsvTest.setScrollViewListener(new HorizontalScrollViewX.ScrollViewListener() {
            @Override
            public void onScrollChanged(HorizontalScrollViewX scrollView, int x, int y, int oldx, int oldy) {

            }

            @Override
            public void onEndScroll(int x) {
                Log.e("onScroll", "停止了");
                nlvTest.setScrollStoped(x);
            }

            @Override
            public void onScrollX(int x) {
                // 滑动的过程中动态记录调整x位置
                Log.e("onScroll", "activity: " + String.valueOf(x));
                nlvTest.setInScrolling(x);
            }
        });

        nlvTest.setOnScrollListener(new NewLineView.OnScrollListener() {
            @Override
            public void onScrollStoped(int yHeight) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) vPointCircle.getLayoutParams();
                lp.topMargin = yHeight - vPointCircle.getHeight() / 2;
                vPointCircle.setLayoutParams(lp);
                vPointCircle.setVisibility(View.VISIBLE);
                Log.e("scrollListener", "stopped");
            }

            @Override
            public void onUnVisibleView() {
//                vPointCircle.setVisibility(View.GONE);
                Log.e("scrollListener", "unVisible");
            }

            @Override
            public void onRequestNext(int position) {
                Log.e("scrollListener", "request");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvZoomLarge:
                nlvTest.setWidthType(1);
                break;
            case R.id.tvZoomLittle:
                nlvTest.setWidthType(-1);
                break;
            case R.id.tvZoomMedium:
                nlvTest.setWidthType(0);
                break;
        }
    }
}
