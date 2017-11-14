package com.zxb.libsdemo.view.ratio;

import java.io.Serializable;

/**
 * Created by yufangyuan on 2017/10/20.
 */

public class PixelPoint implements Serializable {
    public float x;
    public float y1;
    public float y2;
    public float y3;
    public String xStr;
    public PixelPoint(float x, float y1, float y2, float y3, String xStr) {
        this.x = x;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
        this.xStr = xStr;
    }
}
