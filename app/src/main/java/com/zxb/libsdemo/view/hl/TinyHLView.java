package com.zxb.libsdemo.view.hl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

import com.zxb.libsdemo.model.PointC;
import com.zxb.libsdemo.util.J;
import com.zxb.libsdemo.util.Util;

import java.util.ArrayList;

/**
 * Created by yufangyuan on 2017/8/25.
 */

public class TinyHLView extends View {

    private int mInitialHeight, mInitialWidth;
    private int mAreaWidth, mAreaHeight;

    float areaHMaxValue;
    float areaHMinValue;
    float areaLMaxValue;
    float areaLMinValue;

    Context mContext;

    Paint mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mHistogramPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mLastPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Rect mTempRect = new Rect();

    String colorHistogram = "#bad3ff";
    String colorLine = "#1a6eff";

    ArrayList<HLPoint> pList;

    public int mItemWidth;
    float mLastPointRadius = Util.dip2px(8);

    public TinyHLView(Context context) {
        this(context, null);
    }

    public TinyHLView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TinyHLView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mLinePaint.setColor(Color.parseColor(colorLine));
        mLinePaint.setStrokeWidth(4);
        mHistogramPaint.setColor(Color.parseColor(colorHistogram));

        mItemWidth = 46;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (pList == null || pList.size() == 0) {
            mInitialWidth = mAreaWidth;
        } else {
            mInitialWidth = (mItemWidth * pList.size());
            if (mItemWidth < mAreaWidth / pList.size()) {
                mItemWidth = mAreaWidth / pList.size();
            }
        }

        mInitialHeight = mAreaHeight;

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mInitialWidth, mInitialHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mInitialWidth, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, mInitialHeight);
        }
    }

    public void setPointList(ArrayList<HLPoint> pList, int areaWidth, int areaHeight) {
        this.mAreaWidth = areaWidth;
        this.mAreaHeight = areaHeight;
        this.pList = pList;
        computeValues(this.pList);
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (areaHMaxValue == 0 && areaHMinValue == 0) {
            initAreaMaxValue(pList);
        }
        if (pList == null || pList.size() == 0) {
            return;
        }
        for (int i = 0; i < pList.size(); i++) {
            HLPoint p = pList.get(i);
            mTempRect.left = (int) (i * mInitialWidth / pList.size() + mInitialWidth * (1 / 16f));
            mTempRect.top = p.hPixels;
            mTempRect.right = (int) ((i + 1) * mInitialWidth / pList.size() - mInitialWidth * (1 / 16f));
            mTempRect.bottom = mInitialHeight;
            canvas.drawRect(mTempRect, mHistogramPaint);
        }
        for (int i = 0; i < pList.size() - 1; i++) {
            HLPoint p = pList.get(i);
            if (i < pList.size() - 1) {
                int startX = (int) ((float) mInitialWidth / (float) pList.size() * (i + 0.5f));
                int startY = p.lPixels;
                int endX = startX + mInitialWidth / pList.size();
                int endY = pList.get(i + 1).lPixels;
                canvas.drawLine(startX, startY, endX, endY, mLinePaint);
                if (i == pList.size() - 2) {
                    canvas.drawCircle(endX, endY, mLastPointRadius, mLinePaint);
                }
            }
        }
    }

    private void computeValues(ArrayList<HLPoint> list) {
        initAreaMaxValue(list);

        for (int i = 0; i <= list.size(); i++) {
            HLPoint p = list.get(i);
            p.hPixels = (mInitialHeight) - (int) ((mInitialHeight) * p.hValue / areaHMaxValue);
            p.lPixels = (mInitialHeight) - (int) ((mInitialHeight) * p.lValue / areaLMaxValue);
        }
    }

    private void initAreaMaxValue(ArrayList<HLPoint> list) {
        float hMax = list.get(0).hValue;
        float hMin = list.get(0).hValue;
        float lMax = list.get(0).lValue;
        float lMin = list.get(0).lValue;
        for (int i = 0; i < list.size(); i++) {
            if (i == pList.size()) {
                break;
            }
            if (hMax <= list.get(i).hValue) {
                hMax = list.get(i).hValue;
            }
            if (hMin >= list.get(i).hValue) {
                hMin = list.get(i).hValue;
            }
            if (lMax <= list.get(i).lValue) {
                lMax = list.get(i).lValue;
            }
            if (lMin >= list.get(i).lValue) {
                lMin = list.get(i).lValue;
            }
        }
        areaHMaxValue = hMax;
        areaHMinValue = hMin;
        areaLMaxValue = lMax;
        areaLMinValue = lMin;
    }
}
