package com.zxb.libsdemo.model;

/**
 * Created by mrzhou on 2017/4/13.
 */
public class PointC {
    public float x;
    public float yPixels;

    public float xValue;
    public float yValue;

    public PointC(float xValue, float yValue) {
        this.xValue = xValue;
        this.yValue = yValue;
    }

    public PointC() {

    }

    @Override
    public String toString() {
        return "x:" + x + ", y:" + yPixels;
    }
}
