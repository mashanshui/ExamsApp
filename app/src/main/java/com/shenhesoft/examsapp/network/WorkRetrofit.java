package com.shenhesoft.examsapp.network;

import android.text.TextUtils;

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
 * @date 2018/5/22
 * @desc TODO
 */
public class WorkRetrofit {
    private static volatile WorkRetrofit mInstance;

    private WorkRetrofit() {
    }

    public static WorkRetrofit getInstance() {
        if (mInstance == null) {
            synchronized (WorkRetrofit.class) {
                mInstance = new WorkRetrofit();
            }
        }
        return mInstance;
    }

    /**
     * @param start
     * @param length
     * @param productType
     * @return 已购题库
     */
    public Map<String, Object> getAlreadyBuyProducts(int start, int length, int productType, int status) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "{\"productType\":\"" + productType + "\", \"createUserId\":\"" + getUserID() + "\",\"status\":\"" + status + "\"}";
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
     * @return 商品列表
     * sort:排序码 0按销量降序1按销量升序2价格降序3价格升序 null综合排序
     */
    public Map<String, Object> getProductsList(int start, int length, String sort) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "{\"sort\":\"" + sort + "\",\"status\":\"4\",\"userId\":\"" + getUserID() + "\"}";
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
     * @return 主页的商品列表，按学术领域区分
     */
    public Map<String, Object> getHomeProductsList(int start, int length, int productType, String scienceDomainId) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "{\"productType\":\"" + productType + "\",\"status\":\"4\",\"userId\":\"" + getUserID() + "\",\"androidType\":\"1\",\"scienceDomainId\":\"" + scienceDomainId + "\"}";
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
     * @return 知识点(第一次获取 ， 通过pid ）
     */
    public Map<String, Object> getKnowledgeFirst(int start, int length, String pid) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson;
        urlJson = "{\"status\":\"1\",\"pid\":\"" + pid + "\"}";
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
     * @return 知识点（第一次后的获取，通过knowledgePointCode）
     */
    public Map<String, Object> getKnowledge(int start, int length, String knowledgePointCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson;
        urlJson = "{\"status\":\"1\",\"knowledgePointCode\":\"" + knowledgePointCode + "\"}";
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
     * @return 学术领域
     */
    public Map<String, Object> getScienceDomains(int start, int length) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson;
        urlJson = "{\"status\":\"1\"}";
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
     * @return 按知识点查看题目
     */
    public Map<String, Object> getBankByKnowledge(int start, int length, String knowledgePointId) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson;
        urlJson = "{\"userId\":\"" + getUserID() + "\",\"knowledgePointId\":\"" + knowledgePointId + "\"}";
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
     * @param productId
     * @return 试卷列表
     */
    public Map<String, Object> getTestPapersByProductId(int start, int length, String productId, int status) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "{\"productId\":\"" + productId + "\",\"userId\":\"" + getUserID() + "\",\"status\":\"" + status + "\"}";
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
     * @param status
     * @return 做题预览，默认获取十条数据，肯定没有十条
     */
    public Map<String, Object> getAnswerPolicys(int status) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", 0);
        params.put("length", 10);
        String urlJson = "{\"status\":\"" + status + "\"}";
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
     * @param status
     * @return 获取题目列表
     */
    public Map<String, Object> getAnalysisQuestions(int start, int length, String answerId, int wrongOrAll, int status) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson;
        if (wrongOrAll == AppConstant.AllQuestion) {
            urlJson = "{\"answerId\":\"" + answerId + "\",\"userId\":\"" + getUserID() + "\",\"status\":\"" + status + "\"}";
        } else {
            urlJson = "{\"answerId\":\"" + answerId + "\",\"userId\":\"" + getUserID() + "\",\"status\":\"" + status + "\",\"answerErrored\":\"1\"}";
        }
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
     * @param testPaperId
     * @param answerPolicyName
     * @param status
     * @return 获取题目列表
     */
    public Map<String, Object> getHomeWorkQuestions(int start, int length, int answerType, String testPaperId, String answerPolicyName, int status) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "";
        if (!TextUtils.isEmpty(answerPolicyName)) {
            if (AppConstant.AnswerPolicy3.equals(answerPolicyName)) {
                urlJson = "{\"createUserId\":\"" + getUserID() + "\",\"scienceDomainId\":\"" + testPaperId + "\",\"status\":\"" + status + "\",\"collectStatus\":\"1\"}";
            } else if (AppConstant.AnswerPolicy4.equals(answerPolicyName)) {
                urlJson = "{\"answerUserId\":\"" + getUserID() + "\",\"scienceDomainId\":\"" + testPaperId + "\",\"answerStatus\":\"1\",\"answerErrored\":\"1\"}";
            }
        } else {
            if (answerType == AppConstant.DoHomeWork) {
                urlJson = "{\"userId\":\"" + getUserID() + "\",\"testPaperId\":\"" + testPaperId + "\",\"status\":\"" + status + "\",\"finished\":\"1\",\"sign\":\"1\"}";
                //按试卷做题时每次获取都是从0开始
                params.put("start", 0);
            } else {
                urlJson = "{\"userId\":\"" + getUserID() + "\",\"testPaperId\":\"" + testPaperId + "\",\"status\":\"" + status + "\"}";
            }
        }
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
     * @param answerId
     * @param questionId
     * @param answerErrored
     * @param finished
     * @return 下一题
     */
    public Map<String, Object> nextSubject(String answerId, String questionId, int answerErrored, int finished, String answerPolicyName) {
        Map<String, Object> params = new HashMap<>();
        params.put("createUserId", getUserID());
        params.put("answerId", answerId);
        params.put("questionId", questionId);
        params.put("answerErrored", answerErrored);
        params.put("finished", finished);
        if (AppConstant.AnswerPolicy4.equals(answerPolicyName)) {
            params.put("status", 0);
        } else {
            params.put("status", 1);
        }
        XLog.d(params.toString());
        return params;
    }

    /**
     * @param answerId
     * @param questionId
     * @param answerErrored
     * @param finished
     * @return 二次提交, 下一题
     */
    public Map<String, Object> replyNextSubject(String answerId, String questionId, int answerErrored, int finished) {
        Map<String, Object> params = new HashMap<>();
        params.put("createUserId", getUserID());
        params.put("answerId", answerId);
        params.put("questionId", questionId);
        params.put("answerErrored", answerErrored);
        params.put("finished", finished);
        params.put("status", 1);
        XLog.d(params.toString());
        return params;
    }

    /**
     * @param answerPolicy
     * @return 开始做题
     */
    public Map<String, Object> startAnswer(String answerPolicy) {
        Map<String, Object> params = new HashMap<>();
        params.put("answerPolicy", answerPolicy);
        params.put("studentUserId", getUserID());
        XLog.d(params.toString());
        return params;
    }

    /**
     * @return 结束做题
     */
    public Map<String, Object> finishAnswer(String answerId, String answerSchedule, String answerQuestionNum, String answerTimeStart, String answerTimeEnd, String usedTime, String answerPolicyName) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", answerId);
        params.put("answerSchedule", answerSchedule);
        params.put("answerQuestionNum", answerQuestionNum);
        params.put("answerTimeStart", answerTimeStart);
        params.put("answerTimeEnd", answerTimeEnd);
        params.put("usedTime", usedTime);
        if (AppConstant.AnswerPolicy4.equals(answerPolicyName)) {
            params.put("status", 0);
        } else {
            params.put("status", 1);
        }
        XLog.d(params.toString());
        return params;
    }

    /**
     * @return 题库、课件、作文搜索
     */
    public Map<String, Object> searchQuestionBank(int start, int length, int productType, String searchKey) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "{\"productType\":\"" + productType + "\",\"keyword\":\"" + searchKey + "\",\"userId\":\"" + getUserID() + "\"}";
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
     * @return 作文搜索(条件搜索)
     */
    public Map<String, Object> searchWritingByCondition(int start, int length, int productType, String searchKey, String searchType, String searchSource, String searchDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson;
        if (productType == 3) {
            urlJson = "{\"productType\":\"" + productType + "\",\"keyword\":\"" + searchKey + "\",\"userId\":\"" + getUserID() + "\",\"articleSource\":\"" + searchSource + "\",\"articleType\":\"" + searchType + "\",\"articleYear\":\"" + searchDate + "\"}";
        } else {
            urlJson = "{\"productType\":\"" + productType + "\",\"keyword\":\"" + searchKey + "\",\"userId\":\"" + getUserID() + "\"}";
        }
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
     * @return 题目搜索
     */
    public Map<String, Object> searchSubjectBank(int start, int length, String searchKey) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "{\"keyword\":\"" + searchKey + "\"}";
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
     * @return 收藏题目
     */
    public Map<String, Object> collectAnswer(String linkId) {
        Map<String, Object> params = new HashMap<>();
        params.put("createUserId", getUserID());
        params.put("linkId", linkId);
        XLog.d(params.toString());
        return params;
    }

    /**
     * @return 取消收藏题目
     */
    public Map<String, Object> cancelCollect(String linkId) {
        Map<String, Object> params = new HashMap<>();
        params.put("createUserId", getUserID());
        params.put("status", 0);
        params.put("linkId", linkId);
        XLog.d(params.toString());
        return params;
    }

    /**
     * @return 移除错题
     */
    public Map<String, Object> removeQuestion(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("createUserId", getUserID());
        params.put("status", 1);
        params.put("questionId", id);
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
