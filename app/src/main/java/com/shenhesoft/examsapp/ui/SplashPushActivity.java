package com.shenhesoft.examsapp.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.bean.UMengMessageBean;
import com.shenhesoft.examsapp.network.UserRetrofit;
import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.network.model.LoginModel;
import com.shenhesoft.examsapp.ui.activity.LoginActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

import org.android.agoo.common.AgooConstants;

import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XUmengActivity;
import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * @author mashanshui
 * @date 2018/6/22
 * @desc 推送时打开的启动页
 */
public class SplashPushActivity extends XUmengActivity {
    private static final String TAG = "SplashPushActivity";
    private UserService userService;
    private String userName;
    private String userPassword;

    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);
        String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        Log.e("message", "onMessage() returned: " + body);
        Gson gson = new Gson();
        UMengMessageBean mengMessageBean = gson.fromJson(body, UMengMessageBean.class);
//        setPushData(mengMessageBean.getBody().getCustom(), mengMessageBean.getExtra().getData());
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
//        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        requestPermissions();
    }
    private void startLogin() {
        userName = SharedPref.getInstance(context).getString(AppConstant.UserName, "");
        userPassword = SharedPref.getInstance(context).getString(AppConstant.UserPassword, "");
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPassword)) {
            login();
        } else {
            Router.newIntent(context).to(LoginActivity.class).launch();
            finish();
        }
    }

    private void login() {
        Observable<RequestResults<LoginModel>> observable = userService.login(UserRetrofit.getInstance().login(userName, userPassword,""));
        HttpObserver httpObserver = new HttpObserver<RequestResults<LoginModel>>(false, new HttpObserver.OnNextListener<RequestResults<LoginModel>>() {
            @Override
            public void onNext(RequestResults<LoginModel> data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    clearCache();
                    Router.newIntent(context).to(LoginActivity.class).launch();
                    finish();
                } else {
                    SharedPref.getInstance(context).putString(AppConstant.JsessionId, data.getObj().getJsessionid());
                    SharedPref.getInstance(context).putString(AppConstant.UserId, data.getObj().getId());
                    //设置用户别名(用于推送)
                    PushAgent mPushAgent = PushAgent.getInstance(context);
                    mPushAgent.setAlias(String.valueOf(data.getObj().getId()), AppConstant.UMENG_PUSH_ALIAS_TYPE, new UTrack.ICallBack() {
                        @Override
                        public void onMessage(boolean b, String s) {
                            Log.e(TAG, "onMessage: " + b + s);
                        }
                    });
//                    SystemClock.sleep(800);  //上线时使用，测试阶段注释
                    finish();
                }
            }
        }, new HttpObserver.OnErrorListener() {
            @Override
            public void onError(Throwable throwable) {
                clearCache();
                Router.newIntent(context).to(LoginActivity.class).launch();
                finish();
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    private void requestPermissions() {
        RxPermissions rxPermissions = new RxPermissions(context);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        startLogin();
                    }
                });
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_splash_push;
    }


    @Override
    public Object newP() {
        return null;
    }

    private void clearCache() {
        SharedPref.getInstance(context).putString(AppConstant.UserPassword, "");
    }
}
