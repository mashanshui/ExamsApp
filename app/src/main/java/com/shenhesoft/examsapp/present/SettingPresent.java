package com.shenhesoft.examsapp.present;

import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.ui.activity.user.SettingActivity;

import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import io.reactivex.Observable;

/**
 * @author mashanshui
 * @date 2018/6/8
 * @desc TODO
 */
public class SettingPresent extends XPresent<SettingActivity> {
    private UserService userService;

    public SettingPresent() {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
    }

    public void logout() {
        Observable<RequestResults> observable = userService.logout();
        HttpObserver httpObserver = new HttpObserver<RequestResults>(new HttpObserver.OnNextListener<RequestResults>() {
            @Override
            public void onNext(RequestResults data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                getV().logout();
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
