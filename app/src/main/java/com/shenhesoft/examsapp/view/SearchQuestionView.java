package com.shenhesoft.examsapp.view;

import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.present.SearchQuestionPresent;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * @author mashanshui
 * @date 2018/6/7
 * @desc TODO
 */
public interface SearchQuestionView extends IView<SearchQuestionPresent> {
    void updateData(List<ProductModel> subjectListBean);

    void updateAddData(List<ProductModel> subjectListBean);

    int getStart();

    int getLength();

    boolean isLoadingMore();

    void setStart(int start);

    String getSearchKey();

    int getProductType();
}
