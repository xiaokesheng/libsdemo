package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by mrzhou on 16/5/28.
 */
public class BaseActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }
}
