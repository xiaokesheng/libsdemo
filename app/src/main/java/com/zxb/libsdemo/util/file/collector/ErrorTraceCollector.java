package com.zxb.libsdemo.util.file.collector;

import android.content.Context;

import com.zxb.libsdemo.util.file.LogType;

import java.util.List;

/**
 * Created by yufangyuan on 2018/3/16.
 */

public class ErrorTraceCollector implements LogCollector {

    @Override
    public String getLog(Context context) {
        StringBuilder result = new StringBuilder();

        List<String> logList = ReadLogFileUtil.getFileEndLines(context, LogType.ERROR);
        for (String item : logList) {
            result.append(item + "\n");
        }

        return result.toString();
    }
}
