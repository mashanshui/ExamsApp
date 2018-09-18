package com.shenhesoft.examsapp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.EvaluateModel;

import java.util.List;

/**
 * @author mashanshui
 * @date 2018/5/15
 * @desc TODO
 */
public class EvaluateAdapter extends BaseQuickAdapter<EvaluateModel, BaseViewHolder> {

    public EvaluateAdapter(@Nullable List<EvaluateModel> data) {
        super(R.layout.recycler_list_course_evaluate, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EvaluateModel item) {
        String name = item.getStudentUserName();
        switch (name.length()) {
            case 1:
                break;
            case 2:
                name = name.replace(name.substring(1), "*");
                break;
            case 3:
                name = name.replace(name.substring(1, 1), "*");
                break;
            case 4:
                name = name.replace(name.substring(1, 2), "*");
                break;
            default:
                String s1 = name.substring(name.length() / 2 - 1, name.length() / 2 + 1);
                name = name.replace(s1, "***");
                break;
        }
        helper.setText(R.id.tv_people_name, name)
                .setText(R.id.tv_date, item.getScoreTime())
                .setText(R.id.tv_content, item.getScoreContent())
                .setRating(R.id.ratingBar, item.getScoreValue());
    }
}
