package com.zxb.libsdemo.util;

import android.content.Context;

import com.zxb.libsdemo.util.file.clean.Cleaner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yufangyuan on 2018/3/16.
 */

public class Loggers {

    private List<Cleaner> cleaners;

    private Context mContext;

    public Loggers init(Context context) {
        this.mContext = context;
        return this;
    }

    public Loggers addCleaners(Cleaner cleaner) {
        if (cleaners == null) {
            cleaners = new ArrayList<>();
        }
        cleaners.add(cleaner);
        return this;
    }

    public void cleanCache() {
        if (cleaners != null && cleaners.size() > 0) {
            for (Cleaner item : cleaners) {
                item.clean(getCacheFiles());
            }
        }
    }

    private File[] getCacheFiles() {
        return null;
    }
}
