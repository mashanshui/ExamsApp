package com.shenhesoft.examsapp.present;


import android.content.Context;
import android.text.TextUtils;

import com.shenhesoft.examsapp.data.RegisterDataSource;
import com.shenhesoft.examsapp.data.remote.RegisterRemoteDataSource;
import com.shenhesoft.examsapp.ui.activity.RegisterActivity;
import com.shenhesoft.examsapp.util.PatternUtil;

import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * @author mashanshui
 * @date 2018/4/11
 * @desc TODO
 */
public class RegisterPresent extends XPresent<RegisterActivity> {

    private RegisterDataSource registerDataSource;
    private Context context;
    private String saveVerifyCode = null;

    public RegisterPresent(Context context) {
        this.registerDataSource = new RegisterRemoteDataSource();
        this.context = context;
    }

    public void getVerifyCode() {
        if (!getV().getAgreementChecked()) {
            IToast.showShort("请同意用户协议");
            return;
        }
        if (!PatternUtil.isPhone(getV().getPhone())) {
            IToast.showShort("请输入正确的手机号");
            return;
        }
        registerDataSource.getVerifyCode(getV().getPhone(), new RegisterDataSource.GetVerifyCodeCallback() {
            @Override
            public void onSuccess(String verifyCode) {
                IToast.showShort("验证码获取成功");
                saveVerifyCode = verifyCode;
                getV().showCountdown();
            }

            @Override
            public void onFail(String msg) {
                IToast.showShort(msg);
            }
        });
    }

    public void register() {
        if (!getV().getAgreementChecked()) {
            IToast.showShort("请同意用户协议");
            return;
        }
        if (checkData()) {
            return;
        }
        registerDataSource.register(getV().getPhone(), getV().getPhone(), getV().getPassword(), getV().getConfirmPassword(),
                getV().getVerifyCode(), new RegisterDataSource.RegisterCallback() {
                    @Override
                    public void onSuccess() {
                        IToast.showShort("注册成功");
                        getV().toLoginActivity();
                    }

                    @Override
                    public void onFail(String msg) {
                        IToast.showShort(msg);
                    }
                });
    }

    private boolean checkData() {
        if (!getV().getVerifyCode().equals(saveVerifyCode)) {
            IToast.showShort("验证码错误");
            return true;
        }
//        if (TextUtils.isEmpty(getV().getUsername())) {
//            IToast.showShort("请输入账号");
//            return true;
//        }
        if (TextUtils.isEmpty(getV().getPassword())) {
            IToast.showShort("请输入密码");
            return true;
        }
        if (TextUtils.isEmpty(getV().getConfirmPassword())) {
            IToast.showShort("请输入确认密码");
            return true;
        }
        if (TextUtils.isEmpty(getV().getPhone())) {
            IToast.showShort("请输入手机号");
            return true;
        }
        if (PatternUtil.isContainChinese(getV().getConfirmPassword()) || PatternUtil.isContainChinese(getV().getPassword())) {
            IToast.showShort("密码不能包含中文");
            return true;
        }
        if (!TextUtils.equals(getV().getPassword(), getV().getConfirmPassword())) {
            IToast.showShort("两次密码不一致");
            return true;
        }
        return false;
    }
}
