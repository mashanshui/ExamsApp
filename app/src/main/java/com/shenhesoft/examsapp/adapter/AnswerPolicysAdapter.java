package com.shenhesoft.examsapp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.AnswerPolicysModel;

import java.util.List;

/**
 * @author mashanshui
 * @date 2018/5/24
 * @desc TODO
 */
public class AnswerPolicysAdapter extends BaseQuickAdapter<AnswerPolicysModel, BaseViewHolder> {

    public AnswerPolicysAdapter(@Nullable List<AnswerPolicysModel> data) {
        super(R.layout.recycler_list_answer_policys, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnswerPolicysModel item) {
        helper.setText(R.id.tv_message, item.getName());
    }
}
