package com.shenhesoft.examsapp.present;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.shenhesoft.examsapp.network.UserRetrofit;
import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.network.model.AddressModel;
import com.shenhesoft.examsapp.network.model.PayPswModel;
import com.shenhesoft.examsapp.ui.activity.PayActivity;
import com.shenhesoft.examsapp.util.ActivityUtil;

import java.io.IOException;

import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import cn.droidlover.xdroidmvp.net.entity.RequestResultsList;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * @author mashanshui
 * @date 2018/6/7
 * @desc TODO
 */
public class PayPresent extends XPresent<PayActivity> {
    private UserService userService;

    public PayPresent() {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
    }

    public void buy(String password, String id, String addressId) {
        Observable<RequestResults> observable = userService.buy(UserRetrofit.getInstance().buy(password, id, addressId));
        HttpObserver httpObserver = new HttpObserver<RequestResults>(new HttpObserver.OnNextListener<RequestResults>() {
            @Override
            public void onNext(RequestResults data) {
                if (data.getState() != 204) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                String orderId = (String) data.getObj();
                getV().paySuccess(orderId);
            }
        }, new HttpObserver.OnErrorListener() {
            @Override
            public void onError(Throwable throwable) {
                HttpException he = (HttpException) throwable;
                ResponseBody responseBody = he.response().errorBody();
                HttpObserver.ErrorMessage message = null;
                try {
                    Gson gson = new Gson();
                    message = gson.fromJson(responseBody.string(), HttpObserver.ErrorMessage.class);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if (message == null) {
                    return;
                }
                if (TextUtils.isEmpty(message.getMessage())) {
                    IToast.showShort("服务器发生错误（HTTP_CODE:" + he.code() + ")");
                } else {
                    if (message.getCode() == 902) {
                        getV().showConfirmDialog(message.getMessage());
                        return;
                    }
                    IToast.showShort(message.getMessage());
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    public void getAddress() {
        Observable<RequestResults<ListALLResults<AddressModel>>> observable = userService.lookAddress(UserRetrofit.getInstance().lookAddress(0, 1));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<AddressModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<AddressModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<AddressModel>> data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                if (data.getObj().getRows().isEmpty()) {
                    return;
                }
                getV().updateAddress(data.getObj().getRows().get(0));
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    public void isHavePayPsw() {
        Observable<RequestResultsList<PayPswModel>> observable = userService.isHavePayPsw(ActivityUtil.getUserID());
        HttpObserver httpObserver = new HttpObserver<RequestResultsList<PayPswModel>>(new HttpObserver.OnNextListener<RequestResultsList<PayPswModel>>() {
            @Override
            public void onNext(RequestResultsList<PayPswModel> data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                if (!data.getObj().isEmpty() && !TextUtils.isEmpty(data.getObj().get(0).getPayPasswd())) {
                    getV().havePayPsw();
                } else {
                    getV().nonePatPsw();
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
