package com.shenhesoft.examsapp.present;

import com.shenhesoft.examsapp.network.WorkRetrofit;
import com.shenhesoft.examsapp.network.WorkService;
import com.shenhesoft.examsapp.network.model.AnswerPolicysModel;
import com.shenhesoft.examsapp.ui.activity.dohomework.StartDoSubjectActivity;

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
 * @date 2018/5/24
 * @desc TODO
 */
public class StartDoSubjectPresent extends XPresent<StartDoSubjectActivity> {
    private WorkService workService;

    public StartDoSubjectPresent() {
        workService = HttpRequestUtil.getRetrofitClient(WorkService.class.getName());
    }

    public void loadData() {
        Observable<RequestResults<ListALLResults<AnswerPolicysModel>>> observable = workService.getAnswerPolicys(WorkRetrofit.getInstance().getAnswerPolicys(1));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<AnswerPolicysModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<AnswerPolicysModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<AnswerPolicysModel>> data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                getV().updateData(data.getObj().getRows());
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
