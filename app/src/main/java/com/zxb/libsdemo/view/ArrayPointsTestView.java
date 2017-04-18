package com.zxb.libsdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zxb.libsdemo.model.LineBound;
import com.zxb.libsdemo.model.PointC;
import com.zxb.libsdemo.util.J;
import com.zxb.libsdemo.util.Util;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mrzhou on 2017/4/12.
 */
public class ArrayPointsTestView extends View {

    private Paint mLinePaint;

    protected RectF mContentRect = new RectF();

    private float[] mPoints;

    private int lineColor;

    private PointC startPoint;
    private PointC lastPoint;
    private PointC centerPoint;

    private float dx, dy;

    private Matrix mTranslateMatrix;
    private Matrix mSavedMatrix;
    private Matrix mScaleMatrix;

    private Matrix mYScaleMatrix;

    private Matrix mTouch;

    private float mSavedXDist = 1f;
    private float mSavedYDist = 1f;
    private float mSavedDist = 1f;

    private int touchMode;

    private ArrayList<PointC> pointsList;
    private ArrayList<PointC> pointsListC;

    private static final int MODE_DRAG = 1;
    private static final int MODE_ZOOM = 2;
    private static final int MODE_NONE = 3;

    public LineBound mBound;

    public ArrayPointsTestView(Context context) {
        this(context, null);
    }

    public ArrayPointsTestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArrayPointsTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        touchMode = 3;
        pointsList = new ArrayList<>();
        pointsListC = new ArrayList<>();
        mBound = new LineBound();
        mPoints = new float[]{
                0, 600, 100, 500,
                200, 0, 100, 500,
                200, 0, 300, 800,
                400, 600, 300, 800,
                400, 600, 500, 750,
                600, 300, 500, 750,
                600, 300, 700, 300,
                800, 600, 700, 300,
                800, 600, 900, 200,
                900, 200, 1000, 300,
                1000, 300, 1100, 400,
                1100, 400, 1200, 200,
                1200, 200, 1300, 500,
                1300, 500, 1400, 600,
                1400, 600, 1500, 200,
                1500, 200, 1600, 600,
                1600, 600, 1700, 150,
                1700, 150, 1800, 210,
                1800, 210, 1900, 200,
                1900, 200, 2000, 250,
                2000, 250, 2100, 260
        };

        pointsList.add(new PointC(0, 600));
        pointsList.add(new PointC(100, 500));
        pointsList.add(new PointC(200, 0));
        pointsList.add(new PointC(300, 800));
        pointsList.add(new PointC(400, 600));
        pointsList.add(new PointC(500, 750));
        pointsList.add(new PointC(600, 300));
        pointsList.add(new PointC(700, 300));
        pointsList.add(new PointC(800, 600));
        pointsList.add(new PointC(900, 200));
        pointsList.add(new PointC(1000, 500));
        pointsList.add(new PointC(1100, 400));
        pointsList.add(new PointC(1200, 200));
        pointsList.add(new PointC(1300, 500));
        pointsList.add(new PointC(1400, 600));
        pointsList.add(new PointC(1500, 200));
        pointsList.add(new PointC(1600, 600));
        pointsList.add(new PointC(1700, 150));
        pointsList.add(new PointC(1800, 210));
        pointsList.add(new PointC(1900, 200));
        pointsList.add(new PointC(2000, 250));
        pointsList.add(new PointC(2100, 260));
        pointsList.add(new PointC(500 + 1700, 750 * new Random().nextFloat()));
        pointsList.add(new PointC(600 + 1700, 300 * new Random().nextFloat()));
        pointsList.add(new PointC(700 + 1700, 300 * new Random().nextFloat()));
        pointsList.add(new PointC(800 + 1700, 600 * new Random().nextFloat()));
        pointsList.add(new PointC(900 + 1700, 200 * new Random().nextFloat()));
        pointsList.add(new PointC(1000 + 1700, 500 * new Random().nextFloat()));
        pointsList.add(new PointC(1100 + 1700, 400 * new Random().nextFloat()));
        pointsList.add(new PointC(1200 + 1700, 200 * new Random().nextFloat()));
        pointsList.add(new PointC(1300 + 1700, 500 * new Random().nextFloat()));
        pointsList.add(new PointC(1400 + 1700, 600 * new Random().nextFloat()));
        pointsList.add(new PointC(1500 + 1700, 200 * new Random().nextFloat()));
        pointsList.add(new PointC(1600 + 1700, 600 * new Random().nextFloat()));
        pointsList.add(new PointC(1700 + 1700, 150 * new Random().nextFloat()));
        pointsList.add(new PointC(1800 + 1700, 210 * new Random().nextFloat()));
        pointsList.add(new PointC(1900 + 1700, 200 * new Random().nextFloat()));
        pointsList.add(new PointC(2000 + 1700, 250 * new Random().nextFloat()));
        pointsList.add(new PointC(2100 + 1700, 260 * new Random().nextFloat()));

