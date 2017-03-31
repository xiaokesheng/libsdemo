package com.zxb.libsdemo.adapter;

import android.content.Context;
import android.media.midi.MidiManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.line.LinePoint;
import com.zxb.libsdemo.util.Util;
import com.zxb.libsdemo.view.LineView;

import java.util.ArrayList;

/**
 * Created by mrzhou on 16/5/30.
 */
public class LineAdapter extends BaseAdapter {

    private static int LARGE_WIDTH_DP = 32;
    private static int MEDIUM_WIDTH_DP = 16;
    private static int MINIMUM_WIDTH_DP = 8;

    private Context mContext;
    private ArrayList<LinePoint> linePointList;
    private int mItemWidth;

    private View vMinimumLine;

    public LineAdapter(Context context, ArrayList<LinePoint> pointList) {
        this.mContext = context;
        this.linePointList = pointList;
        this.mItemWidth = MEDIUM_WIDTH_DP;
    }

    @Override
    public int getCount() {
        return 60;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public int getItemWidthInDP() {
        return mItemWidth;
    }

    public int getWidthType() {
        if (mItemWidth == MEDIUM_WIDTH_DP) {
            return 0;
        }
        if (mItemWidth == LARGE_WIDTH_DP) {
            return 1;
        }
        if (mItemWidth == MINIMUM_WIDTH_DP) {
            return -1;
        }
        return 0;
    }

    public void setWidthType(int type) {
        int currentWidth = mItemWidth;
        if (type > 0) {
            mItemWidth = LARGE_WIDTH_DP;
        } else
        if (type < 0) {
            mItemWidth = MINIMUM_WIDTH_DP;
        } else {
            mItemWidth = MEDIUM_WIDTH_DP;
        }

        if (currentWidth != mItemWidth) {
            notifyDataSetChanged();
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.item_line, null);

        LineView itemView = (LineView) view.findViewById(R.id.lvItem);
//        itemView.setLinePoint(0, position * 8, 0, (position + 1) * 8);
        itemView.setPoint(linePointList.get(position));

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) itemView.getLayoutParams();
        lp.height = Util.dip2px(300);
        lp.width = Util.dip2px(mItemWidth);
        itemView.setLayoutParams(lp);

//        if (position == 5) {
//            vMinimumLine = view.findViewById(R.id.vLineMinimum);
//            vMinimumLine.setVisibility(View.VISIBLE);
//        } else {
//            if (null != vMinimumLine) {
//                vMinimumLine.setVisibility(View.GONE);
//                Log.e("vMinimumLine", "woka");
//            }
//        }

        return view;
    }
}
