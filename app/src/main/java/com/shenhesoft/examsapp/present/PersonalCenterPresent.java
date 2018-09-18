package com.shenhesoft.examsapp.present;

import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.network.model.PersonMessageModel;
import com.shenhesoft.examsapp.network.model.StatisticsModel;
import com.shenhesoft.examsapp.ui.fragment.PersonalCenterFragment;
import com.shenhesoft.examsapp.util.ActivityUtil;

import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import cn.droidlover.xdroidmvp.net.entity.RequestResultsList;
import io.reactivex.Observable;

/**
 * @author mashanshui
 * @date 2018/6/11
 * @desc TODO
 */
public class PersonalCenterPresent extends XPresent<PersonalCenterFragment> {
    private UserService userService;

    public PersonalCenterPresent() {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
    }

    public void loadData() {
        Observable<RequestResults<PersonMessageModel>> observable = userService.lookPersonData(ActivityUtil.getUserID());
        HttpObserver httpObserver = new HttpObserver<RequestResults<PersonMessageModel>>(new HttpObserver.OnNextListener<RequestResults<PersonMessageModel>>() {
            @Override
            public void onNext(RequestResults<PersonMessageModel> data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                if (data.getObj() != null) {
                    getV().updateData(data.getObj());
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    public void loadStatisticsData(boolean isShowLoading) {
        Observable<RequestResultsList<StatisticsModel>> observable = userService.studyStatistics(ActivityUtil.getUserID(), "1");
        HttpObserver httpObserver = new HttpObserver<RequestResultsList<StatisticsModel>>(isShowLoading, new HttpObserver.OnNextListener<RequestResultsList<StatisticsModel>>() {
            @Override
            public void onNext(RequestResultsList<StatisticsModel> data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                if (!data.getObj().isEmpty()) {
                    getV().updateStatisticsData(data.getObj());
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
