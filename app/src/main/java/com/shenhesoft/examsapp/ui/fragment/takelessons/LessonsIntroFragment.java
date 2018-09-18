package com.shenhesoft.examsapp.ui.fragment.takelessons;


import android.os.Bundle;
import android.widget.TextView;

import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.LessonsIntroModel;
import com.shenhesoft.examsapp.present.LessonsIntroPresent;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.event.Event;
import cn.droidlover.xdroidmvp.event.EventBusUtils;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;


/**
 * @author mashanshui
 * @date 2018/6/5
 * @desc 听课-》简介
 */
public class LessonsIntroFragment extends XLazyFragment<LessonsIntroPresent> {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.tv_lesson_intro)
    TextView tvLessonIntro;

    private String mParam1;
    private String mParam2;


    public LessonsIntroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LessonsIntroFragment.
     */
    public static LessonsIntroFragment newInstance(String param1, String param2) {
        LessonsIntroFragment fragment = new LessonsIntroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_lessons_intro;
    }

    @Override
    public LessonsIntroPresent newP() {
        return new LessonsIntroPresent();
    }

    @Override
    public void receiveEvent(Event event) {
        if (event.getCode() == EventBusUtils.EventCode.LESSONS_INTRO) {
            String id = (String) event.getData();
            getP().loadData(id);
        }
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    public void updateIntro(LessonsIntroModel obj) {
        tvLessonIntro.setText(obj.getProductIntroduction());
    }
}
