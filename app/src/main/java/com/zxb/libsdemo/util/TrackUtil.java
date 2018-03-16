package com.zxb.libsdemo.util;

import android.content.Context;
import android.text.TextUtils;

import com.zxb.libsdemo.util.file.LogType;

/**
 * Created by yufangyuan on 2018/3/15.
 */

public class TrackUtil {

    /**
     * 格式 hh:mm:ss
     */
    public String lastTime;

    private volatile static TrackUtil instance;

    private TrackUtil() {}

    public static TrackUtil getInstance() {
        if (instance == null) {
            synchronized (TrackUtil.class) {
                if (instance == null) {
                    instance = new TrackUtil();
                }
            }
        }
        return instance;
    }

    public void trackPage(Context context, String page, String action) {
        String log = getLog(page, action);
        LogFileUtil.writeToFile(context, log, LogType.TRACE);
    }

    private String getLog(String page, String action) {
        StringBuilder log = new StringBuilder();
        String currentTime = getCurrentLogTime();
        if (!TextUtils.isEmpty(lastTime) && lastTime.equals(currentTime)) {
        } else {
            lastTime = currentTime;
            log.append("TIME:::").append(lastTime).append("\r\n");
        }
        log.append(page).append("-->").append(action).append("\r\n");
        return log.toString();
    }

    /**
     * @return hh:mm::ss
     */
    private String getCurrentLogTime() {
        return TimeUtil.getTimeInYyyyMMddHHmmss();
    }
}
