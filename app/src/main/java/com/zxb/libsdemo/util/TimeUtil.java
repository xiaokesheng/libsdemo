package com.zxb.libsdemo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yufangyuan on 2018/3/15.
 */

public class TimeUtil {

    public static String getTimeInHHMMSS() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
    }

    /**
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getTimeInYyyyMMddHHmmss() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH) + " " +
                cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
    }

    /**
     * 计算两个时间的秒之差
     *
     * @param first  yyyy-MM-dd HH:mm:ss
     * @param second yyyy-MM-dd HH:mm:ss
     * @return 如果 first 大，返回正值
     * 如果 first 小，返回负值
     */
    public static int getSecondDelta(String first, String second) {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int between = 0;
        try {
            Date firstTime = dfs.parse(first);
            Date secondTime = dfs.parse(second);
            between = ((int) (firstTime.getTime() - secondTime.getTime())) / 1000;
            return between;
        } catch (ParseException e) {
            e.printStackTrace();
            return Integer.MAX_VALUE;
        }
    }

    /**
     * 计算 first 和当前时间的差值
     * @param first
     * @return
     */
    public static int getSecondDelta(String first) {
        return getSecondDelta(first, getTimeInYyyyMMddHHmmss());
    }
}
