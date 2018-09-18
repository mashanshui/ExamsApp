package com.shenhesoft.examsapp.present;

import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.network.model.StatisticsModel;
import com.shenhesoft.examsapp.ui.activity.user.StatisticsActivity;
import com.shenhesoft.examsapp.util.ActivityUtil;

import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.RequestResultsList;
import io.reactivex.Observable;

/**
 * @author mashanshui
 * @date 2018/6/1
 * @desc TODO
 */
public class StatisticsPresent extends XPresent<StatisticsActivity> {
    private UserService userService;

    public StatisticsPresent() {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
    }

    public void loadData() {
        Observable<RequestResultsList<StatisticsModel>> observable = userService.studyStatistics(ActivityUtil.getUserID(), "1");
        HttpObserver httpObserver = new HttpObserver<RequestResultsList<StatisticsModel>>(new HttpObserver.OnNextListener<RequestResultsList<StatisticsModel>>() {
            @Override
            public void onNext(RequestResultsList<StatisticsModel> data) {
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
