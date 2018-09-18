package com.shenhesoft.examsapp.view;

import com.shenhesoft.examsapp.network.model.AnswerModel;
import com.shenhesoft.examsapp.network.model.FinishAnswerModel;
import com.shenhesoft.examsapp.present.AnswerPresent;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * @author mashanshui
 * @date 2018/4/18
 * @desc TODO
 */
public interface AnswerView extends IView<AnswerPresent> {
    void updateData(List<AnswerModel> allResults);

    void updateAddData(List<AnswerModel> allResults);

    void nextAnswer();

    void updateSubjectNum(int currentPage);

    void setAnswerId(String id);

    void finishActivity();

    String getAnswerId();

    void setTotalAnswerNum(int totalAnswerNum);

    int getTotalAnswerNum();

    int getStartAnswerNum();

    void toStatisticsActivity(FinishAnswerModel model);

    void setCollectCheck(boolean isCheck);

    void nextSuccess();

    String getAnswerPolicyName();

    int getDoneSize();

    int getCurrentPage();
}
