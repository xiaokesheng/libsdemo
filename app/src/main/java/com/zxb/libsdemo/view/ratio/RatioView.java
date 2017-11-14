package com.zxb.libsdemo.view.ratio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.util.Util;

import java.util.ArrayList;

/**
 * Created by yufangyuan on 2017/10/20.
 */

public class RatioView extends View {

    private Context mContext;

    private Paint mBgLinePaint;
    private Paint mLinePaint;
    private Paint mTextPaint;
    private Paint mCirclePaint;
    private Paint mVLinePaint;
    private Paint mStrPaint;
    private Paint mBitmapPaint;

    private int bottomHeight;
    private int topBottomDelta;
    private int topHeight;

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
    private float valueArea;

    private int color1 = Color.parseColor("#1a6eff");
    private int color2 = Color.parseColor("#ff801a");
    private int color3 = Color.parseColor("#19c49e");

    private float currentX;
    private float currentY;
    private boolean isTouching;

    Bitmap mBitmap;

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
        rightMargin = Util.dip2px(24);
        topHeight = Util.dip2px(28);

        areaHeight = mInitialHeight - topBottomDelta * 2 - bottomHeight - topHeight;
        areaWidth = mInitialWidth - leftMargin - rightMargin;

        mBgLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgLinePaint.setColor(Color.parseColor("#e0e0e0"));
        mBgLinePaint.setStrokeWidth(1);
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStrokeWidth(5);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.parseColor("#525566"));
        mTextPaint.setTextSize(Util.dip2px(8));
        mVLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mVLinePaint.setColor(Color.parseColor("#000000"));
        mVLinePaint.setStrokeWidth(1);
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrPaint.setColor(Color.parseColor("#ffffff"));
        mStrPaint.setTextSize(Util.dip2px(12));

        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_ratio_date);

//        Drawable drawable = ContextCompat.getDrawable(mContext, R.mipmap.icon_ratio_date);
//        if (drawable == null) {
//            return;
//        }
//        mBitmap = drawableToBitamp(drawable);
//        float mScale = 1.0f;
//        Matrix matrix = new Matrix();
//        matrix.postScale(mScale, mScale);
//        Bitmap mEndBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
//        // 将bmp作为着色器，就是在指定区域内绘制bmp
//        BitmapShader mBitmapShader = new BitmapShader(mEndBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//
//
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
//        // 设置shader
//        mBitmapPaint.setShader(mBitmapShader);
//        mBitmapWidth = mEndBitmap.getWidth();
//        mBitmapHeight = mEndBitmap.getHeight();
    }

    private Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
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

        valueArea = this.max - this.min;
        for (int i = 0; i < initialList.size(); i++) {
            RatioPoint item = initialList.get(i);
            float y1 = (this.max - item.floatPoint) / valueArea * areaHeight + topBottomDelta + topHeight;
            float y2 = (this.max - item.industryIndexPoint) / valueArea * areaHeight + topBottomDelta + topHeight;
            float y3 = (this.max - item.hsPoint) / valueArea * areaHeight + topBottomDelta + topHeight;
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
                int y = (int) (topBottomDelta + areaHeight / 4f * i + topHeight);
                int startX = leftMargin;
                int endX = mInitialWidth - rightMargin;
                canvas.drawLine(startX, y, endX, y, mBgLinePaint);
                canvas.drawText(String.valueOf(max - (valueArea) / 4 * i) + "%", Util.dip2px(20), y + topBottomDelta, mTextPaint);
            }

            if (isTouching) {
                canvas.drawLine(currentX, topHeight, currentX, topBottomDelta + areaHeight, mVLinePaint);
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

            if (isTouching) {
                int pos = getIndex(currentX);
                if (pos >= 0) {
                    float x = pointList.get(pos).x;
                    float y1 = pointList.get(pos).y1;
                    float y2 = pointList.get(pos).y2;
                    float y3 = pointList.get(pos).y3;
                    String str = pointList.get(pos).xStr;
                    mCirclePaint.setColor(color1);
                    canvas.drawCircle(x, y1, Util.dip2px(3), mCirclePaint);
                    mCirclePaint.setColor(color2);
                    canvas.drawCircle(x, y2, Util.dip2px(3), mCirclePaint);
                    mCirclePaint.setColor(color3);
                    canvas.drawCircle(x, y3, Util.dip2px(3), mCirclePaint);
                    canvas.drawBitmap(mBitmap, x - mBitmap.getWidth() / 2, 0, mBitmapPaint);
                    canvas.drawText(str, x - Util.dip2px(30), Util.dip2px(14), mStrPaint);
                }
            }
        }
    }

    private int getIndex(float x) {
        float left = x - leftMargin;
        int index = (int) (left / areaWidth * pointList.size());
        float delta = left - ((float) index / pointList.size()) * areaWidth;
        if (delta > areaWidth / pointList.size() / 2) {
            index += 1;
        }
        return index;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.currentX = event.getX();
                this.currentY = event.getY();
                isTouching = true;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                this.currentX = event.getX();
                this.currentY = event.getY();
                isTouching = true;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isTouching = false;
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mInitialWidth, mInitialHeight);
    }
}
