package com.zxb.libsdemo.util;

import android.content.Context;

import com.zxb.libsdemo.util.file.LogType;
import com.zxb.libsdemo.util.file.StorageUtil;
import com.zxb.libsdemo.util.file.clean.Cleaner;
import com.zxb.libsdemo.util.file.clean.LRUCleaner;
import com.zxb.libsdemo.util.file.clean.SingleFileCleaner;
import com.zxb.libsdemo.util.file.clean.TimeCleaner;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yufangyuan on 2018/3/16.
 */

public class Loggers {

    private List<Cleaner> cleaners;

    private Context mContext;

    public static Loggers instance;

    public static Loggers getInstance() {
        if (instance == null) {
            synchronized (Loggers.class) {
                if (instance == null) {
                    instance = new Loggers();
                }
            }
        }
        return instance;
    }

    public Loggers init(Context context) {
        this.mContext = context;
        initCleaners();
        CrashHandler.getInstance().init(context);
        return this;
    }

    private void initCleaners() {
        if (cleaners == null) {
            cleaners = new ArrayList<>();
        }
    }

    public Loggers addCleaners(Cleaner cleaner) {
        cleaners.add(cleaner);
        return this;
    }

    public void cleanCacheFiles() {
        if (cleaners != null && cleaners.size() > 0) {
        } else {
            cleaners.add(new TimeCleaner());
            cleaners.add(new SingleFileCleaner());
            cleaners.add(new LRUCleaner());
        }
        for (Cleaner item : cleaners) {
            item.clean(getCacheFiles());
        }
    }

    public void clearAllCacheFiles() {
        File[] file = getCacheFiles();
        for (File item : file) {
            item.delete();
        }
    }

    private File[] getCacheFiles() {
        List<File> fileList = new ArrayList<>();
        addFilesWithLogType(fileList, LogType.WARNING);
        addFilesWithLogType(fileList, LogType.ERROR);
        addFilesWithLogType(fileList, LogType.TRACE);
        addFilesWithLogType(fileList, LogType.CRASH);
        File[] array = new File[fileList.size()];
        fileList.toArray(array);
        return array;
    }

    private void addFilesWithLogType(List<File> fileList, String logType) {
        File directory = StorageUtil.getDiskCacheDir(mContext, logType);
        if (directory != null && directory.exists()) {
            fileList.addAll(Arrays.asList(directory.listFiles()));
        }
    }
}
