package com.shenhesoft.examsapp.adapter;

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
 * @date 2018/5/28
 * @desc TODO
 */
public class ModifyWorkRecommendAdapter extends BaseSectionQuickAdapter<ModifyWorkBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ModifyWorkRecommendAdapter(List<ModifyWorkBean> data) {
        super(R.layout.recycler_list_modify_work_not_buy, R.layout.recycler_modify_work_head, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, ModifyWorkBean item) {
        helper.setText(R.id.tv_head, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, ModifyWorkBean item) {
        ProductModel productModel = item.t;
        helper.setText(R.id.tv_teacher_intro, productModel.getProductTitle());
        Glide.with(mContext).load(HttpConstant.BASE_IP + productModel.getRemark())
                .placeholder(R.drawable.placeholder)
                .into((ImageView) helper.getView(R.id.iv_teacher_icon));
    }
}
