package com.shenhesoft.examsapp.present;

import com.shenhesoft.examsapp.network.UserRetrofit;
import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.network.model.EvaluateModel;
import com.shenhesoft.examsapp.view.EvaluateContentView;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
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
 * @date 2018/6/6
 * @desc TODO
 */
public class EvaluateContentPresent extends XPresent<EvaluateContentView> {
    private UserService userService;

    public EvaluateContentPresent() {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
    }

    public void loadData(BGARefreshLayout bgaRefreshLayout) {
        Observable<RequestResults<ListALLResults<EvaluateModel>>> observable = userService.lookEvaluate(UserRetrofit.getInstance().lookEvaluate(getV().getStart(), getV().getLength(), getV().getProductId(), getV().getEvaluateType()));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<EvaluateModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<EvaluateModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<EvaluateModel>> data) {
                if (data.getState() != 200) {
                    //加载失败将start重置到加载之前
                    if (getV().getIsLoadingMore()) {
                        getV().setStart(getV().getStart() - getV().getLength());
                    }
                    IToast.showShort(data.getMsg());
                    return;
                }
                List<EvaluateModel> evaluateModels = data.getObj().getRows();
                //下拉刷新
                if (!getV().getIsLoadingMore()) {
                    getV().updateData(evaluateModels);
                }
                //上拉加载后有数据
                else if (getV().getIsLoadingMore() && !evaluateModels.isEmpty()) {
                    getV().updateAddData(evaluateModels);
                }
                //上拉加载后没有数据
                else if (getV().getIsLoadingMore() && evaluateModels.isEmpty()) {
                    getV().setStart(getV().getStart() - getV().getLength());
                    IToast.showShort("没有更多数据");
                }
            }
        });
        httpObserver.setRefreshLayout(bgaRefreshLayout);
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
