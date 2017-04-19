package com.zxb.libsdemo.view.points;

/**
 * Created by mrzhou on 2017/4/19.
 */
public class MinMax {
    public float max;
    public float min;
    public int minIndex;
    public int maxIndex;
    public float realMaxValue;
    public float realMinValue;
    public MinMax() {
        max = -Float.MAX_VALUE;
        min = Float.MAX_VALUE;
    }
    public void reset() {
        max = -Float.MAX_VALUE;
        min = Float.MAX_VALUE;
    }
}
