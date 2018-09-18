package com.shenhesoft.examsapp.present;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.shenhesoft.examsapp.network.UserRetrofit;
import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.network.model.AddressModel;
import com.shenhesoft.examsapp.ui.activity.user.AddAddressActivity;
import com.shenhesoft.examsapp.util.PatternUtil;

import java.io.IOException;

import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * @author mashanshui
 * @date 2018/6/9
 * @desc TODO
 */
public class AddAddressPresent extends XPresent<AddAddressActivity> {
    private UserService userService;

    public AddAddressPresent() {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
    }

    public void loadData() {
        if (!checkData()) {
            return;
        }
        Observable<RequestResults<ListALLResults<AddressModel>>> observable = userService.lookAddress(UserRetrofit.getInstance().lookAddress(0, 20));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<AddressModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<AddressModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<AddressModel>> data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                getV().updateDefaultData(data.getObj().getRows());
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    public void addAddress() {
        if (!checkData()) {
            return;
        }
        int isDefault;
        if (getV().getIsDefault()) {
            isDefault = 1;
        } else {
            isDefault = 0;
        }
        Observable<RequestResults> observable = userService.addAddress(UserRetrofit.getInstance().addAddress(getV().getReceiptName(), getV().getReceiptPhone(), getV().getReceiptAddress(), isDefault));
        HttpObserver httpObserver = new HttpObserver<RequestResults>(new HttpObserver.OnNextListener<RequestResults>() {
            @Override
            public void onNext(RequestResults data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                getV().addSuccess();
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
                    String msg = message.getMessage();
                    msg = msg.substring(0, msg.indexOf("<br>"));
                    IToast.showShort(msg);
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    public void modifyAddress(String id) {
        if (!checkData()) {
            return;
        }
        int isDefault;
        if (getV().getIsDefault()) {
            isDefault = 1;
        } else {
            isDefault = 0;
        }
        Observable<RequestResults> observable = userService.modifyOrDeleteAddress(UserRetrofit.getInstance().modifyAddress(id, getV().getReceiptName(), getV().getReceiptPhone(), getV().getReceiptAddress(), isDefault));
        HttpObserver httpObserver = new HttpObserver<RequestResults>(new HttpObserver.OnNextListener<RequestResults>() {
            @Override
            public void onNext(RequestResults data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                getV().modifySuccess();
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    public void defaultAddress(String id, int isDefault) {
        Observable<RequestResults> observable = userService.modifyOrDeleteAddress(UserRetrofit.getInstance().defaultAddress(id, isDefault));
        HttpObserver httpObserver = new HttpObserver<RequestResults>(new HttpObserver.OnNextListener<RequestResults>() {
            @Override
            public void onNext(RequestResults data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                getV().defaultOldSuccess();
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    private boolean checkData() {
        if (TextUtils.isEmpty(getV().getReceiptName())) {
            IToast.showShort("请填写收货人");
            return false;
        }
        if (TextUtils.isEmpty(getV().getReceiptPhone())) {
            IToast.showShort("请填写手机号");
            return false;
        }
        if (TextUtils.isEmpty(getV().getReceiptAddress())) {
            IToast.showShort("请填写地址");
            return false;
        }
        if (!PatternUtil.isPhone(getV().getReceiptPhone())) {
            IToast.showShort("请填写正确的手机号");
            return false;
        }
        return true;
    }
}
