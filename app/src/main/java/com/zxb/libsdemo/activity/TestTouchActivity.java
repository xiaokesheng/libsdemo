package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.view.TouchTestView;

/**
 * Created by mrzhou on 16/5/19.
 */
public class TestTouchActivity extends Activity {

    TouchTestView ttvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_touch_layout);

        ttvView = (TouchTestView) findViewById(R.id.ttvView);

        ttvView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                VelocityTracker velocityTracker = VelocityTracker.obtain();
                velocityTracker.addMovement(event);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("motionEvent", "TouchListener--Down");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e("touchEvent", "TouchListener--touching");
                        velocityTracker.computeCurrentVelocity(1000);
                        int xVelocity = (int) velocityTracker.getXVelocity();
                        int yVelocity = (int) velocityTracker.getYVelocity();
                        Log.e("touchEvent", "xVelocity: " + String.valueOf(xVelocity));
                        Log.e("touchEvent", "yVelocity: " + String.valueOf(yVelocity));
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e("motionEvent", "TouchListener--UP");
                        velocityTracker.clear();
                        velocityTracker.recycle();
                        break;
                }
                return true;
            }
        });

//        ttvView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("touchClick", "click");
//            }
//        });
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("motionEvent", "Activity--Down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("touchEvent", "Activity--touching");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("motionEvent", "Activity--UP");
                break;
        }
        return super.onTouchEvent(event);
    }
}
