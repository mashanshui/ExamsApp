package com.shenhesoft.examsapp.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.allen.library.SuperTextView;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.PersonMessageModel;
import com.shenhesoft.examsapp.present.PersonDataPresent;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/5/10
 * @desc 个人中心->个人资料
 */
public class PersonDataActivity extends XTitleActivity<PersonDataPresent> {

    public static final int RequestCode = 100;
    @BindView(R.id.tv_account)
    SuperTextView tvAccount;
    @BindView(R.id.tv_phone)
    SuperTextView tvPhone;
    @BindView(R.id.tv_username)
    SuperTextView tvUsername;
    @BindView(R.id.tv_sex)
    SuperTextView tvSex;
    @BindView(R.id.tv_age)
    SuperTextView tvAge;
    @BindView(R.id.tv_mail)
    SuperTextView tvMail;

    @Override
    protected void initTitle() {
        setBackAction();
        setTitle("个人资料");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getP().loadData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_person_data;
    }

    @Override
    public PersonDataPresent newP() {
        return new PersonDataPresent();
    }


    @OnClick({R.id.tv_username, R.id.tv_sex, R.id.tv_age, R.id.tv_mail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_username:
                setResult(RESULT_OK);
                Router.newIntent(context).to(ModifyMessageActivity.class)
                        .requestCode(RequestCode)
                        .putInt("modifyItem", 1)
                        .launch();
                break;
            case R.id.tv_sex:
                Router.newIntent(context).to(ModifyMessageActivity.class)
                        .requestCode(RequestCode)
                        .putInt("modifyItem", 2)
                        .launch();
                break;
            case R.id.tv_age:
                Router.newIntent(context).to(ModifyMessageActivity.class)
                        .requestCode(RequestCode)
                        .putInt("modifyItem", 3)
                        .launch();
                break;
            case R.id.tv_mail:
                Router.newIntent(context).to(ModifyMessageActivity.class)
                        .requestCode(RequestCode)
                        .putInt("modifyItem", 4)
                        .launch();
                break;
            default:
                break;
        }
    }

    public void updateData(PersonMessageModel messageModel) {
        tvAccount.setRightString(messageModel.getAccount());
        tvAge.setRightString(messageModel.getAge());
        tvMail.setRightString(messageModel.getEmail());
        tvPhone.setRightString(messageModel.getPhone());
        tvUsername.setRightString(messageModel.getName());
        tvSex.setRightString(messageModel.getSexName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode && resultCode == 101) {
            getP().loadData();
        }
    }
}
