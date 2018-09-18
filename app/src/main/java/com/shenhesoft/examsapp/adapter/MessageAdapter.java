package com.shenhesoft.examsapp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.PushMessageModel;

import java.util.List;

/**
 * @author mashanshui
 * @date 2018-05-29
 * @desc TODO
 */
public class MessageAdapter extends BaseQuickAdapter<PushMessageModel, BaseViewHolder> {


    public MessageAdapter(@Nullable List<PushMessageModel> data) {
        super(R.layout.recycler_list_message, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PushMessageModel item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_date, item.getPushTime())
                .setText(R.id.tv_text, item.getPreContent());
    }
}
