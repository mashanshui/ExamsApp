package com.shenhesoft.examsapp.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mashanshui
 * @date 2018/6/11
 * @desc TODO
 */
public class PatternUtil {

    private static Pattern Chiese_Pattern = Pattern.compile("[\u4e00-\u9fa5]");

    /**
     * 判断邮箱是否合法
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        return email.matches(strPattern);
    }


    /**
     * @param str
     * @return 判断是否全是数字
     */
    public static boolean isNumeric(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String strPattern = "[0-9]*";
        return str.matches(strPattern);
    }

    /**
     * @param phone
     * @return 判断手机号是否正确
     */
    public static boolean isPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        if (phone.length() != 11) {
            return false;
        }
        String strPattern = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
//        return phone.matches(strPattern);
        return true;
    }

    /**
     * @param str
     * @return 判断qq号是否正确
     */
    public static boolean isQQ(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String strPattern = "[1-9][0-9]{4,}";
        return str.matches(strPattern);
    }

    /**
     * @param str
     * @return 是否包含中文
     */
    public static boolean isContainChinese(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Matcher m = Chiese_Pattern.matcher(str);
        return m.find();
    }
}
