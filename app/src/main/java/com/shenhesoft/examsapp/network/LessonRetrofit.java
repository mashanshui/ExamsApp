package com.shenhesoft.examsapp.network;

import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.MyApplication;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.log.XLog;

/**
 * @author mashanshui
 * @date 2018/6/4
 * @desc TODO
 */
public class LessonRetrofit {
    private static volatile LessonRetrofit mInstance;

    private LessonRetrofit() {
    }

    public static LessonRetrofit getInstance() {
        if (mInstance == null) {
            synchronized (LessonRetrofit.class) {
                mInstance = new LessonRetrofit();
            }
        }
        return mInstance;
    }

    /**
     * @return 听课资料列表
     */
    public Map<String, Object> getLessonsPdf(int start, int length) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "{\"UserId\":\"" + getUserID() + "\"}";
        String content = "";
        try {
            content = URLEncoder.encode(urlJson, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.put("condition", content);
        XLog.d(urlJson);
        return params;
    }

    /**
     * @return 单个听课资料列表
     */
    public Map<String, Object> getSingleLessonsPdf(int start, int length, String productId) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "{\"id\":\"" + productId + "\"}";
        String content = "";
        try {
            content = URLEncoder.encode(urlJson, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.put("condition", content);
        XLog.d(urlJson);
        return params;
    }

    /**
     * @return 听课视频列表
     */
    public Map<String, Object> getLessons(int start, int length) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "{\"UserId\":\"" + getUserID() + "\"}";
        String content = "";
        try {
            content = URLEncoder.encode(urlJson, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.put("condition", content);
        XLog.d(urlJson);
        return params;
    }

    /**
     * @param productId
     * @return 单个听课视频列表
     */
    public Map<String, Object> getSingleLessons(String productId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", productId);
        XLog.d(params.toString());
        return params;
    }

    /**
     * @return 已购买课件
     */
    public Map<String, Object> getAlreadyBuyCourse(int start, int length) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "{\"productType\":\"2\", \"createUserId\":\"" + getUserID() + "\",\"status\":\"1\"}";
        String content = "";
        try {
            content = URLEncoder.encode(urlJson, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.put("condition", content);
        XLog.d(urlJson);
        return params;
    }


    /**
     * @return 已购买课件
     */
    public Map<String, Object> videoStatics(String videoId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", getUserID());
        params.put("videoId", videoId);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    private String getUserID() {
        return SharedPref.getInstance(MyApplication.context).getString(AppConstant.UserId, "");
    }
}
