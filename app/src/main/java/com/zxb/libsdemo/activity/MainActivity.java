package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.activity.testhorizontal.HorizontalTestActivity;
import com.zxb.libsdemo.activity.view.TestMuxActivity;
import com.zxb.libsdemo.util.ToastUtil;
import com.zxb.libsdemo.util.Util;
import com.zxb.libsdemo.view.HorizontalScrollViewX;
import com.zxb.libsdemo.view.PieChart;
import com.zxb.libsdemo.view.TestAvoidXfermodeView;

public class MainActivity extends Activity implements View.OnClickListener {

    Button btnTestTouch;
    Button btnTestMaterial;
    Button btnTestXfermode;
    Button btnTestSurfaceView;
    Button btnTestGallery;
    Button btnTestLineView;
    Button btnTestSDCardPath;

    Button btnTestFileSystem;
    Button btnTestViewPager;

    Button btnTestHorizontalView;
    Button btnTestLineBgView;

    Button btnTestView;

    Button btnTestBitmap;
    Button btnTestMedia;
    Button btnTestMux;
    Button btnTextMix;

    Button btnTestTouchView;

    Button btnTestRangeBar;

    Button btnTestArrayPoints;
    Button btnTestColor;
    Button btnTestWifi;

    Button btnTestElevation;
    Button btnTestVolley;
    Button btnTestHistogram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTestTouch = (Button) findViewById(R.id.btnTestTouch);
        btnTestMaterial = (Button) findViewById(R.id.btnTestMaterial);
        btnTestXfermode = (Button) findViewById(R.id.btnTestXfermode);
        btnTestSurfaceView = (Button) findViewById(R.id.btnTestSurfaceView);
        btnTestFileSystem = (Button) findViewById(R.id.btnTestFileSystem);
        btnTestGallery = (Button) findViewById(R.id.btnTestGallery);
        btnTestLineView = (Button) findViewById(R.id.btnTestLineView);
        btnTestSDCardPath = (Button) findViewById(R.id.btnTestSDCardPath);
        btnTestViewPager = (Button) findViewById(R.id.btnTestViewPager);
        btnTestHorizontalView = (Button) findViewById(R.id.btnTestHorizontalView);
        btnTestLineBgView = (Button) findViewById(R.id.btnTestLineBgView);
        btnTestView = (Button) findViewById(R.id.btnTestView);
        btnTestBitmap = (Button) findViewById(R.id.btnTestBitmap);
        btnTestMedia = (Button) findViewById(R.id.btnTestMedia);
        btnTestMux = (Button) findViewById(R.id.btnTestMux);
        btnTextMix = (Button) findViewById(R.id.btnTextMix);
        btnTestTouchView = (Button) findViewById(R.id.btnTestTouchView);
        btnTestRangeBar = (Button) findViewById(R.id.btnTestRangeBar);
        btnTestArrayPoints = (Button) findViewById(R.id.btnTestArrayPoints);
        btnTestColor = (Button) findViewById(R.id.btnTestColor);
        btnTestWifi = (Button) findViewById(R.id.btnTestWifi);
        btnTestElevation = (Button) findViewById(R.id.btnTestElevation);
        btnTestVolley = (Button) findViewById(R.id.btTestVolley);
        btnTestHistogram = (Button) findViewById(R.id.btnTestHistogram);

        Resources res = getResources();

        final PieChart pie = (PieChart) this.findViewById(R.id.Pie);
        pie.addItem("Agamemnon", 2, res.getColor(R.color.seafoam));
        pie.addItem("Bocephus", 3.5f, res.getColor(R.color.chartreuse));
        pie.addItem("Calliope", 2.5f, res.getColor(R.color.emerald));
        pie.addItem("Daedalus", 3, res.getColor(R.color.bluegrass));
        pie.addItem("Euripides", 1, res.getColor(R.color.turquoise));
        pie.addItem("Ganymede", 3, res.getColor(R.color.slate));

