package com.zxb.libsdemo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.zxb.libsdemo.App;

/**
 * Created by yufangyuan on 2018/3/15.
 */

public class SPUtil {

    public static final String SPNAME_LOG_MODULE = "log_module";
    public static final String SPKEY_LOG_KEY1 = "log_key1";
    public static final String SPKEY_LOG_KEY2 = "log_key2";

    private static SharedPreferences getSharedPreferences(Context context, String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context, String name) {
        return getSharedPreferences(context, name).edit();
    }

    public static void clearSharedPreference(String name) {
        SharedPreferences.Editor editor = getEditor(App.getApp(), name);
        editor.clear().commit();
    }

    public static void putValue(Context context, String name, String key, String value) {
        SharedPreferences.Editor sp = getEditor(context, name);
        sp.putString(key, value);
        sp.commit();
    }

    public static String getValue(Context context, String name, String key) {
        SharedPreferences sp = getSharedPreferences(context, name);
        String value = sp.getString(key, "");
        return value;
    }

}
