package com.shenhesoft.examsapp.ui.activity.dohomework;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.FinishAnswerModel;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/5/14
 * @desc 做题结果统计
 */
public class HomeWorkStatisticsActivity extends XTitleActivity {

    @BindView(R.id.tv_answer_number)
    TextView tvAnswerNumber;
    @BindView(R.id.tv_user_time)
    TextView tvUserTime;
    @BindView(R.id.tv_correct_rate)
    TextView tvCorrectRate;
    @BindView(R.id.btn_wrong_questions_analysis)
    QMUIRoundButton btnWrongQuestionsAnalysis;
    @BindView(R.id.btn_all_questions_analysis)
    QMUIRoundButton btnAllQuestionsAnalysis;
    @BindView(R.id.tv_wrong_question_number)
    TextView tvWrongQuestionNumber;
    @BindView(R.id.tv_right_question_number)
    TextView tvRightQuestionNumber;
    @BindView(R.id.tv_total_answer)
    TextView tvTotalAnswer;
    @BindView(R.id.tv_current_testpaper_notdo)
    TextView tvCurrentTestpaperNotdo;
    @BindView(R.id.btn_continue_answer)
    QMUIRoundButton btnContinueAnswer;

    private FinishAnswerModel answerModel;

    @Override
    protected void initTitle() {
        answerModel = (FinishAnswerModel) getIntent().getSerializableExtra("data");
        setBackAction();
        setTitle("统计成绩");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        btnAllQuestionsAnalysis.setChangeAlphaWhenPress(true);
        btnContinueAnswer.setChangeAlphaWhenPress(true);
        btnWrongQuestionsAnalysis.setChangeAlphaWhenPress(true);
        tvAnswerNumber.setText(answerModel.getAnswerQuestionNum() + "");
        tvUserTime.setText(answerModel.getUsedTime());
        tvCorrectRate.setText(answerModel.getRightRate() + "%");
        tvRightQuestionNumber.setText("正确数：" + answerModel.getRightQuestionNum());
        tvWrongQuestionNumber.setText("错题数" + (answerModel.getAnswerQuestionNum() - answerModel.getRightQuestionNum()));
        String answerSchedule = answerModel.getAnswerSchedule();
        String total = answerSchedule.substring(0, answerSchedule.lastIndexOf("/"));
        String notDone = answerSchedule.substring(answerSchedule.lastIndexOf("/") + 1, answerSchedule.length());
        tvTotalAnswer.setText(total);
        tvCurrentTestpaperNotdo.setText(Integer.valueOf(notDone)-Integer.valueOf(total)+"");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_work_statistics;
    }

    @Override
    public Object newP() {
        return null;
    }

    @OnClick({R.id.btn_wrong_questions_analysis, R.id.btn_all_questions_analysis, R.id.btn_continue_answer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_wrong_questions_analysis:
                Router.newIntent(context).to(AnswerActivity.class)
                        .putInt(AppConstant.AnswerType, AppConstant.HomeAnalysis)
                        .putString("productId", answerModel.getId())
                        //全部题目分析还是错题分析
                        .putInt("WrongOrAll", AppConstant.OnlyWrongQuestion)
                        .launch();
                break;
            case R.id.btn_all_questions_analysis:
                Router.newIntent(context).to(AnswerActivity.class)
                        .putInt(AppConstant.AnswerType, AppConstant.HomeAnalysis)
                        .putString("productId", answerModel.getId())
                        .putInt("WrongOrAll", AppConstant.AllQuestion)
                        .launch();
                break;
            case R.id.btn_continue_answer:
                finish();
                break;
            default:
                break;
        }
    }
}
