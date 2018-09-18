package com.shenhesoft.examsapp.view;

import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.present.OnlineProductPresent;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * @author mashanshui
 * @date 2018/5/29
 * @desc TODO
 */
public interface OnlineProductView extends IView<OnlineProductPresent> {
    int getStart();

    int getLength();

    boolean getIsLoadingMore();

    void setStart(int start);

    void updateData(List<ProductModel> productModelList);

    void updateAddData(List<ProductModel> productModelList);

    void refreshAdapter();

    void setUpOrDown(int upOrDown);

    String getSearchKey();

    int getProductType();

    String getSearchType();

    String getSearchSource();

    String getSearchDate();
}
