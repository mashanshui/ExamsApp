package com.shenhesoft.examsapp.data.remote;

import com.shenhesoft.examsapp.data.TestPagerDataSource;
import com.shenhesoft.examsapp.network.WorkRetrofit;
import com.shenhesoft.examsapp.network.WorkService;
import com.shenhesoft.examsapp.network.model.TestPagerListModel;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
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
public class TestPagerRemoteDataSource implements TestPagerDataSource {
    private WorkService workService;

    public TestPagerRemoteDataSource() {
        workService = HttpRequestUtil.getRetrofitClient(WorkService.class.getName());
    }

    @Override
    public void loadData(String productId, int status, int start, int length, BGARefreshLayout bgaRefreshLayout, final LoadDataCallBack callBack) {
        Observable<RequestResults<ListALLResults<TestPagerListModel>>> observable = workService.getTestPapersByProductId(WorkRetrofit.getInstance().getTestPapersByProductId(start, length, productId, status));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<TestPagerListModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<TestPagerListModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<TestPagerListModel>> data) {
                if (data.getState() != 200) {
                    callBack.onFail(data.getMsg());
                } else {
                    callBack.onSuccess(data.getObj());
                }
            }
        });
        httpObserver.setRefreshLayout(bgaRefreshLayout);
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
