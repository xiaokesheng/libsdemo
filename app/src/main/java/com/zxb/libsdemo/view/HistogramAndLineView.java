package com.zxb.libsdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.OverScroller;

import com.zxb.libsdemo.model.PointC;
import com.zxb.libsdemo.util.J;
import com.zxb.libsdemo.util.Util;

/**
 * Created by yufangyuan on 2017/8/21.
 */

public class HistogramAndLineView extends View {

    private static final String TAG = "HistogramAndLineView";

    private static final int INVALID_POINTER = -1;

    private int mInitialHeight, mInitialWidth;

    Context mContext;

    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Rect mTempRect = new Rect();

    private OverScroller mScroller;

    private int mTouchSlop;
    private int mMinimumVelocity;
    private int mMaximumVelocity;

    private int mOverscrollDistance;
    private int mOverflingDistance;

    private boolean mIsBeingDragged = false;
    private boolean mSmoothScrollingEnabled = true;

    private VelocityTracker mVelocityTracker;

    private float mScale = 1f;

    String[] colors = new String[]{
            "#FFB3A7", "#FFF143", "#A88462", "#0EB83A", "#BBCDC5", "#065279", "#815476", "#88ADA6", "#E0F0E9", "#3D3B4F", "#75664D", "#549688", "#FFFBF0", "#00BC12", "#C83C23"
    };

    public HistogramAndLineView(Context context) {
        this(context, null);
    }

    public HistogramAndLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HistogramAndLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        J.j("tag", "init---");

        mScroller = new OverScroller(mContext);
        setFocusable(true);
//        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
//        setWillNotDraw(false);
        final ViewConfiguration configuration = ViewConfiguration.get(mContext);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mOverscrollDistance = configuration.getScaledOverscrollDistance();
        mOverflingDistance = configuration.getScaledOverflingDistance();