        ((Button) findViewById(R.id.Reset)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                pie.setCurrentItem(0);
            }
        });

        ViewGroup content = (ViewGroup) findViewById(android.R.id.content);
        ViewGroup container = (ViewGroup) content.getChildAt(0);
        if (null != container) {
            Log.i("number", String.valueOf(container.getChildCount()));
        }

        setListener();

        int status = getWindowManager().getDefaultDisplay().getRotation();

        if (status == Surface.ROTATION_90 || status == Surface.ROTATION_270) {
            // landscape
            Util.setGone(btnTestGallery, btnTestLineView, btnTestSDCardPath, btnTestViewPager);
        } else {
            // portrait
        }
    }

    private void setListener() {
        Util.setClickListener(this, btnTestTouch, btnTestMaterial, btnTestXfermode, btnTestSurfaceView);
        Util.setClickListener(this, btnTestFileSystem, btnTestGallery, btnTestLineView);
        Util.setClickListener(this, btnTestSDCardPath, btnTestViewPager, btnTestHorizontalView);
        Util.setClickListener(this, btnTestLineBgView, btnTestView, btnTestBitmap, btnTestMedia, btnTestMux);
        Util.setClickListener(this, btnTextMix, btnTestTouchView, btnTestRangeBar, btnTestArrayPoints, btnTestWifi);
        Util.setClickListener(this, btnTestElevation, btnTestVolley, btnTestHistogram);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTestTouch:
                startActivity(new Intent(this, TestTouchActivity.class));
                break;
            case R.id.btnTestMaterial:
                startActivity(new Intent(this, TestMaterialActivity.class));
                break;
            case R.id.btnTestXfermode:
                startActivity(new Intent(this, TestXfermodeActivity.class));
                break;
            case R.id.btnTestSurfaceView:
                startActivity(new Intent(this, TestSurfaceViewActivity.class));
                break;
            case R.id.btnTestFileSystem:
                startActivity(new Intent(this, TestFileSystemActivity.class));
                break;
            case R.id.btnTestGallery:
                startActivity(new Intent(this, TestGalleryActivity.class));
                break;
            case R.id.btnTestLineView:
                startActivity(new Intent(this, TestLineViewActivity.class));
                break;
            case R.id.btnTestSDCardPath:
                startActivity(new Intent(this, TestSdCardPathActivity.class));
                break;
            case R.id.btnTestViewPager:
                startActivity(new Intent(this, TestViewPagerActivity.class));
                break;
            case R.id.btnTestHorizontalView:
                startActivity(new Intent(this, HorizontalTestActivity.class));
                break;
            case R.id.btnTestLineBgView:
                startActivity(new Intent(this, TestLineBgViewActivity.class));
                break;
            case R.id.btnTestView:
                startActivity(new Intent(this, TestViewActivity.class));
                break;
            case R.id.btnTestBitmap:
                startActivity(new Intent(this, TestCreateBitmapActivity.class));
                break;
            case R.id.btnTestMedia:
                startActivity(new Intent(this, TestMediaActivity.class));
                break;
            case R.id.btnTestMux:
                startActivity(new Intent(this, TestMuxActivity.class));
                break;
            case R.id.btnTextMix:
                startActivity(new Intent(this, TestAudioMixActivity.class));
                break;
            case R.id.btnTestTouchView:
                startActivity(new Intent(this, TestTouchViewActivity.class));
                break;
            case R.id.btnTestRangeBar:
                startActivity(new Intent(this, TestRangeBarActivity.class));
                break;
            case R.id.btnTestArrayPoints:
                startActivity(new Intent(this, TestArrayPointsLineActivity.class));
                break;
            case R.id.btnTestWifi:
                startActivity(new Intent(this, TestWifiActivity.class));
                break;
            case R.id.btnTestElevation:
                startActivity(new Intent(this, TestElevationActivity.class));
                break;
            case R.id.btTestVolley:
                startActivity(new Intent(this, TestVolleyActivity.class));
                break;
            case R.id.btnTestHistogram:
                startActivity(new Intent(this, TestHistsogramViewActivity.class));
                break;
        }
    }
}
