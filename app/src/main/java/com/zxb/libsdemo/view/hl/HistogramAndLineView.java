package com.zxb.libsdemo.view.hl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
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

import java.util.ArrayList;

/**
 * Created by yufangyuan on 2017/8/21.
 */

public class HistogramAndLineView extends View {

    private static final String TAG = "HistogramAndLineView";

    public static final int MINIMUM = 1;
    public static final int MAXIMUM = 2;
    public static final int NORMAL = 3;

    private static final int INVALID_POINTER = -1;

    private int mInitialHeight, mInitialWidth;
    private int mAreaWidth = 0, mAreaHeight = 0;

    private int mTopMargin;

    Context mContext;

    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mHistogramPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mCheckLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mLightLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

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

    private float mXScale = 1f;
    private float mYScale = 1f;

    private float mCurrentScale = 1f;

    Point currentMovePoint;

    String colorHistogram = "#bad3ff";
    String colorLine = "#1a6eff";

    ArrayList<HLPoint> pList;

    private int bottomHeight = Util.dip2px(50);

    public float mItemWidth;
    public float mMinimumItemWidth;
    float mCheckRadius = Util.dip2px(8);

    public int scaleStatus = NORMAL;

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
        mTopMargin = 30;

        mTextPaint.setColor(Color.parseColor("#525566"));
        mTextPaint.setTextSize(Util.dip2px(10));
        mLightLinePaint.setColor(Color.parseColor("#ff0000"));
        mLightLinePaint.setStrokeWidth(1);
        mLinePaint.setColor(Color.parseColor(colorLine));
        mLinePaint.setStrokeWidth(4);
        mCheckLinePaint.setColor(Color.parseColor("#222845"));
        mCheckLinePaint.setStrokeWidth(1);

        mScroller = new OverScroller(mContext);
        setFocusable(true);
        final ViewConfiguration configuration = ViewConfiguration.get(mContext);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mOverscrollDistance = configuration.getScaledOverscrollDistance();
        mOverflingDistance = configuration.getScaledOverflingDistance();

        startPoint = new PointC();
        lastPoint = new PointC();
        centerPoint = new PointC();
        currentMovePoint = new Point();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        if (mAreaWidth == 0 || mAreaHeight == 0) {
            mAreaWidth = widthSpecSize;
            mAreaHeight = heightSpecSize;
            mItemWidth = ((float) mAreaWidth / 24f);
            mMinimumItemWidth = ((float) mAreaWidth) / 48f;
        }

        if (pList == null || pList.size() == 0) {
            mInitialWidth = mAreaWidth;
        } else {
            mInitialWidth = (int) (mItemWidth * pList.size() * mXScale);
            J.j("touchMode", String.valueOf(mLastScale));
            scaleStatus = NORMAL;
            if (mInitialWidth < mMinimumItemWidth * pList.size()) {
                mInitialWidth = (int) (mMinimumItemWidth * pList.size());
//                mXScale = (float) mInitialWidth / (float) (mItemWidth * pList.size());
                scaleStatus = MINIMUM;
                J.j("touchMode", "changeTo MINIMUM");
                if (mCurrentScale > 1) {
                    scaleStatus = NORMAL;
                }
            }
            if (mInitialWidth > mMinimumItemWidth * pList.size() * 4) {
                mInitialWidth = (int) (mMinimumItemWidth * pList.size() * 4);
//                mXScale = (float) mInitialWidth / (float) (mItemWidth * pList.size());
                scaleStatus = MAXIMUM;
                J.j("touchMode", "changeTo MAXIMUM");
                if (mCurrentScale < 1) {
                    scaleStatus = NORMAL;
                }
            }
        }

        J.j("scrollRange", "areaWidth: " + mAreaWidth + ", mInitialWidth: " + mInitialWidth + ", mItemWidth: " + mItemWidth);

        mInitialHeight = mAreaHeight;

//        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
//        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

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
    private static final int MODE_CHECK = 4;

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

