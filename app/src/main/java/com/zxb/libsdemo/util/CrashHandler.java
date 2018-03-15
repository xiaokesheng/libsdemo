package com.zxb.libsdemo.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yufangyuan on 2018/3/11.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (crashHandler == null) {
            synchronized (CrashHandler.class) {
                crashHandler = new CrashHandler();
            }
        }
        return crashHandler;
    }

    // 系统默认的UncaughtException处理类实例
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler静态实例
    private static CrashHandler crashHandler;
    // 程序的Context对象
    private Context mContext;
    //时间输出格式化
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss");

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 调用Thread.setDefaultUncaughtExceptionHandler(this);后，this实现了
     * UncaughtExceptionHandler，所以crash会进入到这里的回调。
     * 当UncaughtException发生时会转入该重写的方法来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("crashHandler", "catch an exception............");
        if (thread == null || ex == null || mDefaultHandler == null) {
            System.exit(0);
            return;
        }
        File savePathFile = getLogFilePath(mContext);
        if (savePathFile == null) {
            //抛出异常
            mDefaultHandler.uncaughtException(thread, ex);
            return;
        }
        String logMessage;
//        String logMessage = String
//                .format("CustomUncaughtExceptionHandler.uncaughtException: Thread %d \nMessage: \n%s \ntrack \n%s",
//                        thread.getId(), ex.getMessage(), Log.getStackTraceString(ex));
        String cause = Log.getStackTraceString(ex.getCause());
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileWriter(savePathFile, true));
            logMessage = String.format("崩溃时间: %s\r\nThread: %d\r\nMessage: \r\n%s\r\nStack Trace:\r\n%s",
                    new Date(),
                    thread.getId(), ex.getMessage(),
                    Log.getStackTraceString(ex));
            printWriter.println(logMessage);
            printWriter.println("-----------------------------------------------------");
            printWriter.println(DeviceInfoUtil.getDeviceInfo(mContext));
            printWriter.println("cause:");
            printWriter.println(cause);
            printWriter.println("LocalizedMessage:");
            printWriter.println(ex.getLocalizedMessage());
            printWriter.println("StackTrace:");
            for (StackTraceElement item : ex.getStackTrace()) {
                printWriter.println(item);
                printWriter.println("-");
            }
            printWriter.println(SPUtil.getValue(mContext, SPUtil.SPNAME_LOG_MODULE, SPUtil.SPKEY_LOG_KEY1));
            printWriter.println(SPUtil.getValue(mContext, SPUtil.SPNAME_LOG_MODULE, SPUtil.SPKEY_LOG_KEY2));
            printWriter.println("\n\n-------------------------------------------------\n\n");
        } catch (Throwable tr2) {
            //最后回抛出异常，所以这里就可以不用处理了。
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
        mDefaultHandler.uncaughtException(thread, ex);
    }

    /**
     * 获取日志保存路径
     *
     * @param ctx
     * @return String  日志保存路径
     */
    public File getLogFilePath(Context ctx) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }

        String pathName = Environment.getExternalStorageDirectory().getPath()
                + "/Android/data/" + ctx.getPackageName()
                + "/files/error/"
                + android.os.Build.MODEL + "_"
                + PackageUtil.getVersionName(ctx) + simpleDateFormat.format(new Date()) + ".log";

        File path = new File(pathName);
        if (!path.getParentFile().exists()) {
            path.getParentFile().mkdirs();
        }

        return path;
    }

}
