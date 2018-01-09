package com.zxb.libsdemo.activity.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.zxb.libsdemo.model.PointC;
import com.zxb.libsdemo.util.Util;

import java.util.ArrayList;

/**
 * Created by yufangyuan on 2017/6/10.
 */

public class HistogramView extends View {

    Rect r = new Rect();

    private int mItemWidth;
    private int bottomTextHeight;
    private int mItemHeight;
    private int mLeftMargin;
    private int mRightMargin;
    private int mTopMargin;
    private int mBottomMargin;
    private int verticalAreaHeight;

    private int mScreenWidth;
    private int mInitialHeight;
    private int mInitialWidth;

    private Paint mBgLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mBlueHistogramPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mBgLineColor = Color.parseColor("#407c8594");
    private int mTextColor = Color.parseColor("#7c8594");

    ArrayList<PointC> pointList;

    private Context mContext;

    private float max;

    public HistogramView(Context context) {
        super(context);
        this.mContext = context;
    }

    public HistogramView(Context context, ArrayList<PointC> list) {
        this(context);
        this.pointList = list;
    }

    private void init() {
        mInitialHeight = Util.dip2px(170);
        mScreenWidth = Util.getScreenWidth(mContext);
        mInitialWidth = mScreenWidth;
        mLeftMargin = Util.dip2px(32);
        mRightMargin = Util.dip2px(16);
        mTopMargin = Util.dip2px(40);
        bottomTextHeight = Util.dip2px(27);
        mBottomMargin = Util.dip2px(20);
        mItemWidth = (mScreenWidth - mLeftMargin - mRightMargin) / 12;
        mItemHeight = (mInitialHeight - mTopMargin - bottomTextHeight - mBottomMargin) / 3;
        verticalAreaHeight = mInitialHeight - mTopMargin - mBottomMargin - bottomTextHeight;

        initDatas();

        mBgLinePaint.setStrokeWidth(2);
        mBgLinePaint.setColor(mBgLineColor);
        mBlueHistogramPaint.setColor(Color.parseColor("#38a2ff"));
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(Util.dip2px(12));
    }

    private void initDatas() {
        pointList.add(new PointC("201608", 1f));
        pointList.add(new PointC("201609", 2f));
        pointList.add(new PointC("201610", 4f));
        pointList.add(new PointC("201611", 5f));
        pointList.add(new PointC("201612", 6f));
        pointList.add(new PointC("201701", 3f));
        pointList.add(new PointC("201702", 1f));
        pointList.add(new PointC("201703", 5f));
        pointList.add(new PointC("201704", 2f));
        pointList.add(new PointC("201705", 0f));
        pointList.add(new PointC("201706", 3f));
        pointList.add(new PointC("201707", 4f));

        max = pointList.get(0).yValue;
        for (int i = 0; i < pointList.size(); i++) {
            if (max < pointList.get(i).yValue) {
                max = pointList.get(i).yValue;
            }
        }

        for (int i = 0; i < pointList.size(); i++) {
            pointList.get(i).x = mLeftMargin + mItemWidth * i + mItemWidth / 2;
            float ratio = pointList.get(i).yValue / max;
            pointList.get(i).yPixels = mTopMargin + verticalAreaHeight * (1.0f - ratio);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画 4 条背景横线
        for (int i = 0; i < 4; i++) {
            canvas.drawLine(
                    mLeftMargin,
                    mTopMargin + i * mItemHeight,
                    mInitialWidth,
                    mTopMargin + i * mItemHeight,
                    mBgLinePaint);
        }

        // 画 蓝色 柱子
        for (int i = 0; i < pointList.size(); i++) {
            r.top = (int) pointList.get(i).yPixels;
            r.left = (int) (pointList.get(i).x - mItemWidth * 0.6f / 2);
            r.right = (int) (pointList.get(i).x + mItemWidth * .6f / 2);
            r.bottom = mTopMargin + verticalAreaHeight;
            canvas.drawRect(r, mBlueHistogramPaint);
        }

        // 画底部文字
        for (int i = 0; i < pointList.size(); i++) {
            int x = (int) pointList.get(i).x - Util.dip2px(16);
            int y = mTopMargin + verticalAreaHeight + bottomTextHeight / 2 + Util.dip2px(25);
            canvas.save();
            canvas.rotate(-30, x, y);
            canvas.drawText(pointList.get(i).xValue, x, y, mTextPaint);
            canvas.restore();
        }

        // 画左边的文字
        for (int i = 0; i < 4; i++) {
            int x = mLeftMargin / 2;
            int y = mTopMargin + mItemHeight * i + Util.dip2px(3);
            canvas.drawText(String.valueOf(max), x, y, mTextPaint);
        }
        canvas.drawText("单位", mLeftMargin / 2, mTopMargin / 2, mTextPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init();
    }
}
