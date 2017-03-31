package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.adapter.FragmentPagerAdapter;
import com.zxb.libsdemo.fragment.TestViewPagerFragment;

import java.util.ArrayList;

/**
 * Created by mrzhou on 16/7/21.
 */
public class TestViewPagerActivity extends FragmentActivity {

    ViewPager vpTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_viewpager);
        vpTest = (ViewPager) findViewById(R.id.vpTest);

        ArrayList<Fragment> list = new ArrayList<>();

        
        Fragment f1 = new TestViewPagerFragment();
        Bundle b1 = new Bundle();
        b1.putInt("type", 1);
        f1.setArguments(b1);
        Fragment f2 = new TestViewPagerFragment();
        Bundle b2 = new Bundle();
        b2.putInt("type", 2);
        f2.setArguments(b2);
        Fragment f3 = new TestViewPagerFragment();
        Bundle b3 = new Bundle();
        b3.putInt("type", 3);
        f3.setArguments(b3);
        Fragment f4 = new TestViewPagerFragment();
        Bundle b4 = new Bundle();
        b4.putInt("type", 4);
        f4.setArguments(b4);
        list.add(f1);
        list.add(f2);
        list.add(f3);
        list.add(f4);

        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), list);
        vpTest.setAdapter(mAdapter);
    }
}
