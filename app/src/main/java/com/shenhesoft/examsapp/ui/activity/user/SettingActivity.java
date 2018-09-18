package com.shenhesoft.examsapp.ui.activity.user;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.MenuAdapter;
import com.shenhesoft.examsapp.adapter.bean.MenuListBean;
import com.shenhesoft.examsapp.present.SettingPresent;
import com.shenhesoft.examsapp.ui.activity.LoginActivity;
import com.shenhesoft.examsapp.util.GridItemDecoration;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.base.ActivityCollector;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/5/10
 * @desc TODO
 */
public class SettingActivity extends XTitleActivity<SettingPresent> {

    @BindView(R.id.rv_setting)
    RecyclerView rvSetting;
    @BindView(R.id.btn_logout)
    QMUIRoundButton btnLogout;

    private MenuAdapter menuAdapter;
    private List<MenuListBean> menuLists = Arrays.asList(
            new MenuListBean(R.string.change_password),
            new MenuListBean(R.string.pay_password_setting),
            new MenuListBean(R.string.address_manager),
            new MenuListBean(R.string.about)
    );


    @Override
    protected void initTitle() {
        setBackAction();
        setTitle("设置");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        btnLogout.setChangeAlphaWhenPress(true);
        menuAdapter = new MenuAdapter(R.layout.recycler_list_setting, menuLists);
        rvSetting.setLayoutManager(new LinearLayoutManager(context));
        rvSetting.setAdapter(menuAdapter);
        rvSetting.addItemDecoration(new GridItemDecoration(context, R.drawable.recycler_menu_divider));
        menuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MenuListBean list = menuLists.get(position);
                switch (list.getResTitleId()) {
                    case R.string.change_password:
                        Router.newIntent(context).to(ChangePswActivity.class).launch();
                        break;
                    case R.string.pay_password_setting:
                        Router.newIntent(context).to(ChangePayPswActivity.class).launch();
                        break;
                    case R.string.address_manager:
                        Router.newIntent(context).to(AddressActivity.class).launch();
                        break;
                    case R.string.about:
                        Router.newIntent(context).to(AboutActivity.class).launch();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public SettingPresent newP() {
        return new SettingPresent();
    }

    @OnClick(R.id.btn_logout)
    public void onViewClicked() {
       showConfirmDialog();
    }

    public void logout() {
        //清除用户数据
        SharedPref.getInstance(context).putString(AppConstant.UserPassword, "");
        Router.newIntent(context).to(LoginActivity.class).launch();
        ActivityCollector.finishAllActivity();
    }

    private void showConfirmDialog() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_confirm_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder viewHolder, final BaseNiceDialog baseNiceDialog) {
                        viewHolder.setText(R.id.tv_title, "提示");
                        viewHolder.setText(R.id.tv_message, "确认退出登录");
                        viewHolder.setOnClickListener(R.id.tv_confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                baseNiceDialog.dismiss();
                                getP().logout();
                            }
                        });
                        viewHolder.setOnClickListener(R.id.tv_cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                baseNiceDialog.dismiss();
                            }
                        });
                    }
                })
                .setWidth(300)
                .setHeight(150)
                .show(getSupportFragmentManager());
    }
}
