package com.shenhesoft.examsapp.ui.activity.user;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.RechargeAdapter;
import com.shenhesoft.examsapp.adapter.bean.RechargeBean;
import com.shenhesoft.examsapp.network.model.PayResultModel;
import com.shenhesoft.examsapp.network.model.RechargeWayModel;
import com.shenhesoft.examsapp.present.ReChargePresent;
import com.shenhesoft.examsapp.util.PayResult;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhuyong.countdownciew.CountDownView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;

/**
 * @author mashanshui
 * @date 2018/5/19
 * @desc 充值
 */
public class ReChargeActivity extends XTitleActivity<ReChargePresent> {
    public static final int ResultCode = 101;
    @BindView(R.id.btn_recharge)
    QMUIRoundButton btnRecharge;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.st_alipay)
    SuperTextView stAlipay;
    @BindView(R.id.st_wechatpay)
    SuperTextView stWechatpay;

    private RechargeAdapter rechargeAdapter;
    private List<RechargeBean> rechargeBeans;
    private static final int SDK_PAY_FLAG = 1;

    private BaseNiceDialog waitDialog;

    private IWXAPI api;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    // 同步返回需要验证的信息
                    String resultInfo = payResult.getResult();
                    // 判断resultStatus 为9000则代表支付成功
                    String resultStatus = payResult.getResultStatus();
                    Log.e(TAG, "handleMessage: " + resultInfo + resultStatus);
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void initTitle() {
        setBackAction();
        setTitle("充值");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        btnRecharge.setChangeAlphaWhenPress(true);
        stAlipay.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                stWechatpay.setCbChecked(false);
                superTextView.setCbChecked(true);
            }
        }).setCheckBoxCheckedChangeListener(new SuperTextView.OnCheckBoxCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    stWechatpay.setCbChecked(false);
                }
            }
        });

        stWechatpay.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                stAlipay.setCbChecked(false);
                superTextView.setCbChecked(true);
            }
        }).setCheckBoxCheckedChangeListener(new SuperTextView.OnCheckBoxCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    stAlipay.setCbChecked(false);
                }
            }
        });

        rechargeBeans = new ArrayList<>();
        rechargeAdapter = new RechargeAdapter(rechargeBeans);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setAdapter(rechargeAdapter);
        rechargeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                refreshStatus();
                rechargeBeans.get(position).setIsSelect(RechargeAdapter.SELECT);
                rechargeAdapter.notifyDataSetChanged();
            }
        });

        getP().loadData();
        //初始化微信支付
        api = WXAPIFactory.createWXAPI(this, AppConstant.WeChatAppId);
    }

    private void refreshStatus() {
        for (int i = 0; i < rechargeBeans.size(); i++) {
            rechargeBeans.get(i).setIsSelect(RechargeAdapter.NOTSELECT);
        }
    }


    /**
     * @param rechargeBeanList 创建六条价格数据（确定的数据50、100、200、500、1000、2000）
     */
    public void updateData(List<RechargeWayModel> rechargeBeanList) {
        RechargeBean bean1 = new RechargeBean();
        bean1.setAmount("50");
        bean1.setDonate(getDonateByList(rechargeBeanList, 50));
        bean1.setGoldDiscountPolicyId(getIdByList(rechargeBeanList, 50));
        rechargeBeans.add(bean1);

        RechargeBean bean2 = new RechargeBean();
        bean2.setAmount("100");
        bean2.setDonate(getDonateByList(rechargeBeanList, 100));
        bean2.setGoldDiscountPolicyId(getIdByList(rechargeBeanList, 100));
        rechargeBeans.add(bean2);

        RechargeBean bean3 = new RechargeBean();
        bean3.setAmount("200");
        bean3.setDonate(getDonateByList(rechargeBeanList, 200));
        bean3.setGoldDiscountPolicyId(getIdByList(rechargeBeanList, 200));
        rechargeBeans.add(bean3);

        RechargeBean bean4 = new RechargeBean();
        bean4.setAmount("500");
        bean4.setDonate(getDonateByList(rechargeBeanList, 500));
        bean4.setGoldDiscountPolicyId(getIdByList(rechargeBeanList, 500));
        rechargeBeans.add(bean4);

        RechargeBean bean5 = new RechargeBean();
        bean5.setAmount("1000");
        bean5.setDonate(getDonateByList(rechargeBeanList, 1000));
        bean5.setGoldDiscountPolicyId(getIdByList(rechargeBeanList, 1000));
        rechargeBeans.add(bean5);

        RechargeBean bean6 = new RechargeBean();
        bean6.setAmount("2000");
        bean6.setDonate(getDonateByList(rechargeBeanList, 2000));
        bean6.setGoldDiscountPolicyId(getIdByList(rechargeBeanList, 2000));
        rechargeBeans.add(bean6);

        rechargeAdapter.notifyDataSetChanged();
    }

    /**
     * @param modelList
     * @param price
     * @return 返回当前价格所在区间的id
     */
    private String getIdByList(List<RechargeWayModel> modelList, int price) {
        for (int i = 0; i < modelList.size(); i++) {
            RechargeWayModel model = modelList.get(i);
            if (model.getPrice() <= price && price <= model.getPolicyBaseValue()) {
                return model.getId();
            }
        }
        return "";
    }

    /**
     * @param modelList
     * @param price
     * @return 返回当前价格所在区间的赠送金币数量
     */
    private String getDonateByList(List<RechargeWayModel> modelList, int price) {
        for (int i = 0; i < modelList.size(); i++) {
            RechargeWayModel model = modelList.get(i);
            if (model.getPrice() <= price && price <= model.getPolicyBaseValue()) {
                return model.getPolicyAddonValue() + "";
            }
        }
        return "0";
    }

    public void startAliPay(final String orderInfo) {
        if (waitDialog != null) {
            waitDialog.dismiss();
        }
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(ReChargeActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public void startWechatPay(PayResultModel.ParamBean paramBean) {
        if (waitDialog != null) {
            waitDialog.dismiss();
        }
        api.registerApp(AppConstant.WeChatAppId);
        PayReq req = new PayReq();
        req.appId = paramBean.getAppid();
        req.partnerId = paramBean.getPartnerid();
        req.prepayId = paramBean.getPrepayid();
        req.nonceStr = paramBean.getNoncestr();
        req.timeStamp = paramBean.getTimestamp();
        req.packageValue = paramBean.getPackageStr();
        req.sign = paramBean.getSign();
        api.sendReq(req);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_re_charge;
    }

    @Override
    public ReChargePresent newP() {
        return new ReChargePresent();
    }

    @OnClick(R.id.btn_recharge)
    public void onViewClicked() {
        setResult(ResultCode);
        if (stAlipay.getCbisChecked()) {
            getP().startPay(getSelectBean(), 1);
        } else if (stWechatpay.getCbisChecked()) {
            getP().startPay(getSelectBean(), 2);
        } else {
            IToast.showShort("请选择支付方式");
        }
    }

    private RechargeBean getSelectBean() {
        for (int i = 0; i < rechargeBeans.size(); i++) {
            RechargeBean bean = rechargeBeans.get(i);
            if (bean.getIsSelect() == RechargeAdapter.SELECT) {
                return bean;
            }
        }
        return null;
    }

    public void showWaitDialog() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_recharge_wait)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder viewHolder, final BaseNiceDialog baseNiceDialog) {
                        waitDialog = baseNiceDialog;
                        viewHolder.setText(R.id.tv_title, "提示");
                        CountDownView countDownView = viewHolder.getView(R.id.countDownView);
                        countDownView.setOnLoadingFinishListener(new CountDownView.OnLoadingFinishListener() {
                            @Override
                            public void finish() {
                                baseNiceDialog.dismiss();
                            }
                        });
                        countDownView.start();
                    }
                })
                .setWidth(300)
                .setHeight(150)
                .setOutCancel(false)
                .show(getSupportFragmentManager());
    }
}
