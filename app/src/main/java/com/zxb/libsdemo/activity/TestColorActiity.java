package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.zxb.libsdemo.R;

/**
 * Created by mrzhou on 2017/5/15.
 */
public class TestColorActiity extends Activity {

    ListView lvColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_test_color);

        lvColor = (ListView) findViewById(R.id.lvColor);
    }
}
