package com.zxb.libsdemo.line;

/**
 * Created by mrzhou on 16/5/31.
 */
public class NewPoint {

    private float y;
    private float yInPixes;

    private int percentage;

    public NewPoint(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;

    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public float getyInPixes() {
        return yInPixes;
    }

    public void setyInPixes(float yInPixes) {
        this.yInPixes = yInPixes;
    }
}
