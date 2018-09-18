package com.shenhesoft.examsapp.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.LoginModel;
import com.shenhesoft.examsapp.network.model.SecretKeyModel;
import com.shenhesoft.examsapp.present.LoginPresent;
import com.shenhesoft.examsapp.ui.MainActivity;
import com.shenhesoft.examsapp.view.LoginView;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/5/3
 * @desc 登录
 */
public class LoginActivity extends XActivity<LoginPresent> implements LoginView {
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.tv_forget_psw)
    TextView tvForgetPsw;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    private String userName;

    @Override
    public void initData(Bundle savedInstanceState) {
        QMUIStatusBarHelper.translucent(context);
        QMUIStatusBarHelper.setStatusBarLightMode(context);
        userName = SharedPref.getInstance(context).getString(AppConstant.UserName, "");
        if (!TextUtils.isEmpty(userName)) {
            etAccount.setText(userName);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginPresent newP() {
        return new LoginPresent(context);
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString().trim();
    }

    @Override
    public String getUsername() {
        return etAccount.getText().toString().trim();
    }

    @Override
    public void loginSuccess(final LoginModel loginModel) {
        //保存用户输入的用户名和密码，方便下一次自动登陆
        SharedPref.getInstance(context).putString(AppConstant.UserName, getUsername());
//        String encryptPsw = EncryptUtils.encryptRSA2HexString(getPassword().getBytes(), base64Decode(AppConstant.PUBLIC_KEY.getBytes()), true, "RSA/ECB/PKCS1Padding");
        SharedPref.getInstance(context).putString(AppConstant.UserPassword, getPassword());
        SharedPref.getInstance(context).putString(AppConstant.JsessionId, loginModel.getJsessionid());
        SharedPref.getInstance(context).putString(AppConstant.UserId, loginModel.getId());
        //设置用户别名(用于推送)
        final PushAgent mPushAgent = PushAgent.getInstance(context);
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
        IToast.showShort("登录成功");
        Router.newIntent(context).to(MainActivity.class).launch();
        finish();
    }

    private static byte[] base64Decode(final byte[] input) {
        return Base64.decode(input, Base64.DEFAULT);
    }

    @Override
    public void loginFail(String message) {
        SharedPref.getInstance(context).putString(AppConstant.UserPassword, "");
        IToast.showShort(message);
    }

    @Override
    public void onError() {
        SharedPref.getInstance(context).putString(AppConstant.UserPassword, "");
        IToast.showShort("登录失败");
    }

    @Override
    public void dealSecretKey(SecretKeyModel secretKeyModel) {
        getP().login(secretKeyModel);
    }

    @Override
    public boolean isShowLoadingDailog() {
        return true;
    }

    @OnClick({R.id.bt_login, R.id.tv_forget_psw, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                getP().getSecretKey();
                break;
            case R.id.tv_forget_psw:
                Router.newIntent(context).to(ForgetPswActivity.class).launch();
                break;
            case R.id.tv_register:
                Router.newIntent(context).to(RegisterActivity.class).launch();
                break;
            default:
                break;
        }
    }
}
