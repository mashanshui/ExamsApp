package com.shenhesoft.examsapp.view;

import com.shenhesoft.examsapp.present.ForgetPswPresent;
import com.shenhesoft.examsapp.util.view.BaseView;

/**
 * @author mashanshui
 * @date 2018/5/4
 * @desc TODO
 */
public interface ForgetPswView extends BaseView<ForgetPswPresent> {

    String getPhone();

    String getVerifyCode();

    String getPassword();

    String getReplyPassword();

    void showCountdown();
}
