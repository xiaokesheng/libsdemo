package com.zxb.libsdemo.model;

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

    public LineBound() {

    }

    @Override
    public String toString() {
        return "left: " + leftIndex + ",right: " + rightIndex + ", top:" + topPixels + ", bottom:" + bottomPixels;
    }
}
