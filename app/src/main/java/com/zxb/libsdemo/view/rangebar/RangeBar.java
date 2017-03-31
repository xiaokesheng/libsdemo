package com.zxb.libsdemo.view.rangebar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zxb.libsdemo.R;

/**
 * Created by mrzhou on 2017/3/30.
 */
public class RangeBar extends View {

    // 上下两根线坐标
    private RectF topLine;
    private RectF bottomLine;

    // 上下两根线的颜色
    private int lineColor;

    // 左右两个指示条
    SeekView leftSeek;
    SeekView rightSeek;

    private Paint mLinePaint;

    // 控件的宽和高
    int mWidth;
    int mHeight;

    // 触摸时, 上一次的 X 和 Y
    private float lastX;
    private float lastY;

    private Context mContext;

    // 左右滑块的最小距离, 用像素表示
    private int minPx = 200;

    // 中间进度条
    private int margin;

    OnRangeScrollListener mListener;

    public RangeBar(Context context) {
        this(context, null);
    }

    public RangeBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public RangeBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public void setOnRangeScrollListener(OnRangeScrollListener listener) {
        this.mListener = listener;
    }

    private void init() {
        lastX = 0;
        lastY = 0;

        topLine = new RectF();
        bottomLine = new RectF();
        lineColor = Color.parseColor("#389cff");
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(lineColor);

        leftSeek = new SeekView(mContext);
        rightSeek = new SeekView(mContext);
    }

    /**
     * 初始化滑动条的坐标, 用于最初的状态和复原时的状态
     */
    public void resetRectLine() {
        topLine.top = mHeight / 4 - 15;
        topLine.bottom = topLine.top + 15;
        topLine.left = 0;
        topLine.right = mWidth;
        bottomLine.top = mHeight / 7 * 3 - 15;
        bottomLine.bottom = bottomLine.top + 15;
        bottomLine.left = 0;
        bottomLine.right = mWidth;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        // 每次布局改变时,都去初始化所有的View,包括进度条,滑动条等
        resetRectLine();
        leftSeek.init(R.mipmap.pin_left, true, mWidth, margin);
        rightSeek.init(R.mipmap.pin_right, false, mWidth, margin);
        setLineValue();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 根据左右两个滑动条,计算中间进度条的坐标
        setLineValue();
        // 画中间的进度条
        canvas.drawRoundRect(topLine, 0, 0, mLinePaint);
        canvas.drawRoundRect(bottomLine, 0, 0, mLinePaint);
        // 画左右两个滑动条
        leftSeek.draw(canvas);
        rightSeek.draw(canvas);

        if (null != mListener) {
            mListener.onScrollPercentage(leftSeek.getPercentage(), rightSeek.getPercentage());
        }
    }

    public void setLeftPercentage(int percentage) {
        leftSeek.setPercentage(percentage);
        invalidate();
    }

    public void setRightPercentage(int percentage) {
        rightSeek.setPercentage(percentage);
        invalidate();
    }

    /**
     * 重置, 重绘
     */
    public void resetRangeBar() {
        leftSeek.reset();
        rightSeek.reset();
        this.reset();
        invalidate();
    }

    /**
     * 设置中间进度条边距, 如果为正, 则说明进度条的起始位置在左右两侧的滑动条外侧, 如果为负, 则说明进度条伸入滑动条内部
     * 伸入了多少距离, 需要自行计算
     * @param margin
     */
    public void setMargin(int margin) {
        this.margin = margin;
        resetRangeBar();
    }

    private void reset() {
        resetRectLine();
    }

    public int getLeftSeekBarWidth() {
        return leftSeek.getRealWidth() + margin;
    }

    public int getRightSeekBarWidth() {
        return rightSeek.getRealWidth() + margin;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                if (onActionDown(lastX, lastY)) {
                    return true;
                } else {
                    return super.onTouchEvent(event);
                }

            case MotionEvent.ACTION_MOVE:
                float currentX = event.getX();
                float currentY = event.getY();
                if (onActionMove(currentX, currentY)) {
                    lastX = currentX;
                    lastY = currentY;
                    return true;
                } else {
                    return super.onTouchEvent(event);
                }
            case MotionEvent.ACTION_UP:
                onActionUp();
                return true;
            case MotionEvent.ACTION_CANCEL:
                onActionUp();
                return true;
            default:
                onActionUp();
                return super.onTouchEvent(event);
        }
    }

    private boolean onActionDown(float x, float y) {
        // 判断是否在触控区域内
        if (x > leftSeek.getStartX() - margin && x < leftSeek.getEndX()) {
            leftSeek.setPressed(true);
            return true;
        }
        if (x > rightSeek.getStartX() && x < rightSeek.getEndX() + margin) {
            rightSeek.setPressed(true);
            return true;
        }
        return false;
    }

    private boolean onActionMove(float x, float y) {

        if (leftSeek.isPressed()) {
            // 如果左侧的起始点超过了右侧的点, 停止
            if (x + leftSeek.getRealWidth() / 2 + margin + minPx >= topLine.right) {
                topLine.left = topLine.right - minPx;
                bottomLine.left = topLine.left;
                leftSeek.setCenterX((int) (topLine.left - margin - leftSeek.getRealWidth() / 2));
                invalidate();
            } else {
                leftSeek.setCenterX((int) x);
                invalidate();
            }
            return true;
        }
        if (rightSeek.isPressed()) {
            // 如果右侧的点超过了左侧的终止点,停止
            if (x - rightSeek.getRealWidth() / 2 - margin - minPx <= topLine.left) {
                topLine.right = topLine.left + minPx;
                bottomLine.right = topLine.right;
                rightSeek.setCenterX((int) (topLine.right + margin + rightSeek.getRealWidth() / 2));
                invalidate();
            } else {
                rightSeek.setCenterX((int) x);
                invalidate();
            }
            return true;
        }
        return false;
    }

    /**
     * 根据左右两个滑动条,计算中间进度条的坐标
     */
    private void setLineValue() {
        topLine.left = leftSeek.getEndX() + margin;
        topLine.right = rightSeek.getStartX() - margin;
        bottomLine.left = leftSeek.getEndX() + margin;
        bottomLine.right = rightSeek.getStartX() - margin;
    }

    /**
     * 手抬起时, 将两个滑动条置为 '不被按下' 状态
     */
    private void onActionUp() {
        leftSeek.setPressed(false);
        rightSeek.setPressed(false);
    }

    public interface OnRangeScrollListener {
        void onScrollPercentage(int leftPercentage, int rightPercentage);
    }

}
