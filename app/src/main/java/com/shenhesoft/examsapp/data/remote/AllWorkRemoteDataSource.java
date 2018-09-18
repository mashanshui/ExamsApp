package com.shenhesoft.examsapp.data.remote;

import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.data.AllWorkDataSource;
import com.shenhesoft.examsapp.network.WorkRetrofit;
import com.shenhesoft.examsapp.network.WorkService;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.network.model.ScienceDomainsModel;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import io.reactivex.Observable;

/**
 * @author mashanshui
 * @date 2018/5/22
 * @desc TODO
 */
public class AllWorkRemoteDataSource implements AllWorkDataSource {
    private WorkService workService;

    public AllWorkRemoteDataSource() {
        workService = HttpRequestUtil.getRetrofitClient(WorkService.class.getName());
    }

    @Override
    public void loadQuestionBankData(int productType, int isBuy, String scienceDomainId, int start, int length, BGARefreshLayout bgaRefreshLayout, final LoadDataCallBack callBack) {
        Observable<RequestResults<ListALLResults<ProductModel>>> observable = null;
        if (isBuy == AppConstant.Buy) {
            observable = workService.getAlreadyBuyProducts(WorkRetrofit.getInstance().getAlreadyBuyProducts(start, length, productType, 1));
        } else if (isBuy == AppConstant.NotBuy) {
            observable = workService.getProductsList(WorkRetrofit.getInstance().getHomeProductsList(start, length, productType, scienceDomainId));
        }
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
    public void loadCourseWareData(int productType, int isBuy, String scienceDomainId, int start, int length, BGARefreshLayout bgaRefreshLayout, final LoadDataCallBack callBack) {
        Observable<RequestResults<ListALLResults<ProductModel>>> observable;
//        if (isBuy == AppConstant.Buy) {
//
//        } else if (isBuy == AppConstant.NotBuy) {
//            observable = workService.getAlreadyBuyProducts(WorkRetrofit.getInstance().getAlreadyBuyProducts(start, length, productType, 1));
//        }
        observable = workService.getProductsList(WorkRetrofit.getInstance().getHomeProductsList(start, length, productType, scienceDomainId));
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
    public void getScienceDomainId(final ScienceDomainIdCallBack callBack) {
        Observable<RequestResults<ListALLResults<ScienceDomainsModel>>> observable = workService.getScienceDomains(WorkRetrofit.getInstance().getScienceDomains(0, 20));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<ScienceDomainsModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<ScienceDomainsModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<ScienceDomainsModel>> data) {
                if (data.getState() != 200) {
                    callBack.onFail(data.getMsg());
                } else {
                    callBack.onSuccess(data.getObj());
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
