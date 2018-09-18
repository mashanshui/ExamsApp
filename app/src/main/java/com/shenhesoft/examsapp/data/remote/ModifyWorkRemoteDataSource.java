package com.shenhesoft.examsapp.data.remote;

import com.shenhesoft.examsapp.data.ModifyWorkDataSource;
import com.shenhesoft.examsapp.network.ModifyRetrofit;
import com.shenhesoft.examsapp.network.ModifyService;
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
 * @date 2018/5/28
 * @desc TODO
 */
public class ModifyWorkRemoteDataSource implements ModifyWorkDataSource {
    private ModifyService modifyService;

    public ModifyWorkRemoteDataSource() {
        modifyService = HttpRequestUtil.getRetrofitClient(ModifyService.class.getName());
    }

    @Override
    public void loadBuyData(int start, int length, final LoadBuyDataCallBack callBack) {
        Observable<RequestResults<ListALLResults<ProductModel>>> observable = modifyService.getAlreadyBuyProduct(ModifyRetrofit.getInstance().getAlreadyBuyProducts(start, length));
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
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    @Override
    public void loadBuyData(int start, int length, BGARefreshLayout bgaRefreshLayout, final LoadBuyDataCallBack callBack) {
        Observable<RequestResults<ListALLResults<ProductModel>>> observable = modifyService.getAlreadyBuyProduct(ModifyRetrofit.getInstance().getAlreadyBuyProducts(start, length));
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
    public void loadRecommendData(int start, int length, final LoadBuyDataCallBack callBack) {
        Observable<RequestResults<ListALLResults<ProductModel>>> observable = modifyService.getRecommendProduct(ModifyRetrofit.getInstance().getRecommendProduct(start, length));
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
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    @Override
    public void loadRecommendData(int start, int length, BGARefreshLayout bgaRefreshLayout, final LoadBuyDataCallBack callBack) {
        Observable<RequestResults<ListALLResults<ProductModel>>> observable = modifyService.getRecommendProduct(ModifyRetrofit.getInstance().getRecommendProduct(start, length));
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
