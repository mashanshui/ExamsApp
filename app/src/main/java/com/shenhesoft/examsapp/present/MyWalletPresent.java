package com.shenhesoft.examsapp.present;

import com.shenhesoft.examsapp.network.UserRetrofit;
import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.network.model.WalletMessageModel;
import com.shenhesoft.examsapp.ui.activity.user.MyWalletActivity;

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
 * @date 2018/6/6
 * @desc TODO
 */
public class MyWalletPresent extends XPresent<MyWalletActivity> {
    private UserService userService;

    public MyWalletPresent() {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
    }

    public void loadData() {
        Observable<RequestResults<ListALLResults<WalletMessageModel>>> observable = userService.myWalletMessage(UserRetrofit.getInstance().myWalletMessage());
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<WalletMessageModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<WalletMessageModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<WalletMessageModel>> data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                List<WalletMessageModel> walletMessageModels = data.getObj().getRows();
                if (!walletMessageModels.isEmpty()) {
                    getV().updateData(walletMessageModels.get(0));
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
