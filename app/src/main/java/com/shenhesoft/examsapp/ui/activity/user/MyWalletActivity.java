package com.shenhesoft.examsapp.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.WalletMessageModel;
import com.shenhesoft.examsapp.present.MyWalletPresent;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.XStatusBar;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/5/19
 * @desc 我的钱包
 */
public class MyWalletActivity extends XActivity<MyWalletPresent> {
    public static final int RequestCode = 100;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_my_wallet)
    TextView tvMyWallet;
    @BindView(R.id.tv_expense_calendar)
    TextView tvExpenseCalendar;
    @BindView(R.id.tv_gold_balance)
    TextView tvGoldBalance;
    @BindView(R.id.tv_total_consume)
    TextView tvTotalConsume;
    @BindView(R.id.tv_total_donate)
    TextView tvTotalDonate;
    @BindView(R.id.tv_total_recharge)
    TextView tvTotalRecharge;
    @BindView(R.id.btn_recharge)
    QMUIRoundButton btnRecharge;

    @Override
    public void initData(Bundle savedInstanceState) {
        XStatusBar.setTransparentForImageView(context, ivBack);
        btnRecharge.setChangeAlphaWhenPress(true);
        getP().loadData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_wallet;
    }

    @Override
    public MyWalletPresent newP() {
        return new MyWalletPresent();
    }

    @OnClick({R.id.iv_back, R.id.tv_expense_calendar, R.id.btn_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_expense_calendar:
                Router.newIntent(context).to(ExpenseCalendarActivity.class).launch();
                break;
            case R.id.btn_recharge:
                Router.newIntent(context).to(ReChargeActivity.class)
                        .requestCode(100)
                        .launch();
                break;
            default:
                break;
        }
    }

    public void updateData(WalletMessageModel walletMessageModel) {
        tvTotalConsume.setText(walletMessageModel.getTotalGoldSpend());
        tvGoldBalance.setText(walletMessageModel.getTotalGoldRemain());
        tvTotalDonate.setText(walletMessageModel.getTotalGoldGive());
        tvTotalRecharge.setText(Float.valueOf(walletMessageModel.getTotalMoneyPay()).intValue() + "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getP().loadData();
    }
}
