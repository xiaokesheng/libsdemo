package com.zxb.libsdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.zxb.libsdemo.R;

/**
 * Created by mrzhou on 2017/3/31.
 */
public class ImageCaptureView extends View {

    private Context mContext;
    private int leftMargin, rightMargin;

    private int screenTotalWidth;

    private int mWidth, mHeight;
    private Bitmap mBitmap;
    private Bitmap mEndBitmap;
    private Paint mBitmapPaint;
    private BitmapShader mBitmapShader;

    private int mBitmapWidth;
    private int mBitmapHeight;

    private Matrix matrix;

    public ImageCaptureView(Context context, int width, int height, int leftMargin, int rightMargin, int totalWidth) {
        this(context);
        this.mWidth = width;
        this.mHeight = height;
        this.leftMargin = leftMargin;
        this.rightMargin = rightMargin;
        this.screenTotalWidth = totalWidth;
    }

    public ImageCaptureView(Context context) {
        this(context, null);
    }

    public ImageCaptureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageCaptureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        Drawable drawable = ContextCompat.getDrawable(mContext, R.mipmap.img_scapture);
        if (drawable == null) {
            return;
        }
        mBitmap = drawableToBitamp(drawable);
        float mScale = 2.0f;
        matrix = new Matrix();
        matrix.postScale(mScale, mScale);
        mEndBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
        // 将bmp作为着色器，就是在指定区域内绘制bmp
        mBitmapShader = new BitmapShader(mEndBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);


        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        // 设置shader
        mBitmapPaint.setShader(mBitmapShader);
        mBitmapWidth = mEndBitmap.getWidth();
        mBitmapHeight = mEndBitmap.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int count = (mWidth - leftMargin - rightMargin) / mEndBitmap.getWidth();
        int delta = (mWidth - leftMargin - rightMargin) % mEndBitmap.getWidth();

        for (int i = 0; i < count; i++) {
            canvas.drawBitmap(mEndBitmap, leftMargin + i * mEndBitmap.getWidth(), 0, mBitmapPaint);
        }
        if (delta > 0) {
            Bitmap mCropBitmap = Bitmap.createBitmap(mEndBitmap, 0, 0, delta, mEndBitmap.getHeight());
            canvas.drawBitmap(mCropBitmap, leftMargin + count * mEndBitmap.getWidth(), 0, mBitmapPaint);
        }
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
}
