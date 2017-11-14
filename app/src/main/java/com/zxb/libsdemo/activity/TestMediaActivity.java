package com.zxb.libsdemo.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.util.AudioCodec;
import com.zxb.libsdemo.util.Util;
import com.zxb.libsdemo.view.ratio.RatioPoint;
import com.zxb.libsdemo.view.ratio.RatioView;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created by mrzhou on 17/3/10.
 */
public class TestMediaActivity extends Activity {

    private static final String TAG = "TestMediaActivity";

    private LinearLayout llContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.a_mediatest);

        llContainer = (LinearLayout) findViewById(R.id.llContainer);

        ArrayList<RatioPoint> pointList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            add(pointList);
        }

        RatioView view = new RatioView(this);
        llContainer.addView(view);
        view.setList(pointList);
    }

    private void add(ArrayList<RatioPoint> pointList) {
        pointList.add(new RatioPoint(0.99f,0.0031f,1.00f,"2017-09-13"));
        pointList.add(new RatioPoint(1.01f,0.0031f,1.00f,"2017-09-12"));
        pointList.add(new RatioPoint(1.02f,-0.001f,1.00f,"2017-09-11"));
        pointList.add(new RatioPoint(1.01f,-0.001f,0.99f,"2017-08-04"));
        pointList.add(new RatioPoint(0.99f,-0.001f,0.99f,"2017-08-03"));
        pointList.add(new RatioPoint(1.01f,-0.001f,1.00f,"2017-08-02"));
        pointList.add(new RatioPoint(1.03f,-0.001f,1.01f,"2017-08-01"));
        pointList.add(new RatioPoint(0.99f,-0.001f,1.20f,"2017-07-31"));
        pointList.add(new RatioPoint(1.01f,-0.001f,1.00f,"2017-07-28"));
        pointList.add(new RatioPoint(0.99f,-0.001f,1.00f,"2017-07-27"));
        pointList.add(new RatioPoint(0.98f,-0.001f,1.00f,"2017-07-26"));
        pointList.add(new RatioPoint(1.01f,-0.001f,0.99f,"2017-07-25"));
        pointList.add(new RatioPoint(0.29f,-0.001f,0.59f,"2017-07-21"));
        pointList.add(new RatioPoint(0.99f,-0.001f,1.00f,"2017-07-20"));
        pointList.add(new RatioPoint(1.00f,-0.011f,1.02f,"2017-07-19"));
        pointList.add(new RatioPoint(1.01f,-0.001f,1.00f,"2017-07-18"));
        pointList.add(new RatioPoint(1.00f,-0.001f,1.00f,"2017-07-14"));
        pointList.add(new RatioPoint(1.05f,-0.001f,1.01f,"2017-07-13"));
        pointList.add(new RatioPoint(1.01f,-0.001f,1.00f,"2017-07-12"));
        pointList.add(new RatioPoint(1.07f,-0.001f,1.00f,"2017-07-11"));
        pointList.add(new RatioPoint(1.01f,-0.601f,1.00f,"2017-07-10"));
        pointList.add(new RatioPoint(1.01f,-0.001f,1.00f,"2017-07-07"));
        pointList.add(new RatioPoint(1.01f,-0.001f,1.00f,"2017-07-06"));
        pointList.add(new RatioPoint(0.99f,-0.001f,1.50f,"2017-07-04"));
        pointList.add(new RatioPoint(1.00f,-0.001f,1.00f,"2017-07-03"));
        pointList.add(new RatioPoint(1.00f,-0.001f,1.00f,"2017-06-30"));
        pointList.add(new RatioPoint(1.00f,-0.001f,1.50f,"2017-06-29"));
        pointList.add(new RatioPoint(1.01f,-0.001f,1.00f,"2017-06-28"));
        pointList.add(new RatioPoint(1.01f,-0.001f,1.50f,"2017-06-27"));
        pointList.add(new RatioPoint(1.01f,-0.001f,1.00f,"2017-06-26"));
    }

}
