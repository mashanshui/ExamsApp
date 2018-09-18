package com.shenhesoft.examsapp.data;

import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.network.model.ScienceDomainsModel;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;

/**
 * @author mashanshui
 * @date 2018/5/22
 * @desc TODO
 */
public interface AllWorkDataSource {

    interface LoadDataCallBack {
        void onSuccess(ListALLResults<ProductModel> allResults);

        void onFail(String msg);
    }

    void loadQuestionBankData(int productType, int isBuy, String scienceDomainId, int start, int length, BGARefreshLayout bgaRefreshLayout, LoadDataCallBack callBack);

    void loadCourseWareData(int productType, int isBuy, String scienceDomainId, int start, int length, BGARefreshLayout bgaRefreshLayout, LoadDataCallBack callBack);

    interface ScienceDomainIdCallBack {
        void onSuccess(ListALLResults<ScienceDomainsModel> allResults);

        void onFail(String msg);
    }

    void getScienceDomainId(ScienceDomainIdCallBack callBack);
}
