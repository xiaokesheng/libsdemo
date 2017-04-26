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
    private Paint mTipLinePaint;

    protected RectF mContentRect = new RectF();
    protected RectF mLeftBg = new RectF();

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

    private ArrayList<PointC> pointsList = new ArrayList<>();
    private ArrayList<PointC> pointsListC = new ArrayList<>();
    private String tag1;
    private String tag2;

    private static final int MODE_DRAG = 1;
    private static final int MODE_ZOOM = 2;
    private static final int MODE_NONE = 3;
    private static final int MODE_FLING = 4;

    private static final int ITEM_WIDTH = 100;

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

    private float minScale;

    private float[] matrixValues = new float[9];

    private boolean isLeft;
    private boolean isRight;
    private boolean isRightEdge;

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

    public void setPointsList(ArrayList<PointC> list1, ArrayList<PointC> list2, String tag1, String tag2) {
        this.pointsList = list1;
        this.pointsListC = list2;
        this.tag1 = tag1;
        this.tag2 = tag2;
        init();
        invalidate();
    }

    public void setPointsList(ArrayList<PointC> list1) {
        this.pointsList = list1;
    }


    private void init() {
        isLeft = true;
        isRight = false;

        mLeftWidth = Util.dip2px(45);
        mBottomHeight = Util.dip2px(50);
        mTopMargin = Util.dip2px(25);
        mRightWidth = Util.dip2px(0);
        mLeftBg = new RectF(0, 0, mLeftWidth, initialHeight);

        lineAreaHeight = initialHeight - mBottomHeight - mTopMargin;
        totalHeight = lineAreaHeight;
        lineAreaWidth = initialWidth - mLeftWidth - mRightWidth;

        verticalDividedCount = 4f;
        itemHeight = lineAreaHeight / verticalDividedCount;

        touchMode = MODE_NONE;

        mBound = new LineBound();
        minMax = new MinMax();

//        initializePointsList();

        minScale = lineAreaWidth * 2 / (pointsList.size() * 100);

        Util.handleValues(lineAreaHeight, ITEM_WIDTH, pointsList, pointsListC);

        lineColor = Color.parseColor("#38bd7f");
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(lineColor);
        mLinePaint.setStrokeWidth(5);
        mBgLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgLinePaint.setColor(Color.parseColor("#f0f2f5"));
        mBgLinePaint.setStrokeWidth(1);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(Util.dip2px(15));
        mTextPaint.setColor(Color.parseColor("#7e8694"));
        mTipPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTipLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTipLinePaint.setColor(Color.parseColor("#4e5661"));
        mTipLinePaint.setStrokeWidth(1);

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
        canvas.drawColor(Color.parseColor("#ffffff"));

        for (int i = 0; i < pointsList.size() - 1; i++) {
            float[] point = new float[4];
            mapPoint(pointsList, point, i);
            if (handleBound(point, i, mBound)) {
                continue;
            } else {
                break;
            }
        }

        // 画背景线 5条
        for (int i = 0; i <= verticalDividedCount; i++) {
            float y = i * itemHeight + mTopMargin;
            canvas.drawLine(mLeftWidth, y, mLeftWidth + lineAreaWidth, y, mBgLinePaint);

        }

        // 当显示tip滑动时,画竖线
        if (touchMode == MODE_FLING) {
            mLinePaint.setColor(Color.parseColor("#4e5661"));
            canvas.drawLine(currentX, mTopMargin, currentX, mTopMargin + lineAreaHeight, mTipLinePaint);
        }

        // 画折线
        drawLines(canvas, pointsList, "#38bd7f", false);
        drawLines(canvas, pointsListC, "#38a2ff", true);

        // 画左边的矩形框
        mTextPaint.setColor(Color.parseColor("#ffffff"));
        canvas.drawRect(mLeftBg, mTextPaint);

        // 画背景线左边的字
        for (int i = 0; i <= verticalDividedCount; i++) {
            float y = i * itemHeight + mTopMargin;
            mTextPaint.setColor(Color.parseColor("#7e8694"));
            mTextPaint.setTextSize(Util.dip2px(9));
            canvas.drawText(
                    String.valueOf(Util.formatDouble(mBound.realMaxValue - (mBound.realMaxValue - mBound.realMinValue) / 4f * i, 2)),
                    40, y + Util.dip2px(2),
                    mTextPaint);
        }

        // 画蓝色tip
        if (touchMode == MODE_FLING) {
            drawTips(canvas, pointsList, pointsListC);
        }

        // 画底部日期
        drawBottomTips(canvas, pointsList);
    }

    private void drawBottomTips(Canvas canvas, ArrayList<PointC> pointList) {
        int delta = (mBound.rightIndex - mBound.leftIndex) / 7;
        mTextPaint.setColor(Color.parseColor("#7e8694"));
        mTextPaint.setTextSize(Util.dip2px(9));
        for (int i = mBound.leftIndex + 1; i < mBound.rightIndex; i = i + delta + 1) {
            float[] point = new float[4];
            mapPoint(pointList, point, i);
            canvas.drawText(
                    pointList.get(i).xValue,
                    point[0] + mLeftWidth - Util.dip2px(20),
                    initialHeight - mBottomHeight + Util.dip2px(30),
                    mTextPaint);
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
//                canvas.drawText(String.valueOf(i), point[0], point[1], mTextPaint);
                if (i == mBound.rightIndex) {
                    canvas.drawCircle(point[2], point[3], Util.dip2px(2), mLinePaint);
//                    canvas.drawText(String.valueOf(i + 1), point[2], point[3], mTextPaint);
                }

                if (touchMode == MODE_FLING) {
                    int startIndex;
                    if (currentX > point[0] && currentX <= point[2]) {
                        if (point[2] - currentX < currentX - point[0]) {
                            startIndex = i + 1;
                            canvas.drawCircle(point[2], point[3], 16, mLinePaint);
                        } else {
                            startIndex = i;
                            canvas.drawCircle(point[0], point[1], 16, mLinePaint);
                        }
                    }
                }
                if (i == pointList.size() - 2) {
                    J.j("pointRight", "x: " + point[2]);
                    if (point[2] < initialWidth - mRightWidth) {
                        // 右侧滑到头了
                        isRightEdge = true;
                    } else {
                        isRightEdge = false;
                    }
                    J.j("pointRight", "total: " + (mLeftWidth + lineAreaWidth + mRightWidth));
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
                        mTipPaint.setColor(Color.parseColor("#e009599f"));
                        canvas.drawRoundRect(rect, 10, 10, mTipPaint);

                        mTipPaint.setTextSize(30);
                        mTipPaint.setColor(Color.parseColor("#ffffff"));
                        // 日期
                        canvas.drawText(pointList[0].get(startIndex).xValue, rect.left + 25, rect.top + 50, mTipPaint);
                        // 项目名称
                        canvas.drawText(tag1 + "    " + pointList[0].get(startIndex).yValue + "%", rect.left + 80, rect.top + 120, mTipPaint);
                        // 行业
                        canvas.drawText(tag2 + "    " + pointList[1].get(startIndex).yValue + "%", rect.left + 80, rect.top + 180, mTipPaint);

                        mTipPaint.setColor(Color.parseColor("#38bd7f"));
                        canvas.drawCircle(rect.left + 25 + 15, rect.top + 120 - 10, 15, mTipPaint);
                        mTipPaint.setColor(Color.parseColor("#38a2ff"));
                        canvas.drawCircle(rect.left + 25 + 15, rect.top + 180 - 10, 15, mTipPaint);
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
        mTouch.mapPoints(point);

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
                    minMax.realMinValue = pointsList.get(minMax.minIndex).yValue;
                }
                if (minMax.min > point[1]) {
                    minMax.min = point[1];
                    minMax.maxIndex = j;
                    minMax.realMaxValue = pointsList.get(minMax.maxIndex).yValue;
                }
                if (minMax.max < point[3]) {
                    minMax.max = point[3];
                    minMax.minIndex = j + 1;
                    minMax.realMinValue = pointsList.get(minMax.minIndex).yValue;
                }
                if (minMax.min > point[3]) {
                    minMax.min = point[3];
                    minMax.maxIndex = j + 1;
                    minMax.realMaxValue = pointsList.get(minMax.maxIndex).yValue;
                }
            }
            if (point[2] >= lineAreaWidth - mRightWidth) {
                mBound.rightIndex = j;
                break;
            }
        }
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
//                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                J.j("TouchEvent", "pointer down---");
                hasMoved = true;
                isRightEdge = false;
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
//                    invalidate();
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
                        if (isRightEdge) {
                            if (dx < 0) {
                                return true;
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
                    } else if (touchMode == MODE_ZOOM) {
                        float totalDist = spacing(event);
                        float scale = totalDist / mSavedDist;
                        float scaleX = scale;
                        J.j("scaleX", "scaleX: " + scaleX);
                        float scaleY = 1;
                        mScaleMatrix.getValues(matrixValues);
                        J.j("scaleX", "matrix->scaleX: " + matrixValues[0]);
                        if (matrixValues[0] >= 1 && scaleX > 1) {
                            matrixValues[0] = 1;
                            mScaleMatrix.setValues(matrixValues);
                            return true;
                        } else {

                        }
                        if (matrixValues[0] <= minScale && scaleX < 1) {
                            matrixValues[0] = minScale;
                            mScaleMatrix.setValues(matrixValues);
                            return true;
                        }
                        mScaleMatrix.set(mSavedMatrix);
                        mScaleMatrix.postScale(scaleX, scaleY, centerPoint.x, centerPoint.yPixels);
                        mTouch.set(mScaleMatrix);
                        invalidate();
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
                if (isLeft) {
                    J.j("isLeft", "isLeft=============");
                }
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

//    private void initializePointsList() {
//        pointsList.add(new PointC(0, 600));
//        pointsList.add(new PointC(100, 500));
//        pointsList.add(new PointC(200, 900));
//        pointsList.add(new PointC(300, 800));
//        pointsList.add(new PointC(400, 600));
//        pointsList.add(new PointC(500, 750));
//        pointsList.add(new PointC(600, 300));
//        pointsList.add(new PointC(700, 300));
//        pointsList.add(new PointC(800, 600));
//        pointsList.add(new PointC(900, 200));
//        pointsList.add(new PointC(1000, 500));
//        pointsList.add(new PointC(1100, 400));
//        pointsList.add(new PointC(1200, 200));
//        pointsList.add(new PointC(1300, 500));
//        pointsList.add(new PointC(1400, 600));
//        pointsList.add(new PointC(1500, 200));
//        pointsList.add(new PointC(1600, 600));
//        pointsList.add(new PointC(1700, 150));
//        pointsList.add(new PointC(1800, 210));
//        pointsList.add(new PointC(1900, 200));
//        pointsList.add(new PointC(2000, 250));
//        pointsList.add(new PointC(2100, 260));
//        pointsList.add(new PointC(500 + 1700, 750 * new Random().nextFloat()));
//        pointsList.add(new PointC(600 + 1700, 300 * new Random().nextFloat()));
//        pointsList.add(new PointC(700 + 1700, 300 * new Random().nextFloat()));
//        pointsList.add(new PointC(800 + 1700, 600 * new Random().nextFloat()));
//        pointsList.add(new PointC(900 + 1700, 200 * new Random().nextFloat()));
//        pointsList.add(new PointC(1000 + 1700, 500 * new Random().nextFloat()));
//        pointsList.add(new PointC(1100 + 1700, 400 * new Random().nextFloat()));
//        pointsList.add(new PointC(1200 + 1700, 200 * new Random().nextFloat()));
//        pointsList.add(new PointC(1300 + 1700, 500 * new Random().nextFloat()));
//        pointsList.add(new PointC(1400 + 1700, 600 * new Random().nextFloat()));
//        pointsList.add(new PointC(1500 + 1700, 200 * new Random().nextFloat()));
//        pointsList.add(new PointC(1600 + 1700, 600 * new Random().nextFloat()));
//        pointsList.add(new PointC(1700 + 1700, 150 * new Random().nextFloat()));
//        pointsList.add(new PointC(1800 + 1700, 210 * new Random().nextFloat()));
//        pointsList.add(new PointC(1900 + 1700, 200 * new Random().nextFloat()));
//        pointsList.add(new PointC(2000 + 1700, 250 * new Random().nextFloat()));
//        pointsList.add(new PointC(2100 + 1700, 260 * new Random().nextFloat()));
//        pointsList.add(new PointC(3900 + 0, 600));
//        pointsList.add(new PointC(3900 + 100, 500));
//        pointsList.add(new PointC(3900 + 200, 900));
//        pointsList.add(new PointC(3900 + 300, 800));
//        pointsList.add(new PointC(3900 + 400, 600));
//        pointsList.add(new PointC(3900 + 500, 750));
//        pointsList.add(new PointC(3900 + 600, 300));
//        pointsList.add(new PointC(3900 + 700, 300));
//        pointsList.add(new PointC(3900 + 800, 600));
//        pointsList.add(new PointC(3900 + 900, 200));
//        pointsList.add(new PointC(3900 + 1000, 500));
//        pointsList.add(new PointC(3900 + 1100, 400));
//        pointsList.add(new PointC(3900 + 1200, 200));
//        pointsList.add(new PointC(3900 + 1300, 500));
//        pointsList.add(new PointC(3900 + 1400, 600));
//        pointsList.add(new PointC(3900 + 1500, 200));
//        pointsList.add(new PointC(3900 + 1600, 600));
//        pointsList.add(new PointC(3900 + 1700, 150));
//        pointsList.add(new PointC(3900 + 1800, 210));
//        pointsList.add(new PointC(3900 + 1900, 200));
//        pointsList.add(new PointC(3900 + 2000, 250));
//        pointsList.add(new PointC(3900 + 2100, 260));
//        pointsList.add(new PointC(3900 + 500 + 1700, 750 * new Random().nextFloat()));
//        pointsList.add(new PointC(3900 + 600 + 1700, 300 * new Random().nextFloat()));
//        pointsList.add(new PointC(3900 + 700 + 1700, 300 * new Random().nextFloat()));
//        pointsList.add(new PointC(3900 + 800 + 1700, 600 * new Random().nextFloat()));
//        pointsList.add(new PointC(3900 + 900 + 1700, 200 * new Random().nextFloat()));
//        pointsList.add(new PointC(3900 + 1000 + 1700, 500 * new Random().nextFloat()));
//        pointsList.add(new PointC(3900 + 1100 + 1700, 400 * new Random().nextFloat()));
//        pointsList.add(new PointC(3900 + 1200 + 1700, 200 * new Random().nextFloat()));
//        pointsList.add(new PointC(3900 + 1300 + 1700, 500 * new Random().nextFloat()));
//        pointsList.add(new PointC(3900 + 1400 + 1700, 600 * new Random().nextFloat()));
//        pointsList.add(new PointC(3900 + 1500 + 1700, 200 * new Random().nextFloat()));
//        pointsList.add(new PointC(3900 + 1600 + 1700, 600 * new Random().nextFloat()));
//        pointsList.add(new PointC(3900 + 1700 + 1700, 150 * new Random().nextFloat()));
//        pointsList.add(new PointC(3900 + 1800 + 1700, 210 * new Random().nextFloat()));
//        pointsList.add(new PointC(3900 + 1900 + 1700, 200 * new Random().nextFloat()));
//        pointsList.add(new PointC(3900 + 2000 + 1700, 250 * new Random().nextFloat()));
//        pointsList.add(new PointC(3900 + 2100 + 1700, 260 * new Random().nextFloat()));
//
//        pointsListC.add(new PointC(0, 600 * 2 + 420));
//        pointsListC.add(new PointC(100, 500 * 2 + 420));
//        pointsListC.add(new PointC(200, 1200));
//        pointsListC.add(new PointC(300, 800 * 2 + 420));
//        pointsListC.add(new PointC(400, 600 * 2 + 420));
//        pointsListC.add(new PointC(500, 750 * 2 + 420));
//        pointsListC.add(new PointC(600, 300 * 2 + 420));
//        pointsListC.add(new PointC(700, 300 * 2 + 420));
//        pointsListC.add(new PointC(800, 600 * 2 + 420));
//        pointsListC.add(new PointC(900, 200 * 2 + 420));
//        pointsListC.add(new PointC(1000, 500 * 2 + 420));
//        pointsListC.add(new PointC(1100, 400 * 2 + 420));
//        pointsListC.add(new PointC(1200, 200 * 2 + 420));
//        pointsListC.add(new PointC(1300, 500 * 2 + 420));
//        pointsListC.add(new PointC(1400, 600 * 2 + 420));
//        pointsListC.add(new PointC(1500, 200 * 2 + 420));
//        pointsListC.add(new PointC(1600, 600 * 2 + 420));
//        pointsListC.add(new PointC(1700, 150 * 2 + 420));
//        pointsListC.add(new PointC(1800, 210 * 2 + 420));
//        pointsListC.add(new PointC(1900, 200 * 2 + 420));
//        pointsListC.add(new PointC(2000, 250 * 2 + 420));
//        pointsListC.add(new PointC(2100, 260 * 2 + 420));
//        pointsListC.add(new PointC(500 + 1700, 750 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(600 + 1700, 300 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(700 + 1700, 300 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(800 + 1700, 600 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(900 + 1700, 200 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(1000 + 1700, 500 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(1100 + 1700, 400 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(1200 + 1700, 200 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(1300 + 1700, 500 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(1400 + 1700, 600 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(1500 + 1700, 200 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(1600 + 1700, 600 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(1700 + 1700, 150 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(1800 + 1700, 210 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(1900 + 1700, 200 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(2000 + 1700, 250 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(2100 + 1700, 260 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(3900 + 0, 600 * 2 + 420));
//        pointsListC.add(new PointC(3900 + 100, 500 * 2 + 420));
//        pointsListC.add(new PointC(3900 + 200, 1200));
//        pointsListC.add(new PointC(3900 + 300, 800 * 2 + 420));
//        pointsListC.add(new PointC(3900 + 400, 600 * 2 + 420));
//        pointsListC.add(new PointC(3900 + 500, 750 * 2 + 420));
//        pointsListC.add(new PointC(3900 + 600, 300 * 2 + 420));
//        pointsListC.add(new PointC(3900 + 700, 300 * 2 + 420));
//        pointsListC.add(new PointC(3900 + 800, 600 * 2 + 420));
//        pointsListC.add(new PointC(3900 + 900, 200 * 2 + 420));
//        pointsListC.add(new PointC(3900 + 1000, 500 * 2 + 420));
//        pointsListC.add(new PointC(3900 + 1100, 400 * 2 + 420));
//        pointsListC.add(new PointC(3900 + 1200, 200 * 2 + 420));
//        pointsListC.add(new PointC(3900 + 1300, 500 * 2 + 420));
//        pointsListC.add(new PointC(3900 + 1400, 600 * 2 + 420));
//        pointsListC.add(new PointC(3900 + 1500, 200 * 2 + 420));
//        pointsListC.add(new PointC(3900 + 1600, 600 * 2 + 420));
//        pointsListC.add(new PointC(3900 + 1700, 150 * 2 + 420));
//        pointsListC.add(new PointC(3900 + 1800, 210 * 2 + 420));
//        pointsListC.add(new PointC(3900 + 1900, 200 * 2 + 420));
//        pointsListC.add(new PointC(3900 + 2000, 250 * 2 + 420));
//        pointsListC.add(new PointC(3900 + 2100, 260 * 2 + 420));
//        pointsListC.add(new PointC(3900 + 500 + 1700, 750 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(3900 + 600 + 1700, 300 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(3900 + 700 + 1700, 300 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(3900 + 800 + 1700, 600 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(3900 + 900 + 1700, 200 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(3900 + 1000 + 1700, 500 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(3900 + 1100 + 1700, 400 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(3900 + 1200 + 1700, 200 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(3900 + 1300 + 1700, 500 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(3900 + 1400 + 1700, 600 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(3900 + 1500 + 1700, 200 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(3900 + 1600 + 1700, 600 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(3900 + 1700 + 1700, 150 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(3900 + 1800 + 1700, 210 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(3900 + 1900 + 1700, 200 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(3900 + 2000 + 1700, 250 * new Random().nextFloat() * 3));
//        pointsListC.add(new PointC(3900 + 2100 + 1700, 260 * new Random().nextFloat() * 3));
//    }
}
