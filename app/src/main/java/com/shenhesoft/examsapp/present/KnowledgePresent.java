package com.shenhesoft.examsapp.present;

import com.shenhesoft.examsapp.network.WorkRetrofit;
import com.shenhesoft.examsapp.network.WorkService;
import com.shenhesoft.examsapp.network.model.KnowledgeModel;
import com.shenhesoft.examsapp.view.KnowledgeView;

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
public class KnowledgePresent extends XPresent<KnowledgeView> {
    private WorkService workService;

    public KnowledgePresent() {
        workService = HttpRequestUtil.getRetrofitClient(WorkService.class.getName());
    }

    public void loadDataByPid(String pid) {
        Observable<RequestResults<ListALLResults<KnowledgeModel>>> observable = workService.getKnowledge(WorkRetrofit.getInstance().getKnowledgeFirst(0, 20, pid));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<KnowledgeModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<KnowledgeModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<KnowledgeModel>> data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                getV().updateData(data.getObj().getRows());
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    public void loadDataByKnowledgePointCode(String knowledgePointCode) {
        Observable<RequestResults<ListALLResults<KnowledgeModel>>> observable = workService.getKnowledge(WorkRetrofit.getInstance().getKnowledge(0, 20, knowledgePointCode));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<KnowledgeModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<KnowledgeModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<KnowledgeModel>> data) {
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
