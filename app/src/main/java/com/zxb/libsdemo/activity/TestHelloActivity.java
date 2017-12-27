package com.zxb.libsdemo.activity;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.fragment.HelloFragment;
import com.zxb.libsdemo.util.Util;

/**
 * Created by yufangyuan on 2017/11/14.
 */

public class TestHelloActivity extends FragmentActivity implements View.OnClickListener {

    FrameLayout flHello;

    android.app.FragmentTransaction action;
    android.app.FragmentManager manager;

    int page = 1;

    TextView tvAdd, tvShow, tvReplace, tvHide;

    WindowManager mManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_fragment_hello);

        Resources res = getResources();
        res.getDrawable(R.drawable.shape_blue_color);

        flHello = (FrameLayout) findViewById(R.id.flHello);
        tvAdd = (TextView) findViewById(R.id.tvAdd);
        tvShow = (TextView) findViewById(R.id.tvShow);
        tvReplace = (TextView) findViewById(R.id.tvReplace);
        tvHide = (TextView) findViewById(R.id.tvHide);

        Util.setClickListener(this, tvAdd, tvShow, tvReplace, tvHide);

        HelloFragment fragment = HelloFragment.getInstance(page++);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction action = manager.beginTransaction();
        action.setCustomAnimations(R.anim.right_enter, R.anim.left_out, R.anim.top_enter, R.anim.bottom_out);
        action.add(R.id.flHello, fragment);
        action.commit();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAdd:
                HelloFragment fragment = HelloFragment.getInstance(page++);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction action = manager.beginTransaction();
                action.setCustomAnimations(R.anim.right_enter, R.anim.left_out, R.anim.top_enter, R.anim.bottom_out);
                action.add(R.id.flHello, fragment);
                action.addToBackStack(fragment.getClass().getName());
                action.commit();
                break;
            case R.id.tvReplace:
                HelloFragment fragment2 = HelloFragment.getInstance(page++);
                FragmentManager manager2 = getSupportFragmentManager();
                FragmentTransaction action2 = manager2.beginTransaction();
                action2.setCustomAnimations(R.anim.right_enter, R.anim.left_out, R.anim.top_enter, R.anim.bottom_out);
                action2.replace(R.id.flHello, fragment2, fragment2.getClass().getName());
                action2.addToBackStack(fragment2.getClass().getName());
                action2.commit();
                break;
            case R.id.tvShow:
                break;
            case R.id.tvHide:
                break;
        }
    }
}
