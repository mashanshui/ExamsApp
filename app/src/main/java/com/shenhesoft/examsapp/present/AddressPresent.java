package com.shenhesoft.examsapp.present;

import com.shenhesoft.examsapp.network.UserRetrofit;
import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.network.model.AddressModel;
import com.shenhesoft.examsapp.view.AddressView;

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
 * @date 2018/6/9
 * @desc TODO
 */
public class AddressPresent extends XPresent<AddressView> {
    private UserService userService;

    public AddressPresent() {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
    }

    public void loadData() {
        Observable<RequestResults<ListALLResults<AddressModel>>> observable = userService.lookAddress(UserRetrofit.getInstance().lookAddress(0, 20));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<AddressModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<AddressModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<AddressModel>> data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                if (getV().isModifyDefaultAddress()) {
                    getV().updateDefaultData(data.getObj().getRows());
                } else {
                    getV().updateData(data.getObj().getRows());
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    public void deleteAddress(String id) {
        Observable<RequestResults> observable = userService.modifyOrDeleteAddress(UserRetrofit.getInstance().deleteAddress(id));
        HttpObserver httpObserver = new HttpObserver<RequestResults>(new HttpObserver.OnNextListener<RequestResults>() {
            @Override
            public void onNext(RequestResults data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                getV().deleteSuccess();
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
                if (getV().isModifyDefaultAddress()) {
                    getV().defaultOldSuccess();
                } else {
                    getV().defaultSuccess();
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
