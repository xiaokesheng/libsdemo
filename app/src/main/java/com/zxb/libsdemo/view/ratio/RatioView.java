package com.zxb.libsdemo.view.ratio;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.zxb.libsdemo.util.Util;

import java.util.ArrayList;

/**
 * Created by yufangyuan on 2017/10/20.
 */

public class RatioView extends View {

    private Context mContext;

    private Paint mBgLinePaint;
    private Paint mLinePaint;

    private int bottomHeight;
    private int topBottomDelta;

    private int leftMargin;
    private int rightMargin;

    private int mInitialHeight;
    private int mInitialWidth;

    public ArrayList<PixelPoint> pointList;
    public ArrayList<RatioPoint> initialList;

    private float min;
    private float max;

    private int areaHeight;
    private int areaWidth;

    private int color1 = Color.parseColor("#1a6eff");
    private int color2 = Color.parseColor("#ff801a");
    private int color3 = Color.parseColor("#19c49e");

    public RatioView(Context context) {
        this(context, null);
        init();
    }

    public RatioView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public RatioView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        mInitialHeight = Util.dip2px(200);
        mInitialWidth = Util.getScreenWidth(mContext);

        bottomHeight = Util.dip2px(40);
        topBottomDelta = Util.dip2px(5);
        leftMargin = Util.dip2px(50);
        rightMargin = Util.dip2px(50);

        areaHeight = mInitialHeight - topBottomDelta * 2 - bottomHeight;
        areaWidth = mInitialWidth - leftMargin - rightMargin;

        mBgLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgLinePaint.setColor(Color.parseColor("#e8e8e8"));
        mBgLinePaint.setStrokeWidth(1);
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStrokeWidth(5);
    }

    public void setList(ArrayList<RatioPoint> list) {
        if (initialList == null) {
            initialList = new ArrayList<>();
        }
        this.initialList.clear();
        initialList.addAll(list);
        handleList();
    }

    private void handleList() {
        if (null == pointList) {
            pointList = new ArrayList<>();
        }
        for (RatioPoint item : initialList) {
            initMinMax(item);
        }
        float min = initialList.get(0).min;
        float max = initialList.get(0).max;
        for (RatioPoint item : initialList) {
            min = Math.min(item.min, min);
            max = Math.max(item.max, max);
        }
        this.min = min;
        this.max = max;

        float valueArea = this.max - this.min;
        for (int i = 0; i < initialList.size(); i++) {
            RatioPoint item = initialList.get(i);
            float y1 = (this.max - item.floatPoint) / valueArea * areaHeight + topBottomDelta;
            float y2 = (this.max - item.industryIndexPoint) / valueArea * areaHeight + topBottomDelta;
            float y3 = (this.max - item.hsPoint) / valueArea * areaHeight + topBottomDelta;
            float x = (((float) i) / initialList.size()) * areaWidth + leftMargin;
            String str = item.date;
            pointList.add(new PixelPoint(x, y1, y2, y3, str));
        }
        if (null != pointList && null != getParent()) {
            invalidate();
        }
    }

    private void initMinMax(RatioPoint point) {
        point.min = Math.min(Math.min(point.floatPoint, point.hsPoint), point.industryIndexPoint);
        point.max = Math.max(Math.max(point.floatPoint, point.hsPoint), point.industryIndexPoint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null != pointList && pointList.size() > 0) {
            // 画 5 道背景线
            for (int i = 0; i < 5; i++) {
                int y = (int) (topBottomDelta + areaHeight / 4f * i);
                int startX = leftMargin;
                int endX = mInitialWidth - rightMargin;
                canvas.drawLine(startX, y, endX, y, mBgLinePaint);
            }

            // 画 3 道折线
            for (int i = 0; i < pointList.size() - 1; i++) {
                PixelPoint point1 = pointList.get(i);
                PixelPoint point2 = pointList.get(i + 1);
                float sx = point1.x;
                float sy1 = point1.y1;
                float sy2 = point1.y2;
                float sy3 = point1.y3;
                float ex = point2.x;
                float ey1 = point2.y1;
                float ey2 = point2.y2;
                float ey3 = point2.y3;
                mLinePaint.setColor(color1);
                canvas.drawLine(sx, sy1, ex, ey1, mLinePaint);
                mLinePaint.setColor(color2);
                canvas.drawLine(sx, sy2, ex, ey2, mLinePaint);
                mLinePaint.setColor(color3);
                canvas.drawLine(sx, sy3, ex, ey3, mLinePaint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mInitialWidth, mInitialHeight);
    }
}
