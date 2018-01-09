package com.zxb.libsdemo.view.ratio;

/**
 * Created by yufangyuan on 2017/10/20.
 */

public class RatioPoint {
    public float floatPoint;
    public float hsPoint;
    public float industryIndexPoint;
    public String date;
    public float min;
    public float max;

    public RatioPoint(float y1, float y2, float y3, String data) {
        this.floatPoint = y1;
        this.industryIndexPoint = y2;
        this.hsPoint = y3;
        this.date = data;
    }
}
