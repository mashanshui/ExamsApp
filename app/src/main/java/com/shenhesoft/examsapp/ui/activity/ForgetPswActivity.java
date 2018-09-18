package com.shenhesoft.examsapp.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.present.ForgetPswPresent;
import com.shenhesoft.examsapp.util.view.VerifyEditText;
import com.shenhesoft.examsapp.view.ForgetPswView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * @author mashanshui
 * @date 2018/5/3
 * @desc 重置密码
 */
public class  ForgetPswActivity extends XActivity<ForgetPswPresent> implements ForgetPswView {

    QMUITipDialog errorDialog;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_verify_code)
    VerifyEditText etVerifyCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_reply_password)
    EditText etReplyPassword;
    @BindView(R.id.bt_confirm)
    Button btConfirm;

    @Override
    public void initData(Bundle savedInstanceState) {
        QMUIStatusBarHelper.translucent(context);
        QMUIStatusBarHelper.setStatusBarLightMode(context);
        etVerifyCode.setVerifyClickListener(new VerifyEditText.VerifyClickListener() {
            @Override
            public void onVerifyClick() {
                getP().getVerifyCode();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public ForgetPswPresent newP() {
        return new ForgetPswPresent();
    }

    @OnClick(R.id.bt_confirm)
    public void onViewClicked() {
        getP().setNewPassword();
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
    public String getPassword() {
        return etPassword.getText().toString().trim();
    }

    @Override
    public String getReplyPassword() {
        return etReplyPassword.getText().toString().trim();
    }

    @Override
    public void showCountdown() {
        etVerifyCode.showCountdown();
    }

    @Override
    public void showFailedErrorDialog() {
        errorDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord("密码错误")
                .create();
        errorDialog.show();
    }

    @Override
    public void dismissErrorDialog() {
        errorDialog.cancel();
    }

    public void setNewPswSuccess() {
        IToast.showShort("修改成功");
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        etVerifyCode.stopCountdown();
    }
}
