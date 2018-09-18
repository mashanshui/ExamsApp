package com.shenhesoft.examsapp.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.bean.RechargeBean;

import java.util.List;

/**
 * @author mashanshui
 * @date 2018/5/19
 * @desc TODO
 */
public class RechargeAdapter extends BaseQuickAdapter<RechargeBean, BaseViewHolder> {
    public static final int SELECT = 0;
    public static final int NOTSELECT = 1;

    public RechargeAdapter(@Nullable List<RechargeBean> data) {
        super(R.layout.recycler_list_wallet_recharge_scope, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeBean item) {
        if (item.getIsSelect() == SELECT) {
            helper.setBackgroundRes(R.id.ll_content_layout, R.drawable.bg_ll_focus_round_conner)
                    .setTextColor(R.id.tv_recharge_amount, Color.parseColor("#00aeff"))
                    .setTextColor(R.id.tv_recharge_explain, Color.parseColor("#00aeff"));
        } else {
            helper.setBackgroundRes(R.id.ll_content_layout, R.drawable.bg_ll_nofocus_round_conner)
                    .setTextColor(R.id.tv_recharge_amount, Color.parseColor("#a3a3a3"))
                    .setTextColor(R.id.tv_recharge_explain, Color.parseColor("#a3a3a3"));
        }
        helper.setText(R.id.tv_recharge_amount, item.getAmount() + "元");
        if (TextUtils.equals("0", item.getDonate())) {
            helper.setText(R.id.tv_recharge_explain, item.getAmount() + "金币");
        } else {
            helper.setText(R.id.tv_recharge_explain, item.getAmount() + "+" + item.getDonate() + "金币");
        }
    }
}
