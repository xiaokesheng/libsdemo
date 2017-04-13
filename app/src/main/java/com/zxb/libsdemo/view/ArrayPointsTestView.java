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

import com.zxb.libsdemo.model.Point;
import com.zxb.libsdemo.util.J;

/**
 * Created by mrzhou on 2017/4/12.
 */
public class ArrayPointsTestView extends View {

    private Paint mLinePaint;

    protected RectF mContentRect = new RectF();

    private float[] mPoints;

    private float[] mOther;

    private int lineColor;

    private Point startPoint;
    private Point lastPoint;
    private Point centerPoint;

    private float dx, dy;

    private Matrix mTranslateMatrix;
    private Matrix mSavedMatrix;
    private Matrix mScaleMatrix;

    private Matrix mTouch;

    private float mSavedXDist = 1f;
    private float mSavedYDist = 1f;
    private float mSavedDist = 1f;

    private int touchMode;

    private static final int MODE_DRAG = 1;
    private static final int MODE_ZOOM = 2;
    private static final int MODE_NONE = 3;

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
        mOther = mPoints.clone();

        lineColor = Color.parseColor("#389cff");
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(lineColor);
        mLinePaint.setStrokeWidth(5);
        startPoint = new Point();
        centerPoint = new Point();

        mTranslateMatrix = new Matrix();
        mSavedMatrix = new Matrix();
        mScaleMatrix = new Matrix();
        mTouch = new Matrix();
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
        canvas.drawLines(mPoints
                , mLinePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                touchMode = MODE_DRAG;
                J.j("TOUCHMOVE", "down");
                if (null != mDragListener) {
                    mDragListener.start();
                }
                mSavedMatrix.set(mTranslateMatrix);
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

                    // get the distance between the pointers on the x-axis
                    mSavedXDist = getXDist(event);

                    // get the distance between the pointers on the y-axis
                    mSavedYDist = getYDist(event);

                    // get the total distance between the pointers
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
                    mTranslateMatrix.postTranslate(dx, dy);
//                if (null != mDragListener) {
//                    mDragListener.drag(dx, dy);
//                }
                    startPoint.x = event.getX();
                    startPoint.y = event.getY();

                    mTouch.set(mTranslateMatrix);
                    limitTransAndScale(mTouch, mContentRect);
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
                    mSavedDist = totalDist;

                    mTouch.set(mScaleMatrix);
                    limitTransAndScale(mTouch, mContentRect);
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
//        mTouch.set(mTranslateMatrix);
//        limitTransAndScale(mTouch, mContentRect);
//        mTranslateMatrix.mapPoints(mPoints);
//        invalidate();
//        mTranslateMatrix.set(mTouch);
        return true;
    }

    OnDragListener mDragListener;

    public interface OnDragListener {
        void drag(float dx, float dy);

        void start();

        void end();
    }

    public void setDragListener(OnDragListener listener) {
        this.mDragListener = listener;
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
