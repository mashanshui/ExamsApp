package com.shenhesoft.examsapp.data.remote;

import android.text.TextUtils;

import com.shenhesoft.examsapp.data.LoginDataSource;
import com.shenhesoft.examsapp.network.UserRetrofit;
import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.network.model.LoginModel;
import com.shenhesoft.examsapp.network.model.SecretKeyModel;

import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import io.reactivex.Observable;

/**
 * @author mashanshui
 * @date 2018/6/26
 * @desc TODO
 */
public class LoginRemoteDataSource implements LoginDataSource {
    private UserService userService;

    public LoginRemoteDataSource() {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
    }

    @Override
    public void login(boolean isShowLoadingDialog, String account, String password, String sign, final LoginCallback loginCallback) {
        Observable<RequestResults<LoginModel>> observable = userService.login(UserRetrofit.getInstance().login(account, password, sign));
        HttpObserver httpObserver = new HttpObserver<RequestResults<LoginModel>>(isShowLoadingDialog, new HttpObserver.OnNextListener<RequestResults<LoginModel>>() {
            @Override
            public void onNext(RequestResults<LoginModel> data) {
                if (data.getState() != 200) {
                    loginCallback.onFail(data.getMsg());
                } else {
                    if (TextUtils.isEmpty(data.getObj().getJsessionid())) {
                        loginCallback.onFail(data.getMsg());
                        return;
                    }
                    loginCallback.onSuccess(data.getObj());
                }
            }
        }, new HttpObserver.OnErrorListener() {
            @Override
            public void onError(Throwable throwable) {
                loginCallback.onError();
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    @Override
    public void getSecretKey(boolean isShowLoadingDialog, final SecretKeyCallback callback) {
        Observable<RequestResults<SecretKeyModel>> observable = userService.getSecretKey();
        HttpObserver httpObserver = new HttpObserver<RequestResults<SecretKeyModel>>(isShowLoadingDialog, new HttpObserver.OnNextListener<RequestResults<SecretKeyModel>>() {
            @Override
            public void onNext(RequestResults<SecretKeyModel> data) {
                if (data.getState() != 200) {
                    callback.onFail(data.getMsg());
                } else {
                    callback.onSuccess(data.getObj());
                }
            }
        }, new HttpObserver.OnErrorListener() {
            @Override
            public void onError(Throwable throwable) {
                callback.onError();
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
