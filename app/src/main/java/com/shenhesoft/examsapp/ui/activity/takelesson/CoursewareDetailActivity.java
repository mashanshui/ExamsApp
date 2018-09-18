package com.shenhesoft.examsapp.ui.activity.takelesson;

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
import com.shenhesoft.examsapp.ui.fragment.EvaluateFragment;
import com.shenhesoft.examsapp.ui.fragment.takelessons.CoursewareDetailFragment;
import com.shenhesoft.examsapp.util.BackHandlerHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * @author mashanshui
 * @date 2018/5/14
 * @desc 课件详情
 */
public class CoursewareDetailActivity extends XActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.viewPage)
    ViewPager viewPage;

    private List<Fragment> fragmentList;
    private CoursewareDetailFragment coursewareDetailFragment;
    private EvaluateFragment evaluateFragment;

    @Override
    public void initData(Bundle savedInstanceState) {
        QMUIStatusBarHelper.translucent(context);
        QMUIStatusBarHelper.setStatusBarLightMode(context);
        fragmentList = new ArrayList<>();
        coursewareDetailFragment = new CoursewareDetailFragment();
        evaluateFragment = new EvaluateFragment();
        fragmentList.add(coursewareDetailFragment);
        fragmentList.add(evaluateFragment);
        List<String> titleList = Arrays.asList("课件详情", "评价");
        ViewTabPagerAdapter viewPageFragmentAdapter = new ViewTabPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPage.setAdapter(viewPageFragmentAdapter);
        tabLayout.setupWithViewPager(viewPage);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_courseware_detail;
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

    //    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
//    public void onStickyEvent(ProductModel listBean) {
//        if (listBean == null) {
//            IToast.showShort("异常，请重新进入");
//            finish();
//        }
//        coursewareDetailFragment = new CoursewareDetailFragment();
//        evaluateFragment = EvaluateFragment.newInstance(listBean.getProductId());
//        fragmentList.add(coursewareDetailFragment);
//        fragmentList.add(evaluateFragment);
//        List<String> titleList = Arrays.asList("课件详情", "评价");
//        ViewTabPagerAdapter viewPageFragmentAdapter = new ViewTabPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
//        viewPage.setAdapter(viewPageFragmentAdapter);
//        tabLayout.setupWithViewPager(viewPage);
//    }
//
//    @Override
//    public boolean useEventBus() {
//        return true;
//    }


    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            super.onBackPressed();
        }
    }
}
