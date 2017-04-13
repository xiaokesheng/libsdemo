package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.util.J;
import com.zxb.libsdemo.view.ArrayPointsTestView;

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

        btnMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float[] mPoints = aptvLine.getPoints();
                J.j(mPoints);
                Matrix matrix = new Matrix();
                matrix.setScale(0.9f, 0.9f, 200, 0);
                matrix.mapPoints(mPoints);
                aptvLine.setPoints(mPoints);
                J.j(mPoints);
            }
        });

        btnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float[] mPoints = aptvLine.getPoints();
                Matrix matrix = new Matrix();
                matrix.setScale(1.1f, 1.1f, 200, 0);
                matrix.mapPoints(mPoints);
                aptvLine.setPoints(mPoints);
            }
        });

//        aptvLine.setDragListener(new ArrayPointsTestView.OnDragListener() {
//            @Override
//            public void drag(float dx, float dy) {
//                mTranslateMatrix.set(mSavedMatrix);
//                float[] mPoints = aptvLine.getPoints();
//                mTranslateMatrix.postTranslate(dx, 0);
//                mTranslateMatrix.mapPoints(mPoints);
//                aptvLine.setPoints(mPoints);
//            }
//
//            @Override
//            public void start() {
//                mSavedMatrix.set(mTranslateMatrix);
//            }
//
//            @Override
//            public void end() {
//                mTouch.set(mTranslateMatrix);
//                aptvLine.limitTransAndScale(mTouch, mContentRect);
//                mTranslateMatrix.set(mTouch);
//            }
//        });
    }
}
