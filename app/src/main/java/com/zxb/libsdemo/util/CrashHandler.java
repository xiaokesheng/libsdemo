package com.zxb.libsdemo.util;

import android.content.Context;
import android.util.Log;

import com.zxb.libsdemo.util.file.LogType;
import com.zxb.libsdemo.util.file.StorageUtil;
import com.zxb.libsdemo.util.file.collector.CrashLogCollector;
import com.zxb.libsdemo.util.file.collector.DeviceLogCollector;
import com.zxb.libsdemo.util.file.collector.ErrorTraceCollector;
import com.zxb.libsdemo.util.file.collector.LogCollector;
import com.zxb.libsdemo.util.file.collector.PageTraceCollector;
import com.zxb.libsdemo.util.file.collector.UserInfoCollector;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

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

    private List<LogCollector> logCollectorList;

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

    public void initLogCollectors(List<LogCollector> collectorList) {
        this.logCollectorList = collectorList;
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
        File savePathFile = StorageUtil.getDiskCacheDir(mContext, "crash" + File.separator + "upload");
        if (savePathFile == null) {
            //抛出异常
            mDefaultHandler.uncaughtException(thread, ex);
            return;
        }
        StringBuilder crashMsg = new StringBuilder();
        try {
            crashMsg.append(new CrashLogCollector(mContext, thread, ex).getLog(mContext));
            crashMsg.append(new DeviceLogCollector().getLog(mContext));
            crashMsg.append(new PageTraceCollector().getLog(mContext));
            crashMsg.append(new UserInfoCollector().getLog(mContext));
            crashMsg.append(new ErrorTraceCollector().getLog(mContext));
            // TODO 其它
            crashMsg.append("\n\n-------------------------------------------------\n\n");
            LogFileUtil.writeToFile(mContext, crashMsg.toString(), LogType.CRASH);
        } catch (Throwable tr2) {
            //最后回抛出异常，所以这里就可以不用处理了。
        } finally {
        }
        mDefaultHandler.uncaughtException(thread, ex);
    }
}
