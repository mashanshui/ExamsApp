package com.shenhesoft.examsapp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenhesoft.examsapp.R;

import java.util.List;

/**
 * @author mashanshui
 * @date 2018/7/24
 * @desc TODO
 */
public class QuestionNumAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public QuestionNumAdapter(@Nullable List<String> data) {
        super(R.layout.recycler_qusetion_num, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.btn_num, item)
                .addOnClickListener(R.id.btn_num);
    }
}
