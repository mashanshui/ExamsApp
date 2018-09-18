package com.shenhesoft.examsapp.view;

import com.shenhesoft.examsapp.network.model.ChatMsgModel;
import com.shenhesoft.examsapp.present.AskPresent;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * @author mashanshui
 * @date 2018/5/31
 * @desc TODO
 */
public interface AskView extends IView<AskPresent> {

    int getStart();

    int getLength();

    void updateData(List<ChatMsgModel> msgModels);

    String getSendMessage();

    void clearSendMessage();
}
