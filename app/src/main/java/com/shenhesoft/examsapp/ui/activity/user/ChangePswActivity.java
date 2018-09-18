package com.shenhesoft.examsapp.ui.activity.user;

import android.os.Bundle;
import android.widget.EditText;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.present.ChangePswPresent;
import com.shenhesoft.examsapp.ui.activity.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.base.ActivityCollector;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/5/10
 * @desc 修改密码
 */
public class ChangePswActivity extends XTitleActivity<ChangePswPresent> {


    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_reply_new_password)
    EditText etReplyNewPassword;
    @BindView(R.id.btn_confirm)
    QMUIRoundButton btnConfirm;

    @Override
    protected void initTitle() {
        setBackAction();
        setTitle("密码修改");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        btnConfirm.setChangeAlphaWhenPress(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_psw;
    }

    @Override
    public ChangePswPresent newP() {
        return new ChangePswPresent();
    }

    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
        getP().changePsw(etOldPassword.getText().toString().trim(), etNewPassword.getText().toString().trim(), etReplyNewPassword.getText().toString().trim());
    }

    public void changeSuccess() {
        IToast.showShort("修改成功，请重新登录");
        //清除用户数据
        SharedPref.getInstance(context).putString(AppConstant.UserPassword, "");
        Router.newIntent(context).to(LoginActivity.class).launch();
        ActivityCollector.finishAllActivity();
    }
}
