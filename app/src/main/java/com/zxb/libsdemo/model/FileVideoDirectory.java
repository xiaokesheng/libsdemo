package com.zxb.libsdemo.model;

import java.io.Serializable;

/**
 * Created by mrzhou on 16/5/28.
 */
public class FileVideoDirectory implements Serializable {

    public String dirName;
    public int number;

    public FileVideoDirectory(String dirName, int number) {
        this.dirName = dirName;
        this.number = number;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dirName + ":" + String.valueOf(number) + "\n");
        return sb.toString();
    }
}
