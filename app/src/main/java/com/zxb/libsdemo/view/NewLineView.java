package com.zxb.libsdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.line.NewPoint;
import com.zxb.libsdemo.util.Util;

import java.util.ArrayList;

/**
 * Created by mrzhou on 16/5/31.
 */
public class NewLineView extends View {

    private static final int ITEM_WIDTH_LARGE_INDP = 32;
    private static final int ITEM_WIDTH_MEDIUM_INDP = 16;
    private static final int ITEM_WIDTH_MINIMUM_INDP = 8;

    private int mSelectArea;

    private int mItemWidth;

    private static final int mMarginTop = Util.dip2px(30);
    private static final int mMarginBottom = Util.dip2px(30);

    private int mScreenWidth;

    private int mColor = Color.parseColor("#389cff");
    private int mLineColor = Color.parseColor("#7fffffff");
    private int mCircleColor = Color.parseColor("#7fffffff");

    private int mBgLineColor = Color.parseColor("#18ffffff");
    private int mBgBottomLineColor = Color.parseColor("#33ffffff");

    private int mTextColor = Color.parseColor("#ffffff");

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mStrokeLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mBgLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTextPaint = new Paint();
    private Paint mImagePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Path strokePath;

    private Bitmap mQuoteBottomBitmap;

    private int mInitialWidth = Util.dip2px(60 * 40);
    private int mInitialHeight = Util.dip2px(210);

    private int mCircleRadius;

    private int paddingLeft;
    private int paddingTop;
    private int paddingRight;
    private int paddingBottom;

    private ArrayList<NewPoint> pointList;

    private Context mContext;

    private Rect r;

    private int mLeftRemainWidth;

    private float minimumMark = 33.8f;
    private int minimumPosition;

    private int mCurrentPosition;

    private HorizontalScrollViewX mHorizontalScrollViewX;

    private OnScrollListener mOnScrollListener;

    private boolean positionSelected = true;

    public NewLineView(Context context) {
        this(context, null);
    }

