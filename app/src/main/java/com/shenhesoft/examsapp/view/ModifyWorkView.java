package com.shenhesoft.examsapp.view;

import com.shenhesoft.examsapp.adapter.bean.ModifyWorkBean;
import com.shenhesoft.examsapp.network.model.SearchTypeModel;
import com.shenhesoft.examsapp.present.ModifyWorkPresent;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * @author mashanshui
 * @date 2018/5/28
 * @desc TODO
 */
public interface ModifyWorkView extends IView<ModifyWorkPresent> {
    void updateAlreadyBuy(List<ModifyWorkBean> modifyWorkBeans);

    void updateRecommend(List<ModifyWorkBean> modifyWorkBeans);

    void updateAddAlreadyBuy(List<ModifyWorkBean> modifyWorkBeans);

    void updateAddRecommend(List<ModifyWorkBean> modifyWorkBeans);

    void refreshAlreadyBuyData();

    void refreshRecommendData();

    int getStart();

    int getLength();

    boolean getIsLoadingMore();

    void setStart(int start);

    void updatePopupData(List<SearchTypeModel> searchTypeModels);
}
