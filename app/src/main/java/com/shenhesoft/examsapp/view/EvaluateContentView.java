package com.shenhesoft.examsapp.view;

import com.shenhesoft.examsapp.network.model.EvaluateModel;
import com.shenhesoft.examsapp.present.EvaluateContentPresent;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * @author mashanshui
 * @date 2018/6/6
 * @desc TODO
 */
public interface EvaluateContentView extends IView<EvaluateContentPresent> {
    int getStart();

    int getLength();

    boolean getIsLoadingMore();

    void setStart(int start);

    String getProductId();

    String getEvaluateType();

    void updateData(List<EvaluateModel> evaluateModels);

    void updateAddData(List<EvaluateModel> evaluateModels);
}
