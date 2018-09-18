package com.shenhesoft.examsapp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.KnowledgeModel;

import java.util.List;

/**
 * @author mashanshui
 * @date 2018/6/8
 * @desc TODO
 */
public class KnowledgeAdapter extends BaseQuickAdapter<KnowledgeModel, BaseViewHolder> {

    public KnowledgeAdapter(@Nullable List<KnowledgeModel> data) {
        super(R.layout.recycler_list_answer_policys,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeModel item) {
        helper.setText(R.id.tv_message, item.getKnowledgePointName());
    }
}
