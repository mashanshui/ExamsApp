package com.shenhesoft.examsapp.data.remote;

import com.shenhesoft.examsapp.data.RegisterDataSource;
import com.shenhesoft.examsapp.network.UserRetrofit;
import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.network.model.VerifyCodeModel;

import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import io.reactivex.Observable;

/**
 * @author mashanshui
 * @date 2018/5/7
 * @desc TODO
 */
public class RegisterRemoteDataSource implements RegisterDataSource {
    private UserService userService;

    public RegisterRemoteDataSource() {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
    }

    @Override
    public void register(String phone, String account, String password, String replyPassword, String verify, final RegisterCallback callback) {
        Observable<RequestResults> observable = userService.register(UserRetrofit.getInstance().register(phone, account, password, replyPassword, verify));
        HttpObserver httpObserver = new HttpObserver<RequestResults>(new HttpObserver.OnNextListener<RequestResults>() {
            @Override
            public void onNext(RequestResults requestResults) {
                if (requestResults.getState() != 200) {
                    callback.onFail(requestResults.getMsg());
                } else {
                    callback.onSuccess();
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    @Override
    public void forgetPsw(String phone, String verify, String password, String replyPassword, final ForgetPswCallback callback) {
        Observable<RequestResults> observable = userService.forgetPsw(UserRetrofit.getInstance().forgetPsw(phone, verify, password, replyPassword));
        HttpObserver httpObserver = new HttpObserver<RequestResults>(new HttpObserver.OnNextListener<RequestResults>() {
            @Override
            public void onNext(RequestResults requestResults) {
                if (requestResults.getState() != 201) {
                    callback.onFail(requestResults.getMsg());
                } else {
                    callback.onSuccess();
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    @Override
    public void getVerifyCode(String phone, final GetVerifyCodeCallback callback) {
        Observable<RequestResults<VerifyCodeModel>> observable = userService.getVerifyCode(UserRetrofit.getInstance().getVerifyCode(phone));
        HttpObserver httpObserver = new HttpObserver<RequestResults<VerifyCodeModel>>(new HttpObserver.OnNextListener<RequestResults<VerifyCodeModel>>() {
            @Override
            public void onNext(RequestResults<VerifyCodeModel> verifyCodeModelRequestResults) {
                if (verifyCodeModelRequestResults.getState() != 200) {
                    callback.onFail(verifyCodeModelRequestResults.getMsg());
                } else {
                    callback.onSuccess(verifyCodeModelRequestResults.getObj().getCheckCode());
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
