package com.zxb.libsdemo.util;

import android.os.Environment;

import com.zxb.libsdemo.model.FileVideoDirectory;

import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mrzhou on 16/5/28.
 */
public class MediaUtil {

    /**
     * 默认的媒体文件扩展名
     */
    public final static String[] MEDIA_EXTENSIONS = {
            "mp4", "avi", "mkv", "3gp", "flv", "asf",
            "mov", "mpeg", "mpg", "rmvb", "swf", "wmv"
    };

    public final static String mediaRegularExp = "(mp4|avi|mkv|3gp|flv|asf|mov|mpeg|mpg|rmvb|swf|wmv)";

    /**
     * 目录黑名单，在扫描时将会跳过这些目录
     */
    public final static HashSet<String> FOLDER_BLACKLIST;

    /**
     * 关键词黑名单，包含这些关键词的目录会被跳过
     */
    public final static HashSet<String> KEYWORD_BLACKLIST;

    static {
        final String[] folder_blacklist = {
                "/Alarms",
                "/Notifications",
                "/Ringtones",
                "/media/alarms",
                "/media/notifications",
                "/media/ringtones",
                "/media/audio/alarms",
                "/media/audio/notifications",
                "/media/audio/ringtones",
                "/system",
                "/Android/media",
                "/360",
                "/360Download",
                "/baidu",
                "/BaiduMap",
                "/Amap",
                "/Qmap",
                "/Tencent",
                "/alipay",
                "/zuimei",
                "/TitaniumBackup",
                "/WhatsApp",
        };

        final String[] keyword_blacklist = {
                "Cache",
                "cache",
                "Music",
                "music",
                "image",
                "Image",
                "img",
                "Book",
                "book",
                "Font",
                "font",
                "Log",
                "log",
                "SDK",
                "Sdk",
                "sdk",
                "Temp",
                "temp",
                "tmp"
        };

        FOLDER_BLACKLIST = new HashSet<>();
        for (String item : folder_blacklist)
            FOLDER_BLACKLIST.add(Environment.getExternalStorageDirectory().getPath() + item);

        KEYWORD_BLACKLIST = new HashSet<>(Arrays.asList(keyword_blacklist));
    }


    public static IOFileFilter getExtensionFilter(final String[] extensions) {
        return new IOFileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) return !shouldSkip(file);
                else return isType(file, extensions);
            }

            @Override
            public boolean accept(File dir, String name) {
                if (dir.isDirectory()) return !shouldSkip(dir);
                else return isType(dir, extensions);
            }
        };
    }

    /**
     * 使用多种策略判断扫描时是否应该跳过指定目录
     */
    private static boolean shouldSkip(File dir) {
        if (dir.isDirectory()) {
            if (FOLDER_BLACKLIST.contains(dir.getPath()) || // 过滤黑名单目录
                    dir.getName().startsWith(".") || // 过滤.开头的目录（Unix 隐藏目录）
                    containKeyword(dir, KEYWORD_BLACKLIST) || // 过滤黑名单关键词
                    new File(dir.getPath() + "/.nomedia").exists() // 过滤包含.nomedia文件的目录
                    ) {

                return true;
            }
        }
        return false;
    }


    private static boolean containKeyword(File file, HashSet<String> keywords) {
        String name = file.getName();
        for (String keyword : keywords) {
            if (name.contains(keyword)) return true;
        }
        return false;
    }

    private static String[] toSuffixes(String[] extensions) {
        String[] suffixes = new String[extensions.length];
        for (int i = 0; i < extensions.length; i++) {
            suffixes[i] = "." + extensions[i];
        }
        return suffixes;
    }

    /**
     * 判断文件是否以给定扩展名结尾
     *
     * @param file       文件
     * @param extensions 扩展名列表
     */
    public static boolean isType(File file, String[] extensions) {
        String name = file.getName();

        for (String ext : toSuffixes(extensions)) {
            if (name.endsWith(ext)) {
                return true;
            }
        }

        return false;
    }

    public static List<FileVideoDirectory> getFileVideoList(List<FileVideoDirectory> list, File dirPath) {
        if (list == null) {
            list = new ArrayList<>();
        }

        int number = getVideoNumber(dirPath);
        if (number > 0) {
            list.add(new FileVideoDirectory(dirPath.getPath(), number));
        }

        File[] files = dirPath.listFiles();


        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    getFileVideoList(list, file);
                }
            }
        }

        return list;
    }

    public static int getVideoNumber(File dirPath) {
        int number = 0;
        if (null != dirPath) {
            File[] files = dirPath.listFiles();
            if (null != files && files.length > 0) {
                for (File file : files) {
                    if (!file.isDirectory() && isType(file, MEDIA_EXTENSIONS)) {
                        number++;
                    }
                }
            }
        }
        return number;
    }

    public boolean isVideo(String fileName) {

//        if (null != fileName && fileName.length() > 0) {
//            String[] fileNameStrs = fileName.split("")
//            Pattern pattern = Pattern.compile(mediaRegularExp);
//            Matcher m = pattern.matcher(fileNa);
//            return m.matches();
//        }
        return false;
    }
}
