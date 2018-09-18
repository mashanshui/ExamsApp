package com.shenhesoft.examsapp.data.remote;

import com.shenhesoft.examsapp.data.OnlineProductDataSource;
import com.shenhesoft.examsapp.network.WorkRetrofit;
import com.shenhesoft.examsapp.network.WorkService;
import com.shenhesoft.examsapp.network.model.ProductModel;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import io.reactivex.Observable;


/**
 * @author mashanshui
 * @date 2018/5/29
 * @desc TODO
 */
public class OnlineProductRemoteDataSource implements OnlineProductDataSource {
    private WorkService workService;

    public OnlineProductRemoteDataSource() {
        workService = HttpRequestUtil.getRetrofitClient(WorkService.class.getName());
    }

    @Override
    public void loadData(int productType, String sort, int start, int length, BGARefreshLayout bgaRefreshLayout, final LoadDataCallBack callBack) {
        Observable<RequestResults<ListALLResults<ProductModel>>> observable = workService.getProductsList(WorkRetrofit.getInstance().getProductsList(start, length, sort));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<ProductModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<ProductModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<ProductModel>> data) {
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

    @Override
    public void searchData(int productType, int start, int length, String searchKey, String searchType, String searchSource, String searchDate, BGARefreshLayout bgaRefreshLayout, final SearchDataCallBack callBack) {
        Observable<RequestResults<ListALLResults<ProductModel>>> observable = workService.searchQuestionBank(WorkRetrofit.getInstance().searchWritingByCondition(start, length, productType, searchKey, searchType, searchSource, searchDate));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<ProductModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<ProductModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<ProductModel>> data) {
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