        pointsList.add(new PointC(0, 600 * 2 + 120));
        pointsList.add(new PointC(100, 500 * 2 + 120));
        pointsList.add(new PointC(200, 0 * 2 + 120));
        pointsList.add(new PointC(300, 800 * 2 + 120));
        pointsList.add(new PointC(400, 600 * 2 + 120));
        pointsList.add(new PointC(500, 750 * 2 + 120));
        pointsList.add(new PointC(600, 300 * 2 + 120));
        pointsList.add(new PointC(700, 300 * 2 + 120));
        pointsList.add(new PointC(800, 600 * 2 + 120));
        pointsList.add(new PointC(900, 200 * 2 + 120));
        pointsList.add(new PointC(1000, 500 * 2 + 120));
        pointsList.add(new PointC(1100, 400 * 2 + 120));
        pointsList.add(new PointC(1200, 200 * 2 + 120));
        pointsList.add(new PointC(1300, 500 * 2 + 120));
        pointsList.add(new PointC(1400, 600 * 2 + 120));
        pointsList.add(new PointC(1500, 200 * 2 + 120));
        pointsList.add(new PointC(1600, 600 * 2 + 120));
        pointsList.add(new PointC(1700, 150 * 2 + 120));
        pointsList.add(new PointC(1800, 210 * 2 + 120));
        pointsList.add(new PointC(1900, 200 * 2 + 120));
        pointsList.add(new PointC(2000, 250 * 2 + 120));
        pointsList.add(new PointC(2100, 260 * 2 + 120));
        pointsList.add(new PointC(500 + 1700, 750 * new Random().nextFloat() * 2));
        pointsList.add(new PointC(600 + 1700, 300 * new Random().nextFloat() * 2));
        pointsList.add(new PointC(700 + 1700, 300 * new Random().nextFloat() * 2));
        pointsList.add(new PointC(800 + 1700, 600 * new Random().nextFloat() * 2));
        pointsList.add(new PointC(900 + 1700, 200 * new Random().nextFloat() * 2));
        pointsList.add(new PointC(1000 + 1700, 500 * new Random().nextFloat() * 2));
        pointsList.add(new PointC(1100 + 1700, 400 * new Random().nextFloat() * 2));
        pointsList.add(new PointC(1200 + 1700, 200 * new Random().nextFloat() * 2));
        pointsList.add(new PointC(1300 + 1700, 500 * new Random().nextFloat() * 2));
        pointsList.add(new PointC(1400 + 1700, 600 * new Random().nextFloat() * 2));
        pointsList.add(new PointC(1500 + 1700, 200 * new Random().nextFloat() * 2));
        pointsList.add(new PointC(1600 + 1700, 600 * new Random().nextFloat() * 2));
        pointsList.add(new PointC(1700 + 1700, 150 * new Random().nextFloat() * 2));
        pointsList.add(new PointC(1800 + 1700, 210 * new Random().nextFloat() * 2));
        pointsList.add(new PointC(1900 + 1700, 200 * new Random().nextFloat() * 2));
        pointsList.add(new PointC(2000 + 1700, 250 * new Random().nextFloat() * 2));
        pointsList.add(new PointC(2100 + 1700, 260 * new Random().nextFloat() * 2));

        Util.handleValues(pointsList);

        lineColor = Color.parseColor("#389cff");
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(lineColor);
        mLinePaint.setStrokeWidth(5);
        startPoint = new PointC();
        centerPoint = new PointC();

