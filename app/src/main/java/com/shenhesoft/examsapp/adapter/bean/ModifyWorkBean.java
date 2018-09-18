package com.shenhesoft.examsapp.adapter.bean;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.shenhesoft.examsapp.network.model.ProductModel;

/**
 * @author mashanshui
 * @date 2018/5/17
 * @desc TODO
 */
public class ModifyWorkBean extends SectionEntity<ProductModel> {

    public ModifyWorkBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public ModifyWorkBean(ProductModel t) {
        super(t);
    }
}
