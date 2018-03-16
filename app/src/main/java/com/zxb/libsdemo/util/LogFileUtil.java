package com.zxb.libsdemo.util;

import android.content.Context;
import android.os.Environment;

import com.zxb.libsdemo.util.file.StorageUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by yufangyuan on 2018/3/15.
 */
public class LogFileUtil {

    public static final int FILE_MAX_LEGNTH = 48 * 1024;

    public static void writeToFile(Context context, String log, String tag) {
        File savePathFile = getLogFilePath(context, tag);
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileWriter(savePathFile, true));
            printWriter.write(log);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            printWriter.close();
        }
    }

    /**
     * 获取日志保存路径
     *
     * @param ctx
     * @return String  日志保存路径
     */
    public static File getLogFilePath(Context ctx, String tag) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }

        StringBuilder sb = new StringBuilder(StorageUtil.getDiskCacheDir(ctx, tag).toString());
        // TODO 补全文件名
        sb.append(File.separator);
        sb.append("32312.log");

        File path = new File(sb.toString());
        if (!path.getParentFile().exists()) {
            path.getParentFile().mkdirs();
        }
        return path;
    }

    public static List<String> getLogFromFile(Context ctx, String logType, int secondPeriod) {

        return null;
    }
}
