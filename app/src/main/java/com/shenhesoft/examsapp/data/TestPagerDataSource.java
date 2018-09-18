package com.shenhesoft.examsapp.data;

import com.shenhesoft.examsapp.network.model.TestPagerListModel;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;

/**
 * @author mashanshui
 * @date 2018-05-23
 * @desc TODO
 */
public interface TestPagerDataSource {
    interface LoadDataCallBack {
        void onSuccess(ListALLResults<TestPagerListModel> allResults);

        void onFail(String msg);
    }

    void loadData(String productId, int status, int start, int length, BGARefreshLayout bgaRefreshLayout, LoadDataCallBack callBack);
}
