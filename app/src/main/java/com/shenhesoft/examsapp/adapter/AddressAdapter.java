package com.shenhesoft.examsapp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.AddressModel;

import java.util.List;

/**
 * @author mashanshui
 * @date 2018/5/10
 * @desc TODO
 */
public class AddressAdapter extends BaseQuickAdapter<AddressModel, BaseViewHolder> {

    public AddressAdapter(@Nullable List<AddressModel> data) {
        super(R.layout.recycler_list_address, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressModel item) {
        boolean isCheck;
        if (item.getDefaultFlag() == 1) {
            isCheck = true;
        } else {
            isCheck = false;
        }
        helper.setText(R.id.tv_receipt_username, item.getReceiverName())
                .setText(R.id.tv_receipt_phone, item.getContactWay())
                .setText(R.id.tv_receipt_address, item.getAddress())
                .setChecked(R.id.cb_is_default, isCheck)
                .addOnClickListener(R.id.cb_is_default)
                .addOnClickListener(R.id.tv_modify)
                .addOnClickListener(R.id.tv_delete);
    }
}
