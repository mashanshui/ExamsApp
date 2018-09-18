package com.shenhesoft.examsapp.view;

import com.shenhesoft.examsapp.network.model.AnswerModel;
import com.shenhesoft.examsapp.present.SearchSubjectPresent;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * @author mashanshui
 * @date 2018/6/7
 * @desc TODO
 */
public interface SearchSubjectView extends IView<SearchSubjectPresent> {
    void updateData(List<AnswerModel> subjectListBean);

    void updateAddData(List<AnswerModel> subjectListBean);

    int getStart();

    int getLength();

    boolean isLoadingMore();

    void setStart(int start);

    String getSearchKey();
}
