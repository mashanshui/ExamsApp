package com.shenhesoft.examsapp.data;

import com.shenhesoft.examsapp.network.model.InteractiveModel;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;
import okhttp3.MultipartBody;

/**
 * @author mashanshui
 * @date 2018/5/30
 * @desc TODO
 */
public interface InteractiveDataSource {
    interface LoadDataCallBack {
        void onSuccess(ListALLResults<InteractiveModel> allResults);

        void onFail(String msg);
    }

    void loadData(BGARefreshLayout bgaRefreshLayout,String articleId, LoadDataCallBack callBack);

    interface UploadCallBack {
        void onSuccess();

        void onFail(String msg);
    }

    void uploadWriting(MultipartBody.Part body, String orderId, String articleTitle, UploadCallBack callBack);
}