    private boolean isLoadingLeft = false;
    private boolean dataIsLoadOver = false;
    private int currentPage;

    public void setDataIsLoadOver(boolean isOver) {
        this.dataIsLoadOver = isOver;
    }

    public void setPointList(ArrayList<HLPoint> pList, int areaWidth, int areaHeight) {
        this.mAreaWidth = areaWidth;
        this.mAreaHeight = areaHeight;
        this.pList = pList;
//        initListData(pList);
        setScrollX(0);
        requestLayout();
        currentPage = 1;
    }

    public void setPointList(ArrayList<HLPoint> pList) {
        this.pList = pList;
        setScrollX(0);
        requestLayout();
        currentPage = 1;
    }

    public void addPointList(ArrayList<HLPoint> addList, int addPage) {
        if (null != addList) {
            int itemWidth = (int) ((float) mInitialWidth / (float) pList.size());
            this.pList.addAll(0, addList);
            currentPage += addPage;
            requestLayout();

            int addScrollX = itemWidth * addList.size();
            setScrollX(addScrollX);
            isLoadingLeft = false;
        }

    }

    public int getCurrentPage() {
        return currentPage;
    }

//    private void initListData(ArrayList<HLPoint> list) {
//
//    }

    private boolean hasMoved;
    private boolean hasTouch;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(ev);

