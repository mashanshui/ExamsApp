package com.shenhesoft.examsapp.present;

import com.shenhesoft.examsapp.network.ModifyRetrofit;
import com.shenhesoft.examsapp.network.ModifyService;
import com.shenhesoft.examsapp.network.model.InteractiveModel;
import com.shenhesoft.examsapp.ui.activity.modifyhomework.TeacherDetailsActivity;

import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import io.reactivex.Observable;

/**
 * @author mashanshui
 * @date 2018-06-25
 * @desc TODO
 */
public class TeacherDetailsPresent extends XPresent<TeacherDetailsActivity> {
    private ModifyService modifyService;

    public TeacherDetailsPresent() {
        modifyService = HttpRequestUtil.getRetrofitClient(ModifyService.class.getName());
    }

    public void loadData(String orderId) {
        Observable<RequestResults<ListALLResults<InteractiveModel>>> observable = modifyService.getInteractiveDetail(ModifyRetrofit.getInstance().getInteractiveDetail(0, 1, orderId));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<InteractiveModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<InteractiveModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<InteractiveModel>> data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                getV().isBack(data.getObj().getRows());
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
