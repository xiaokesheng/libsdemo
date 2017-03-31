package com.zxb.libsdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.View;

import com.zxb.libsdemo.util.Util;

import java.util.ArrayList;

/**
 * Created by mrzhou on 16/11/22.
 */
public class LineWithBgView extends View {

    private double[] values;
    private int mInitialWidth, mInitialHeight;

    private int paddingLeft;
    private int paddingTop;
    private int paddingRight;
    private int paddingBottom;

    private Paint paint;

    private ArrayList<Position> positionList;

    private int leftMargin;
    private int rightMargin;

    private double heightRate;

    public LineWithBgView(Context context) {
        super(context);
        initData();
    }

    private void initData() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        leftMargin = Util.dip2px(5);
        rightMargin = Util.dip2px(5);

        values = new double[] {
                1.2, 3.2, 4.5, 2.8, 4.3
        };

        heightRate = 0.6;

        mInitialWidth = Util.dip2px(150);
        mInitialHeight = Util.dip2px(40);

        positionList = new ArrayList<>();

        double[] minMax = getMinAndMaxValue(values);
        for (int i = 0; i < values.length; i++) {
            positionList.add(transtoPosition(values[i], i, minMax[0], minMax[1]));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mInitialWidth, mInitialHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        // 画路径
        paint.reset();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#d3eefd"));
        paint.setStyle(Paint.Style.FILL);

        Path path = new Path();
        path.moveTo(positionList.get(0).x, positionList.get(0).y);
        for (int i = 0; i < positionList.size(); i++) {
            path.lineTo(positionList.get(i).x, positionList.get(i).y);
        }
        path.lineTo(
                positionList.get(positionList.size() - 1).x,
                positionList.get(positionList.size() - 1).y);
        path.close();
        canvas.drawPath(path, paint);

        // 画线
        paint.reset();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#45beff"));
        paint.setStrokeWidth(2);
        for (int i = 0; i < positionList.size() - 1; i++) {
            Position start = positionList.get(i);
            Position end = positionList.get(i + 1);
            canvas.drawLine(start.x, start.y, end.x, end.y, paint);
        }

        // 画点
        paint.reset();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#45beff"));
        for (int i = 0; i < positionList.size(); i++) {
            canvas.drawCircle(
                    positionList.get(i).x, positionList.get(i).y,
                    Util.dip2px(4), paint);
        }
    }





    public double[] getMinAndMaxValue(double[] values) {
        if (values == null) {
            return new double[] {0.0, 0.0};
        }
        if (values.length == 1) {
            return new double[] {values[0], values[0]};
        }

        double min = values[0];
        double max = values[0];
        for (int i = 1; i < values.length; i++) {
            if (min > values[i]) {
                min = values[i];
            }
            if (max < values[i]) {
                max = values[i];
            }
        }
        Log.e("height", "min: " + min + ", max: " + max);
        return new double[] {min, max};
    }

    public Position transtoPosition(double value, int pos, double minValue, double maxValue) {
        int mItemWidth = (mInitialWidth - leftMargin - rightMargin) / 4;
        int x = leftMargin + pos * mItemWidth;
        int height = (int) (((maxValue - value) / (maxValue - minValue)) * (mInitialHeight * heightRate));
        int y = height + Util.dip2px(5);
        Position position = new Position(x, y);
        return position;
    }

    public class Position {
        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
