package com.zxb.libsdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.zxb.libsdemo.line.LinePoint;
import com.zxb.libsdemo.util.Util;

/**
 * Created by mrzhou on 16/5/30.
 */
public class LineView extends View {

    private int mColor = Color.BLUE;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int paddingLeft;
    private int paddingTop;
    private int paddingRight;
    private int paddingBottom;

    private int mInitialWidth = 200;
    private int mInitialHeight = 200;

    private float startX, startY, stopX, stopY;

    private float leftX, leftY, centerX, centerY, rightX, rightY;

    private LinePoint mPoint;

    public LineView(Context context) {
        super(context);
        init();
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setColor(mColor);
        mLinePaint.setColor(Color.parseColor("#7fffffff"));
        mLinePaint.setStrokeWidth(3);
        mCirclePaint.setColor(Color.parseColor("#7fffffff"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        Rect r = new Rect();
        r.top = paddingTop;
        r.left = paddingLeft;
        r.right = r.left + width;
        r.bottom = r.top + height;

        canvas.drawRect(r, mPaint);

//        canvas.drawLine(startX, startY, r.right, stopY, mLinePaint);
        if (leftX != -1 && leftY != -1) {
            canvas.drawLine(0, leftY, r.right / 2, centerY, mLinePaint);
        }
        if (rightX != -1 && rightY != -1) {
            canvas.drawLine(r.right / 2, centerY, r.right, rightY, mLinePaint);
        }

        canvas.drawCircle(r.right / 2, centerY, Util.dip2px(4f), mCirclePaint);
    }

    public void setLinePoint(float startX, float startY, float stopX, float stopY) {
        this.startX = startX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;

        invalidate();
    }

    public void setPoint(LinePoint point) {
        this.mPoint = point;

        this.leftX = point.getLeftFloatX();
        this.leftY = (1 - point.getLeftFloatY()) * 400;
        this.centerX = point.getCenterFloatX();
        this.centerY = (1 - point.getCenterFloatY()) * 400;
        this.rightX = point.getRightFloatX();
        this.rightY = (1 - point.getRightFloatY()) * 400;

        invalidate();
    }
}
