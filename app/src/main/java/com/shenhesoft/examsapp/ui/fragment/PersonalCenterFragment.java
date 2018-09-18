package com.shenhesoft.examsapp.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.MenuAdapter;
import com.shenhesoft.examsapp.adapter.bean.MenuListBean;
import com.shenhesoft.examsapp.network.model.PersonMessageModel;
import com.shenhesoft.examsapp.network.model.StatisticsModel;
import com.shenhesoft.examsapp.present.PersonalCenterPresent;
import com.shenhesoft.examsapp.ui.activity.user.AdviceActivity;
import com.shenhesoft.examsapp.ui.activity.user.MessageActivity;
import com.shenhesoft.examsapp.ui.activity.user.MyAchieveActivity;
import com.shenhesoft.examsapp.ui.activity.user.MyWalletActivity;
import com.shenhesoft.examsapp.ui.activity.user.PersonDataActivity;
import com.shenhesoft.examsapp.ui.activity.user.SettingActivity;
import com.shenhesoft.examsapp.ui.activity.user.StatisticsActivity;
import com.shenhesoft.examsapp.util.GridItemDecoration;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.router.Router;

import static android.app.Activity.RESULT_OK;

/**
 * @author mashanshui
 * @date 2018/5/3
 * @desc TODO
 */
public class PersonalCenterFragment extends XFragment<PersonalCenterPresent> {
    public static final int RequestCode = 101;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.tv_last_login_time)
    TextView tvLastLoginTime;
    @BindView(R.id.rv_menu_list)
    RecyclerView rvMenuList;
    @BindView(R.id.tv_work_count)
    TextView tvWorkCount;
    @BindView(R.id.tv_lesson_count)
    TextView tvLessonCount;
    @BindView(R.id.tv_modify_count)
    TextView tvModifyCount;

    private MenuAdapter menuAdapter;
    private List<MenuListBean> menuListBeans = Arrays.asList(
            new MenuListBean(R.drawable.personal_data, R.string.personal_data, ""),
            new MenuListBean(R.drawable.purse, R.string.purse, ""),
            new MenuListBean(R.drawable.message, R.string.message, ""),
            new MenuListBean(R.drawable.learning_statistics, R.string.learning_statistics, ""),
            new MenuListBean(R.drawable.advice, R.string.advice, ""),
            new MenuListBean(R.drawable.achievement, R.string.achievement, "")
    );

    public PersonalCenterFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getP().loadStatisticsData(false);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        menuAdapter = new MenuAdapter(R.layout.recycler_list_person_menu, menuListBeans);
        rvMenuList.setLayoutManager(new LinearLayoutManager(context));
        rvMenuList.setAdapter(menuAdapter);
        rvMenuList.addItemDecoration(new GridItemDecoration(context, R.drawable.recycler_menu_divider));
        menuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MenuListBean list = menuListBeans.get(position);
                switch (list.getResTitleId()) {
                    case R.string.personal_data:
                        Intent intent = new Intent(context, PersonDataActivity.class);
                        startActivityForResult(intent, RequestCode);
                        break;
                    case R.string.purse:
                        Router.newIntent(context).to(MyWalletActivity.class).launch();
                        break;
                    case R.string.message:
                        Router.newIntent(context).to(MessageActivity.class).launch();
                        break;
                    case R.string.learning_statistics:
                        Router.newIntent(context).to(StatisticsActivity.class).launch();
                        break;
                    case R.string.advice:
                        Router.newIntent(context).to(AdviceActivity.class).launch();
                        break;
                    case R.string.achievement:
                        Router.newIntent(context).to(MyAchieveActivity.class).launch();
                        break;
                    default:
                        break;
                }
            }
        });
        getP().loadData();
        getP().loadStatisticsData(false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_center;
    }

    @Override
    public PersonalCenterPresent newP() {
        return new PersonalCenterPresent();
    }

    @OnClick(R.id.iv_setting)
    public void onViewClicked() {
        Router.newIntent(context).to(SettingActivity.class).launch();
    }

    public void updateData(PersonMessageModel obj) {
        tvUsername.setText(TextUtils.isEmpty(obj.getName()) ? "用户名未设置" : obj.getName());
        tvLastLoginTime.setText(obj.getLastLoginTime());
    }

    public void updateStatisticsData(List<StatisticsModel> statisticsModelList) {
        tvWorkCount.setText(String.valueOf(statisticsModelList.get(0).getO_total()));
        tvLessonCount.setText(String.valueOf(statisticsModelList.get(1).getO_total()));
        tvModifyCount.setText(String.valueOf(statisticsModelList.get(2).getO_total()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode && resultCode == RESULT_OK) {
            getP().loadData();
        }
    }
}
