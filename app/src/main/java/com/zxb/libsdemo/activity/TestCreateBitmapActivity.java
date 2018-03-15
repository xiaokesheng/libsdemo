package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by mrzhou on 17/3/6.
 */
public class TestCreateBitmapActivity extends BaseActivity {

    ImageView ivBitmap;
    TextView tvBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_test_bitmap);

        ivBitmap = (ImageView) findViewById(R.id.ivBitmap);
        tvBitmap = (TextView) findViewById(R.id.tvBitmap);

        tvBitmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage();
            }
        });
    }

    private void setImage() {
        View view = View.inflate(this, R.layout.l_testbitmap, null);
//        ivBitmap.setImageBitmap(convertViewToBitmap(view, 1080, Util.dip2px(300)));
        ivBitmap.setImageBitmap(convertViewToBitmap(view));
        saveBitmap("bitmap", convertViewToBitmap(view));
    }

    public static Bitmap convertViewToBitmap(View view, int bitmapWidth, int bitmapHeight) {
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));

        return bitmap;
    }

    public Bitmap convertViewToBitmap(View view) {
        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
//        saveBitmap("zzz" + String.valueOf(new Random().nextInt(200000)), bitmap);
        return bitmap;
    }

    public void saveBitmap(String bitName, Bitmap mBitmap) {
        File f = new File("/sdcard/" + bitName + ".png");
        try {
            f.createNewFile();
        } catch (IOException e) {
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