        startPoint = new PointC();
        lastPoint = new PointC();
        centerPoint = new PointC();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        if (mScale > 1) {
//            mInitialHeight = (int) (Util.dip2px(200) * (mScale * 0.9f));
//        } else {
//            mInitialHeight = (int) (Util.dip2px(200) * mScale);
//        }
        mInitialHeight = Util.dip2px(200);
        mInitialWidth = (int) (Util.dip2px(1500) * mScale);
        J.j("tag", "measure---");

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

    private void initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        } else {
            mVelocityTracker.clear();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private int touchMode;

    private static final int MODE_DRAG = 1;
    private static final int MODE_ZOOM = 2;
    private static final int MODE_NONE = 3;
    private static final int MODE_FLING = 4;

    private float mSavedXDist = 1f;
    private float mSavedYDist = 1f;
    private float mSavedDist = 1f;

    private PointC startPoint;
    private PointC lastPoint;
    private PointC centerPoint;

    private int mLastMotionX;
    private int mActivePointerId = INVALID_POINTER;

    private float mLastScale = 1;

    private int lastScrollX;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(ev);

        final int action = ev.getAction();

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                initOrResetVelocityTracker();
                if ((mIsBeingDragged = !mScroller.isFinished())) {
                    final ViewParent parent = getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                }
                /*
                 * If being flinged and user touches, stop the fling. isFinished
                 * will be false if being flinged.
                 */
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                // Remember where the motion event started
                mLastMotionX = (int) ev.getX();
                mActivePointerId = ev.getPointerId(0);

                initOrResetVelocityTracker();
                mVelocityTracker.addMovement(ev);

                lastScrollX = getScrollX();

                break;
            }
            case MotionEvent.ACTION_MOVE:
                if (touchMode == MODE_ZOOM) {
//                    if (getScrollRange() < getScrollX()) {
//                        setScrollX(getScrollRange());
//                        invalidate();
//                        break;
//                    }
                    float totalDist = spacing(ev);
                    float scale = totalDist / mSavedDist;
                    mScale = scale * mLastScale;
                    J.j("zoom", "scale: " + String.valueOf(scale));
                    float newScrollX = (lastScrollX + centerPoint.x) * scale - centerPoint.x;
                    if (newScrollX <= 0) {
                        newScrollX = 0;
                    }

//                    if (newScrollX + Util.getScreenWidth(mContext) >= mInitialWidth) {
//                        newScrollX = mInitialWidth - Util.getScreenWidth(mContext);
//                    }
//                    J.j("scrollX-width", newScrollX + Util.getScreenWidth(mContext) + "px-->real");
//                    J.j("scrollX-width", mInitialWidth + "px--initial");

                    J.j("scrollX-compute", "newScrollX:--" + newScrollX);
                    setScrollX((int) newScrollX);
                    requestLayout();

                    J.j("scrollX-width", getScrollRange() + "px-->range");
                    J.j("scrollX-width", getScrollX() + "px--scrollX");
                    if (getScrollRange() <= getScrollX()) {
                        setScrollX(getScrollRange());
                        invalidate();
                    } else {
                        invalidate();
                    }

                } else {
                    final int activePointerIndex = ev.findPointerIndex(mActivePointerId);
                    if (activePointerIndex == -1) {
                        Log.e(TAG, "Invalid pointerId=" + mActivePointerId + " in onTouchEvent");
                        break;
                    }

                    final int x = (int) ev.getX(activePointerIndex);
                    int deltaX = mLastMotionX - x;
                    if (!mIsBeingDragged && Math.abs(deltaX) > mTouchSlop) {
                        J.j(TAG, "isDragging");
                        final ViewParent parent = getParent();
                        if (parent != null) {
                            parent.requestDisallowInterceptTouchEvent(true);
                        }
                        mIsBeingDragged = true;
                        if (deltaX > 0) {
                            deltaX -= mTouchSlop;
                        } else {
                            deltaX += mTouchSlop;
                        }
                        initVelocityTrackerIfNotExists();
                        mVelocityTracker.addMovement(ev);
                    }
                    if (mIsBeingDragged) {
                        J.j(TAG, "isDragging=====");
                        // Scroll to follow the motion event
                        mLastMotionX = x;

                        final int oldX = getScrollX();
                        final int oldY = getScrollY();
                        final int range = getScrollRange();
                        final int overscrollMode = getOverScrollMode();
                        final boolean canOverscroll = overscrollMode == OVER_SCROLL_ALWAYS ||
                                (overscrollMode == OVER_SCROLL_IF_CONTENT_SCROLLS && range > 0);

                        // Calling overScrollBy will call onOverScrolled, which
                        // calls onScrollChanged if applicable.
                        if (overScrollBy(deltaX, 0, getScrollX(), 0, range, 0,
                                mOverscrollDistance, 0, true)) {
                            // Break our velocity if we hit a scroll barrier.
                            mVelocityTracker.clear();
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mIsBeingDragged) {
                    final VelocityTracker velocityTracker = mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                    int initialVelocity = (int) velocityTracker.getXVelocity(mActivePointerId);

//                    if (getChildCount() > 0) {
                    if ((Math.abs(initialVelocity) > mMinimumVelocity)) {
                        fling(-initialVelocity);
                    } else {
                        if (mScroller.springBack(getScrollX(), getScrollY(), 0,
                                getScrollRange(), 0, 0)) {
                            postInvalidateOnAnimation();
                        }
                    }
//                    }

                    mActivePointerId = INVALID_POINTER;
                    mIsBeingDragged = false;
                    recycleVelocityTracker();

//                    if (mEdgeGlowLeft != null) {
//                        mEdgeGlowLeft.onRelease();
//                        mEdgeGlowRight.onRelease();
//                    }
                }
                touchMode = MODE_NONE;
                break;
            case MotionEvent.ACTION_CANCEL:
                if (mIsBeingDragged/* && getChildCount() > 0*/) {
//                    if (mScroller.springBack(mScrollX, mScrollY, 0, getScrollRange(), 0, 0)) {
//                        postInvalidateOnAnimation();
//                    }
                    mActivePointerId = INVALID_POINTER;
                    mIsBeingDragged = false;
                    recycleVelocityTracker();

//                    if (mEdgeGlowLeft != null) {
//                        mEdgeGlowLeft.onRelease();
//                        mEdgeGlowRight.onRelease();
//                    }
                }
                touchMode = MODE_NONE;
                break;
            case MotionEvent.ACTION_POINTER_UP:
//                onSecondaryPointerUp(ev);
                touchMode = MODE_NONE;
                mLastScale = mScale;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                lastScrollX = getScrollX();
                if (ev.getPointerCount() >= 2) {
                    touchMode = MODE_ZOOM;
                    startPoint.x = ev.getX();
                    startPoint.yPixels = ev.getY();

                    mSavedXDist = getXDist(ev);
                    mSavedYDist = getYDist(ev);
                    mSavedDist = spacing(ev);

                    float x_a = ev.getX(0) + ev.getX(1);
                    float y_a = ev.getY(0) + ev.getY(1);
                    centerPoint.x = (x_a / 2f);
                    centerPoint.yPixels = (y_a / 2f);
                }
                break;
        }
        return true;
    }

