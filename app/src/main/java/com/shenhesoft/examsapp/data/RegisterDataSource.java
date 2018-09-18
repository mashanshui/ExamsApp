package com.shenhesoft.examsapp.data;

/**
 * @author mashanshui
 * @date 2018/5/7
 * @desc TODO
 */
public interface RegisterDataSource {
    interface RegisterCallback {
        void onSuccess();

        void onFail(String msg);
    }

    interface GetVerifyCodeCallback {
        void onSuccess(String verifyCode);

        void onFail(String msg);
    }

    interface ForgetPswCallback {
        void onSuccess();

        void onFail(String msg);
    }

    void register(String phone, String account, String password, String replyPassword, String verify, RegisterCallback callback);

    void forgetPsw(String phone, String verify, String password, String replyPassword, ForgetPswCallback callback);

    void getVerifyCode(String phone, GetVerifyCodeCallback callback);
}
