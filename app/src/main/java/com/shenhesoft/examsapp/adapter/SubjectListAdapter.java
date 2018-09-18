package com.shenhesoft.examsapp.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.ProductModel;

import java.util.List;

import cn.droidlover.xdroidmvp.net.HttpConstant;

/**
 * @author mashanshui
 * @date 2018/7/3
 * @desc TODO
 */
public class SubjectListAdapter extends BaseQuickAdapter<ProductModel, BaseViewHolder> {
    public static final int NOTBUY = AppConstant.NotBuy;
    public static final int BUY = AppConstant.Buy;
    public static final int FREE = AppConstant.Free;

    public SubjectListAdapter(@Nullable List<ProductModel> data) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<ProductModel>() {
            @Override
            protected int getItemType(ProductModel subjectListBean) {
                if (TextUtils.equals(subjectListBean.getStatusOrderName(), "未购买")) {
                    if (TextUtils.equals(subjectListBean.getProductFreeName(), "免费")) {
                        return FREE;
                    }
                    return NOTBUY;
                }
                return BUY;
            }
        });
        getMultiTypeDelegate().registerItemType(NOTBUY, R.layout.recycler_list_able_subject)
                .registerItemType(BUY, R.layout.recycler_list_enable_subject)
                .registerItemType(FREE, R.layout.recycler_list_enable_subject_free);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductModel item) {
        ImageView imageView1 = helper.getView(R.id.iv_subject_icon);
        Glide.with(mContext).load(HttpConstant.BASE_IP + item.getRemark())
                .placeholder(R.drawable.placeholder)
//                .dontAnimate()
                .into(imageView1);
        switch (helper.getItemViewType()) {
            case NOTBUY:
                helper.setText(R.id.tv_message, item.getProductTitle())
                        .setText(R.id.tv_price, item.getProductPrice())
                        .setText(R.id.tv_payment_number, item.getSalNumShow() + "人付款")
                        .setText(R.id.tv_teacher_name, item.getAuthorUserName());
                break;
            case BUY:
                helper.setText(R.id.tv_message, item.getProductTitle())
                        .setText(R.id.tv_buy_or_free, "【已购】")
                        .setText(R.id.tv_teacher_name, item.getAuthorUserName());
                break;
            case FREE:
                helper.setText(R.id.tv_message, item.getProductTitle())
                        .setText(R.id.tv_buy_or_free, "【免费】")
                        .setText(R.id.tv_teacher_name, item.getAuthorUserName());
                break;
            default:
                break;
        }
    }
}
