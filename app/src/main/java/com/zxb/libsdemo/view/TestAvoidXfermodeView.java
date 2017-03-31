package com.zxb.libsdemo.view;

import android.content.Context;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.util.Util;

/**
 * Created by mrzhou on 16/5/25.
 */
public class TestAvoidXfermodeView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;
    private Context mContext;
    private int x, y, w, h;
    private AvoidXfermode avoidXfermode;

    public TestAvoidXfermodeView(Context context) {
        this(context, null);
        mContext = context;
    }

    public TestAvoidXfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initRes();
        initPaint();
    }

    private void initRes() {
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.sweet_cat);
        x = Util.getScreenWidth(mContext) / 2 - mBitmap.getWidth() / 2;
        y = Util.getScreenHeight(mContext) / 2 - mBitmap.getHeight() / 2;
        w = Util.getScreenWidth(mContext) / 2 + mBitmap.getWidth() / 2;
        h = Util.getScreenHeight(mContext) / 2 + mBitmap.getHeight() / 2;
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        avoidXfermode = new AvoidXfermode(0XFFFFFFFF, 0, AvoidXfermode.Mode.TARGET);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, x, y, mPaint);
        mPaint.setARGB(255, 255, 0, 0);
        mPaint.setXfermode(avoidXfermode);
        canvas.drawRect(x, y, w, h, mPaint);
    }
}
