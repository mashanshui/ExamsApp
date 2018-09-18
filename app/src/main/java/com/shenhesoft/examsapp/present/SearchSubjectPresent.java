package com.shenhesoft.examsapp.present;

import android.text.TextUtils;

import com.shenhesoft.examsapp.network.WorkRetrofit;
import com.shenhesoft.examsapp.network.WorkService;
import com.shenhesoft.examsapp.network.model.AnswerModel;
import com.shenhesoft.examsapp.view.SearchSubjectView;

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
 * @date 2018/6/7
 * @desc TODO
 */
public class SearchSubjectPresent extends XPresent<SearchSubjectView> {
    private WorkService workService;

    public SearchSubjectPresent() {
        workService = HttpRequestUtil.getRetrofitClient(WorkService.class.getName());
    }

    public void searchData(BGARefreshLayout bgaRefreshLayout) {
        if (TextUtils.isEmpty(getV().getSearchKey())) {
            IToast.showShort("请输入关键字");
            return;
        }
        Observable<RequestResults<ListALLResults<AnswerModel>>> observable = workService.searchSubjectBank(WorkRetrofit.getInstance().searchSubjectBank(getV().getStart(), getV().getLength(), getV().getSearchKey()));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<AnswerModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<AnswerModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<AnswerModel>> data) {
                if (data.getState() != 200) {
                    //加载失败将start重置到加载之前
                    if (getV().isLoadingMore()) {
                        getV().setStart(getV().getStart() - getV().getLength());
                    }
                    IToast.showShort(data.getMsg());
                    return;
                }
                List<AnswerModel> productModelList = data.getObj().getRows();
                //下拉刷新
                if (!getV().isLoadingMore()) {
                    getV().updateData(productModelList);
                }
                //上拉加载后有数据
                else if (getV().isLoadingMore() && !productModelList.isEmpty()) {
                    getV().updateAddData(productModelList);
                }
                //上拉加载后没有数据
                else if (getV().isLoadingMore() && productModelList.isEmpty()) {
                    getV().setStart(getV().getStart() - getV().getLength());
                    IToast.showShort("没有更多数据");
                }
            }
        });
        httpObserver.setRefreshLayout(bgaRefreshLayout);
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
