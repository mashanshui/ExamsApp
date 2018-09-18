package com.shenhesoft.examsapp.ui.fragment.dohomework;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.SubjectOptionAdapter;
import com.shenhesoft.examsapp.network.model.AnswerModel;
import com.shenhesoft.examsapp.present.AnswerPresent;
import com.shenhesoft.examsapp.ui.activity.dohomework.AnswerActivity;
import com.zzhoujay.richtext.RichText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.droidlover.xdroidmvp.event.Event;
import cn.droidlover.xdroidmvp.event.EventBusUtils;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.net.HttpConstant;

/**
 * @author mashanshui
 * @date 2018/5/24
 * @desc 题目显示界面
 */
public class AnswerFragment extends XFragment<AnswerPresent> {
    //题目是否作对
    public static final int Right = 0;
    public static final int Error = 1;

    //题目答案
    public static final int RightOption = 1;
    public static final int ErrorOption = 2;

    //题目是否做了
    public static final int Done = 0;
    public static final int NotDone = 1;

    private static final String ARG_PARAM1 = "answerType";
    private static final String ARG_PARAM2 = "productId";
    private static final String ARG_PARAM3 = "answerData";
    @BindView(R.id.tv_subject_content)
    TextView tvSubjectContent;
    @BindView(R.id.tv_questionNo)
    TextView tvQuestionNo;
    @BindView(R.id.rv_subject_option)
    RecyclerView rvSubjectOption;
    @BindView(R.id.tv_subject_analysis)
    TextView tvSubjectAnalysis;
    @BindView(R.id.tv_subject_correct_rate)
    TextView tvSubjectCorrectRate;
    @BindView(R.id.ns_layout_main)
    NestedScrollView nsLayoutMain;
    @BindView(R.id.btn_next_question)
    QMUIRoundButton btnNextQuestion;
    @BindView(R.id.ll_answer_analysis)
    LinearLayout llAnswerAnalysis;
    @BindView(R.id.ll_answer_percent_correct)
    LinearLayout llAnswerPercentCorrect;
    @BindView(R.id.tv_testpager_name)
    TextView tvTestpagerName;
    @BindView(R.id.tv_knowledgePoint_name)
    TextView tvKnowledgePointName;
    @BindView(R.id.ll_answer_source)
    LinearLayout llAnswerSource;

    private int answerType;
    private String productId;
    private SubjectOptionAdapter subjectOptionAdapter;
    private AnswerModel answerModel;

    public AnswerFragment() {
        // Required empty public constructor
    }

