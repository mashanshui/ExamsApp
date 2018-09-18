package com.shenhesoft.examsapp.present;

import com.shenhesoft.examsapp.network.WorkRetrofit;
import com.shenhesoft.examsapp.network.WorkService;
import com.shenhesoft.examsapp.network.model.ScienceDomainsModel;
import com.shenhesoft.examsapp.ui.activity.dohomework.ScienceDomainsActivity;

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
 * @date 2018/6/8
 * @desc TODO
 */
public class ScienceDomainsPresent extends XPresent<ScienceDomainsActivity> {
    private WorkService workService;

    public ScienceDomainsPresent() {
        workService = HttpRequestUtil.getRetrofitClient(WorkService.class.getName());
    }

    public void loadData() {
        Observable<RequestResults<ListALLResults<ScienceDomainsModel>>> observable = workService.getScienceDomains(WorkRetrofit.getInstance().getScienceDomains(0, 20));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<ScienceDomainsModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<ScienceDomainsModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<ScienceDomainsModel>> data) {
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
