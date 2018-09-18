package com.shenhesoft.examsapp.adapter;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.AnswerModel;

import java.util.List;

import cn.droidlover.xdroidmvp.net.HttpConstant;

/**
 * @author mashanshui
 * @date 2018/5/11
 * @desc TODO
 */
public class SubjectOptionAdapter extends BaseQuickAdapter<AnswerModel.OptionsBean, BaseViewHolder> {

    public SubjectOptionAdapter(@Nullable List<AnswerModel.OptionsBean> data) {
        super(R.layout.recycler_list_subject_option, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final AnswerModel.OptionsBean item) {
        boolean isSelect;
        isSelect = item.getOptionSelect() == AppConstant.Select;
        helper.setChecked(R.id.cb_is_select, isSelect);
        helper.setText(R.id.tv_subject_option, item.getContent());
        final ImageView imageView = helper.getView(R.id.iv_option_image);
        if (!TextUtils.isEmpty(item.getRemark())) {
            helper.setVisible(R.id.iv_option_image, true);
            Glide.with(mContext).load(HttpConstant.BASE_IP + item.getRemark()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    imageView.setImageBitmap(resource);
                }
            });
        }
    }
}
