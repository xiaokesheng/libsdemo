package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.google.gson.Gson;
import com.zxb.libsdemo.R;
import com.zxb.libsdemo.model.FuyingItem;
import com.zxb.libsdemo.model.PointC;
import com.zxb.libsdemo.model.PointsLine;
import com.zxb.libsdemo.util.J;
import com.zxb.libsdemo.util.Util;
import com.zxb.libsdemo.view.points.ArrayPointsTestView;

import java.util.ArrayList;

/**
 * Created by mrzhou on 2017/4/12.
 */
public class TestArrayPointsLineActivity extends Activity {

    ArrayPointsTestView aptvLine;

    Button btnMax;
    Button btnMin;

    private Matrix mTranslateMatrix;
    private Matrix mSavedMatrix;

    private Matrix mTouch;

    /**
     * this rectangle defines the area in which graph values can be drawn
     */
    protected RectF mContentRect = new RectF();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_arraypoints);
        aptvLine = (ArrayPointsTestView) findViewById(R.id.aptvLine);

        btnMax = (Button) findViewById(R.id.btnMax);
        btnMin = (Button) findViewById(R.id.btnMin);

        mTranslateMatrix = new Matrix();
        mSavedMatrix = new Matrix();
        mTouch = new Matrix();

        String str = Util.readStrFromAssets(this, "result.txt");

        PointsLine result = new Gson().fromJson(str, PointsLine.class);
        ArrayList<FuyingItem> list = (ArrayList<FuyingItem>) result.data.fyList;
        ArrayList<PointC> list1 = new ArrayList<>();
        ArrayList<PointC> list2 = new ArrayList<>();
        for (FuyingItem item : list) {
            list1.add(new PointC(item.date, Float.parseFloat(item.floatPoint)));
            list2.add(new PointC(item.date, Float.parseFloat(item.hsPoint)));
        }
        aptvLine.setPointsList(list2);

        aptvLine.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                aptvLine.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                aptvLine.setStartIndex(20);
            }
        });

    }
}
