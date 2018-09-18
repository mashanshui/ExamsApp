package com.shenhesoft.examsapp.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.AdviceModel;

import java.util.List;

public class AdviceAdapter extends BaseQuickAdapter<AdviceModel, BaseViewHolder> {

    public AdviceAdapter(@Nullable List<AdviceModel> data) {
        super(R.layout.recycler_list_advice, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AdviceModel item) {
        if (TextUtils.equals("投诉", item.getOptType())) {
            helper.setText(R.id.tv_title, "【投诉】" + item.getTitle())
                    .setText(R.id.tv_message, item.getApplyContent())
                    .setText(R.id.tv_deal_status, "【" + item.getItemName() + "】")
                    .setText(R.id.tv_date, item.getCreateTime());
            ImageView imageView = helper.getView(R.id.iv_image);
            Glide.with(mContext)
                    .load(R.drawable.advice_complain)
                    .into(imageView);
        } else {
            helper.setText(R.id.tv_title, "【建议】" + item.getTitle())
                    .setText(R.id.tv_message, item.getApplyContent())
                    .setText(R.id.tv_deal_status, "【" + item.getItemName() + "】")
                    .setText(R.id.tv_date, item.getCreateTime());
            ImageView imageView = helper.getView(R.id.iv_image);
            Glide.with(mContext)
                    .load(R.drawable.advice_advice)
                    .into(imageView);
        }
    }
}
