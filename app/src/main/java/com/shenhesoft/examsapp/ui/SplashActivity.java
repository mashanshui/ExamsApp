package com.shenhesoft.examsapp.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.PushConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.bean.UMengMessageBean;
import com.shenhesoft.examsapp.network.model.LoginModel;
import com.shenhesoft.examsapp.network.model.SecretKeyModel;
import com.shenhesoft.examsapp.present.LoginPresent;
import com.shenhesoft.examsapp.ui.activity.LoginActivity;
import com.shenhesoft.examsapp.view.LoginView;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

import org.android.agoo.common.AgooConstants;

import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XUmengActivity;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.functions.Consumer;

/**
 * @author mashanshui
 * @date 2018/5/4
 * @desc 启动页
 */
public class SplashActivity extends XUmengActivity<LoginPresent> implements LoginView {
    private static final String TAG = "SplashActivity";
    private String userName;
    private String userPassword;

    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);
        String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        Log.e("message", "onMessage() returned: " + body);
        Gson gson = new Gson();
        UMengMessageBean mengMessageBean = gson.fromJson(body, UMengMessageBean.class);
        setPushData(mengMessageBean.getBody().getCustom(), mengMessageBean.getExtra().getData());
    }

    private void setPushData(String pushStatus, String pushData) {
        SharedPref.getInstance(context).putString(PushConstant.PushStatus, pushStatus);
        SharedPref.getInstance(context).putString(PushConstant.PushData, pushData);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        requestPermissions();
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        getWindow().getDecorView().setBackgroundResource(R.drawable.splash);
//        super.onCreate(savedInstanceState);
//    }

    private void startLogin() {
        userName = SharedPref.getInstance(context).getString(AppConstant.UserName, "");
        userPassword = SharedPref.getInstance(context).getString(AppConstant.UserPassword, "");
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPassword)) {
//            byte[] paw = EncryptUtils.decryptHexStringRSA(userPassword, base64Decode(AppConstant.PRIVATE_KEY.getBytes()), false, "RSA/ECB/PKCS1Padding");
//            userPassword = hexStringToString(bytes2HexString(paw));
            getP().getSecretKey();
        } else {
            Router.newIntent(context).to(LoginActivity.class).launch();
            finish();
        }
    }

    /**
     * 16进制转换成为string类型字符串
     *
     * @param s
     * @return
     */
    public static String hexStringToString(String s) {
        if (TextUtils.isEmpty(s)) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    private static byte[] base64Decode(final byte[] input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    private void requestPermissions() {
        getRxPermissions().request(Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        startLogin();
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public LoginPresent newP() {
        return new LoginPresent(context);
    }

    private void clearCache() {
        SharedPref.getInstance(context).putString(AppConstant.UserPassword, "");
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public void loginSuccess(final LoginModel loginModel) {
        SharedPref.getInstance(context).putString(AppConstant.JsessionId, loginModel.getJsessionid());
        SharedPref.getInstance(context).putString(AppConstant.UserId, loginModel.getId());
        //设置用户别名(用于推送)
        final PushAgent mPushAgent = PushAgent.getInstance(context);
        Log.e(TAG, "loginSuccess: " + mPushAgent.getRegistrationId());
        mPushAgent.deleteAlias(String.valueOf(loginModel.getId()), AppConstant.UMENG_PUSH_ALIAS_TYPE, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean b, String s) {
                Log.e(TAG, "onMessage: " + b + "   " + s);
                mPushAgent.setAlias(String.valueOf(loginModel.getId()), AppConstant.UMENG_PUSH_ALIAS_TYPE, new UTrack.ICallBack() {
                    @Override
                    public void onMessage(boolean b, String s) {
                        Log.e(TAG, "onMessage: " + b + "   " + s);
                    }
                });
            }
        });
        SystemClock.sleep(1500);  //上线时使用，测试阶段注释
        Router.newIntent(context).to(MainActivity.class).launch();
        finish();
    }

    @Override
    public void loginFail(String message) {
        IToast.showShort(message);
        clearCache();
        Router.newIntent(context).to(LoginActivity.class).launch();
        finish();
    }

    @Override
    public void onError() {
        clearCache();
        Router.newIntent(context).to(LoginActivity.class).launch();
        finish();
    }

    @Override
    public void dealSecretKey(SecretKeyModel secretKeyModel) {
        getP().login(secretKeyModel);
    }

    @Override
    public boolean isShowLoadingDailog() {
        return false;
    }
}
