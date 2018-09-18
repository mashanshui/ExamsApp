package com.shenhesoft.examsapp.view;

import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.present.AllWorkPresent;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * @author mashanshui
 * @date 2018/5/22
 * @desc TODO
 */
public interface AllWorkView extends IView<AllWorkPresent> {

    void updateData(List<ProductModel> subjectListBean);

    void updateAddData(List<ProductModel> subjectListBean);

    void refreshAdapter();

    String getScienceDomainId();

    void setScienceDomainId(String id);

    void getScienceDomainSuccess();
}
