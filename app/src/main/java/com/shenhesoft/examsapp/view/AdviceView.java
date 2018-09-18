package com.shenhesoft.examsapp.view;

import com.shenhesoft.examsapp.network.model.AdviceModel;
import com.shenhesoft.examsapp.present.AdvicePresent;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * @author mashanshui
 * @date 2018-06-01
 * @desc TODO
 */
public interface AdviceView extends IView<AdvicePresent> {
    int getStart();

    int getLength();

    boolean isLoadingMore();

    void setStart(int start);

    void updateDate(List<AdviceModel> adviceModels);

    void updateAddDate(List<AdviceModel> adviceModels);
}
