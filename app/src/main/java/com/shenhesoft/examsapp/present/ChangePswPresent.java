package com.shenhesoft.examsapp.present;

import android.text.TextUtils;

import com.shenhesoft.examsapp.network.UserRetrofit;
import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.ui.activity.user.ChangePswActivity;
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
 * @date 2018/6/9
 * @desc TODO
 */
public class ChangePswPresent extends XPresent<ChangePswActivity> {
    private UserService userService;

    public ChangePswPresent() {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
    }

    public void changePsw(String oldPsw, String newPsw, String reNewPsw) {
        if (TextUtils.isEmpty(oldPsw)) {
            IToast.showShort("请输入旧密码");
            return;
        }
        if (TextUtils.isEmpty(newPsw) || TextUtils.isEmpty(reNewPsw)) {
            IToast.showShort("请输入新密码");
            return;
        }
        if (PatternUtil.isContainChinese(oldPsw) || PatternUtil.isContainChinese(newPsw) || PatternUtil.isContainChinese(reNewPsw)) {
            IToast.showShort("密码不能包含中文");
            return;
        }
        if (!TextUtils.equals(newPsw, reNewPsw)) {
            IToast.showShort("两次密码不一致");
            return;
        }
        Observable<RequestResults> observable = userService.changePsw(UserRetrofit.getInstance().changePsw(oldPsw, newPsw, reNewPsw));
        HttpObserver httpObserver = new HttpObserver<RequestResults>(new HttpObserver.OnNextListener<RequestResults>() {
            @Override
            public void onNext(RequestResults data) {
                if (data.getState() != 201) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                getV().changeSuccess();
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
