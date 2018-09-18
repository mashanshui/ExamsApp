package com.shenhesoft.examsapp.present;

import android.text.TextUtils;

import com.shenhesoft.examsapp.network.UserRetrofit;
import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.ui.activity.user.ModifyMessageActivity;
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
public class ModifyMessagePresent extends XPresent<ModifyMessageActivity> {
    private UserService userService;

    public ModifyMessagePresent() {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
    }

    public void modifyMessage(String message) {
        if (checkData(message)) {
            return;
        }
        Observable<RequestResults> observable = userService.modifyMessage(UserRetrofit.getInstance().modifyMessage(getV().getType(), message));
        HttpObserver httpObserver = new HttpObserver<RequestResults>(new HttpObserver.OnNextListener<RequestResults>() {
            @Override
            public void onNext(RequestResults data) {
                if (data.getState() != 201) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                getV().modifySuccess();
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    private boolean checkData(String message) {
        if (TextUtils.isEmpty(message)) {
            IToast.showShort("请输入修改的内容");
            return true;
        }
        if (getV().getType() == 3 && message.length() > 3) {
            IToast.showShort("请输入真实的年龄");
            return true;
        }
        if (getV().getType() == 3 && !PatternUtil.isNumeric(message)) {
            IToast.showShort("请输入真实的年龄");
            return true;
        }
        if (getV().getType() == 4 && !PatternUtil.isEmail(message)) {
            IToast.showShort("请输入正确的邮箱");
            return true;
        }
        return false;
    }
}
