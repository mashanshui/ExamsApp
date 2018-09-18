package com.shenhesoft.examsapp.ui.fragment.takelessons;


import android.os.Bundle;

import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.util.FragmentUtil;

import cn.droidlover.xdroidmvp.mvp.XLazyFragment;

/**
 * @author mashanshui
 * @date 2018/5/16
 * @desc 听课->资料
 */
public class LessonsDataFragment extends XLazyFragment {
    private static final String TAG = "LessonsDataFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private LessonsDataListFragment lessonsDataListFragment;

    public LessonsDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LessonsDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LessonsDataFragment newInstance(String param1, String param2) {
        LessonsDataFragment fragment = new LessonsDataFragment();
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
        lessonsDataListFragment = new LessonsDataListFragment();
        FragmentUtil.addFragmentToFragmentToBackStack(getChildFragmentManager(), lessonsDataListFragment, R.id.frameLayout);
    }

    public void addFragment() {
        FragmentUtil.addFragmentToFragmentToBackStack(getChildFragmentManager(), new LessonsDataPdfFragment(), R.id.frameLayout);
    }

    public void back() {
        getChildFragmentManager().popBackStack();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_lessons_data;
    }

    @Override
    public Object newP() {
        return null;
    }
}
