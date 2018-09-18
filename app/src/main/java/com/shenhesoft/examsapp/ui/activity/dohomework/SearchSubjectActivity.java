package com.shenhesoft.examsapp.ui.activity.dohomework;

import android.os.Bundle;

import com.blankj.utilcode.util.KeyboardUtils;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.AnswerModel;
import com.shenhesoft.examsapp.ui.fragment.dohomework.AnswerFragment;
import com.shenhesoft.examsapp.util.ActivityUtil;

import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;

/**
 * @author mashanshui
 * @date 2018/7/23
 * @desc 搜索题目返回（显示单个题目）
 */
public class SearchSubjectActivity extends XTitleActivity {

    private AnswerModel answerModel;

    @Override
    protected void initTitle() {
        setBackAction();
        setTitle("题目搜索结果");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        answerModel = (AnswerModel) getIntent().getSerializableExtra("answer");
        ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), AnswerFragment.newInstance(AppConstant.HomeAnalysis, "", answerModel),R.id.fragment);
    }

    @Override
    public int getLayoutId() {
        QMUIStatusBarHelper.setStatusBarLightMode(context);
        QMUIStatusBarHelper.translucent(context);
        return R.layout.activity_search_subject;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyboardUtils.fixSoftInputLeaks(context);
    }
}
