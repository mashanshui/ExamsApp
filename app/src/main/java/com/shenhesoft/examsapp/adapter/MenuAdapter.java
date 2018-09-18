package com.shenhesoft.examsapp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.bean.MenuListBean;

import java.util.List;

/**
 * @author mashanshui
 * @date 2018/5/9
 * @desc TODO
 */
public class MenuAdapter extends BaseQuickAdapter<MenuListBean, BaseViewHolder> {

    private int layoutId;

    public MenuAdapter(int layoutResId, @Nullable List<MenuListBean> data) {
        super(layoutResId, data);
        layoutId = layoutResId;
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuListBean item) {
        if (layoutId == R.layout.recycler_list_person_menu) {
            helper.setText(R.id.tv_message, item.getResTitleId())
                    .setBackgroundRes(R.id.iv_indicate_image, item.getResIconId())
                    .setText(R.id.tv_message_count, item.getMessageCount());
        } else if (layoutId == R.layout.recycler_list_setting) {
            helper.setText(R.id.tv_message, item.getResTitleId());
        }
    }
}