        mTranslateMatrix = new Matrix();
        mSavedMatrix = new Matrix();
        mScaleMatrix = new Matrix();
        mTouch = new Matrix();
        mYScaleMatrix = new Matrix();
    }

    public float[] getPoints() {
        return mPoints;
    }

    public void setPoints(float[] points) {
        this.mPoints = points;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawLines(mPoints, mLinePaint);

        int k = 0;
        for (int i = 0; i < pointsList.size() - 1; i++) {
            float[] point = new float[4];
            mapPoint(point, i);
            if (handleBound(point, i, mBound)) {
                continue;
            } else {
                break;
            }
        }

        if (mBound.leftIndex >= 0 && mBound.rightIndex > 0) {
            float deltaHeight = mBound.bottomPixels - mBound.topPixels;
            float yScale = totalHeight / deltaHeight;
            float yPoint;

            if (mBound.bottomPixels - mBound.topPixels == initialHeight) {
                yPoint = mBound.bottomPixels;
            } else {
                yPoint = mBound.topPixels * initialHeight / (initialHeight - mBound.bottomPixels + mBound.topPixels);
            }
            for (int i = mBound.leftIndex; i <= mBound.rightIndex; i++) {
                float[] point = new float[4];
                mapPoint(point, i);
                mYScaleMatrix.postScale(1, yScale, 0, yPoint);
                mYScaleMatrix.mapPoints(point);
                mYScaleMatrix.reset();
                canvas.drawLines(point, mLinePaint);
                k++;
            }
        }
    }

    float totalHeight = Util.dip2px(300);
    float initialHeight = Util.dip2px(300);


    private float[] mapPoint(float[] point, int i) {
        point[0] = pointsList.get(i).x;
        point[1] = pointsList.get(i).y;
        point[2] = pointsList.get(i + 1).x;
        point[3] = pointsList.get(i + 1).y;
        switch (touchMode) {
            case MODE_DRAG:
                mTranslateMatrix.mapPoints(point);
                break;
            case MODE_ZOOM:
                mScaleMatrix.mapPoints(point);
                break;
            case MODE_NONE:
                mTouch.mapPoints(point);
                break;
        }
        return point;
    }

    private boolean handleBound(float[] point, int i, LineBound mBound) {
        if (point[0] >= 0) {
            if (i > 0) {
                mBound.leftIndex = i - 1;
            } else {
                mBound.leftIndex = 0;
            }

            float max = pointsList.get(mBound.leftIndex).y;
            float min = max;

            for (int j = mBound.leftIndex + 1; j < pointsList.size() - 1; j++) {
                float[] pointB = new float[4];
                mapPoint(pointB, j);
                if (j >= mBound.leftIndex + 1) {
                    if (max < pointB[1]) {
                        max = pointB[1];
                    }
                    if (min > pointB[1]) {
                        min = pointB[1];
                    }
                }
                if (pointB[0] >= 1080) {
                    mBound.rightIndex = j - 1;
                    break;
                }

            }
            mBound.topPixels = min;
//                mBound.bottomPixels = max;
            mBound.bottomPixels = 1800;
        } else {
            return true;
        }
        if (mBound.leftIndex >= 0 && mBound.rightIndex > 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                touchMode = MODE_DRAG;
                J.j("TOUCHMOVE", "down");
                mSavedMatrix.set(mTouch);
                startPoint.x = event.getX();
                startPoint.y = event.getY();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                J.j("TOUCHMOVE", "pointerDown");
                if (event.getPointerCount() >= 2) {
                    touchMode = MODE_ZOOM;
                    mSavedMatrix.set(mTranslateMatrix);
                    startPoint.x = event.getX();
                    startPoint.y = event.getY();

                    mSavedXDist = getXDist(event);
                    mSavedYDist = getYDist(event);
                    mSavedDist = spacing(event);

                    float x = event.getX(0) + event.getX(1);
                    float y = event.getY(0) + event.getY(1);
                    centerPoint.x = (x / 2f);
                    centerPoint.y = (y / 2f);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                J.j("TOUCHMOVE", "move");
                if (touchMode == MODE_DRAG) {
                    J.j("TOUCHMOVE", "move_drag");
                    mTranslateMatrix.set(mSavedMatrix);
                    dx = event.getX() - startPoint.x;
                    dy = event.getY() - startPoint.y;
                    mTranslateMatrix.postTranslate(dx, 0);
//                    startPoint.x = event.getX();
//                    startPoint.y = event.getY();

                    mTouch.set(mTranslateMatrix);
//                    limitTransAndScale(mTouch, mContentRect);
                    mTranslateMatrix.mapPoints(mPoints);
                    invalidate();
                    mTranslateMatrix.set(mTouch);
                } else if (touchMode == MODE_ZOOM) {
                    J.j("TOUCHMOVE", "move_zoom");
                    float totalDist = spacing(event);
                    float scale = totalDist / mSavedDist;
                    float scaleX = scale;
                    float scaleY = 1;
                    mScaleMatrix.set(mSavedMatrix);
                    mScaleMatrix.postScale(scaleX, scaleY, centerPoint.x, centerPoint.y);
//                    mSavedDist = totalDist;

                    mTouch.set(mScaleMatrix);
//                    limitTransAndScale(mTouch, mContentRect);
                    mScaleMatrix.mapPoints(mPoints);
                    invalidate();
                    mScaleMatrix.set(mTouch);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                touchMode = MODE_NONE;
                break;
        }
        return true;
    }

    /**
     * buffer for storing the 9 matrix values of a 3x3 matrix
     */
    protected final float[] matrixBuffer = new float[9];

    /**
     * limits the maximum scale and X translation of the given matrix
     *
     * @param matrix
     */
    public void limitTransAndScale(Matrix matrix, RectF content) {

        matrix.getValues(matrixBuffer);

        float curTransX = matrixBuffer[Matrix.MTRANS_X];
        float curScaleX = matrixBuffer[Matrix.MSCALE_X];

        float curTransY = matrixBuffer[Matrix.MTRANS_Y];
        float curScaleY = matrixBuffer[Matrix.MSCALE_Y];

        // min scale-x is 1f
        mScaleX = Math.min(Math.max(mMinScaleX, curScaleX), mMaxScaleX);

        // min scale-y is 1f
        mScaleY = Math.min(Math.max(mMinScaleY, curScaleY), mMaxScaleY);

        float width = 0f;
        float height = 0f;

        if (content != null) {
            width = content.width();
            height = content.height();
        }

        float maxTransX = -width * (mScaleX - 1f);
        mTransX = Math.min(Math.max(curTransX, maxTransX - mTransOffsetX), mTransOffsetX);

        float maxTransY = height * (mScaleY - 1f);
        mTransY = Math.max(Math.min(curTransY, maxTransY + mTransOffsetY), -mTransOffsetY);

        matrixBuffer[Matrix.MTRANS_X] = mTransX;
        matrixBuffer[Matrix.MSCALE_X] = mScaleX;

        matrixBuffer[Matrix.MTRANS_Y] = mTransY;
        matrixBuffer[Matrix.MSCALE_Y] = mScaleY;

        matrix.setValues(matrixBuffer);
    }

    /**
     * minimum scale value on the y-axis
     */
    private float mMinScaleY = 1f;

    /**
     * maximum scale value on the y-axis
     */
    private float mMaxScaleY = Float.MAX_VALUE;

    /**
     * minimum scale value on the x-axis
     */
    private float mMinScaleX = 1f;

    /**
     * maximum scale value on the x-axis
     */
    private float mMaxScaleX = Float.MAX_VALUE;

    /**
     * contains the current scale factor of the x-axis
     */
    private float mScaleX = 1f;

    /**
     * contains the current scale factor of the y-axis
     */
    private float mScaleY = 1f;

    /**
     * current translation (drag distance) on the x-axis
     */
    private float mTransX = 0f;

    /**
     * current translation (drag distance) on the y-axis
     */
    private float mTransY = 0f;

    /**
     * offset that allows the chart to be dragged over its bounds on the x-axis
     */
    private float mTransOffsetX = 0f;

    /**
     * offset that allows the chart to be dragged over its bounds on the x-axis
     */
    private float mTransOffsetY = 0f;

    private static float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * calculates the distance on the x-axis between two pointers (fingers on
     * the display)
     *
     * @param e
     * @return
     */
    private static float getXDist(MotionEvent e) {
        float x = Math.abs(e.getX(0) - e.getX(1));
        return x;
    }

    /**
     * calculates the distance on the y-axis between two pointers (fingers on
     * the display)
     *
     * @param e
     * @return
     */
    private static float getYDist(MotionEvent e) {
        float y = Math.abs(e.getY(0) - e.getY(1));
        return y;
    }
}
