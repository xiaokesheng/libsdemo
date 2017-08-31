package com.zxb.libsdemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by USER-PC on 2017/8/31.
 */

public class VerticalProgressView extends View {

    int mInitialWidth;
    int mInitialHeight;

    int mRoundRectRadius;

    final int TIME_ANIMATION_DURATION = 200;

    ValueAnimator progressAnimator;

    int[] bgGradientColor = new int[] {
            Color.parseColor("#ff0000"), Color.parseColor("#00ff00")
    };

    Paint mProgressPaint;
    Paint mBgPaint;

    RectF progressRect;

    Context mContext;

    /**
     * 0f ~ 100f
     */
    float mEndProgress;
    float mBeforeProgress;

    float mProgress;

    public VerticalProgressView(Context context) {
        this(context, null);
    }

    public VerticalProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mInitialHeight = dip2px(250);
        mInitialWidth = dip2px(20);

        progressRect = new RectF();

        mEndProgress = 0f;
        mBeforeProgress = 0f;

        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mBgPaint.setColor(Color.parseColor("#7c8594"));
    }

    /**
     *
     * @param progress
     */
    public void setProgress(float progress) {
        if (progress < 0 || progress > 100) {
            throw new IllegalArgumentException("Please set a value between 0f and 100f");
        }
        this.mBeforeProgress = this.mEndProgress;
        this.mEndProgress = progress;
        startProgressAnimation();
    }

    private void startProgressAnimation() {
        setAnimation(TIME_ANIMATION_DURATION);
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

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        mRoundRectRadius = width / 2;

        int bottomHeight = height + paddingTop;
        int topHeight = 0;
        if (mProgress == 0f) {
            topHeight = bottomHeight;
        } else {
            topHeight = (int) (height - mProgress / 100f * (height - mRoundRectRadius * 2) - mRoundRectRadius * 2 + paddingTop);
        }

        // 画背景
        canvas.drawRoundRect(
                paddingLeft,
                paddingTop,
                paddingLeft + width,
                paddingTop + height,
                mRoundRectRadius,
                mRoundRectRadius,
                mBgPaint);

        int gStartX = paddingLeft;
        int gEndX = paddingLeft;
        int gStartY = bottomHeight;
        int gEndY = topHeight;

        progressRect.left = paddingLeft;
        progressRect.right = width + paddingLeft;
        progressRect.top = topHeight;
        progressRect.bottom = bottomHeight;

        LinearGradient shader = new LinearGradient(
                gStartX, gStartY,
                gEndX, gEndY,
                bgGradientColor[0], bgGradientColor[1],
                Shader.TileMode.CLAMP);
        mProgressPaint.setShader(shader);
        canvas.drawRoundRect(progressRect, mRoundRectRadius, mRoundRectRadius, mProgressPaint);
    }

    private void setAnimation(long duration) {
        progressAnimator = ValueAnimator.ofFloat(mBeforeProgress, mEndProgress);
        progressAnimator.setDuration(duration);
        progressAnimator.setTarget(mProgress);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        progressAnimator.start();
    }

    private int dip2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
