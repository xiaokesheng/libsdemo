package com.zxb.libsdemo.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by mrzhou on 2017/5/15.
 */
public class ColorAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return (0xffffff - 0x0) / 0x10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
