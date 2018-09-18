package com.shenhesoft.examsapp.view;


import com.shenhesoft.examsapp.present.RegisterPresent;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * @author mashanshui
 * @date 2018/4/11
 * @desc TODO
 */
public interface RegisterView extends IView<RegisterPresent> {
    String getUsername();

    String getPassword();

    String getConfirmPassword();

    String getPhone();

    String getVerifyCode();

    void showFailedErrorDialog();

    void dismissFailedErrorDialog();

    void toLoginActivity();

    void showCountdown();

    void showVerify();

    boolean getAgreementChecked();
}