        final int action = ev.getAction();

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                hasTouch = true;
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
                hasMoved = true;
                currentMovePoint.set((int) ev.getX(), (int) ev.getY());
                J.j("touchMode", "in move ----> " + getTouchMode());
                if (touchMode == MODE_ZOOM) {
                    mLastMotionX = (int) ev.getX(0);
                    float totalDist = spacing(ev);
                    float scale = totalDist / mSavedDist;
                    J.j("touchMode", "currentScale" + scale);
                    mCurrentScale = scale;
                    mXScale = scale * mLastScale;

                    J.j("touchMode", "in move in zoom ----> " + getTouchMode());
//                    if (scaleStatus == MINIMUM || scaleStatus == MAXIMUM) {
//                        J.j("touchMode", "in move equal min max ----> " + getTouchMode());
//                        scaleStatus = NORMAL;
//                        touchMode = MODE_NONE;
//                        return true;
//                    }
//                    float totalDist = spacing(ev);
//                    float scale = totalDist / mSavedDist;
//                    J.j("touchMode", "currentScale" + scale);
//                    mXScale = scale * mLastScale;

                    float newScrollX = (lastScrollX + centerPoint.x) * scale - centerPoint.x;
                    if (newScrollX <= 0) {
                        newScrollX = 0;
                    }

                    J.j("newScrollX", String.valueOf(newScrollX));

                    setScrollX((int) newScrollX);

                    requestLayout();

                    if (scaleStatus == MINIMUM || scaleStatus == MAXIMUM) {
                        J.j("touchMode", "in move equal min max ----> " + getTouchMode());
                        scaleStatus = NORMAL;
                        touchMode = MODE_NONE;
                        return true;
                    }

//                    if (getScrollRange() <= getScrollX()) {
//                        setScrollX(getScrollRange());
//                        invalidate();
//                    } else {
//                        invalidate();
//                    }

                } else if (touchMode == MODE_CHECK) {
                    invalidate();
                } else {
                    final int activePointerIndex = ev.findPointerIndex(mActivePointerId);
                    if (activePointerIndex == -1) {
                        Log.e(TAG, "Invalid pointerId=" + mActivePointerId + " in onTouchEvent");
                        break;
                    }

                    J.j("pointerIndex", String.valueOf(activePointerIndex));
                    final int x = (int) ev.getX(activePointerIndex);
                    int deltaX = mLastMotionX - x;
                    if (!mIsBeingDragged && Math.abs(deltaX) > mTouchSlop) {

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
                        computeIndex();

                        if (getScrollX() == 0 && deltaX < 0 && !isLoadingLeft && !dataIsLoadOver) {
                            isLoadingLeft = true;
                            //
                            if (null != mLoadLeftListener) {
                                mLoadLeftListener.loadLeft(currentPage);
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (hasTouch) {
                    if (!hasMoved) {
                        if (touchMode == MODE_CHECK) {
                            touchMode = MODE_NONE;
                            invalidate();
                        } else {
                            touchMode = MODE_CHECK;
                        }
                    } else {
                        // touch mode不变
//                        touchMode = MODE_NONE;
                    }
                }
                hasTouch = false;
                hasMoved = false;
                if (touchMode == MODE_CHECK) {
                    break;
                }
                if (mIsBeingDragged && touchMode == MODE_NONE) {
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
                }
//                touchMode = MODE_NONE;
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
                mLastScale = mXScale;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                hasMoved = true;
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
        int width = mAreaWidth;
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
        // TODO 处理滑动边界问题
        scrollRange = Math.max(0,
                mInitialWidth - mAreaWidth);
        return scrollRange;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (areaHMaxValue == 0 && areaHMinValue == 0) {
            computeIndex();
        }
        // 画背景 TODO
        if (pList == null || pList.size() == 0) {
            return;
        }

        for (int i = 0; i < 6; i++) {
            int startX = getScrollX();
            int startY = i * (mInitialHeight - mTopMargin - bottomHeight) / 6 + mTopMargin;
            int endX = startX + mAreaWidth;
            int endY = startY;
            canvas.drawLine(startX, startY, endX, endY, mLightLinePaint);
        }


        for (int i = 0; i < pList.size(); i++) {
            HLPoint p = pList.get(i);
            mPaint.setColor(Color.parseColor(colorHistogram));
            mTempRect.left = (int) (((float) (i * mInitialWidth)) / pList.size() + (int) (5 * mXScale));
//            mTempRect.top = 0;
            mTempRect.top = p.hPixels;
            mTempRect.right = (int) (((float) ((i + 1) * mInitialWidth)) / pList.size() - (int) (5 * mXScale));
            mTempRect.bottom = mInitialHeight - bottomHeight;
            canvas.drawRect(mTempRect, mPaint);
            if (i % 4 == 0) {
                canvas.drawLine(mTempRect.left, mTopMargin, mTempRect.left, mInitialHeight - bottomHeight, mLightLinePaint);
            }


            if (i % 7 == 3) {
                Rect rect = new Rect();
                mTextPaint.getTextBounds(pList.get(i).xString, 0, pList.get(i).xString.length(), rect);
                int deltaX = (rect.right - rect.left) / 2 - (mTempRect.right - mTempRect.left) / 2;
                canvas.drawText(
                        pList.get(i).xString, mTempRect.left - deltaX,
                        mInitialHeight - bottomHeight + (rect.bottom - rect.top) + Util.dip2px(5),
                        mTextPaint);
            }
//            canvas.drawText(
//                    String.valueOf(i),
//                    mTempRect.left,
//                    mInitialHeight - 30,
//                    mTextPaint);
        }
        for (int i = 0; i < pList.size() - 1; i++) {
            HLPoint p = pList.get(i);
            if (i < pList.size() - 1) {
                int startX = (int) ((float) mInitialWidth / (float) pList.size() * (i + 0.5f));
                int startY = p.lPixels;
                int endX = startX + mInitialWidth / pList.size();
                int endY = pList.get(i + 1).lPixels;
                canvas.drawLine(startX, startY, endX, endY, mLinePaint);
            }
        }

        if (touchMode == MODE_CHECK) {

            int currentIndex = getCurrentIndex();
            if (currentIndex <= pList.size() - 1) {
                if (null != mOnCheckListener) {
                    mOnCheckListener.onChecked(pList.get(currentIndex));
                }
                int x = (int) ((float) mInitialWidth / (float) pList.size() * (currentIndex + 0.5f));
                int y = pList.get(currentIndex).lPixels;
                canvas.drawLine(x, 0, x, mInitialHeight - bottomHeight, mCheckLinePaint);
                canvas.drawCircle(x, y, mCheckRadius, mLinePaint);
            }
        }
    }

    private int getCurrentIndex() {
        float x = currentMovePoint.x + getScrollX();
        int index = (int) (x /((float) mInitialWidth / (float) pList.size()));
        return index;
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
        computeIndex();
    }

    int lastLeftIndex;
    int lastRightIndex;

    float areaHMaxValue;
    float areaHMinValue;
    float areaLMaxValue;
    float areaLMinValue;

    private void computeIndex() {
        if (pList == null || pList.size() == 0) {
            return;
        }
        float totalWidth = mInitialWidth;
        float itemWidth = totalWidth / pList.size();
        int leftIndex = (int) ((float) getScrollX() / itemWidth);
        float leftDelta = (float) getScrollX() % (itemWidth);
        // TODO 处理边界问题
        int baseRightNum = (int) ((float) mAreaWidth / itemWidth);
        int totalRemainPx = (int) ((float) mAreaWidth - baseRightNum * itemWidth);
        if (leftDelta >= 0f) {
            baseRightNum++;
        }
        if (totalRemainPx - (itemWidth - leftDelta) > 0) {
            baseRightNum++;
        }
        int rightIndex = leftIndex + baseRightNum - 1;


        if (rightIndex >= pList.size()) {
            rightIndex = pList.size() - 1;
        }

        if (lastLeftIndex != leftIndex || lastRightIndex != rightIndex) {
            getAreaMaxValue(pList, leftIndex, rightIndex);
            invalidate();
            lastLeftIndex = leftIndex;
            lastRightIndex = rightIndex;

            for (int i = leftIndex; i <= rightIndex; i++) {
                HLPoint p = pList.get(i);
                p.hPixels = (mInitialHeight - bottomHeight - mTopMargin) -
                        (int) ((mInitialHeight - bottomHeight - mTopMargin) * p.hValue / areaHMaxValue) +
                        mTopMargin;

                p.lPixels = (mInitialHeight - bottomHeight - mTopMargin) -
                        (int) ((mInitialHeight - bottomHeight - mTopMargin) * p.lValue / areaLMaxValue) +
                        mTopMargin;
            }
        }


    }

    private void getAreaMaxValue(ArrayList<HLPoint> list, int leftIndex, int rightIndex) {
        float hMax = list.get(leftIndex).hValue;
        float hMin = list.get(leftIndex).hValue;
        float lMax = list.get(leftIndex).lValue;
        float lMin = list.get(leftIndex).lValue;
        for (int i = leftIndex; i <= rightIndex; i++) {
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

        if (null != mValueChangedListener) {
            mValueChangedListener.onHLValueChanged((int) areaHMaxValue, areaLMaxValue);
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

    OnLeftLoadListener mLoadLeftListener;

    public interface OnLeftLoadListener {
        void loadLeft(int page);
    }

    public void setOnLeftLoadListener(OnLeftLoadListener listener) {
        this.mLoadLeftListener = listener;
    }

    public interface OnMaxValueChangedListener {
        void onHLValueChanged(int HValue, float lValue);
    }

    OnMaxValueChangedListener mValueChangedListener;

    public void setOnMaxValueChangedListener(OnMaxValueChangedListener listener) {
        this.mValueChangedListener = listener;
    }

    public interface OnCheckListener {
        void onChecked(HLPoint point);
    }

    OnCheckListener mOnCheckListener;

    public void setOnCheckListener(OnCheckListener listener) {
        this.mOnCheckListener = listener;
    }

    public String getTouchMode() {
        switch (touchMode) {
            case MODE_NONE:
                return "NONE";
            case MODE_ZOOM:
                return "ZOOM";
            case MODE_CHECK:
                return "CHECK";
            case MODE_DRAG:
                return "DRAG";
        }
        return "NULL";
    }
}
