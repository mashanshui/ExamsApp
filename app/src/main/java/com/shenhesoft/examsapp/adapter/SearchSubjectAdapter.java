package com.shenhesoft.examsapp.adapter;

import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.AnswerModel;
import com.zzhoujay.richtext.RichText;

import java.util.List;

/**
 * @author mashanshui
 * @date 2018/5/14
 * @desc TODO
 */
public class SearchSubjectAdapter extends BaseQuickAdapter<AnswerModel, BaseViewHolder> {

    public SearchSubjectAdapter(@Nullable List<AnswerModel> data) {
        super(R.layout.recycler_list_search_subject, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnswerModel item) {
//        helper.setText(R.id.tv_search_subject, item.getContent());
        RichText.fromHtml(item.getContent()).into((TextView) helper.getView(R.id.tv_search_subject));
        helper.addOnClickListener(R.id.tv_search_subject);
    }
}
