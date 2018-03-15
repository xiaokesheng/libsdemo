package com.zxb.libsdemo.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by yufangyuan on 2018/3/15.
 */

public class LogFileUtil {

    public static void writeToFile(Context context, String log) {
        File savePathFile = getLogFilePath(context);
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
    public static File getLogFilePath(Context ctx) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }

        String pathName = Environment.getExternalStorageDirectory().getPath()
                + "/Android/data/" + ctx.getPackageName()
                + "/files/trace/"
                + android.os.Build.MODEL + "_"
                + PackageUtil.getVersionName(ctx) + ".log";

        File path = new File(pathName);
        if (!path.getParentFile().exists()) {
            path.getParentFile().mkdirs();
        }
        return path;
    }
}
