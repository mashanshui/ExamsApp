package com.shenhesoft.examsapp.util;

import java.text.NumberFormat;

/**
 * @author mashanshui
 * @date 2018/5/28
 * @desc TODO
 */
public class MathUtil {

    public static String getStringPercent(int x, int total) {
        String result = "";//接受百分比的值
        double x1 = x * 1.0;
        double tempresult = x1 / total;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(0);  //精确多少位
        result = nf.format(tempresult);
        return result;
    }

    public static int getIntPercent(int x, int total) {
        double x1 = x * 1.0;
        double tempresult = (x1 / total) * 100;
        return (int) tempresult;
    }
}
