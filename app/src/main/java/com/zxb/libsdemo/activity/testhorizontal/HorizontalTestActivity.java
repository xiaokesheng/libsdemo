package com.zxb.libsdemo.activity.testhorizontal;

import java.util.ArrayList;
import java.util.List;

import com.zxb.libsdemo.activity.testhorizontal.MyHScrollView.OnScrollChangedListener;
import com.zxb.libsdemo.R;
import com.zxb.libsdemo.util.ToastUtil;
import com.zxb.libsdemo.util.Util;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HorizontalTestActivity extends Activity implements View.OnClickListener {

    ListView mListView1;
    MyAdapter myAdapter;
    RelativeLayout rlHead;
    LinearLayout main;

    private Context mContext;

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horizontal_main);
        mContext = this;

        rlHead = (RelativeLayout) findViewById(R.id.head);
        rlHead.setFocusable(true);
        rlHead.setClickable(true);
        rlHead.setBackgroundColor(Color.parseColor("#b2d235"));
//        rlHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());

        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView6 = (TextView) findViewById(R.id.textView6);
        textView7 = (TextView) findViewById(R.id.textView7);

        textView2.setText("hahaha");

        mListView1 = (ListView) findViewById(R.id.listView1);
        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (canClick) {
                    ToastUtil.toast(mContext, String.valueOf(position));
                }
            }
        });
        mListView1.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());

        myAdapter = new MyAdapter(this, R.layout.item);
        mListView1.setAdapter(myAdapter);

        Util.setClickListener(this, textView1, textView2, textView3, textView4, textView5, textView6, textView7);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView1:
                ToastUtil.toast(this, "TextView1");
                break;
            case R.id.textView2:
                ToastUtil.toast(this, "TextView2");
                break;
            case R.id.textView3:
                ToastUtil.toast(this, "TextView3");
                break;
            case R.id.textView4:
                ToastUtil.toast(this, "TextView4");
                break;
            case R.id.textView5:
                ToastUtil.toast(this, "TextView5");
                break;
            case R.id.textView6:
                ToastUtil.toast(this, "TextView6");
                break;
            case R.id.textView7:
                ToastUtil.toast(this, "TextView7");
                break;
        }

    }

    float lastY;
    float lastX;
    boolean canClick = true;

    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View arg0, MotionEvent ev) {
            HorizontalScrollView headSrcrollView = (HorizontalScrollView) rlHead
                    .findViewById(R.id.horizontalScrollView1);
            headSrcrollView.onTouchEvent(ev);

            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    canClick = true;
                    lastY = ev.getRawY();
                    lastX = ev.getRawX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    final float deltaY = ev.getRawY() - lastY;
                    final float deltaX = ev.getRawX() - lastX;
                    if (Math.abs(deltaX) > 10 || Math.abs(deltaY) > 10) {
                        canClick = false;
                        return false;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            return false;
        }
    }

    public class MyAdapter extends BaseAdapter {
        public List<ViewHolder> mHolderList = new ArrayList<ViewHolder>();

        int id_row_layout;
        LayoutInflater mInflater;

        public MyAdapter(Context context, int id_row_layout) {
            super();
            this.id_row_layout = id_row_layout;
            mInflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return 250;
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parentView) {
            ViewHolder holder = null;
            if (convertView == null) {
                synchronized (HorizontalTestActivity.this) {
                    convertView = mInflater.inflate(id_row_layout, null);
                    holder = new ViewHolder();

                    InterceptScrollContainer scrollContainer = (InterceptScrollContainer) convertView.findViewById(R.id.scroollContainter);
                    scrollContainer.setTag("listview");

                    MyHScrollView scrollView1 = (MyHScrollView) convertView.findViewById(R.id.horizontalScrollView1);



                    holder.scrollView = scrollView1;
                    holder.txt1 = (TextView) convertView.findViewById(R.id.textView1);
                    holder.txt2 = (TextView) convertView.findViewById(R.id.textView2);
                    holder.txt3 = (TextView) convertView.findViewById(R.id.textView3);
                    holder.txt4 = (TextView) convertView.findViewById(R.id.textView4);
                    holder.txt5 = (TextView) convertView.findViewById(R.id.textView5);

                    MyHScrollView headSrcrollView = (MyHScrollView) rlHead.findViewById(R.id.horizontalScrollView1);
                    headSrcrollView.addOnScrollChangedListener(new OnScrollChangedListenerImp(scrollView1));

                    convertView.setTag(holder);
                    mHolderList.add(holder);
                }
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.txt1.setText(position + "" + 1);
            holder.txt2.setText(position + "" + 2);
            holder.txt3.setText(position + "" + 3);
            holder.txt4.setText(position + "" + 4);
            holder.txt5.setText(position + "" + 5);

            return convertView;
        }

        class OnScrollChangedListenerImp implements OnScrollChangedListener {
            MyHScrollView mScrollViewArg;

            public OnScrollChangedListenerImp(MyHScrollView scrollViewar) {
                mScrollViewArg = scrollViewar;
            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                mScrollViewArg.smoothScrollTo(l, t);
            }
        }

        class ViewHolder {
            TextView txt1;
            TextView txt2;
            TextView txt3;
            TextView txt4;
            TextView txt5;
            HorizontalScrollView scrollView;
        }
    }

}
