package com.shenhesoft.examsapp.data;

import com.shenhesoft.examsapp.network.model.LessonsModel;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;

/**
 * @author mashanshui
 * @date 2018/6/5
 * @desc TODO
 */
public interface LessonsDataDataSource {
    interface LoadDataCallBack {
        void onSuccess(ListALLResults<LessonsModel> allResults);

        void onFail(String msg);
    }

    void loadData(BGARefreshLayout bgaRefreshLayout, int start, int length, LoadDataCallBack callBack);

    interface LoadSingleDataCallBack {
        void onSuccess(ListALLResults<LessonsModel> allResults);

        void onFail(String msg);
    }

    void loadSingleData(BGARefreshLayout bgaRefreshLayout, int start, int length, String productId, LoadSingleDataCallBack callBack);

    interface GetUrlCallBack {
        void onSuccess(String allResults);

        void onFail(String msg);
    }

    void getPdfUrl(String videoId, GetUrlCallBack callBack);
}
