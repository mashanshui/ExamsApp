package com.shenhesoft.examsapp.data;

import com.shenhesoft.examsapp.network.model.ChatMsgModel;

import cn.droidlover.xdroidmvp.net.entity.ListALLResults;

/**
 * @author mashanshui
 * @date 2018/5/31
 * @desc TODO
 */
public interface AskDataSource {
    interface LoadDataCallBack {
        void onSuccess(ListALLResults<ChatMsgModel> allResults);

        void onFail(String msg);
    }

    void loadData(int start, int length, String chatId, LoadDataCallBack callBack);

    interface SendMessageCallBack {
        void onSuccess();

        void onFail(String msg);
    }

    void sendMessage(String chatId, String message, SendMessageCallBack callBack);
}
