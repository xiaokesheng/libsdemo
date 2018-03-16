package com.zxb.libsdemo.util.file;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by yufangyuan on 2018/3/16.
 */

public class StorageUtil {

    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
