package com.shenhesoft.examsapp.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.qmuiteam.qmui.span.QMUITouchableSpan;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.textview.QMUISpanTouchFixTextView;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.present.RegisterPresent;
import com.shenhesoft.examsapp.ui.activity.user.RegisterAgreementActivity;
import com.shenhesoft.examsapp.util.view.VerifyEditText;
import com.shenhesoft.examsapp.view.RegisterView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/5/3
 * @desc 注册
 */
public class RegisterActivity extends XActivity<RegisterPresent> implements RegisterView {
    private static final String TAG = "RegisterActivity";
    @BindView(R.id.tv_login)
    QMUISpanTouchFixTextView tvLogin;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_reply_password)
    EditText etReplyPassword;
    @BindView(R.id.et_verify_code)
    VerifyEditText etVerifyCode;
    @BindView(R.id.bt_register)
    Button btRegister;
    QMUITipDialog failDialog;
    @BindView(R.id.tv_register_agreement)
    QMUISpanTouchFixTextView tvRegisterAgreement;
    @BindView(R.id.cb_agree_agreement)
    CheckBox cbAgreeAgreement;

    @Override
    public void initData(Bundle savedInstanceState) {
        QMUIStatusBarHelper.translucent(context);
        QMUIStatusBarHelper.setStatusBarLightMode(context);
        tvLogin.setMovementMethodDefault();
        tvLogin.setText(generateSp("已有账户？登录"));
        tvRegisterAgreement.setMovementMethodDefault();
        tvRegisterAgreement.setText(generateSp2("我已阅读并接受《青阳MPAcc用户注册协议》"));
        etVerifyCode.setVerifyClickListener(new VerifyEditText.VerifyClickListener() {
            @Override
            public void onVerifyClick() {
                getP().getVerifyCode();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public RegisterPresent newP() {
        return new RegisterPresent(context);
    }

    private SpannableString generateSp(String text) {
        String highlight1 = "登录";
        SpannableString sp = new SpannableString(text);
        int start = 0, end;
        int index;
        while ((index = text.indexOf(highlight1, start)) > -1) {
            end = index + highlight1.length();
            sp.setSpan(new QMUITouchableSpan(ContextCompat.getColor(context, R.color.colorPrimary), ContextCompat.getColor(context, R.color.colorPrimaryLight)
                    , Color.TRANSPARENT, Color.TRANSPARENT) {
                @Override
                public void onSpanClick(View widget) {
                    finish();
                }
            }, index, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            start = end;
        }
        return sp;
    }

    private SpannableString generateSp2(String text) {
        String highlight1 = "《青阳MPAcc用户注册协议》";
        SpannableString sp = new SpannableString(text);
        int start = 0, end;
        int index;
        while ((index = text.indexOf(highlight1, start)) > -1) {
            end = index + highlight1.length();
            sp.setSpan(new QMUITouchableSpan(ContextCompat.getColor(context, R.color.colorPrimary), ContextCompat.getColor(context, R.color.colorPrimaryLight)
                    , Color.TRANSPARENT, Color.TRANSPARENT) {
                @Override
                public void onSpanClick(View widget) {
                    Router.newIntent(context).to(RegisterAgreementActivity.class).launch();
                }
            }, index, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            start = end;
        }
        return sp;
    }

    @Override
    public String getUsername() {
        return etAccount.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString().trim();
    }

    @Override
    public String getConfirmPassword() {
        return etReplyPassword.getText().toString().trim();
    }

    @Override
    public String getPhone() {
        return etPhone.getText().toString().trim();
    }

    @Override
    public String getVerifyCode() {
        return etVerifyCode.getText().toString().trim();
    }

    @Override
    public void showFailedErrorDialog() {
        failDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord("注册失败")
                .create();
        failDialog.show();
    }

    @Override
    public void dismissFailedErrorDialog() {
        failDialog.cancel();
    }

    @Override
    public void toLoginActivity() {
        finish();
    }

    @Override
    public void showCountdown() {
        etVerifyCode.showCountdown();
    }

    @Override
    public void showVerify() {
        etVerifyCode.showVerify();
    }

    @Override
    public boolean getAgreementChecked() {
        return cbAgreeAgreement.isChecked();
    }

    @OnClick({R.id.et_verify_code, R.id.bt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_register:
                getP().register();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        etVerifyCode.stopCountdown();
    }
}
