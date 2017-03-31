package com.zxb.libsdemo.line;

import java.io.Serializable;

/**
 * Created by mrzhou on 16/5/30.
 */
public class LinePoint implements Serializable {

    private float leftFloatX;
    private float leftFloatY;

    private float centerFloatX;
    private float centerFloatY;

    private float rightFloatX;
    private float rightFloatY;

    public LinePoint(float x, float y) {
        this.centerFloatX = x;
        this.centerFloatY = y;
    }

    public float getCenterFloatX() {
        return centerFloatX;
    }

    public void setCenterFloatX(float centerFloatX) {
        this.centerFloatX = centerFloatX;
    }

    public float getCenterFloatY() {
        return centerFloatY;
    }

    public void setCenterFloatY(float centerFloatY) {
        this.centerFloatY = centerFloatY;
    }

    public float getLeftFloatX() {
        return leftFloatX;
    }

    public void setLeftFloatX(float leftFloatX) {
        this.leftFloatX = leftFloatX;
    }

    public float getLeftFloatY() {
        return leftFloatY;
    }

    public void setLeftFloatY(float leftFloatY) {
        this.leftFloatY = leftFloatY;
    }

    public float getRightFloatX() {
        return rightFloatX;
    }

    public void setRightFloatX(float rightFloatX) {
        this.rightFloatX = rightFloatX;
    }

    public float getRightFloatY() {
        return rightFloatY;
    }

    public void setRightFloatY(float rightFloatY) {
        this.rightFloatY = rightFloatY;
    }

    public void setLeftPoint(float x, float y) {
        setLeftFloatX(x);
        setLeftFloatY(y);
    }

    public void setCenterPoint(float x, float y) {
        setCenterFloatX(x);
        setCenterFloatY(y);
    }

    public void setRightPoint(float x, float y) {
        setRightFloatX(x);
        setRightFloatY(y);
    }
}
