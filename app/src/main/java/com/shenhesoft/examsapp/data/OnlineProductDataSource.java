package com.shenhesoft.examsapp.data;

import com.shenhesoft.examsapp.network.model.ProductModel;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;

/**
 * @author mashanshui
 * @date 2018/5/29
 * @desc TODO
 */
public interface OnlineProductDataSource {
    interface LoadDataCallBack {
        void onSuccess(ListALLResults<ProductModel> allResults);

        void onFail(String msg);
    }

    void loadData(int productType, String sort, int start, int length, BGARefreshLayout bgaRefreshLayout, LoadDataCallBack callBack);

    interface SearchDataCallBack {
        void onSuccess(ListALLResults<ProductModel> allResults);

        void onFail(String msg);
    }

    void searchData(int productType, int start, int length, String searchKey, String searchType, String searchSource, String searchDate, BGARefreshLayout bgaRefreshLayout, SearchDataCallBack callBack);
}
