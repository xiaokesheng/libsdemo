package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.rey.material.widget.LinearLayout;
import com.zxb.libsdemo.R;

/**
 * Created by mrzhou on 16/5/20.
 */
public class TestMaterialActivity extends BaseActivity {

    LinearLayout llTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_material_layout);

        llTest = (LinearLayout) findViewById(R.id.llTest);

    }
}