    /**
     * @param answerType   做题类型(等于0做题/等于1查看分析)
     * @param answerModels
     * @return
     */
    public static AnswerFragment newInstance(int answerType, String productId, AnswerModel answerModels) {
        AnswerFragment fragment = new AnswerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, answerType);
        args.putString(ARG_PARAM2, productId);
        args.putSerializable(ARG_PARAM3, answerModels);
        fragment.setArguments(args);
        return fragment;
    }

    private void refreshUI() {
        TypedValue background = new TypedValue();//背景色
        TypedValue textColor = new TypedValue();//字体颜色
        Resources.Theme theme = getActivity().getTheme();
        theme.resolveAttribute(R.attr.clockBackground, background, true);
        theme.resolveAttribute(R.attr.clockTextColor, textColor, true);
        nsLayoutMain.setBackgroundResource(background.resourceId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            answerType = getArguments().getInt(ARG_PARAM1);
            productId = getArguments().getString(ARG_PARAM2);
            answerModel = (AnswerModel) getArguments().getSerializable(ARG_PARAM3);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        RichText.initCacheDir(context);
        btnNextQuestion.setChangeAlphaWhenPress(true);
//        tvSubjectContent.setText(answerModel.getContent());
        tvQuestionNo.setText(answerModel.getQuestionNo() + ".");
        String content = answerModel.getContent();
        content = content.replace("../", HttpConstant.BASE_IP);
        RichText.fromHtml(content).into(tvSubjectContent);
        subjectOptionAdapter = new SubjectOptionAdapter(answerModel.getOptions());
        rvSubjectOption.setNestedScrollingEnabled(false);
        rvSubjectOption.setLayoutManager(new LinearLayoutManager(context));
        rvSubjectOption.setAdapter(subjectOptionAdapter);
        subjectOptionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CheckBox checkBox = view.findViewById(R.id.cb_is_select);
                if (answerType == AppConstant.HomeAnalysis) {
                    checkBox.setClickable(false);
                    return;
                }
                if (checkBox.isChecked()) {
                    checkBox.setChecked(false);
                    answerModel.getOptions().get(position).setOptionSelect(AppConstant.NotSelect);
                } else {
                    checkBox.setChecked(true);
                    answerModel.getOptions().get(position).setOptionSelect(AppConstant.Select);
                }
            }
        });
        if (answerType == AppConstant.DoHomeWork) {
            btnNextQuestion.setVisibility(View.VISIBLE);
            llAnswerAnalysis.setVisibility(View.GONE);
            llAnswerPercentCorrect.setVisibility(View.GONE);
            llAnswerSource.setVisibility(View.GONE);
        } else if (answerType == AppConstant.HomeAnalysis) {
            llAnswerSource.setVisibility(View.VISIBLE);
            tvTestpagerName.setText("试卷：" + answerModel.getPaperTitle());
            tvKnowledgePointName.setText("知识点：" + answerModel.getKnowledgePointName());
            btnNextQuestion.setVisibility(View.GONE);
            llAnswerAnalysis.setVisibility(View.VISIBLE);
            llAnswerPercentCorrect.setVisibility(View.VISIBLE);
            String questionAnalysis = answerModel.getQuestionStatis();
            questionAnalysis = questionAnalysis.replace("../", HttpConstant.BASE_IP);
            RichText.fromHtml(questionAnalysis).into(tvSubjectAnalysis);
            tvSubjectCorrectRate.setText(answerModel.getRate() + "%");
            selectRightOption();
        }
    }

    private void selectRightOption() {
        List<AnswerModel.OptionsBean> optionsBeanList = answerModel.getOptions();
        for (int i = 0; i < optionsBeanList.size(); i++) {
            AnswerModel.OptionsBean optionsBean = optionsBeanList.get(i);
            if (optionsBean.getOptionAnswered() == RightOption) {
                optionsBean.setOptionSelect(AppConstant.Select);
            }
        }
        subjectOptionAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public void receiveEvent(Event event) {
        int code = event.getCode();
        if (code == EventBusUtils.EventCode.THEME_MODE) {
            refreshUI();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_answer;
    }

    @Override
    public AnswerPresent newP() {
        return new AnswerPresent();
    }

    @OnClick(R.id.btn_next_question)
    public void onViewClicked() {
//        nextAnswer();
        EventBusUtils.sendEvent(new Event(EventBusUtils.EventCode.NEXT_ANSWER));
    }

    private void nextAnswer() {
        getP().nextSubject(false, ((AnswerActivity) getActivity()).getAnswerId(), answerModel.getId(), isRight(), isFinish());
    }

    /**
     * @return 用户选中的选项id
     */
    private String getSelectOptionId() {
        StringBuilder optionId = new StringBuilder();
        List<AnswerModel.OptionsBean> optionsBeanList = answerModel.getOptions();
        for (int i = 0; i < optionsBeanList.size(); i++) {
            AnswerModel.OptionsBean optionsBean = optionsBeanList.get(i);
            if (optionsBean.getOptionSelect() == AppConstant.Select) {
                optionId.append(optionsBean.getId() + ";");
            }
        }
        if (optionId.length() != 0) {
            optionId.deleteCharAt(optionId.length() - 1);
        }
        return optionId.toString();
    }

    private int isRight() {
        List<AnswerModel.OptionsBean> optionsBeanList = answerModel.getOptions();
        for (int i = 0; i < optionsBeanList.size(); i++) {
            AnswerModel.OptionsBean optionsBean = optionsBeanList.get(i);
            //如果没有选择的选项是对的,则说明答题错误
            if (optionsBean.getOptionSelect() == AppConstant.NotSelect && optionsBean.getOptionAnswered() == RightOption) {
                return Error;
            }
            //如果选择的选项是错的,则说明答题错误
            if (optionsBean.getOptionSelect() == AppConstant.Select && optionsBean.getOptionAnswered() == ErrorOption) {
                return Error;
            }
        }
        return Right;
    }

    private int isFinish() {
        List<AnswerModel.OptionsBean> optionsBeanList = answerModel.getOptions();
        for (int i = 0; i < optionsBeanList.size(); i++) {
            AnswerModel.OptionsBean optionsBean = optionsBeanList.get(i);
            if (optionsBean.getOptionSelect() == AppConstant.Select) {
                return Done;
            }
        }
        return NotDone;
    }

}
