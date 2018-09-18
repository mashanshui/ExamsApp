package com.shenhesoft.examsapp.present;

import com.shenhesoft.examsapp.data.AskDataSource;
import com.shenhesoft.examsapp.data.remote.AskRemoteDataaSource;
import com.shenhesoft.examsapp.network.model.ChatMsgModel;
import com.shenhesoft.examsapp.ui.fragment.modifyhomework.AskFragment;

import java.util.List;

import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;

/**
 * @author mashanshui
 * @date 2018/5/31
 * @desc TODO
 */
public class AskPresent extends XPresent<AskFragment> {
    private AskDataSource askDataSource;

    public AskPresent() {
        askDataSource = new AskRemoteDataaSource();
    }

    public void loadData(String chatId) {
        askDataSource.loadData(getV().getStart(), getV().getLength(), chatId, new AskDataSource.LoadDataCallBack() {
            @Override
            public void onSuccess(ListALLResults<ChatMsgModel> allResults) {
                List<ChatMsgModel> chatMsgModels = allResults.getRows();
                getV().updateData(chatMsgModels);
            }

            @Override
            public void onFail(String msg) {
                IToast.showShort(msg);
            }
        });
    }

    public void sendMessage(final String chatId) {
        askDataSource.sendMessage(chatId, getV().getSendMessage(), new AskDataSource.SendMessageCallBack() {
            @Override
            public void onSuccess() {
                getV().clearSendMessage();
                loadData(chatId);
            }

            @Override
            public void onFail(String msg) {
                IToast.showShort(msg);
            }
        });
    }
}
