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
 * @date 2018/5/30
 * @desc TODO
 */
public class ModifyRetrofit {
    private static volatile ModifyRetrofit mInstance;

    private ModifyRetrofit() {
    }

    public static ModifyRetrofit getInstance() {
        if (mInstance == null) {
            synchronized (ModifyRetrofit.class) {
                mInstance = new ModifyRetrofit();
            }
        }
        return mInstance;
    }

    /**
     * @param start
     * @param length
     * @return 已购作文批改
     */
    public Map<String, Object> getAlreadyBuyProducts(int start, int length) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "{\"studentUserId\":\"" + getUserID() + "\"}";
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
     * @param start
     * @param length
     * @return 推荐作文批改
     */
    public Map<String, Object> getRecommendProduct(int start, int length) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "{\"productType\":\"3\",\"recommend\":\"1\",\"userId\":\"" + getUserID() + "\",\"androidType\":\"1\"}";
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
     * @param start
     * @param length
     * @return 推荐作文批改——》筛选
     */
    public Map<String, Object> getFilterRecommendProduct(int start, int length, String searchType, String searchSource, String searchDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "{\"productType\":\"3\",\"recommend\":\"1\",\"userId\":\"" + getUserID() + "\",\"articleSource\":\"" + searchSource + "\",\"articleType\":\"" + searchType + "\",\"articleYear\":\"" + searchDate + "\"}";
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
     * @return 老师详情
     */
    public Map<String, Object> getTeacherDetail(String productId) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", 0);
        params.put("length", 20);
        String urlJson = "{\"productType\":\"3\",\"id\":\"" + productId + "\",\"userId\":\"" + getUserID() + "\"}";
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
     * @return 获取师生问答详情
     */
    public Map<String, Object> getAskDetail(int start, int length, String chatId) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "{\"chatId\":\"" + chatId + "\"}";
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
     * @return 师生问答发送消息
     */
    public Map<String, Object> sendMessage(String chatId, String message) {
        Map<String, Object> params = new HashMap<>();
        params.put("chatUserId", getUserID());
        params.put("chatId", chatId);
        params.put("chatContent", message);
        XLog.d(params.toString());
        return params;
    }

    /**
     * @return 获取师生交互详情
     */
    public Map<String, Object> getInteractiveDetail(int start, int length, String orderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "{\"orderId\":\"" + orderId + "\"}";
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
     * @return 上传作文
     */
    public Map<String, Object> uploadWriting(String orderId, String articleTitle, String articleContent, String scoreUserId) {
        Map<String, Object> params = new HashMap<>();
        params.put("authorUserId", getUserID());
        params.put("orderId", orderId);
        params.put("articleTitle", articleTitle);
        params.put("articleContent", articleContent);
        params.put("scoreUserId", scoreUserId);
        params.put("status", 1);
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
