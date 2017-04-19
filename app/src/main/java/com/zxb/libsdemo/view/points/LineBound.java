package com.zxb.libsdemo.view.points;

/**
 * Created by mrzhou on 2017/4/14.
 */
public class LineBound {
    public int leftIndex;
    public int rightIndex;

    public int startPixels;
    public int endPixels;

    public float topPixels;
    public float bottomPixels;

    public int topIndex;
    public int bottomIndex;

    public float realMaxValue;
    public float realMinValue;

    public LineBound() {

    }

    @Override
    public String toString() {
        return "left: " + leftIndex + ",right: " + rightIndex + ", top:" + topPixels + ", bottom:" + bottomPixels;
    }
}
