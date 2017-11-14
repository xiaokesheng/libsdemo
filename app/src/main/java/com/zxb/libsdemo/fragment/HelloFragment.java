package com.zxb.libsdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxb.libsdemo.R;

/**
 * Created by yufangyuan on 2017/11/14.
 */

public class HelloFragment extends Fragment {

    public final static String TAG = "HelloFragment";

    TextView tvPage;
    int page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_hello, container, false);
        tvPage = (TextView) view.findViewById(R.id.tvPage);
        Bundle bundle = getArguments();
        if (null != bundle) {
            if (bundle.getInt("page") > 0) {
                tvPage.setText("Helloworld+" + String.valueOf(bundle.getInt("page")));
                this.page = bundle.getInt("page");
            }
        }
        return view;
    }

    public HelloFragment() {
        super();
    }

    public static HelloFragment getInstance(int page) {
        HelloFragment fragment = new HelloFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("page", page);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume--" + this.page);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause--" + this.page);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop--" + this.page);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy--" + this.page);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "onDetach--" + this.page);
    }
}
