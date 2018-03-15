package com.zxb.libsdemo.util;

import android.content.Context;
import android.os.Build;

/**
 * Created by yufangyuan on 2018/3/15.
 */

public class DeviceInfoUtil {

    public static String getDeviceInfo(Context context) {
        StringBuilder result = new StringBuilder();
        result.append("设备型号: ").append(Build.MODEL).append("\r\n");
        result.append("手机厂商: ").append(Build.BRAND).append("\r\n");
        result.append("API版本: ").append(Build.VERSION.SDK).append("\r\n");
        result.append("系统版本: ").append(Build.VERSION.RELEASE).append("\r\n");
        // TODO Rom版本
        result.append("APP版本: ").append(PackageUtil.getVersionName(context)).append("\r\n");
        result.append("网络状态: ").append(NetworkUtil.getAPNTypeStr(context)).append("\r\n");
        result.append("GPS状态: ").append(NetworkUtil.isGPSEnabled(context) ? "开启" : "关闭").append("\r\n");
        return result.toString();
    }


}
