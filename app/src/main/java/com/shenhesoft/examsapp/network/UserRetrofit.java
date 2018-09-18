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

public class UserRetrofit {
    private static volatile UserRetrofit mInstance;

    private UserRetrofit() {
    }

    public static UserRetrofit getInstance() {
        if (mInstance == null) {
            synchronized (UserRetrofit.class) {
                mInstance = new UserRetrofit();
            }
        }
        return mInstance;
    }

    /**
     * 发送验证码
     *
     * @param phone 手机号
     * @return
     */
    public Map<String, Object> getVerifyCode(String phone) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 注册
     *
     * @param phone
     * @param account
     * @param password
     * @param replyPassword
     * @param verify
     * @return
     */
    public Map<String, Object> register(String phone, String account, String password, String replyPassword, String verify) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("passwd", password);
        params.put("passwdAgain", replyPassword);
        params.put("account", account);
        params.put("checkCode", verify);
        XLog.d(params.toString());
        return params;
    }

    /**
     * 忘记密码
     *
     * @return
     */
    public Map<String, Object> forgetPsw(String phone, String verify, String password, String replyPassword) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("passwd", password);
        params.put("passwdAgain", replyPassword);
        params.put("checkCode", verify);
        XLog.d(params.toString());
        return params;
    }

    /**
     * @param account
     * @param password
     * @return 登录
     */
    public Map<String, Object> login(String account, String password, String sign) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", account);
        params.put("password", password);
        params.put("sessionId", sign);
        XLog.d(params.toString());
        return params;
    }

    /**
     * @return 获取赠送金币区间
     */
    public Map<String, Object> getGoldSection(int start, int length) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "{\"status\":\"1\"}";
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
     * @return 充值
     */
    public Map<String, Object> recharge(String payMoney, int tradeType, String goldDiscountPolicyId, int payWay) {
        Map<String, Object> params = new HashMap<>();
        params.put("payMoney", payMoney);
        params.put("tradeType", tradeType);
        params.put("goldDiscountPolicyId", goldDiscountPolicyId);
        params.put("remark", payWay);
        params.put("studentUserId", getUserID());
        XLog.d(params.toString());
        return params;
    }

    /**
     * @return 准备微信支付
     */
    public Map<String, Object> prepareWechatPay(String orderId, String price) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("price", price);
        params.put("tradeType", "APP");
        XLog.d(params.toString());
        return params;
    }

    /**
     * @return 投诉建议管理列表
     */
    public Map<String, Object> getAdviceList(int start, int length) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "{\"applyUserId\":\"" + getUserID() + "\"}";
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
     * @return 新增建议管理
     */
    public Map<String, Object> addAdvice(String type, String title, String orderCode, String content, String contactType, String contactMethod) {
        Map<String, Object> params = new HashMap<>();
        if (TextUtils.equals("投诉", type)) {
            params.put("optType", 1);
        } else {
            params.put("optType", 0);
        }
        if (TextUtils.equals("手机", contactType)) {
            params.put("concatType", 1);
        } else if (TextUtils.equals("邮箱", contactType)) {
            params.put("concatType", 2);
        } else {
            params.put("concatType", 3);
        }
        params.put("title", title);
        params.put("applyContent", content);
        params.put("complainOrder", orderCode);
        params.put("applyConcat", contactMethod);
        params.put("applyUserId", getUserID());
        XLog.d(params.toString());
        return params;
    }

    /**
     * @return 我的成就
     */
    public Map<String, Object> getMyAchieveList() {
        Map<String, Object> params = new HashMap<>();
        params.put("studentUserId", getUserID());
        XLog.d(params.toString());
        return params;
    }

    /**
     * @return 查看评价
     */
    public Map<String, Object> lookEvaluate(int start, int length, String productId, String scoreType) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson;
        if (TextUtils.isEmpty(scoreType)) {
            urlJson = "{\"studentUserId\":\"" + getUserID() + "\",\"productId\":\"" + productId + "\"}";
        } else {
            urlJson = "{\"studentUserId\":\"" + getUserID() + "\",\"productId\":\"" + productId + "\",\"scoreType\":\"" + scoreType + "\"}";
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
     * @return 查看是否评价
     */
    public Map<String, Object> isEvaluate(int start, int length, String productId) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "{\"productId\":\"" + productId + "\",\"studentUserId\":\"" + getUserID() + "\"}";
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
     * @return 填写评价
     */
    public Map<String, Object> writeEvaluate(String productId, int scoreValue, String scoreContent) {
        Map<String, Object> params = new HashMap<>();
        params.put("studentUserId", getUserID());
        params.put("productId", productId);
        params.put("scoreValue", scoreValue);
        params.put("scoreContent", scoreContent);
        XLog.d(params.toString());
        return params;
    }


    /**
     * @return 钱包金币余额等信息
     */
    public Map<String, Object> myWalletMessage() {
        Map<String, Object> params = new HashMap<>();
        params.put("start", 0);
        params.put("length", 100);
        String urlJson = "{\"id\":\"" + getUserID() + "\"}";
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
     * @return 钱包消费记录
     */
    public Map<String, Object> expenseCalendar(int start, int length) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "{\"studentUserId\":\"" + getUserID() + "\",\"status\":\"1\"}";
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
     * @return 购买产品
     */
    public Map<String, Object> buy(String password, String productId, String addressId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", getUserID());
        params.put("payPwd", password);
        params.put("productId", productId);
        params.put("contactWayId", addressId);
        XLog.d(params.toString());
        return params;
    }

    /**
     * @return 设置或修改支付密码
     */
    public Map<String, Object> changePayPsw(String loginPsw, String payPsw) {
        Map<String, Object> params = new HashMap<>();
        params.put("payChkcode", loginPsw);
        params.put("payPasswd", payPsw);
        params.put("status", 1);
        params.put("userId", getUserID());
        XLog.d(params.toString());
        return params;
    }

    /**
     * @param oldPsw
     * @param newPsw
     * @param reNewPsw
     * @return 修改登录密码
     */
    public Map<String, Object> changePsw(String oldPsw, String newPsw, String reNewPsw) {
        Map<String, Object> params = new HashMap<>();
        params.put("passwdOld", oldPsw);
        params.put("passwd", newPsw);
        params.put("passwdAgain", reNewPsw);
        params.put("userId", getUserID());
        XLog.d(params.toString());
        return params;
    }

    /**
     * @return 修改个人资料
     */
    public Map<String, Object> modifyMessage(int type, String message) {
        Map<String, Object> params = new HashMap<>();
        switch (type) {
            case 1:
                params.put("name", message);
                break;
            case 2:
                params.put("sex", message);
                break;
            case 3:
                params.put("age", message);
                break;
            case 4:
                params.put("email", message);
                break;
            default:
                break;
        }
        params.put("id", getUserID());
        XLog.d(params.toString());
        return params;
    }

    /**
     * @return 地址管理查看地址
     */
    public Map<String, Object> lookAddress(int start, int length) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "{\"userId\":\"" + getUserID() + "\",\"status\":\"1\"}";
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
     * @param name
     * @param phone
     * @param address
     * @param isDefault
     * @return 地址管理新增地址
     */
    public Map<String, Object> addAddress(String name, String phone, String address, int isDefault) {
        Map<String, Object> params = new HashMap<>();
        params.put("receiverName", name);
        params.put("contactWay", phone);
        params.put("address", address);
        params.put("defaultFlag", isDefault);
        params.put("status", 1);
        params.put("title", "Android端没有该字段");
        params.put("userId", getUserID());
        XLog.d(params.toString());
        return params;
    }

    /**
     * @param name
     * @param phone
     * @param address
     * @param isDefault
     * @return 修改地址
     */
    public Map<String, Object> modifyAddress(String id, String name, String phone, String address, int isDefault) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("receiverName", name);
        params.put("contactWay", phone);
        params.put("address", address);
        params.put("defaultFlag", isDefault);
        params.put("title", "");
        params.put("userId", getUserID());
        XLog.d(params.toString());
        return params;
    }

    /**
     * @return 删除地址
     */
    public Map<String, Object> deleteAddress(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("status", 0);
        params.put("id", id);
        params.put("userId", getUserID());
        XLog.d(params.toString());
        return params;
    }

    /**
     * @return 修改为默认地址
     */
    public Map<String, Object> defaultAddress(String id, int isDefault) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("defaultFlag", isDefault);
        params.put("userId", getUserID());
        XLog.d(params.toString());
        return params;
    }

    /**
     * @return 获取消息推送历史列表
     */
    public Map<String, Object> getPushMessage(int start, int length) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        String urlJson = "{\"receiver\":\"" + getUserID() + "\"}";
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
     * @return 商品详情
     */
    public Map<String, Object> getProductDetail(String productNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", productNo);
        params.put("userId", getUserID());
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
