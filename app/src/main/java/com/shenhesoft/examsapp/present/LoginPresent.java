package com.shenhesoft.examsapp.present;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.StringUtils;
import com.shenhesoft.examsapp.data.LoginDataSource;
import com.shenhesoft.examsapp.data.remote.LoginRemoteDataSource;
import com.shenhesoft.examsapp.network.model.LoginModel;
import com.shenhesoft.examsapp.network.model.SecretKeyModel;
import com.shenhesoft.examsapp.util.PatternUtil;
import com.shenhesoft.examsapp.util.RSAClientHelper;
import com.shenhesoft.examsapp.view.LoginView;

import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * @author 马山水
 * @date 2018/3/30
 * @desc TODO
 */
public class LoginPresent extends XPresent<LoginView> {
    private static final String TAG = "LoginPresent";
    private LoginDataSource loginDataSource;
    private Context context;

    public LoginPresent(Activity context) {
        loginDataSource = new LoginRemoteDataSource();
        this.context = context;
    }

    public void login(SecretKeyModel secretKeyModel) {
        String password = RSAClientHelper.getPassword(secretKeyModel.getModulus(), secretKeyModel.getExponent(), StringUtils.reverse(getV().getPassword()));
        String sign = RSAClientHelper.getSessionId(getV().getUsername(), password);
        loginDataSource.login(getV().isShowLoadingDailog(), getV().getUsername(), password, sign, new LoginDataSource.LoginCallback() {
            @Override
            public void onSuccess(LoginModel loginModel) {
                if (!TextUtils.equals(loginModel.getRoleTypeName(), "学生")) {
                    IToast.showShort("只有学生用户才能登录");
                    return;
                }
                getV().loginSuccess(loginModel);
            }

            @Override
            public void onFail(String msg) {
                getV().loginFail(msg);
            }

            @Override
            public void onError() {
                getV().onError();
            }
        });
    }

    public void getSecretKey() {
        if (TextUtils.isEmpty(getV().getUsername())) {
            IToast.showShort("请输入手机号");
            return;
        }
//        if (getV().getUsername().length() != 11) {
//            IToast.showShort("请输入正确的手机号");
//            return;
//        }
        if (TextUtils.isEmpty(getV().getPassword())) {
            IToast.showShort("请输入密码");
            return;
        }
        if (PatternUtil.isContainChinese(getV().getPassword())) {
            IToast.showShort("密码不能包含中文");
            return;
        }
        loginDataSource.getSecretKey(getV().isShowLoadingDailog(), new LoginDataSource.SecretKeyCallback() {
            @Override
            public void onSuccess(SecretKeyModel secretKeyModel) {
                if (TextUtils.isEmpty(secretKeyModel.getExponent()) || TextUtils.isEmpty(secretKeyModel.getModulus())) {
                    IToast.showShort("登录失败");
                    return;
                }
                getV().dealSecretKey(secretKeyModel);
            }

            @Override
            public void onFail(String msg) {
                IToast.showShort("登录失败");
            }

            @Override
            public void onError() {
                getV().onError();
            }
        });
    }
}
