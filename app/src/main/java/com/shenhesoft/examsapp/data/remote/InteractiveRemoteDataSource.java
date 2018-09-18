package com.shenhesoft.examsapp.data.remote;

import com.shenhesoft.examsapp.data.InteractiveDataSource;
import com.shenhesoft.examsapp.network.ModifyRetrofit;
import com.shenhesoft.examsapp.network.ModifyService;
import com.shenhesoft.examsapp.network.model.InteractiveModel;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * @author mashanshui
 * @date 2018/5/30
 * @desc TODO
 */
public class InteractiveRemoteDataSource implements InteractiveDataSource {
    private ModifyService modifyService;

    public InteractiveRemoteDataSource() {
        modifyService = HttpRequestUtil.getRetrofitClient(ModifyService.class.getName());
    }

    @Override
    public void loadData(BGARefreshLayout bgaRefreshLayout,String articleId, final LoadDataCallBack callBack) {
        Observable<RequestResults<ListALLResults<InteractiveModel>>> observable = modifyService.getInteractiveDetail(ModifyRetrofit.getInstance().getInteractiveDetail(0, 20, articleId));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<InteractiveModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<InteractiveModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<InteractiveModel>> data) {
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
    public void uploadWriting(MultipartBody.Part body, String orderId, String articleTitle, final UploadCallBack callBack) {
        Observable<RequestResults> observable = modifyService.uploadWriting(ModifyRetrofit.getInstance().uploadWriting(orderId, articleTitle, "", ""), body);
        HttpObserver httpObserver = new HttpObserver<RequestResults>(new HttpObserver.OnNextListener<RequestResults>() {
            @Override
            public void onNext(RequestResults data) {
                if (data.getState() != 201) {
                    callBack.onFail(data.getMsg());
                } else {
                    callBack.onSuccess();
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

}
