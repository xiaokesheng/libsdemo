package com.zxb.libsdemo.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by yufangyuan on 2018/3/15.
 */

public class PackageUtil {

    /**
     * 获取应用版本号
     *
     * @param context
     * @return String  应用版本号
     */
    public static String getVersionName(Context context) {
        String version = "";
        if (context == null) {
            return version;
        }
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            if (packageManager != null) {
                PackageInfo packInfo;
                packInfo = packageManager.getPackageInfo(context.getPackageName(),
                        0);
                if (packInfo != null) {
                    version = packInfo.versionName;
                }
            }
        } catch (Exception e) {
        }
        return version;
    }

    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
