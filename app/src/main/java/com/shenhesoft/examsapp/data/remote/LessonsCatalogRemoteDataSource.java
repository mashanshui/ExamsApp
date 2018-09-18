package com.shenhesoft.examsapp.data.remote;

import com.shenhesoft.examsapp.data.LessonsCatalogDataSource;
import com.shenhesoft.examsapp.network.LessonRetrofit;
import com.shenhesoft.examsapp.network.LessonService;
import com.shenhesoft.examsapp.network.model.LessonsModel;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
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
public class LessonsCatalogRemoteDataSource implements LessonsCatalogDataSource {
    private LessonService lessonService;

    public LessonsCatalogRemoteDataSource() {
        lessonService = HttpRequestUtil.getRetrofitClient(LessonService.class.getName());
    }

    @Override
    public void loadData(BGARefreshLayout bgaRefreshLayout, int start, int length, final LoadDataCallBack callBack) {
        Observable<RequestResults<ListALLResults<LessonsModel>>> observable = lessonService.getLessons(LessonRetrofit.getInstance().getLessons(start, length));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<LessonsModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<LessonsModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<LessonsModel>> data) {
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
    public void loadSingleData(BGARefreshLayout bgaRefreshLayout, int start, int length, String productId, final LoadSingleDataCallBack callBack) {
        Observable<RequestResults<ListALLResults<LessonsModel>>> observable = lessonService.getSingleLessons(productId);
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<LessonsModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<LessonsModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<LessonsModel>> data) {
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
