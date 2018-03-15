package com.zxb.libsdemo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.HandlerThread;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.activity.nettest.TestOkhttpActivity;
import com.zxb.libsdemo.activity.testhorizontal.HorizontalTestActivity;
import com.zxb.libsdemo.activity.view.TestMuxActivity;
import com.zxb.libsdemo.util.Constants;
import com.zxb.libsdemo.util.SPUtil;
import com.zxb.libsdemo.util.ToastUtil;
import com.zxb.libsdemo.util.Util;
import com.zxb.libsdemo.view.PieChart;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    HandlerThread thread;

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
    Button btnTestOkhttp;
    Button btnTestHistogram;

    Button btnTestFragment;
    Button btnTestWebView;

    int i = 0;

    protected String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private static final int PERMISSON_REQUESTCODE = 0;
    private boolean isNeedCheck = true;

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
        btnTestFragment = (Button) findViewById(R.id.btnTestFragment);
        btnTestOkhttp = (Button) findViewById(R.id.btnTestOkhttp);
        btnTestWebView = (Button) findViewById(R.id.btnTestWebView);

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

        putSomeStrToSP();
        checkPermissions(needPermissions);
    }

    private void setListener() {
        Util.setClickListener(this, btnTestTouch, btnTestMaterial, btnTestXfermode, btnTestSurfaceView);
        Util.setClickListener(this, btnTestFileSystem, btnTestGallery, btnTestLineView);
        Util.setClickListener(this, btnTestSDCardPath, btnTestViewPager, btnTestHorizontalView);
        Util.setClickListener(this, btnTestLineBgView, btnTestView, btnTestBitmap, btnTestMedia, btnTestMux);
        Util.setClickListener(this, btnTextMix, btnTestTouchView, btnTestRangeBar, btnTestArrayPoints, btnTestWifi);
        Util.setClickListener(this, btnTestElevation, btnTestVolley, btnTestHistogram, btnTestColor, btnTestFragment);
        Util.setClickListener(this, btnTestOkhttp, btnTestWebView);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTestTouch:
//                Log.e("patchLog", "it's bask app");
//                startActivity(new Intent(this, TestTouchActivity.class));
                Util.toast(this, "WHAT THE FUCK?");
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
//                TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");
                startActivity(new Intent(this, TestVolleyActivity.class));
                break;
            case R.id.btnTestHistogram:
                startActivity(new Intent(this, TestHistsogramViewActivity.class));
                break;
            case R.id.btnTestColor:
                startActivity(new Intent(this, TestColorActiity.class));
                break;
            case R.id.btnTestFragment:
                startActivity(new Intent(this, TestHelloActivity.class));
                break;
            case R.id.btnTestOkhttp:
                startActivity(new Intent(this, TestOkhttpActivity.class));
                break;
            case R.id.btnTestWebView:
                if (i++ % 2 == 0) {
                    showSP(SPUtil.SPKEY_LOG_KEY1);
                } else {
                    showSP(SPUtil.SPKEY_LOG_KEY2);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                try {
                    showMissingPermissionDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                isNeedCheck = false;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, paramArrayOfInt);
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {

    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    private void putSomeStrToSP() {
        String first = "abcdefghijklmn";
        String second = "opqrstuvwxyz";
        SPUtil.putValue(this, SPUtil.SPNAME_LOG_MODULE, SPUtil.SPKEY_LOG_KEY1, first);
        SPUtil.putValue(this, SPUtil.SPNAME_LOG_MODULE, SPUtil.SPKEY_LOG_KEY2, second);
    }

    private void showSP(String key) {
        String value = SPUtil.getValue(this, SPUtil.SPNAME_LOG_MODULE, key);
        Util.toast(this, value);
    }
}
