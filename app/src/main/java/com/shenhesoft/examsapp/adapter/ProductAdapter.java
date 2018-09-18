package com.shenhesoft.examsapp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.ProductModel;

import java.util.List;

import cn.droidlover.xdroidmvp.net.HttpConstant;

/**
 * @author mashanshui
 * @date 2018/5/18
 * @desc TODO
 */
public class ProductAdapter extends BaseQuickAdapter<ProductModel, BaseViewHolder> {

    public ProductAdapter(@Nullable List<ProductModel> data) {
        super(R.layout.recycler_list_online_product, data);
    }

    /**
     * @param helper
     * @param item
     */
    @Override
    protected void convert(BaseViewHolder helper, ProductModel item) {
        String type = "";
        if (item.getProductType() == AppConstant.QuestionBank) {
            type = "题库";
        } else if (item.getProductType() == AppConstant.CourseWare) {
            type = "课件";
        } else if (item.getProductType() == AppConstant.WritingCheck) {
            type = "作文批改";
        }
        helper.setText(R.id.tv_product_intro, "【" + type + "】" + item.getProductTitle())
                .setText(R.id.tv_product_owner, item.getAuthorUserName())
                .setText(R.id.tv_product_price, item.getProductPrice())
                .setText(R.id.tv_product_subject, item.getScienceDomainName())
                .setText(R.id.tv_buy_number, item.getSalNumShow() + "人付款");
        Glide.with(mContext).load(HttpConstant.BASE_IP + item.getRemark())
                .placeholder(R.drawable.placeholder)
                .into((ImageView) helper.getView(R.id.iv_product_image));
    }
}
