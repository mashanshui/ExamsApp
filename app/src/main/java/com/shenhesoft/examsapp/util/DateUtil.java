package com.shenhesoft.examsapp.util;

import java.text.SimpleDateFormat;

/**
 * @author mashanshui
 * @date 2018/5/28
 * @desc TODO
 */
public class DateUtil {
    public static String MillisToDate(long time) {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateformat.format(time);
    }

    public static String TimeDifference(long startTime, long endTime) {
        long time = endTime - startTime;
        long hours = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (time % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (time % (1000 * 60)) / 1000;
        return hours + "时" + minutes + "分" + seconds + "秒";
    }
}
