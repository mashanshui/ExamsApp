package com.shenhesoft.examsapp.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.AddressAdapter;
import com.shenhesoft.examsapp.network.model.AddressModel;
import com.shenhesoft.examsapp.present.AddressPresent;
import com.shenhesoft.examsapp.util.GridItemDecoration;
import com.shenhesoft.examsapp.view.AddressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/5/10
 * @desc 管理地址
 */
public class AddressActivity extends XTitleActivity<AddressPresent> implements AddressView {
    public static final int RequestCode = 100;
    public static final int ResultCode = 101;
    @BindView(R.id.btn_add_address)
    QMUIRoundButton btnAddAddress;
    @BindView(R.id.rv_address)
    RecyclerView rvAddress;
    private AddressAdapter addressAdapter;
    private List<AddressModel> addressBeanList;
    private int type;
    private boolean isModifyDefaultAddress = false;
    private boolean isDefaultCheck;
    private String defaultAddressId;

    @Override
    protected void initTitle() {
        type = getIntent().getIntExtra("payActivity", 0);
        setBackAction();
        setTitle("地址管理");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        btnAddAddress.setChangeAlphaWhenPress(true);
        addressBeanList = new ArrayList<>();
        addressAdapter = new AddressAdapter(addressBeanList);
        rvAddress.setLayoutManager(new LinearLayoutManager(context));
        rvAddress.addItemDecoration(new GridItemDecoration(context, R.drawable.recycler_menu_divider));
        rvAddress.setAdapter(addressAdapter);
        addressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (type == 1) {
                    Intent intent = new Intent();
                    intent.putExtra("address", addressBeanList.get(position));
                    setResult(ResultCode, intent);
                    finish();
                }
            }
        });
        addressAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.cb_is_default:
                        defaultAddressId = addressBeanList.get(position).getId();
                        CheckBox checkBox = view.findViewById(R.id.cb_is_default);
                        isDefaultCheck = checkBox.isChecked();
                        if (isDefaultCheck) {
                            isModifyDefaultAddress = true;
                            getP().loadData();
                        } else {
                            getP().defaultAddress(defaultAddressId, isDefaultCheck ? 1 : 0);
                        }
                        break;
                    case R.id.tv_delete:
                        showConfirmDialog(position);
                        break;
                    case R.id.tv_modify:
                        Router.newIntent(context).to(AddAddressActivity.class)
                                .requestCode(RequestCode)
                                .putInt("type", 1)
                                .putSerializable("address", addressBeanList.get(position))
                                .launch();
                        break;
                    default:
                        break;
                }
            }
        });
        getP().loadData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_address;
    }

    @Override
    public AddressPresent newP() {
        return new AddressPresent();
    }

    private void showConfirmDialog(final int position) {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_confirm_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder viewHolder, final BaseNiceDialog baseNiceDialog) {
                        viewHolder.setText(R.id.tv_title, "提示");
                        viewHolder.setText(R.id.tv_message, "删除地址");
                        viewHolder.setOnClickListener(R.id.tv_confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getP().deleteAddress(addressBeanList.get(position).getId());
                                baseNiceDialog.dismiss();
                            }
                        });
                        viewHolder.setOnClickListener(R.id.tv_cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                baseNiceDialog.dismiss();
                            }
                        });
                    }
                })
                .setWidth(300)
                .setHeight(150)
                .show(getSupportFragmentManager());
    }

    @OnClick(R.id.btn_add_address)
    public void onViewClicked() {
        Router.newIntent(context).to(AddAddressActivity.class)
                .requestCode(RequestCode)
                .putInt("type", 0)
                .launch();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCode || resultCode == 1001) {
            getP().loadData();
        }
    }

    @Override
    public void updateData(List<AddressModel> addressModelList) {
        if (!addressBeanList.isEmpty()) {
            addressBeanList.clear();
        }
        addressBeanList.addAll(addressModelList);
        addressAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteSuccess() {
        getP().loadData();
    }

    @Override
    public void defaultSuccess() {
        getP().loadData();
    }

    @Override
    public boolean isModifyDefaultAddress() {
        return isModifyDefaultAddress;
    }

    @Override
    public void updateDefaultData(List<AddressModel> addressModel) {
        getP().defaultAddress(addressModel.get(0).getId(), 0);
    }

    @Override
    public void defaultOldSuccess() {
        isModifyDefaultAddress = false;
        getP().defaultAddress(defaultAddressId, isDefaultCheck ? 1 : 0);
    }

}
