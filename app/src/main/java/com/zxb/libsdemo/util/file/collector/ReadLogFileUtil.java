package com.zxb.libsdemo.util.file.collector;

import android.content.Context;

import com.zxb.libsdemo.util.TimeUtil;
import com.zxb.libsdemo.util.file.StorageUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yufangyuan on 2018/3/16.
 */

public class ReadLogFileUtil {
    public static List<String> getFileEndLines(Context context, String tag) {
        List<String> list = new LinkedList<>();
        RandomAccessFile rf = null;
        try {
            rf = new RandomAccessFile(StorageUtil.getDiskCacheDir(context, tag) + File.separator + "32312.log", "r");
            long len = rf.length();
            long start = rf.getFilePointer();
            long nextend = start + len - 1;
            String line;
            rf.seek(nextend);
            int c = -1;
            while (nextend > start) {
                c = rf.read();
                if (c == '\n' || c == '\r') {
                    line = rf.readLine();
                    if (line != null) {
                        if (!line.startsWith("TIME:::")) {
                            if (list.size() >= 99) {
                                break;
                            } else {
                                list.add(0, line);
                            }
                        } else {
                            list.add(0, line);
                            String time = line.replace("TIME:::", "");
                            if (TimeUtil.getSecondDelta(time) >= 10) {
                                list.add(0, line);
                                break;
                            }
                        }
                    }
                    nextend--;
                }
                nextend--;
                rf.seek(nextend);
                if (nextend == 0) {
                    String firstLine = rf.readLine();
                    list.add(0, firstLine);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rf != null) {
                    rf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private String getRightUnicodeStr(String str) {
        try {
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
