package com.zxb.libsdemo.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.zxb.libsdemo.util.file.LogType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mrzhou on 2017/4/12.
 */
public class J {

    private static Context mContext;

    /**
     * 格式 yyyy-MM-dd hh:mm:ss
     */
    public static String lastTime;

    private static String TAG = "J";
    private static String logPath = null;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);//日期格式;
    private static Date date = new Date();//因为log日志是使用日期命名的，使用静态成员变量主要是为了在整个程序运行期间只存在一个.log文件中;

    public static void init(Context context) {
        mContext = context;
        logPath = getFilePath(context) + "/Logs";//获得文件储存路径,在后面加"/Logs"建立子文件夹
    }

    /**
     * 获得文件存储路径
     *
     * @return
     */
    private static String getFilePath(Context context) {

        if (Environment.MEDIA_MOUNTED.equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()) {//如果外部储存可用
            return context.getExternalFilesDir(null).getPath();//获得外部存储路径,默认路径为 /storage/emulated/0/Android/data/com.waka.workspace.logtofile/files/Logs/log_2016-03-14_16-15-09.log
        } else {
            return context.getFilesDir().getPath();//直接存在/data/data里，非root手机是看不到的
        }
    }

    public static void j(String tag, String str) {
        Log.e(tag, str);
        StringBuilder log = new StringBuilder();
        String currentTime = TimeUtil.getTimeInYyyyMMddHHmmss();;
        if (!TextUtils.isEmpty(lastTime) && lastTime.equals(currentTime)) {
        } else {
            lastTime = currentTime;
            log.append("TIME:::").append(lastTime).append("\r\n");
        }
        log.append(str + "\r\n");
        LogFileUtil.writeToFile(mContext, log.toString(), LogType.ERROR);
    }

    public static void j(float[] pts) {
        for (float i : pts) {
            Log.e("Points_Log", String.valueOf(i));
        }
    }
}
