package com.shenhesoft.examsapp.present;

import com.shenhesoft.examsapp.network.UserRetrofit;
import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.network.model.AchieveModel;
import com.shenhesoft.examsapp.ui.activity.user.MyAchieveActivity;

import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import io.reactivex.Observable;

/**
 * @author mashanshui
 * @date 2018/6/5
 * @desc TODO
 */
public class MyAchievePresent extends XPresent<MyAchieveActivity> {
    private UserService userService;

    public MyAchievePresent() {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
    }

    public void loadData() {
        Observable<RequestResults<AchieveModel>> observable = userService.getMyAchieveList(UserRetrofit.getInstance().getMyAchieveList());
        HttpObserver httpObserver = new HttpObserver<RequestResults<AchieveModel>>(new HttpObserver.OnNextListener<RequestResults<AchieveModel>>() {
            @Override
            public void onNext(RequestResults<AchieveModel> data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                getV().updateData(data.getObj());
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
