package com.shenhesoft.examsapp.view;

import com.shenhesoft.examsapp.network.model.AddressModel;
import com.shenhesoft.examsapp.present.AddressPresent;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * @author mashanshui
 * @date 2018/6/9
 * @desc TODO
 */
public interface AddressView extends IView<AddressPresent> {
    void updateData(List<AddressModel> addressModelList);

    void deleteSuccess();

    void defaultSuccess();

    boolean isModifyDefaultAddress();

    void updateDefaultData(List<AddressModel> addressModel);

    void defaultOldSuccess();
}
