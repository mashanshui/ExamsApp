package com.shenhesoft.examsapp.ui.activity.user;

import android.os.Bundle;
import android.widget.EditText;

import com.allen.library.SuperTextView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.AddressModel;
import com.shenhesoft.examsapp.present.AddAddressPresent;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;

/**
 * @author mashanshui
 * @date 2018/5/10
 * @desc 添加地址
 */
public class AddAddressActivity extends XTitleActivity<AddAddressPresent> {

    public static final int ResultCode = 1001;
    @BindView(R.id.et_receipt_name)
    EditText etReceiptName;
    @BindView(R.id.et_receipt_phone)
    EditText etReceiptPhone;
    @BindView(R.id.et_receipt_address)
    EditText etReceiptAddress;
    @BindView(R.id.tv_is_default)
    SuperTextView tvIsDefault;
    @BindView(R.id.btn_confirm)
    QMUIRoundButton btnConfirm;

    /**
     * 类型
     * 0新增地址
     * 1修改地址
     */
    private int type;
    /**
     * 修改地址时传过来旧地址
     */
    private AddressModel addressModel;

    @Override
    protected void initTitle() {
        type = getIntent().getIntExtra("type", 0);
        addressModel = (AddressModel) getIntent().getSerializableExtra("address");
        setBackAction();
        if (type == 0) {
            setTitle("新增收货地址");
        } else {
            setTitle("修改地址");
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        btnConfirm.setChangeAlphaWhenPress(true);
        if (addressModel != null) {
            etReceiptName.setText(addressModel.getReceiverName());
            etReceiptAddress.setText(addressModel.getAddress());
            etReceiptPhone.setText(addressModel.getContactWay());
            if (addressModel.getDefaultFlag() == 1) {
                tvIsDefault.setCbChecked(true);
            } else {
                tvIsDefault.setCbChecked(false);
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    public AddAddressPresent newP() {
        return new AddAddressPresent();
    }

    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
        if (type == 0) {
            if (tvIsDefault.getCbisChecked()) {
                getP().loadData();
            } else {
                getP().addAddress();
            }
        } else if (type == 1 && addressModel != null) {
            if (tvIsDefault.getCbisChecked()) {
                getP().loadData();
            } else {
                getP().modifyAddress(addressModel.getId());
            }
        }
    }

    public void addSuccess() {
        IToast.showShort("添加成功");
        setResult(ResultCode);
        finish();
    }

    public void modifySuccess() {
        IToast.showShort("修改成功");
        setResult(ResultCode);
        finish();
    }

    public void updateDefaultData(List<AddressModel> addressModels) {
        if (addressModels.isEmpty()) {
            if (type == 0) {
                getP().addAddress();
            } else {
                getP().modifyAddress(addressModel.getId());
            }
        } else {
            getP().defaultAddress(addressModels.get(0).getId(), 0);
        }
    }

    public void defaultOldSuccess() {
        if (type == 0) {
            getP().addAddress();
        } else {
            getP().modifyAddress(addressModel.getId());
        }
    }

    public String getReceiptName() {
        return etReceiptName.getText().toString();
    }

    public String getReceiptPhone() {
        return etReceiptPhone.getText().toString();
    }

    public String getReceiptAddress() {
        return etReceiptAddress.getText().toString();
    }

    public boolean getIsDefault() {
        return tvIsDefault.getCbisChecked();
    }
}
