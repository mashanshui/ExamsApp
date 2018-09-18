package com.shenhesoft.examsapp.present;

import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.network.model.PersonMessageModel;
import com.shenhesoft.examsapp.ui.activity.user.PersonDataActivity;
import com.shenhesoft.examsapp.util.ActivityUtil;

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
public class PersonDataPresent extends XPresent<PersonDataActivity> {
    private UserService userService;

    public PersonDataPresent() {
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
                getV().updateData(data.getObj());
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
