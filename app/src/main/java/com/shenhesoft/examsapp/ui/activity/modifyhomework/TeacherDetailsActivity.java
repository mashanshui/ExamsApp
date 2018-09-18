package com.shenhesoft.examsapp.ui.activity.modifyhomework;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.ViewTabPagerAdapter;
import com.shenhesoft.examsapp.network.model.InteractiveModel;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.present.TeacherDetailsPresent;
import com.shenhesoft.examsapp.ui.fragment.EvaluateFragment;
import com.shenhesoft.examsapp.ui.fragment.modifyhomework.TeacherDetailFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * @author mashanshui
 * @date 2018/5/17
 * @desc 老师详情
 */
public class TeacherDetailsActivity extends XActivity<TeacherDetailsPresent> {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.viewPage)
    ViewPager viewPage;

    private List<Fragment> fragmentList;
    private TeacherDetailFragment teacherDetailFragment;
    private EvaluateFragment evaluateFragment;
    private ProductModel productModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        String isBuy = getIntent().getStringExtra("isBuy");
        QMUIStatusBarHelper.translucent(context);
        QMUIStatusBarHelper.setStatusBarLightMode(context);
        fragmentList = new ArrayList<>();
//        teacherDetailFragment = TeacherDetailFragment.newInstance(productModel.getProductId());
//        evaluateFragment = EvaluateFragment.newInstance(productModel.getProductId());
        teacherDetailFragment = TeacherDetailFragment.newInstance(isBuy);
        evaluateFragment = new EvaluateFragment();
        fragmentList.add(teacherDetailFragment);
        fragmentList.add(evaluateFragment);
        List<String> titleList = Arrays.asList("作文详情", "评价");
        ViewTabPagerAdapter viewPageFragmentAdapter = new ViewTabPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPage.setAdapter(viewPageFragmentAdapter);
        tabLayout.setupWithViewPager(viewPage);

        setResult(RESULT_OK);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_teacher_details;
    }

    @Override
    public TeacherDetailsPresent newP() {
        return new TeacherDetailsPresent();
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
    public void onBackPressed() {
        if (teacherDetailFragment.getisBuy()) {
            getP().loadData(teacherDetailFragment.getOrderId());
            return;
        }
        super.onBackPressed();
    }


    public void isBack(List<InteractiveModel> rows) {
        if (rows.isEmpty()) {
            showConfirmDialog();
        } else {
            finish();
        }
    }

    private void showConfirmDialog() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_confirm_teacher_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder viewHolder, final BaseNiceDialog baseNiceDialog) {
                        viewHolder.setText(R.id.tv_title, "提示");
                        viewHolder.setText(R.id.tv_message, "不上传作文老师将无法批改,确定退出吗?");
                        viewHolder.setOnClickListener(R.id.tv_confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                baseNiceDialog.dismiss();
                                finish();
                            }
                        });
                        viewHolder.setOnClickListener(R.id.tv_cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                baseNiceDialog.dismiss();
                                teacherDetailFragment.uploadWriting();
                            }
                        });
                    }
                })
                .setWidth(300)
                .setHeight(150)
                .show(getSupportFragmentManager());
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
//        productModel = listBean;
//        teacherDetailFragment = TeacherDetailFragment.newInstance(productModel.getProductId());
//        evaluateFragment = EvaluateFragment.newInstance(productModel.getProductId());
//        fragmentList.add(teacherDetailFragment);
//        fragmentList.add(evaluateFragment);
//        List<String> titleList = Arrays.asList("课件详情", "评价");
//        ViewTabPagerAdapter viewPageFragmentAdapter = new ViewTabPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
//        viewPage.setAdapter(viewPageFragmentAdapter);
//        tabLayout.setupWithViewPager(viewPage);
//    }

//    @Override
//    public boolean useEventBus() {
//        return true;
//    }
}