    public NewLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {

        r = new Rect();
        mPaint.setColor(mColor);
        mCircleRadius = Util.dip2px(2);
        mScreenWidth = Util.getScreenWidth(mContext);
        mItemWidth = Util.dip2px(ITEM_WIDTH_LARGE_INDP);
        mSelectArea = (int) (((float) mItemWidth) / 2f * 0.2f);
        mLeftRemainWidth = mScreenWidth / 2;

        mLinePaint.setColor(mLineColor);
        mLinePaint.setStrokeWidth(Util.dip2px(1));

        mCirclePaint.setColor(mCircleColor);

        mStrokeLinePaint.setColor(mLineColor);
        mStrokeLinePaint.setStyle(Paint.Style.STROKE);
        mStrokeLinePaint.setStrokeWidth(Util.dip2px(1));
        mStrokeLinePaint.setAntiAlias(true);
        DashPathEffect effects = new DashPathEffect(new float[] { 5, 5, 5, 5 }, 1);
        mStrokeLinePaint.setStyle(Paint.Style.STROKE);
        mStrokeLinePaint.setPathEffect(effects);

        mBgLinePaint.setStrokeWidth(2);

        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(Util.dip2px(10));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        strokePath = new Path();

        minimumPosition = (int) minimumMark;

        mCurrentPosition = 0;

        mQuoteBottomBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.image_quotebottom);
    }

    public void setOnScrollListener(OnScrollListener listener) {
        this.mOnScrollListener = listener;
    }

    public void setHorizontalScrollViewX(HorizontalScrollViewX horizontalScrollViewX) {
        this.mHorizontalScrollViewX = horizontalScrollViewX;
    }

    public void setPointList(ArrayList<NewPoint> pointList) {
        this.pointList = pointList;

        initFromPointList();
    }

    public ArrayList<NewPoint> getPointList() {
        return pointList;
    }

    private void initFromPointList() {
        int width = 0;
        width += mScreenWidth;
        if (null != pointList && pointList.size() > 0) {
            width += (pointList.size() - 1) * mItemWidth;
            mInitialWidth = width;

            initPointListRealYInPixels(pointList);

            requestLayout();
            Log.e("position", "position == " + String.valueOf(mCurrentPosition));
        } else {
            // TODO 处理异常，没有点的数据
            return;
        }

    }

    private void initPointListRealYInPixels(ArrayList<NewPoint> pointList) {
        for (NewPoint point : pointList) {
            point.setyInPixes((1 - point.getY()) * 300 + mMarginTop);
        }
    }

    public void setScrollStoped(int x) {
        int position = x / mItemWidth;
        int delta = x % mItemWidth;

        if (delta < mItemWidth / 2) {
        } else {
            position++;
        }

        setCurrentPosition(position);
    }

    // 提醒当前的滑动位置
    public void setInScrolling(int x) {
        int delta = x % mItemWidth;
        if (delta < mItemWidth / 2) {
            int position = x / mItemWidth;
            if (delta < mSelectArea) {
                // 选中了，在position页
//                if (!positionSelected) {
                positionSelected = true;
                Log.e("tagPositionOnScroll", "选中了第" + String.valueOf(position) + "个");
                mCurrentPosition = position;
                if (null != mOnScrollListener) {
                    mOnScrollListener.onScrollStoped(getCurrentPointHeight());
                }
//                }
            } else {
                positionSelected = false;
                if (null != mOnScrollListener) {
                    mOnScrollListener.onUnVisibleView();
                }
            }
        } else {
            int position = x / mItemWidth + 1;
            if (mItemWidth - delta < mSelectArea) {
                // 选中了，在position页
//                if (!positionSelected) {
                positionSelected = true;
                Log.e("tagPositionOnScroll", "选中了第" + String.valueOf(position) + "个");
                mCurrentPosition = position;
                if (null != mOnScrollListener) {
                    mOnScrollListener.onScrollStoped(getCurrentPointHeight());
                }
//                }
            } else {
                positionSelected = false;
                if (null != mOnScrollListener) {
                    mOnScrollListener.onUnVisibleView();
                }
            }
        }
    }


    private int oldX;

    private void setScroll(int x) {
        if (null != mHorizontalScrollViewX) {
            mHorizontalScrollViewX.scrollTo(x, 0);

            Log.e("scrollposition", "setScroll to " + String.valueOf(x));
            if (null != mOnScrollListener) {
                if (oldX != x) {
                    mOnScrollListener.onScrollStoped(getCurrentPointHeight());
                    Log.e("scrollposition", "Request" + String.valueOf(x) + ":-->" + String.valueOf(getPosition()));

                    // TODO 执行网络请求
                    mOnScrollListener.onRequestNext(getPosition());
                    oldX = x;
                }
            }
        } else {
            throw new NullPointerException("Null HorizontalScrollView");
        }
    }

    private void setCurrentPosition(int position) {
        this.mCurrentPosition = position;
        positionSelected = true;
        if (null != mHorizontalScrollViewX) {
            if (position >= 0 && position < pointList.size()) {
                setScroll(position * mItemWidth);
            }
        }
    }

    public int getPosition() {
        return mCurrentPosition;
    }

    public void setPosition(int position) {
        setCurrentPosition(position);
    }

    public int getCurrentPointHeight() {
        return (int) pointList.get(mCurrentPosition).getyInPixes();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setCurrentPosition(mCurrentPosition);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mInitialWidth, mInitialHeight);
    }

    public void setWidthType(int type) {
        if (type > 0) {
            mItemWidth = Util.dip2px(ITEM_WIDTH_LARGE_INDP);
        } else if (type < 0) {
            mItemWidth = Util.dip2px(ITEM_WIDTH_MINIMUM_INDP);
        } else {
            mItemWidth = Util.dip2px(ITEM_WIDTH_MEDIUM_INDP);
        }
        mSelectArea = mItemWidth / 2 - 2;
        initFromPointList();
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        // 重新测量布局之后，重新绘制
        invalidate();
    }

    public int getWidthType() {
        if (mItemWidth == Util.dip2px(ITEM_WIDTH_LARGE_INDP)) {
            return 1;
        } else if (mItemWidth == Util.dip2px(ITEM_WIDTH_MEDIUM_INDP)) {
            return 0;
        } else if (mItemWidth == Util.dip2px(ITEM_WIDTH_MINIMUM_INDP)) {
            return -1;
        }
        return 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (null != canvas) {

        }

        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        r.top = paddingTop;
        r.left = paddingLeft;
        r.right = r.left + width;
        r.bottom = r.top + height;

        canvas.drawRect(r, mPaint);

        // 开始画点和线
        if (null != pointList && pointList.size() > 1) {
            // 画除最后一个点之外的所有点和线
            for (int i = 0; i < pointList.size() - 1; i++) {
                NewPoint firstPoint = pointList.get(i);
                NewPoint secondPoint = pointList.get(i + 1);
                canvas.drawCircle(
                        mLeftRemainWidth + mItemWidth * i,
                        firstPoint.getyInPixes(),
                        mCircleRadius,
                        mCirclePaint);

                canvas.drawLine(
                        mLeftRemainWidth + mItemWidth * i,
                        firstPoint.getyInPixes(),
                        mLeftRemainWidth + mItemWidth * (i + 1),
                        secondPoint.getyInPixes(),
                        mLinePaint);

                // 如果有标记最低价，展示
                // 除了展示最低价之外，还要展示图标
//                if (i + 1 == minimumPosition) {
//
//                    int left = (int) (mLeftRemainWidth + mItemWidth * (minimumMark - 1) - mQuoteBottomBitmap.getWidth() / 2);
//                    int right = (int) (mLeftRemainWidth + mItemWidth * (minimumMark - 1) + mQuoteBottomBitmap.getWidth() / 2);
//                    int top = Util.dip2px(4);
//                    int bottom = top + mQuoteBottomBitmap.getHeight();
//
//                    strokePath.moveTo(mLeftRemainWidth + mItemWidth * (minimumMark - 1), bottom);
//                    strokePath.lineTo(mLeftRemainWidth + mItemWidth * (minimumMark - 1), mInitialHeight - mMarginBottom - mMarginTop);
//                    canvas.drawPath(strokePath, mStrokeLinePaint);
//
//                    canvas.drawBitmap(mQuoteBottomBitmap, left, top, mImagePaint);
//                }
            }

            // 画最后一个点
            canvas.drawCircle(
                    mLeftRemainWidth + mItemWidth * (pointList.size() - 1),
                    pointList.get(pointList.size() - 1).getyInPixes(),
                    mCircleRadius,
                    mCirclePaint);

            mBgLinePaint.setColor(mBgLineColor);

            int mItemHeight = (mInitialHeight - mMarginTop - mMarginBottom) / 5;

            // 画6条白色横线
            for (int i = 0; i < 6; i++) {
                if (i == 5) {
                    mBgLinePaint.setColor(mBgBottomLineColor);
                }
                canvas.drawLine(
                        0,
                        mMarginTop + i * mItemHeight,
                        mInitialWidth,
                        mMarginTop + i * mItemHeight,
                        mBgLinePaint);
            }

            // 画底部文字
            for (int i = 0; i < pointList.size(); i++) {
                int percentage = pointList.get(i).getPercentage();
                Log.e("percentage", String.valueOf(percentage));
                if (percentage % 5 == 0) {

                    canvas.drawText(
                            String.valueOf(percentage) + "%",
                            mLeftRemainWidth + i * mItemWidth,
                            mMarginTop + 5 * mItemHeight + Util.dip2px(14),
                            mTextPaint);
                }
            }
        }
    }

    public interface OnScrollListener {
        void onScrollStoped(int yHeight);

        void onUnVisibleView();

        void onRequestNext(int position);
    }
}
