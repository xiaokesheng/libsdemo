package com.zxb.libsdemo.model;

/**
 * Created by mrzhou on 2017/4/13.
 */
public class PointC {
    public float x;
    public float y;

    public float xValue;
    public float yValue;

    public PointC(float x, float y) {
        this.xValue = x;
        this.yValue = y;
    }

    public PointC() {

    }

    @Override
    public String toString() {
        return "x:" + x + ", y:" + y;
    }
}
