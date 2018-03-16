package com.zxb.libsdemo.util.file.collector;

import android.content.Context;

import com.zxb.libsdemo.util.DeviceInfoUtil;

/**
 * Created by yufangyuan on 2018/3/16.
 */

public class DeviceLogCollector implements LogCollector {

    @Override
    public String getLog(Context context) {
        return DeviceInfoUtil.getDeviceInfo(context);
    }
}
