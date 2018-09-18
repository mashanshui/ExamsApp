package com.shenhesoft.examsapp.data;

import com.shenhesoft.examsapp.network.model.ProductModel;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;

/**
 * @author mashanshui
 * @date 2018/5/28
 * @desc TODO
 */
public interface ModifyWorkDataSource {
    interface LoadBuyDataCallBack {
        void onSuccess(ListALLResults<ProductModel> allResults);

        void onFail(String msg);
    }

    void loadBuyData(int start, int length, LoadBuyDataCallBack callBack);

    void loadBuyData(int start, int length, BGARefreshLayout bgaRefreshLayout, LoadBuyDataCallBack callBack);


    void loadRecommendData(int start, int length, LoadBuyDataCallBack callBack);

    void loadRecommendData(int start, int length, BGARefreshLayout bgaRefreshLayout, LoadBuyDataCallBack callBack);
}
