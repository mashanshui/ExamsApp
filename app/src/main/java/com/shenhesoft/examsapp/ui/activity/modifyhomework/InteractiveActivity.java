package com.shenhesoft.examsapp.ui.activity.modifyhomework;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.ViewTabPagerAdapter;
import com.shenhesoft.examsapp.ui.fragment.modifyhomework.AskFragment;
import com.shenhesoft.examsapp.ui.fragment.modifyhomework.InteractiveFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * @author mashanshui
 * @date 2018/5/17
 * @desc 师生交互
 */
public class InteractiveActivity extends XActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.viewPage)
    ViewPager viewPage;

    private List<Fragment> fragmentList;
    private InteractiveFragment interactiveFragment;
    private AskFragment askFragment;

    @Override
    public void initData(Bundle savedInstanceState) {
        QMUIStatusBarHelper.translucent(context);
        QMUIStatusBarHelper.setStatusBarLightMode(context);
        fragmentList = new ArrayList<>();
        interactiveFragment = new InteractiveFragment();
        askFragment = new AskFragment();
        fragmentList.add(interactiveFragment);
        fragmentList.add(askFragment);
        List<String> titleList = Arrays.asList("师生交互", "师生问答");
        ViewTabPagerAdapter viewPageFragmentAdapter = new ViewTabPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPage.setAdapter(viewPageFragmentAdapter);
        tabLayout.setupWithViewPager(viewPage);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_interactive;
    }

    @Override
    public Object newP() {
        return null;
    }


    @OnClick({R.id.iv_back, R.id.iv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
