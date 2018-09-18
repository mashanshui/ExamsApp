package com.shenhesoft.examsapp.view;


import com.shenhesoft.examsapp.network.model.LoginModel;
import com.shenhesoft.examsapp.network.model.SecretKeyModel;
import com.shenhesoft.examsapp.present.LoginPresent;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * @author 马山水
 * @date 2018/3/30
 * @desc TODO
 */
public interface LoginView extends IView<LoginPresent> {
    String getPassword();

    String getUsername();

    void loginSuccess(LoginModel loginModel);

    void loginFail(String message);

    void onError();

    void dealSecretKey(SecretKeyModel secretKeyModel);

    boolean isShowLoadingDailog();
}
