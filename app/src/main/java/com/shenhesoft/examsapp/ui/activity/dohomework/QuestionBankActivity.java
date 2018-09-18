package com.shenhesoft.examsapp.ui.activity.dohomework;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.ui.fragment.dohomework.AllWorkFragment;
import com.shenhesoft.examsapp.util.ActivityUtil;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;

/**
 * @author mashanshui
 * @date 2018/5/24
 * @desc 做题预览——》试卷
 */
public class QuestionBankActivity extends XTitleActivity {

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    /**
     * 所有已购题库
     */
    private AllWorkFragment allWorkFragment;

    @Override
    protected void initTitle() {
        setBackAction();
        setTitle("选择题库");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        allWorkFragment = AllWorkFragment.newInstance(AppConstant.QuestionBank, AppConstant.Buy,null);
        ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), allWorkFragment, R.id.frameLayout);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_question_bank;
    }

    @Override
    public Object newP() {
        return null;
    }

}