    public void fling(int velocityX) {
        int width = Util.getScreenWidth(mContext);
//        int right = getChildAt(0).getWidth();
        int right = mInitialWidth;

        mScroller.fling(getScrollX(), getScrollY(), velocityX, 0, 0,
                Math.max(0, right - width), 0, 0, width / 2, 0);

        final boolean movingRight = velocityX > 0;

//        View currentFocused = findFocus();
//        View newFocused = findFocusableViewInMyBounds(movingRight,
//                mScroller.getFinalX(), currentFocused);
//
//        if (newFocused == null) {
//            newFocused = this;
//        }
//
//        if (newFocused != currentFocused) {
//            newFocused.requestFocus(movingRight ? View.FOCUS_RIGHT : View.FOCUS_LEFT);
//        }
//
//        postInvalidateOnAnimation();
    }

    private int getScrollRange() {
        int scrollRange = 0;
        scrollRange = Math.max(0,
                mInitialWidth - Util.getScreenWidth(mContext));
        J.j(TAG, "scrollRange" + scrollRange);
        return scrollRange;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 15; i++) {
            mPaint.setColor(Color.parseColor(colors[i]));
            mTempRect.left = i * mInitialWidth / 15;
            mTempRect.top = 0;
            mTempRect.right = (i + 1) * mInitialWidth / 15;
            mTempRect.bottom = mInitialHeight;
            canvas.drawRect(mTempRect, mPaint);
        }
        J.j("scrollX", getScrollX() + "--> scrollX");
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            // This is called at drawing time by ViewGroup.  We don't want to
            // re-show the scrollbars at this point, which scrollTo will do,
            // so we replicate most of scrollTo here.
            //
            //         It's a little odd to call onScrollChanged from inside the drawing.
            //
            //         It is, except when you remember that computeScroll() is used to
            //         animate scrolling. So unless we want to defer the onScrollChanged()
            //         until the end of the animated scrolling, we don't really have a
            //         choice here.
            //
            //         I agree.  The alternative, which I think would be worse, is to post
            //         something and tell the subclasses later.  This is bad because there
            //         will be a window where mScrollX/Y is different from what the app
            //         thinks it is.
            //
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = mScroller.getCurrX();
            int y = mScroller.getCurrY();

            if (oldX != x || oldY != y) {
                final int range = getScrollRange();
                final int overscrollMode = getOverScrollMode();
                final boolean canOverscroll = overscrollMode == OVER_SCROLL_ALWAYS ||
                        (overscrollMode == OVER_SCROLL_IF_CONTENT_SCROLLS && range > 0);

                overScrollBy(x - oldX, y - oldY, oldX, oldY, range, 0,
                        mOverflingDistance, 0, false);
                onScrollChanged(getScrollX(), getScrollY(), oldX, oldY);
            }

//            if (!awakenScrollBars()) {
//                postInvalidateOnAnimation();
//            }
        }
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY,
                                  boolean clampedX, boolean clampedY) {
        // Treat animating scrolls differently; see #computeScroll() for why.
        if (!mScroller.isFinished()) {
            final int oldX = getScrollX();
            final int oldY = getScrollY();
            setScrollX(scrollX);
            setScrollY(scrollY);
//            invalidateParentIfNeeded();
//            onScrollChanged(mScrollX, mScrollY, oldX, oldY);
//            if (clampedX) {
//                mScroller.springBack(mScrollX, mScrollY, 0, getScrollRange(), 0, 0);
//            }
        } else {
            super.scrollTo(scrollX, scrollY);
        }

//        awakenScrollBars();
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

    private static float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }
}
