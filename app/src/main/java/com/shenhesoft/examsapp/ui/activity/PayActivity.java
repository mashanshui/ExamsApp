package com.shenhesoft.examsapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.AddressModel;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.present.PayPresent;
import com.shenhesoft.examsapp.ui.activity.user.AddressActivity;
import com.shenhesoft.examsapp.ui.activity.user.ReChargeActivity;
import com.shenhesoft.examsapp.util.keyboard.widget.PopEnterPassword;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;
import cn.droidlover.xdroidmvp.net.HttpConstant;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/5/18
 * @desc 支付界面
 */
public class PayActivity extends XTitleActivity<PayPresent> {
    public static final int RequestCode = 100;
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    @BindView(R.id.tv_receiving_people)
    TextView tvReceivingPeople;
    @BindView(R.id.tv_receive_phone)
    TextView tvReceivePhone;
    @BindView(R.id.tv_receive_address)
    TextView tvReceiveAddress;
    @BindView(R.id.tv_order_amount)
    TextView tvOrderAmount;
    @BindView(R.id.btn_confirm)
    QMUIRoundButton btnConfirm;
    @BindView(R.id.cl_address)
    ConstraintLayout clAddress;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.tv_goods_message)
    TextView tvGoodsMessage;
    @BindView(R.id.iv_goods_icon)
    ImageView ivGoodsIcon;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;

    private ProductModel productModel;
    private AddressModel address;

    @Override
    protected void initTitle() {
        productModel = (ProductModel) getIntent().getSerializableExtra("productDetail");
        setBackAction();
        setTitle("支付订单");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        btnConfirm.setChangeAlphaWhenPress(true);
        if (productModel != null) {
            updateUI();
        }
        getP().getAddress();
    }

    private void updateUI() {
        tvOrderAmount.setText(productModel.getProductPrice());
        tvGoodsMessage.setText(productModel.getProductTitle());
        tvGoodsPrice.setText(productModel.getProductPrice());
        Glide.with(context).load(HttpConstant.BASE_IP + productModel.getRemark())
                .placeholder(R.drawable.placeholder)
                .into(ivGoodsIcon);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public PayPresent newP() {
        return new PayPresent();
    }

    @OnClick({R.id.cl_address, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cl_address:
                Router.newIntent(context).to(AddressActivity.class)
                        .putInt("payActivity", 1)
                        .requestCode(RequestCode)
                        .launch();
                break;
            case R.id.btn_confirm:
                //课件必须设置地址
                if (address == null && productModel.getProductType() == 2) {
                    IToast.showShort("请添加地址，部分课件需邮寄讲义");
                    return;
                }
                getP().isHavePayPsw();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCode && resultCode == 101 && data != null) {
            updateAddress((AddressModel) data.getSerializableExtra("address"));
        }
    }

    public void updateAddress(AddressModel address) {
        this.address = address;
        tvReceivingPeople.setText(address.getReceiverName());
        tvReceiveAddress.setText(address.getAddress());
        tvReceivePhone.setText(address.getContactWay());
    }

    private void showMessagePositiveDialog(String message) {
        new QMUIDialog.MessageDialogBuilder(context)
                .setTitle("确认商品信息")
                .setMessage(message)
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        showPayKeyBoard();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }


    public void showPayKeyBoard() {
        PopEnterPassword popEnterPassword = new PopEnterPassword(this);
        popEnterPassword.setPrice(productModel.getProductPrice());
        // 显示窗口
        popEnterPassword.showAtLocation(this.findViewById(R.id.layoutContent),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popEnterPassword.setOnInputListener(new PopEnterPassword.OnInputListener() {
            @Override
            public void onInputFinish(String password) {
                getP().buy(password, TextUtils.isEmpty(productModel.getId()) ? productModel.getProductId() : productModel.getId(), address == null ? "" : address.getId());
            }
        });
    }

    public void havePayPsw() {
        String message = null;
        int type = productModel.getProductType();
        String typeName;
        if (type == 1) {
            typeName = "题库";
        } else if (type == 2) {
            typeName = "课件";
        } else {
            typeName = "作文批改";
        }
        switch (productModel.getProductType()) {
            case 1:
                message = "商品名称：" + productModel.getProductTitle() + "\n"
                        + "商品类型：" + typeName;
                break;
            case 2:
                message = "商品名称：" + productModel.getProductTitle() + "\n"
                        + "商品类型：" + typeName;
                break;
            case 3:
                message = "商品名称：" + productModel.getProductTitle() + "\n"
                        + "商品类型：" + typeName + "\n"
                        + "题源：" + productModel.getArticleSource() + "\n"
                        + "年份：" + productModel.getArticleYear();
                break;
            default:
                break;
        }
        showMessagePositiveDialog(message);
    }

    public void nonePatPsw() {
        IToast.showLong("请前往个人中心设置支付密码");
    }

    public void paySuccess(String orderId) {
        IToast.showShort("购买成功");
        Intent intent = new Intent();
        intent.putExtra("orderId", orderId);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void showConfirmDialog(final String msg) {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_confirm_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder viewHolder, final BaseNiceDialog baseNiceDialog) {
                        viewHolder.setText(R.id.tv_title, "提示");
                        viewHolder.setText(R.id.tv_confirm,"去充值");
                        viewHolder.setText(R.id.tv_cancel,"取消");
                        viewHolder.setText(R.id.tv_message, msg);
                        viewHolder.setOnClickListener(R.id.tv_confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                baseNiceDialog.dismiss();
                                Router.newIntent(context).to(ReChargeActivity.class).launch();
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
}
