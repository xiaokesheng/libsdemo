package com.zxb.libsdemo.view.points;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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
    private Paint mBgLinePaint;
    private Paint mTextPaint;
    private Paint mTipPaint;

    protected RectF mContentRect = new RectF();

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
    private static final int MODE_FLING = 4;

    private boolean hasMoved;
    private boolean hasTouch;

    public LineBound mBound;

    public MinMax minMax;

    float totalHeight;
    float initialHeight;
    float lineAreaHeight;
    float lineAreaWidth;

    float initialWidth;

    private int mLeftWidth;
    private int mBottomHeight;
    private int mTopMargin;
    private int mRightWidth;

    private float verticalDividedCount;
    private float itemHeight;

    private float currentX;

    private float[] matrixValues = new float[9];

    private boolean isLeft;

    RectF rect = new RectF();

    public ArrayPointsTestView(Context context) {
        this(context, null);
    }

    public ArrayPointsTestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArrayPointsTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initialHeight = h;
        initialWidth = w;
        init();
    }

    private void init() {

        isLeft = true;

        mLeftWidth = Util.dip2px(60);
        mBottomHeight = Util.dip2px(50);
        mTopMargin = Util.dip2px(25);
        mRightWidth = Util.dip2px(0);

        lineAreaHeight = initialHeight - mBottomHeight - mTopMargin;
        totalHeight = lineAreaHeight;
        lineAreaWidth = initialWidth - mLeftWidth - mRightWidth;

        verticalDividedCount = 4f;
        itemHeight = lineAreaHeight / verticalDividedCount;

        touchMode = MODE_NONE;
        pointsList = new ArrayList<>();
        pointsListC = new ArrayList<>();
        mBound = new LineBound();
        minMax = new MinMax();

        pointsList.add(new PointC(0, 600));
        pointsList.add(new PointC(100, 500));
        pointsList.add(new PointC(200, 900));
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
        pointsList.add(new PointC(3900 + 0, 600));
        pointsList.add(new PointC(3900 + 100, 500));
        pointsList.add(new PointC(3900 + 200, 900));
        pointsList.add(new PointC(3900 + 300, 800));
        pointsList.add(new PointC(3900 + 400, 600));
        pointsList.add(new PointC(3900 + 500, 750));
        pointsList.add(new PointC(3900 + 600, 300));
        pointsList.add(new PointC(3900 + 700, 300));
        pointsList.add(new PointC(3900 + 800, 600));
        pointsList.add(new PointC(3900 + 900, 200));
        pointsList.add(new PointC(3900 + 1000, 500));
        pointsList.add(new PointC(3900 + 1100, 400));
        pointsList.add(new PointC(3900 + 1200, 200));
        pointsList.add(new PointC(3900 + 1300, 500));
        pointsList.add(new PointC(3900 + 1400, 600));
        pointsList.add(new PointC(3900 + 1500, 200));
        pointsList.add(new PointC(3900 + 1600, 600));
        pointsList.add(new PointC(3900 + 1700, 150));
        pointsList.add(new PointC(3900 + 1800, 210));
        pointsList.add(new PointC(3900 + 1900, 200));
        pointsList.add(new PointC(3900 + 2000, 250));
        pointsList.add(new PointC(3900 + 2100, 260));
        pointsList.add(new PointC(3900 + 500 + 1700, 750 * new Random().nextFloat()));
        pointsList.add(new PointC(3900 + 600 + 1700, 300 * new Random().nextFloat()));
        pointsList.add(new PointC(3900 + 700 + 1700, 300 * new Random().nextFloat()));
        pointsList.add(new PointC(3900 + 800 + 1700, 600 * new Random().nextFloat()));
        pointsList.add(new PointC(3900 + 900 + 1700, 200 * new Random().nextFloat()));
        pointsList.add(new PointC(3900 + 1000 + 1700, 500 * new Random().nextFloat()));
        pointsList.add(new PointC(3900 + 1100 + 1700, 400 * new Random().nextFloat()));
        pointsList.add(new PointC(3900 + 1200 + 1700, 200 * new Random().nextFloat()));
        pointsList.add(new PointC(3900 + 1300 + 1700, 500 * new Random().nextFloat()));
        pointsList.add(new PointC(3900 + 1400 + 1700, 600 * new Random().nextFloat()));
        pointsList.add(new PointC(3900 + 1500 + 1700, 200 * new Random().nextFloat()));
        pointsList.add(new PointC(3900 + 1600 + 1700, 600 * new Random().nextFloat()));
        pointsList.add(new PointC(3900 + 1700 + 1700, 150 * new Random().nextFloat()));
        pointsList.add(new PointC(3900 + 1800 + 1700, 210 * new Random().nextFloat()));
        pointsList.add(new PointC(3900 + 1900 + 1700, 200 * new Random().nextFloat()));
        pointsList.add(new PointC(3900 + 2000 + 1700, 250 * new Random().nextFloat()));
        pointsList.add(new PointC(3900 + 2100 + 1700, 260 * new Random().nextFloat()));

        pointsListC.add(new PointC(0, 600 * 2 + 420));
        pointsListC.add(new PointC(100, 500 * 2 + 420));
        pointsListC.add(new PointC(200, 1200));
        pointsListC.add(new PointC(300, 800 * 2 + 420));
        pointsListC.add(new PointC(400, 600 * 2 + 420));
        pointsListC.add(new PointC(500, 750 * 2 + 420));
        pointsListC.add(new PointC(600, 300 * 2 + 420));
        pointsListC.add(new PointC(700, 300 * 2 + 420));
        pointsListC.add(new PointC(800, 600 * 2 + 420));
        pointsListC.add(new PointC(900, 200 * 2 + 420));
        pointsListC.add(new PointC(1000, 500 * 2 + 420));
        pointsListC.add(new PointC(1100, 400 * 2 + 420));
        pointsListC.add(new PointC(1200, 200 * 2 + 420));
        pointsListC.add(new PointC(1300, 500 * 2 + 420));
        pointsListC.add(new PointC(1400, 600 * 2 + 420));
        pointsListC.add(new PointC(1500, 200 * 2 + 420));
        pointsListC.add(new PointC(1600, 600 * 2 + 420));
        pointsListC.add(new PointC(1700, 150 * 2 + 420));
        pointsListC.add(new PointC(1800, 210 * 2 + 420));
        pointsListC.add(new PointC(1900, 200 * 2 + 420));
        pointsListC.add(new PointC(2000, 250 * 2 + 420));
        pointsListC.add(new PointC(2100, 260 * 2 + 420));
        pointsListC.add(new PointC(500 + 1700, 750 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(600 + 1700, 300 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(700 + 1700, 300 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(800 + 1700, 600 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(900 + 1700, 200 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(1000 + 1700, 500 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(1100 + 1700, 400 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(1200 + 1700, 200 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(1300 + 1700, 500 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(1400 + 1700, 600 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(1500 + 1700, 200 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(1600 + 1700, 600 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(1700 + 1700, 150 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(1800 + 1700, 210 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(1900 + 1700, 200 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(2000 + 1700, 250 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(2100 + 1700, 260 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(3900 + 0, 600 * 2 + 420));
        pointsListC.add(new PointC(3900 + 100, 500 * 2 + 420));
        pointsListC.add(new PointC(3900 + 200, 1200));
        pointsListC.add(new PointC(3900 + 300, 800 * 2 + 420));
        pointsListC.add(new PointC(3900 + 400, 600 * 2 + 420));
        pointsListC.add(new PointC(3900 + 500, 750 * 2 + 420));
        pointsListC.add(new PointC(3900 + 600, 300 * 2 + 420));
        pointsListC.add(new PointC(3900 + 700, 300 * 2 + 420));
        pointsListC.add(new PointC(3900 + 800, 600 * 2 + 420));
        pointsListC.add(new PointC(3900 + 900, 200 * 2 + 420));
        pointsListC.add(new PointC(3900 + 1000, 500 * 2 + 420));
        pointsListC.add(new PointC(3900 + 1100, 400 * 2 + 420));
        pointsListC.add(new PointC(3900 + 1200, 200 * 2 + 420));
        pointsListC.add(new PointC(3900 + 1300, 500 * 2 + 420));
        pointsListC.add(new PointC(3900 + 1400, 600 * 2 + 420));
        pointsListC.add(new PointC(3900 + 1500, 200 * 2 + 420));
        pointsListC.add(new PointC(3900 + 1600, 600 * 2 + 420));
        pointsListC.add(new PointC(3900 + 1700, 150 * 2 + 420));
        pointsListC.add(new PointC(3900 + 1800, 210 * 2 + 420));
        pointsListC.add(new PointC(3900 + 1900, 200 * 2 + 420));
        pointsListC.add(new PointC(3900 + 2000, 250 * 2 + 420));
        pointsListC.add(new PointC(3900 + 2100, 260 * 2 + 420));
        pointsListC.add(new PointC(3900 + 500 + 1700, 750 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(3900 + 600 + 1700, 300 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(3900 + 700 + 1700, 300 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(3900 + 800 + 1700, 600 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(3900 + 900 + 1700, 200 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(3900 + 1000 + 1700, 500 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(3900 + 1100 + 1700, 400 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(3900 + 1200 + 1700, 200 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(3900 + 1300 + 1700, 500 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(3900 + 1400 + 1700, 600 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(3900 + 1500 + 1700, 200 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(3900 + 1600 + 1700, 600 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(3900 + 1700 + 1700, 150 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(3900 + 1800 + 1700, 210 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(3900 + 1900 + 1700, 200 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(3900 + 2000 + 1700, 250 * new Random().nextFloat() * 3));
        pointsListC.add(new PointC(3900 + 2100 + 1700, 260 * new Random().nextFloat() * 3));

        Util.handleValues(lineAreaHeight, pointsList, pointsListC);

        lineColor = Color.parseColor("#389cff");
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(lineColor);
        mLinePaint.setStrokeWidth(5);
        mBgLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgLinePaint.setColor(Color.parseColor("#7c8594"));
        mBgLinePaint.setStrokeWidth(1);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(Util.dip2px(15));
        mTextPaint.setColor(Color.parseColor("#ffffff"));
        mTipPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        startPoint = new PointC();
        centerPoint = new PointC();

        mTranslateMatrix = new Matrix();
        mSavedMatrix = new Matrix();
        mScaleMatrix = new Matrix();
        mTouch = new Matrix();
        mYScaleMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < pointsList.size() - 1; i++) {
            float[] point = new float[4];
            mapPoint(pointsList, point, i);
            if (handleBound(point, i, mBound)) {
                continue;
            } else {
                break;
            }
        }

        drawLines(canvas, pointsList, "#389cff", false);
        drawLines(canvas, pointsListC, "#ff0000", true);

        for (int i = 0; i <= verticalDividedCount; i++) {
            float y = i * itemHeight + mTopMargin;
            canvas.drawLine(mLeftWidth, y, mLeftWidth + lineAreaWidth, y, mBgLinePaint);
            canvas.drawText(String.valueOf(mBound.realMaxValue - (mBound.realMaxValue - mBound.realMinValue) / 4f * i), 40, y + Util.dip2px(8), mTextPaint);
        }

        if (touchMode == MODE_FLING) {
            mLinePaint.setColor(Color.parseColor("#ffff00"));
            canvas.drawLine(currentX, mTopMargin, currentX, mTopMargin + lineAreaHeight, mLinePaint);
        }

        if (touchMode == MODE_FLING) {
            drawTips(canvas, pointsList, pointsListC);
        }
    }

    private void drawLines(Canvas canvas, ArrayList<PointC> pointList, String color, boolean isLast) {
        mLinePaint.setColor(Color.parseColor(color));
        if (mBound.leftIndex >= 0 && mBound.rightIndex > 0) {
            float deltaHeight = mBound.bottomPixels - mBound.topPixels;
            float yScale = totalHeight / deltaHeight;
            float yPoint;

            if (mBound.bottomPixels - mBound.topPixels == lineAreaHeight) {
                yPoint = mBound.bottomPixels;
            } else {
                yPoint = mBound.topPixels * lineAreaHeight / (lineAreaHeight - mBound.bottomPixels + mBound.topPixels);
            }
            for (int i = mBound.leftIndex; i <= mBound.rightIndex; i++) {
                float[] point = new float[4];
                mapPoint(pointList, point, i);
                mYScaleMatrix.postScale(1, yScale, 0, yPoint);
                mYScaleMatrix.mapPoints(point);
                mYScaleMatrix.reset();
                point[0] += mLeftWidth;
                point[2] += mLeftWidth;
                point[1] += mTopMargin;
                point[3] += mTopMargin;
                canvas.drawLines(point, mLinePaint);
                canvas.drawCircle(point[0], point[1], Util.dip2px(2), mLinePaint);
                if (i == mBound.rightIndex) {
                    canvas.drawCircle(point[2], point[3], Util.dip2px(2), mLinePaint);
                }

                if (touchMode == MODE_FLING) {
                    int startIndex;
                    if (currentX > point[0] && currentX <= point[2]) {
                        if (point[2] - currentX < currentX - point[0]) {
                            startIndex = i + 1;
                            canvas.drawCircle(point[2], point[3], Util.dip2px(10), mLinePaint);
                        } else {
                            startIndex = i;
                            canvas.drawCircle(point[0], point[1], Util.dip2px(10), mLinePaint);
                        }
                    }
                }
            }
        }
    }

    private void drawTips(Canvas canvas, ArrayList<PointC>... pointList) {
        if (mBound.leftIndex >= 0 && mBound.rightIndex > 0) {
            float deltaHeight = mBound.bottomPixels - mBound.topPixels;
            float yScale = totalHeight / deltaHeight;
            float yPoint;

            if (mBound.bottomPixels - mBound.topPixels == lineAreaHeight) {
                yPoint = mBound.bottomPixels;
            } else {
                yPoint = mBound.topPixels * lineAreaHeight / (lineAreaHeight - mBound.bottomPixels + mBound.topPixels);
            }
            for (int i = mBound.leftIndex; i <= mBound.rightIndex; i++) {
                float[] point = new float[4];
                mapPoint(pointList[0], point, i);
                mYScaleMatrix.postScale(1, yScale, 0, yPoint);
                mYScaleMatrix.mapPoints(point);
                mYScaleMatrix.reset();
                point[0] += mLeftWidth;
                point[2] += mLeftWidth;

                if (touchMode == MODE_FLING) {
                    int startIndex;
                    if (currentX > point[0] && currentX <= point[2]) {
                        if (point[2] - currentX < currentX - point[0]) {
                            startIndex = i + 1;
                        } else {
                            startIndex = i;
                        }
                        rect.top = mTopMargin;
                        if (currentX < mLeftWidth + lineAreaWidth / 2) {
                            rect.left = currentX + 12;
                            rect.right = rect.left + 350;
                        } else {
                            rect.right = currentX - 12;
                            rect.left = rect.right - 350;
                        }
                        rect.bottom = rect.top + 250;
                        mTipPaint.setColor(Color.parseColor("#66389cff"));
                        canvas.drawRoundRect(rect, 10, 10, mTipPaint);

                        mTipPaint.setTextSize(30);
                        mTipPaint.setColor(Color.parseColor("#ffffff"));
                        canvas.drawText("2017-21-24", rect.left + 25, rect.top + 40, mTipPaint);
                        canvas.drawText("鼎泰新材" + pointList[0].get(startIndex).yValue, rect.left + 60, rect.top + 100, mTipPaint);
                        canvas.drawText("交通运输" + pointList[1].get(startIndex).yValue, rect.left + 60, rect.top + 140, mTipPaint);

                        mTipPaint.setColor(Color.parseColor("#389cff"));
                        canvas.drawCircle(rect.left + 25 + 15, rect.top + 100 - 15, 15, mTipPaint);
                        mTipPaint.setColor(Color.parseColor("#ff0000"));
                        canvas.drawCircle(rect.left + 25 + 15, rect.top + 140 - 15, 15, mTipPaint);
                    }
                }
            }
        }
    }

    private float[] mapPoint(ArrayList<PointC> pointsList, float[] point, int i) {
        point[0] = pointsList.get(i).x;
        point[1] = pointsList.get(i).yPixels;
        point[2] = pointsList.get(i + 1).x;
        point[3] = pointsList.get(i + 1).yPixels;
        switch (touchMode) {
            case MODE_DRAG:
                mTranslateMatrix.mapPoints(point);
                break;
            case MODE_ZOOM:
                mScaleMatrix.mapPoints(point);
                break;
            case MODE_NONE:
            case MODE_FLING:
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
            minMax.reset();
            handleMinMax(pointsList, minMax);
            handleMinMax(pointsListC, minMax);

            mBound.topPixels = minMax.min;
            mBound.topIndex = minMax.maxIndex;
            mBound.bottomPixels = minMax.max;
            mBound.bottomIndex = minMax.minIndex;
            mBound.realMaxValue = minMax.realMaxValue;
            mBound.realMinValue = minMax.realMinValue;
            J.j("minMax", "-->maxIndex = " + mBound.topIndex + ", value: " + mBound.realMaxValue);
            J.j("minMax", "-->minIndex = " + mBound.bottomIndex + ", value: " + mBound.realMinValue);
        } else {
            return true;
        }
        if (mBound.leftIndex >= 0 && mBound.rightIndex > 0) {
            return false;
        }
        return true;
    }

    private void handleMinMax(ArrayList<PointC> pointsList, MinMax minMax) {
        for (int j = mBound.leftIndex; j < pointsList.size() - 1; j++) {
            float[] point = new float[4];
            mapPoint(pointsList, point, j);
            if (j >= mBound.leftIndex) {
                if (minMax.max < point[1]) {
                    minMax.max = point[1];
                    minMax.minIndex = j;
                    J.j("minMaxReal", "minMax.minIndex: " + j + ", value: " + pointsList.get(j).yValue);
                    minMax.realMinValue = pointsList.get(minMax.minIndex).yValue;
                }
                if (minMax.min > point[1]) {
                    minMax.min = point[1];
                    minMax.maxIndex = j;
                    minMax.realMaxValue = pointsList.get(minMax.maxIndex).yValue;
                }
            }
            if (point[0] >= lineAreaWidth - mRightWidth) {
                mBound.rightIndex = j - 1;
                break;
            }
        }

        J.j("minMax", "maxIndex = " + minMax.maxIndex + ", value: " + minMax.realMaxValue);
        J.j("minMax", "minIndex = " + minMax.minIndex + ", value: " + minMax.realMinValue);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                J.j("TouchEvent", "down---");
                hasTouch = true;
                if (touchMode == MODE_FLING) {

                } else {
                    touchMode = MODE_DRAG;

                }
                mSavedMatrix.set(mTouch);
                startPoint.x = event.getX();
                startPoint.yPixels = event.getY();
                currentX = event.getX();
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                J.j("TouchEvent", "pointer down---");
                hasMoved = true;
                if (event.getPointerCount() >= 2) {
                    touchMode = MODE_ZOOM;
                    mSavedMatrix.set(mTouch);
                    startPoint.x = event.getX();
                    startPoint.yPixels = event.getY();

                    mSavedXDist = getXDist(event);
                    mSavedYDist = getYDist(event);
                    mSavedDist = spacing(event);

                    float x = event.getX(0) + event.getX(1);
                    float y = event.getY(0) + event.getY(1);
                    centerPoint.x = (x / 2f);
                    centerPoint.yPixels = (y / 2f);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                hasMoved = true;
                currentX = event.getX();
                if (touchMode == MODE_FLING) {

                    invalidate();
                } else {
                    J.j("TouchEvent", "move---");
                    if (touchMode == MODE_DRAG) {
                        dx = event.getX() - startPoint.x;
                        dy = event.getY() - startPoint.yPixels;
                        if (isLeft) {
                            if (dx > 0) {
                                return true;
                            } else {

                            }
                        }
                        mTranslateMatrix.set(mSavedMatrix);
                        mTranslateMatrix.postTranslate(dx, 0);
                        mTouch.set(mTranslateMatrix);
                        mTranslateMatrix.getValues(matrixValues);
                        J.j("transX", "matrix->transX: " + matrixValues[2]);
                        if (matrixValues[2] >= 0) {
                            isLeft = true;
                        } else {
                            isLeft = false;
                        }
                        invalidate();
                        mTranslateMatrix.set(mTouch);
                    } else if (touchMode == MODE_ZOOM) {
                        float totalDist = spacing(event);
                        float scale = totalDist / mSavedDist;
                        float scaleX = scale;
                        J.j("scaleX", "scaleX: " + scaleX);
                        float scaleY = 1;
                        mScaleMatrix.getValues(matrixValues);
                        J.j("scaleX", "matrix->scaleX: " + matrixValues[0]);
                        if (matrixValues[0] > 1) {
                            scaleX = 1;
                        }
                        mScaleMatrix.set(mSavedMatrix);
                        mScaleMatrix.postScale(scaleX, scaleY, centerPoint.x, centerPoint.yPixels);


                        mTouch.set(mScaleMatrix);
                        invalidate();
                        mScaleMatrix.set(mTouch);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                J.j("TouchEvent", "up---");
                if (hasTouch) {
                    if (!hasMoved) {
                        if (touchMode == MODE_FLING) {
                            touchMode = MODE_NONE;
                        } else {
                            touchMode = MODE_FLING;
                        }
                    } else {
                        // touch mode不变
                    }
                }
                hasTouch = false;
                hasMoved = false;
                invalidate();
                break;
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

        // min scale-yPixels is 1f
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
     * minimum scale value on the yPixels-axis
     */
    private float mMinScaleY = 1f;

    /**
     * maximum scale value on the yPixels-axis
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
     * contains the current scale factor of the yPixels-axis
     */
    private float mScaleY = 1f;

    /**
     * current translation (drag distance) on the x-axis
     */
    private float mTransX = 0f;

    /**
     * current translation (drag distance) on the yPixels-axis
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
     * calculates the distance on the yPixels-axis between two pointers (fingers on
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
