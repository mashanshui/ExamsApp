package com.shenhesoft.examsapp.present;

import android.text.TextUtils;

import com.shenhesoft.examsapp.data.RegisterDataSource;
import com.shenhesoft.examsapp.data.remote.RegisterRemoteDataSource;
import com.shenhesoft.examsapp.ui.activity.ForgetPswActivity;
import com.shenhesoft.examsapp.util.PatternUtil;

import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * @author mashanshui
 * @date 2018/5/4
 * @desc TODO
 */
public class ForgetPswPresent extends XPresent<ForgetPswActivity> {
    private RegisterDataSource registerDataSource;
    private String saveVerifyCode = null;

    public ForgetPswPresent() {
        this.registerDataSource = new RegisterRemoteDataSource();
    }

    public void getVerifyCode() {
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

    public void setNewPassword() {
        if (checkData()) {
            return;
        }
        registerDataSource.forgetPsw(getV().getPhone(), saveVerifyCode, getV().getPassword(), getV().getReplyPassword(), new RegisterDataSource.ForgetPswCallback() {
            @Override
            public void onSuccess() {
                getV().setNewPswSuccess();
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
        if (TextUtils.isEmpty(getV().getPassword())) {
            IToast.showShort("请输入密码");
            return true;
        }
        if (TextUtils.isEmpty(getV().getReplyPassword())) {
            IToast.showShort("请输入确认密码");
            return true;
        }
        if (TextUtils.isEmpty(getV().getPhone())) {
            IToast.showShort("请输入手机号");
            return true;
        }
        if (PatternUtil.isContainChinese(getV().getReplyPassword()) || PatternUtil.isContainChinese(getV().getPassword())) {
            IToast.showShort("密码不能包含中文");
            return true;
        }
        if (!TextUtils.equals(getV().getPassword(), getV().getReplyPassword())) {
            IToast.showShort("两次密码不一致");
            return true;
        }
        return false;
    }
}
