package com.zxb.libsdemo.util.file.collector;

import android.content.Context;
import android.util.Log;

import java.util.Date;

/**
 * Created by yufangyuan on 2018/3/16.
 */

public class CrashLogCollector implements LogCollector {

    StringBuilder crashMsg;

    public CrashLogCollector(Context context, Thread thread, Throwable ex) {
        crashMsg = new StringBuilder();
        String cause = Log.getStackTraceString(ex.getCause());
        crashMsg.append(String.format("崩溃时间: %s\r\nThread: %d\r\nMessage: \r\n%s\r\nStack Trace:\r\n%s",
                new Date(),
                thread.getId(), ex.getMessage(),
                Log.getStackTraceString(ex)));
        crashMsg.append("-----------------------------------------------------");
        crashMsg.append("cause:");
        crashMsg.append(cause);
        crashMsg.append("LocalizedMessage:");
        crashMsg.append(ex.getLocalizedMessage());
        crashMsg.append("StackTrace:");
        for (StackTraceElement item : ex.getStackTrace()) {
            crashMsg.append(item);
        }
    }

    @Override
    public String getLog(Context context) {
        return crashMsg.toString();
    }
}
