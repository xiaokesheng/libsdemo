package com.zxb.libsdemo.util.file.collector;

import android.content.Context;

import com.zxb.libsdemo.util.SPUtil;

/**
 * Created by yufangyuan on 2018/3/16.
 */

public class UserInfoCollector implements LogCollector {

    // TODO，业务类中实现

    @Override
    public String getLog(Context context) {
        StringBuilder result = new StringBuilder();
        result.append(SPUtil.getValue(context, SPUtil.SPNAME_LOG_MODULE, SPUtil.SPKEY_LOG_KEY1));
        result.append(SPUtil.getValue(context, SPUtil.SPNAME_LOG_MODULE, SPUtil.SPKEY_LOG_KEY2));
        return result.toString();
    }
}
