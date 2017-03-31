package com.zxb.libsdemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import android.view.ViewConfiguration;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.util.ToastUtil;

/**
 * Created by mrzhou on 16/7/21.
 */
public class TestViewPagerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_test_viewpager, container, false);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll);
        Bundle bundle = getArguments();
        int type = bundle.getInt("type");
        int delta = type % 2;
        if (delta == 0) {
            ll.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.shape_gradient_blue_green));
        } else {
            ll.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.shape_red_blue));
        }

        ToastUtil.toast(getActivity(), String.valueOf(getTouchSlop()));
        ToastUtil.toast(getActivity(), String.valueOf(getDoubleTapTimeout()));

        return view;
    }

    private int getTouchSlop() {
        int a = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        return a;
    }

    private int getDoubleTapTimeout() {
        return ViewConfiguration.get(getContext()).getDoubleTapTimeout();
    }
}
