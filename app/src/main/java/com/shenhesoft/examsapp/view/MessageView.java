package com.shenhesoft.examsapp.view;

import com.shenhesoft.examsapp.network.model.PushMessageModel;
import com.shenhesoft.examsapp.present.MessagePresent;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * @author mashanshui
 * @date 2018/6/11
 * @desc TODO
 */
public interface MessageView extends IView<MessagePresent> {
    int getStart();

    int getLength();

    boolean isLoadingMore();

    void setStart(int start);

    void updateDate(List<PushMessageModel> pushMessageModels);

    void updateAddDate(List<PushMessageModel> pushMessageModels);
}
