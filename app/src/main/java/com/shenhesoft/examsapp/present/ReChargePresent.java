package com.shenhesoft.examsapp.present;

import android.text.TextUtils;

import com.shenhesoft.examsapp.adapter.bean.RechargeBean;
import com.shenhesoft.examsapp.network.UserRetrofit;
import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.network.model.PayModel;
import com.shenhesoft.examsapp.network.model.PayResultModel;
import com.shenhesoft.examsapp.network.model.RechargeWayModel;
import com.shenhesoft.examsapp.ui.activity.user.ReChargeActivity;

import java.util.List;

import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import io.reactivex.Observable;

/**
 * @author mashanshui
 * @date 2018/6/1
 * @desc TODO
 */
public class ReChargePresent extends XPresent<ReChargeActivity> {
    private UserService userService;

    public ReChargePresent() {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
    }

    public void loadData() {
        Observable<RequestResults<ListALLResults<RechargeWayModel>>> observable = userService.getGoldSection(UserRetrofit.getInstance().getGoldSection(0, 10));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<RechargeWayModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<RechargeWayModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<RechargeWayModel>> data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                List<RechargeWayModel> modelList = data.getObj().getRows();
                getV().updateData(modelList);
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }


    public void startPay(RechargeBean selectBean, final int payWay) {
        if (selectBean == null) {
            IToast.showShort("请选择金额");
            return;
        }
        getV().showWaitDialog();
        Observable<RequestResults<PayModel>> observable = userService.recharge(UserRetrofit.getInstance().recharge(selectBean.getAmount(), 1, selectBean.getGoldDiscountPolicyId(), payWay));
        HttpObserver httpObserver = new HttpObserver<RequestResults<PayModel>>(false,new HttpObserver.OnNextListener<RequestResults<PayModel>>() {
            @Override
            public void onNext(RequestResults<PayModel> data) {
                if (data.getState() != 201) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                if (payWay == 1 && !TextUtils.isEmpty(data.getObj().getAliStr())) {
                    //支付宝
                    getV().startAliPay(data.getObj().getAliStr());
                } else if (payWay == 2 && !TextUtils.isEmpty(data.getObj().getOrderId()) && !TextUtils.isEmpty(data.getObj().getPrice())) {
                    //微信
                    prepareWechatPay(data.getObj().getOrderId(), data.getObj().getPrice());
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    public void prepareWechatPay(String orderId, String price) {
        Observable<RequestResults<PayResultModel>> observable = userService.prepareWechatPay(UserRetrofit.getInstance().prepareWechatPay(orderId, price));
        HttpObserver httpObserver = new HttpObserver<RequestResults<PayResultModel>>(false,new HttpObserver.OnNextListener<RequestResults<PayResultModel>>() {
            @Override
            public void onNext(RequestResults<PayResultModel> data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                PayResultModel.ParamBean paramBean = data.getObj().getParam();
                getV().startWechatPay(paramBean);
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
