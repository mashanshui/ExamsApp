package com.shenhesoft.examsapp.present;

import com.shenhesoft.examsapp.network.UserRetrofit;
import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.network.model.AdviceModel;
import com.shenhesoft.examsapp.view.AdviceView;

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
 * @date 2018-06-01
 * @desc TODO
 */
public class AdvicePresent extends XPresent<AdviceView> {
    private UserService userService;

    public AdvicePresent() {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
    }

    public void loadData(BGARefreshLayout bgaRefreshLayout) {
        Observable<RequestResults<ListALLResults<AdviceModel>>> observable = userService.getAdviceList(UserRetrofit.getInstance().getAdviceList(getV().getStart(), getV().getLength()));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<AdviceModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<AdviceModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<AdviceModel>> data) {
                if (data.getState() != 200) {
                    //加载失败将start重置到加载之前
                    if (getV().isLoadingMore()) {
                        getV().setStart(getV().getStart() - getV().getLength());
                    }
                    IToast.showShort(data.getMsg());
                    return;
                }
                List<AdviceModel> adviceModels = data.getObj().getRows();
                //下拉刷新
                if (!getV().isLoadingMore()) {
                    getV().updateDate(adviceModels);
                }
                //上拉加载后有数据
                else if (getV().isLoadingMore() && !adviceModels.isEmpty()) {
                    getV().updateAddDate(adviceModels);
                }
                //上拉加载后没有数据
                else if (getV().isLoadingMore() && adviceModels.isEmpty()) {
                    getV().setStart(getV().getStart() - getV().getLength());
                    IToast.showShort("没有更多数据");
                }
            }
        });
        httpObserver.setRefreshLayout(bgaRefreshLayout);
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
