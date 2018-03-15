package com.zxb.libsdemo.listener;

/**
 * Created by yufangyuan on 2018/3/9.
 */

public interface MyNetListener {
    void onError(int type);
    void onSuccess(Object o);
}
