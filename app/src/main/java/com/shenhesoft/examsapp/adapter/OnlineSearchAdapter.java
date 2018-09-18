package com.shenhesoft.examsapp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.SearchTypeModel;

import java.util.List;

/**
 * @author mashanshui
 * @date 2018/6/25
 * @desc TODO
 */
public class OnlineSearchAdapter extends BaseQuickAdapter<SearchTypeModel, BaseViewHolder> {

    public OnlineSearchAdapter(@Nullable List<SearchTypeModel> data) {
        super(R.layout.recycler_list_popup_level_two, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchTypeModel item) {
        helper.setText(R.id.tv_message, item.getText());
    }
}
