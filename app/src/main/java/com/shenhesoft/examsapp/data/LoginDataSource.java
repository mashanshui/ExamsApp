package com.shenhesoft.examsapp.data;

import com.shenhesoft.examsapp.network.model.LoginModel;
import com.shenhesoft.examsapp.network.model.SecretKeyModel;

public interface LoginDataSource {
    interface LoginCallback {
        void onSuccess(LoginModel loginModel);

        void onFail(String msg);

        void onError();
    }

    void login(boolean isShowLoadingDialog, String account, String password, String sign, LoginCallback loginCallback);

    interface SecretKeyCallback {
        void onSuccess(SecretKeyModel secretKeyModel);

        void onFail(String msg);

        void onError();
    }

    void getSecretKey(boolean isShowLoadingDialog, SecretKeyCallback callback);
}
