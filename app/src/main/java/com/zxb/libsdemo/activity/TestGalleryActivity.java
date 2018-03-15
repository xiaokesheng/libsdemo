package com.zxb.libsdemo.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Gallery;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.adapter.LineAdapter;
import com.zxb.libsdemo.line.LinePoint;
import com.zxb.libsdemo.line.LinePointUtil;
import com.zxb.libsdemo.util.Util;
import com.zxb.libsdemo.view.GalleryX;

import java.util.ArrayList;

/**
 * Created by mrzhou on 16/5/30.
 */
public class TestGalleryActivity extends BaseActivity implements View.OnClickListener {

    GalleryX gyTest;

    private ArrayList<LinePoint> linePointList;

    private TextView tvZoomLittle;
    private TextView tvZoomLarge;

    private LineAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_gallery);

        tvZoomLarge = (TextView) findViewById(R.id.tvZoomLarge);
        tvZoomLittle = (TextView) findViewById(R.id.tvZoomLittle);

        linePointList = new ArrayList<>();
        LinePointUtil.initLinePoints(linePointList);
        LinePointUtil.handleLinePointList(linePointList);

        mAdapter = new LineAdapter(this, linePointList);

        gyTest = (GalleryX) findViewById(R.id.gyTest);
        gyTest.setUnselectedAlpha(100f);
        gyTest.setAdapter(mAdapter);

        setListen();
    }

    private void setListen() {
        Util.setClickListener(this, tvZoomLarge, tvZoomLittle);

        gyTest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("gallery", String.valueOf(position));

//                handleVLinePosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Log.e("gallery", String.valueOf(gyTest.getChildCount()));


    }

    private void handleVLinePosition(int galleryPosition) {
        int xCenter = Util.getScreenWidth(this) / 2;
        int itemWidth = Util.dip2px(mAdapter.getItemWidthInDP());
        int xOffset = xCenter - galleryPosition * itemWidth + (int) (5.4 * itemWidth);
//        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) vMinimumLine.getLayoutParams();
//        int currentLeftMargin = lp.leftMargin;
//        int delta = xOffset - currentLeftMargin;
//        if (delta > 0) {
//            for (int i = currentLeftMargin; i <= xOffset; i++) {
//                lp.setMargins(i, 0, 0, 0);
//            }
//        } else {
//            for (int i = currentLeftMargin; i >= xOffset; i--) {
//                lp.setMargins(i, 0, 0, 0);
//            }
//        }
//        lp.setMargins(xOffset, 0, 0, 0);

//        vMinimumLine.setLayoutParams(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvZoomLittle:
                mAdapter.setWidthType(-1);
                break;
            case R.id.tvZoomLarge:
                mAdapter.setWidthType(1);
                break;
        }
    }
}
