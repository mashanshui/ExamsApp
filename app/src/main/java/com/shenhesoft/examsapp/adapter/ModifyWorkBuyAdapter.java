package com.shenhesoft.examsapp.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.bean.ModifyWorkBean;
import com.shenhesoft.examsapp.network.model.ProductModel;

import java.util.List;

import cn.droidlover.xdroidmvp.net.HttpConstant;

/**
 * @author mashanshui
 * @date 2018/5/17
 * @desc TODO
 */
public class ModifyWorkBuyAdapter extends BaseSectionQuickAdapter<ModifyWorkBean, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ModifyWorkBuyAdapter(List<ModifyWorkBean> data) {
        super(R.layout.recycler_list_modify_work_buy, R.layout.recycler_modify_work_head, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, ModifyWorkBean item) {
        helper.setText(R.id.tv_head, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, ModifyWorkBean item) {
        ProductModel productModel = item.t;
        int color;
        if (TextUtils.equals("已完成", productModel.getItemName())) {
            color = mContext.getResources().getColor(R.color.colorPrimary);
        } else {
            color = Color.RED;
        }
        helper.setText(R.id.tv_teacher_intro, productModel.getProductTitle())
                .setText(R.id.tv_teacher_subject, productModel.getArticleTypeName())
                .setText(R.id.tv_teacher_name, productModel.getTeacherName())
                .setText(R.id.tv_status, "【" + productModel.getItemName() + "】")
                .setTextColor(R.id.tv_status, color);
        Glide.with(mContext).load(HttpConstant.BASE_IP + productModel.getOrderPic())
                .placeholder(R.drawable.placeholder)
                .dontAnimate()
                .into((ImageView) helper.getView(R.id.iv_teacher_icon));
    }

}
