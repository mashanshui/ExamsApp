package com.shenhesoft.examsapp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.ChatMsgModel;

import java.util.List;

public class MsgAdapter extends BaseQuickAdapter<ChatMsgModel, BaseViewHolder> {
    public static final int MESSAGE_SEND = 0;
    public static final int MESSAGE_RECEIVE = 1;

    public MsgAdapter(@Nullable final List<ChatMsgModel> data) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<ChatMsgModel>() {
            @Override
            protected int getItemType(ChatMsgModel chatMsgModel) {
                return chatMsgModel.getChaterType();
            }
        });
        getMultiTypeDelegate()
                .registerItemType(MESSAGE_SEND, R.layout.recycler_list_msg_send)
                .registerItemType(MESSAGE_RECEIVE, R.layout.recycler_list_msg_receiver);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatMsgModel item) {
        switch (helper.getItemViewType()) {
            case MESSAGE_SEND:
                helper.setText(R.id.tv_name, item.getChatUserName())
                        .setText(R.id.tv_date, item.getChatTime())
                        .setText(R.id.tv_message_content, item.getChatContent());
                break;
            case MESSAGE_RECEIVE:
                helper.setText(R.id.tv_name, item.getChatUserName())
                        .setText(R.id.tv_date, item.getChatTime())
                        .setText(R.id.tv_message_content, item.getChatContent());
                break;
            default:
                break;
        }
    }
}
