package com.zxb.libsdemo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.zxb.libsdemo.util.TrackUtil;

/**
 * Created by mrzhou on 16/5/28.
 */
public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TrackUtil.getInstance().trackPage(this, getActivityName(this.toString()), "onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        TrackUtil.getInstance().trackPage(this, getActivityName(this.toString()), "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        TrackUtil.getInstance().trackPage(this, getActivityName(this.toString()), "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TrackUtil.getInstance().trackPage(this, getActivityName(this.toString()), "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        TrackUtil.getInstance().trackPage(this, getActivityName(this.toString()), "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        TrackUtil.getInstance().trackPage(this, getActivityName(this.toString()), "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        TrackUtil.getInstance().trackPage(this, getActivityName(this.toString()), "onStop");
    }

    private String getActivityName(String activityObj) {
        if (!TextUtils.isEmpty(activityObj)) {
            String[] arrays = activityObj.split("@");
            if (arrays.length >= 1) {
                return arrays[0];
            }
        }
        return activityObj;
    }
}
