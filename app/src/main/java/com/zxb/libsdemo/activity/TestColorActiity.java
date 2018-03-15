package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ListView;

import com.zxb.libsdemo.R;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by mrzhou on 2017/5/15.
 */
public class TestColorActiity extends BaseActivity {

    ListView lvColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_test_color);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lvColor = (ListView) findViewById(R.id.lvColor);

//        Callable<Integer> callable = new Callable<Integer>() {
//            @Override
//            public Integer call() throws Exception {
//                Thread.sleep(3000);
//                return 5;
//            }
//        };
//        try {
//            Log.e("haha", "en");
//            Log.e("haha", "je" + callable.call());
//            Log.e("haha", "end");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Future<Integer> future = new Future<Integer>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public Integer get() throws InterruptedException, ExecutionException {
                Thread.sleep(3000);
                return 6;
            }

            @Override
            public Integer get(long timeout, @NonNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        };
        try {
            Log.e("haha", "en");
            Log.e("haha", "je" + future.get());
            Log.e("haha", "end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
