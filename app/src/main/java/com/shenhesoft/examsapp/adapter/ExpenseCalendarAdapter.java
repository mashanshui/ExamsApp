package com.shenhesoft.examsapp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.ExpenseCalendarModel;

import java.util.List;

import cn.droidlover.xdroidmvp.base.ActivityCollector;

/**
 * @author mashanshui
 * @date 2018/5/19
 * @desc TODO
 */
public class ExpenseCalendarAdapter extends BaseQuickAdapter<ExpenseCalendarModel, BaseViewHolder> {

    public ExpenseCalendarAdapter(@Nullable List<ExpenseCalendarModel> data) {
        super(R.layout.recycler_list_expense_calendar, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExpenseCalendarModel item) {
        helper.setText(R.id.tv_message, item.getRemark())
                .setText(R.id.tv_date, item.getTradeTime());
        Glide.with(ActivityCollector.getTopActivity())
                .load(item.getTradeType() == 1 ? R.drawable.recharge : R.drawable.consume)
                .into((ImageView) helper.getView(R.id.iv_image));
    }
}
