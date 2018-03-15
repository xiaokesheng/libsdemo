package com.zxb.libsdemo.util;

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
}
