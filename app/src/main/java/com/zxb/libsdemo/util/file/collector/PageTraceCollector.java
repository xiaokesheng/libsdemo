package com.zxb.libsdemo.util.file.collector;

import android.content.Context;

import com.zxb.libsdemo.util.J;
import com.zxb.libsdemo.util.PackageUtil;
import com.zxb.libsdemo.util.TimeUtil;
import com.zxb.libsdemo.util.file.LogType;
import com.zxb.libsdemo.util.file.StorageUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yufangyuan on 2018/3/16.
 */

public class PageTraceCollector implements LogCollector {

    @Override
    public String getLog(Context context) {
        StringBuilder result = new StringBuilder();

        List<String> logList = ReadLogFileUtil.getFileEndLines(context, LogType.TRACE);
        for (String item : logList) {
            result.append(item + "\n");
        }

        return result.toString();
    }
}
