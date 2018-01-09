package com.zxb.libsdemo.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zxb.libsdemo.R;

/**
 * Created by yufangyuan on 2017/12/29.
 */

public enum ToastMgr {
    builder;
    private View v;
    private TextView tv;
    private Toast toast;
    private LinearLayout llBg;
    private TextView tvToastTitle;
    private LinearLayout llTitle;
    private ImageView ivTitle;

    public void init(Context c) {
        v = LayoutInflater.from(c).inflate(R.layout.view_toast, null);
        llBg = (LinearLayout) v.findViewById(R.id.llBg);
        llTitle = (LinearLayout) v.findViewById(R.id.llTitle);
        ivTitle = (ImageView) v.findViewById(R.id.ivToastTitle);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) llBg.getLayoutParams();
        lp.width = Util.getScreenWidth(c) / 3 * 2;
        llBg.setLayoutParams(lp);
        tv = (TextView) v.findViewById(R.id.tv_toast);
        tvToastTitle = (TextView) v.findViewById(R.id.tvToastTitle);
        toast = new Toast(c);
        toast.setView(v);
    }

    public void display(CharSequence text, int duration) {
        llTitle.setVisibility(View.GONE);
        if (text != null && text.length() != 0) {
            tv.setText(text);
            toast.setDuration(duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public void display(CharSequence title, CharSequence content, int duration) {
        if (null != title && title.length() != 0) {
            tvToastTitle.setText(title);
            llTitle.setVisibility(View.VISIBLE);
        }
        if (content != null && content.length() != 0) {
            tv.setText(content);
            toast.setDuration(duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public void display(int Resid, int duration) {
        llTitle.setVisibility(View.GONE);
        if (Resid != 0) {
            tv.setText(Resid);
            toast.setDuration(duration);
            toast.show();
        }
    }

    public void display(CharSequence text, int duration, int position, int yOffset) {
        llTitle.setVisibility(View.GONE);
        if (text.length() != 0) {
            tv.setText(text);
            toast.setDuration(duration);
            toast.setGravity(position, 0, yOffset);
            toast.show();
        }
    }

    public void display(CharSequence title, CharSequence content, int duration, boolean hasIcon) {
        if (!hasIcon) {
            ivTitle.setVisibility(View.GONE);
        }
        if (null != title && title.length() != 0) {
            tvToastTitle.setText(title);
            llTitle.setVisibility(View.VISIBLE);
        }
        if (content != null && content.length() != 0) {
            tv.setText(content);
            toast.setDuration(duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
