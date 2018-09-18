package com.shenhesoft.examsapp.adapter;

import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.AchieveModel;

import java.util.List;

/**
 * @author mashanshui
 * @date 2018/5/21
 * @desc TODO
 */
public class AchieveAdapter extends BaseQuickAdapter<AchieveModel.ListBean, BaseViewHolder> {

    public AchieveAdapter(@Nullable List<AchieveModel.ListBean> data) {
        super(R.layout.recycler_list_my_achieve, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AchieveModel.ListBean item) {
        Typeface mtypeface = Typeface.createFromAsset(mContext.getAssets(), "achievefont.TTF");
        TextView textView = helper.getView(R.id.tv_achieve_name);
        textView.setTypeface(mtypeface);

        ImageView imageView = helper.getView(R.id.achieve_icon);
        helper.setText(R.id.tv_achieve_name, item.getMedalName());
        switch (item.getMedalImg()) {
            case "1":
                Glide.with(mContext).load(R.drawable.achieve1).into(imageView);
                break;
            case "2":
                Glide.with(mContext).load(R.drawable.achieve2).into(imageView);
                break;
            case "3":
                Glide.with(mContext).load(R.drawable.achieve3).into(imageView);
                break;
            default:
                Glide.with(mContext).load(R.drawable.achieve_default_icon).into(imageView);
                break;
        }
        if (TextUtils.isEmpty(item.getRemark())) {
            helper.setText(R.id.tv_get_times, "未解锁");
            Glide.with(mContext).load(R.drawable.achieve_default_icon).into(imageView);
        } else {
            helper.setText(R.id.tv_get_times, "已获得"+item.getCount()+"次");
        }
    }

}
