package com.shenhesoft.examsapp.view;

import com.shenhesoft.examsapp.network.model.LessonsModel;
import com.shenhesoft.examsapp.present.LessonsDataPresent;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * @author mashanshui
 * @date 2018/6/5
 * @desc TODO
 */
public interface LessonsDataView extends IView<LessonsDataPresent> {
    int getStart();

    int getLength();

    boolean getIsLoadingMore();

    void setStart(int start);

    void updateAddData(List<LessonsModel> lessonsModelList);

    void updateData(List<LessonsModel> lessonsModelList);

    String getProductId();

    void getUrlSuccess(String allResults);
}
