package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.view.HorizontalScrollViewX;
import com.zxb.libsdemo.view.SyncLinearLayout;

/**
 * Created by mrzhou on 16/11/2.
 */
public class TestHorizontalScrollViewActivity extends BaseActivity {

    RecyclerView rvTest;
    RecyclerView rvLeft;

    HorizontalScrollViewX hsv;
    SyncLinearLayout sll;

    TestAdapter mAdapter;
    LeftAdapter mLeftAdapter;

    LinearLayoutManager manager;
    LinearLayoutManager leftManager;

    private Context mContext;

    private boolean hsvIntercept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.a_test_horizontalscrollview);

        rvTest = (RecyclerView) findViewById(R.id.rvTest);
        rvLeft = (RecyclerView) findViewById(R.id.rvLeft);
        hsv = (HorizontalScrollViewX) findViewById(R.id.hsv);
        sll = (SyncLinearLayout) findViewById(R.id.sll);

        mAdapter = new TestAdapter(mContext);
        manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTest.setLayoutManager(manager);
        rvTest.setAdapter(mAdapter);

        mLeftAdapter = new LeftAdapter(mContext);
        leftManager = new LinearLayoutManager(mContext);
        leftManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvLeft.setLayoutManager(leftManager);
        rvLeft.setAdapter(mLeftAdapter);

        setListener();

    }

    private void setListener() {
        hsv.setOnInterceptChangedListener(new HorizontalScrollViewX.OnInterceptChangedListener() {
            @Override
            public void onInterceptChanged(boolean value) {
                hsvIntercept = value;
                sll.setCanIntercept(hsvIntercept);
            }
        });

//        sll.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                return false;
//            }
//        });

    }

    private static class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

        private Context mContext;

        public TestAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.a_a_itemtest, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 30;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    private static class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.ViewHolder> {

        private Context mContext;

        public LeftAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.a_a_leftitem, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 30;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
