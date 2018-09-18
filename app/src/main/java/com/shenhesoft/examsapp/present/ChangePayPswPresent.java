package com.shenhesoft.examsapp.present;

import android.text.TextUtils;

import com.shenhesoft.examsapp.network.UserRetrofit;
import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.ui.activity.user.ChangePayPswActivity;
import com.shenhesoft.examsapp.util.PatternUtil;

import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import io.reactivex.Observable;

/**
 * @author mashanshui
 * @date 2018/6/7
 * @desc TODO
 */
public class ChangePayPswPresent extends XPresent<ChangePayPswActivity> {
    private UserService userService;

    public ChangePayPswPresent() {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
    }

    public void changePayPsw(String loginPsw, String payPsw, String rePayPsw) {
        if (TextUtils.isEmpty(loginPsw)) {
            IToast.showShort("请输入登录密码");
            return;
        }
        if (TextUtils.isEmpty(payPsw) || TextUtils.isEmpty(rePayPsw)) {
            IToast.showShort("请输入支付密码");
            return;
        }
        if (PatternUtil.isContainChinese(loginPsw)) {
            IToast.showShort("密码不能包含中文");
            return;
        }
        if (!TextUtils.equals(payPsw, rePayPsw)) {
            IToast.showShort("两次支付密码不一致");
            return;
        }
        Observable<RequestResults> observable = userService.changePayPsw(UserRetrofit.getInstance().changePayPsw(loginPsw, payPsw));
        HttpObserver httpObserver = new HttpObserver<RequestResults>(new HttpObserver.OnNextListener<RequestResults>() {
            @Override
            public void onNext(RequestResults data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                getV().changeSuccess();
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
