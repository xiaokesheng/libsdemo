package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zxb.libsdemo.R;
import com.zxb.libsdemo.model.HLPointResult;
import com.zxb.libsdemo.util.SrcUtil;
import com.zxb.libsdemo.util.Util;
import com.zxb.libsdemo.view.hl.HLPoint;
import com.zxb.libsdemo.view.hl.HistogramAndLineView;
import com.zxb.libsdemo.view.hl.TinyHLView;

import java.util.ArrayList;

/**
 * Created by yufangyuan on 2017/8/21.
 */

public class TestHistsogramViewActivity extends Activity {

    HistogramAndLineView hlvView;
    LinearLayout llHLArea;
    TinyHLView thlv;
    LinearLayout llThlv;

    TextView tvLeft1;
    TextView tvLeft2;
    TextView tvLeft3;
    TextView tvLeft4;
    TextView tvLeft5;
    TextView tvLeft6;
    TextView tvRight1;
    TextView tvRight2;
    TextView tvRight3;
    TextView tvRight4;
    TextView tvRight5;
    TextView tvRight6;

    ArrayList<TextView> leftList;
    ArrayList<TextView> rightList;
    
    TextView tvAmount;
    TextView tvNum;
    TextView tvTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_test_histogram);

        hlvView = (HistogramAndLineView) findViewById(R.id.hlvView);
        llHLArea = (LinearLayout) findViewById(R.id.llHLArea);
//        thlv = (TinyHLView) findViewById(R.id.thlv);
//        llThlv = (LinearLayout) findViewById(R.id.llThlv);

        tvAmount = (TextView) findViewById(R.id.tvAmount);
        tvNum = (TextView) findViewById(R.id.tvNum);
        tvTime = (TextView) findViewById(R.id.tvTime);

        tvLeft1 = (TextView) findViewById(R.id.tvLeft1);
        tvLeft2 = (TextView) findViewById(R.id.tvLeft2);
        tvLeft3 = (TextView) findViewById(R.id.tvLeft3);
        tvLeft4 = (TextView) findViewById(R.id.tvLeft4);
        tvLeft5 = (TextView) findViewById(R.id.tvLeft5);
        tvLeft6 = (TextView) findViewById(R.id.tvLeft6);
        tvRight1 = (TextView) findViewById(R.id.tvRight1);
        tvRight2 = (TextView) findViewById(R.id.tvRight2);
        tvRight3 = (TextView) findViewById(R.id.tvRight3);
        tvRight4 = (TextView) findViewById(R.id.tvRight4);
        tvRight5 = (TextView) findViewById(R.id.tvRight5);
        tvRight6 = (TextView) findViewById(R.id.tvRight6);
        leftList = new ArrayList<>();
        leftList.add(tvLeft1);
        leftList.add(tvLeft2);
        leftList.add(tvLeft3);
        leftList.add(tvLeft4);
        leftList.add(tvLeft5);
        leftList.add(tvLeft6);
        rightList = new ArrayList<>();
        rightList.add(tvRight1);
        rightList.add(tvRight2);
        rightList.add(tvRight3);
        rightList.add(tvRight4);
        rightList.add(tvRight5);
        rightList.add(tvRight6);

//        llHLArea.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                llHLArea.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                int width = llHLArea.getWidth();
//                int height = llHLArea.getHeight();
                hlvView.setPointList(getPointList(1));
//            }
//        });

        hlvView.setOnLeftLoadListener(new HistogramAndLineView.OnLeftLoadListener() {
            @Override
            public void loadLeft(int page) {
                ArrayList<HLPoint> pList = getPointList(hlvView.getCurrentPage() + 1);
                if (null != pList && pList.size() > 0) {
                    hlvView.addPointList(pList, 1);
                } else {
                    hlvView.setDataIsLoadOver(true);
                }
            }
        });

        hlvView.setOnCheckListener(new HistogramAndLineView.OnCheckListener() {
            @Override
            public void onChecked(HLPoint point) {
                tvAmount.setText(String.valueOf(point.lValue));
                tvNum.setText(String.valueOf(point.hValue));
                tvTime.setText(point.xString);
            }
        });

        hlvView.setOnMaxValueChangedListener(new HistogramAndLineView.OnMaxValueChangedListener() {
            @Override
            public void onHLValueChanged(int hValue, float lValue) {
                float leftDelta = lValue / (leftList.size() - 1);
                for (int i = 0; i < leftList.size(); i++) {
                    leftList.get(i).setText(String.valueOf((int) ((6 - i - 1) * leftDelta)));
                }
                float rightDelta = hValue / (rightList.size() - 1);
                for (int i = 0; i < rightList.size(); i++) {
                    rightList.get(i).setText(String.valueOf((int) ((6 - i - 1) * rightDelta)));
                }
            }
        });

//        final ArrayList<HLPoint> pointList = new ArrayList<>();
//        pointList.add(new HLPoint("", 1f, 4.1f));
//        pointList.add(new HLPoint("", 2f, 2.1f));
//        pointList.add(new HLPoint("", 4f, 3.1f));
//        pointList.add(new HLPoint("", 2f, 1.1f));
//        pointList.add(new HLPoint("", 7f, 8.1f));
//        pointList.add(new HLPoint("", 7f, 9.1f));
//        pointList.add(new HLPoint("", 2f, 2.1f));
//        pointList.add(new HLPoint("", 1f, 1.1f));

//        llThlv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                llThlv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                thlv.setPointList(pointList, llThlv.getWidth(), llThlv.getHeight());
//            }
//        });

        /*
         <LinearLayout
         android:id="@+id/llThlv"
         android:layout_width="200dp"
         android:layout_height="80dp"
         android:orientation="horizontal"
         >

         <com.zxb.libsdemo.view.hl.TinyHLView
         android:id="@+id/thlv"
         android:layout_width="match_parent"
         android:layout_height="match_parent" />

         </LinearLayout>
         */

    }

    private ArrayList<HLPoint> getPointList(int page) {
        Gson gson = new Gson();
        HLPointResult result = gson.fromJson(SrcUtil.getHLPointSrc(page), HLPointResult.class);
        if (null != result.data && null != result.data.list) {
            ArrayList<HLPoint> pList = new ArrayList<>();
            for (HLPointResult.DataBean.ListBean item : result.data.list) {
                pList.add(new HLPoint(item.time, item.num, (float) item.amount));
            }
            return pList;
        }
        return null;
    }
}
