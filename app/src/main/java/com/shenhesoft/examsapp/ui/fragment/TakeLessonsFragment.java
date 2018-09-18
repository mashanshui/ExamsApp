package com.shenhesoft.examsapp.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.ViewTabPagerAdapter;
import com.shenhesoft.examsapp.ui.activity.VideoPlayActivity;
import com.shenhesoft.examsapp.ui.activity.takelesson.SearchResultActivity;
import com.shenhesoft.examsapp.ui.fragment.dohomework.AllWorkFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.router.Router;

import static com.shenhesoft.examsapp.AppConstant.ENGLISH;
import static com.shenhesoft.examsapp.AppConstant.OTHER;

/**
 * @author mashanshui
 * @date 2018/5/3
 * @desc TODO
 */
public class TakeLessonsFragment extends XFragment {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager viewPage;
    @BindView(R.id.iv_start_do_homework)
    ImageView ivStartDoHomework;

    private List<Fragment> fragments;
    private AllWorkFragment mathFragment;
    private AllWorkFragment logicFragment;
    private AllWorkFragment writingFragment;
    private AllWorkFragment majorFragment;
    private AllWorkFragment englishFragment;
    private AllWorkFragment otherFragment;

    public TakeLessonsFragment() {
        // Required empty public constructor
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mathFragment = AllWorkFragment.newInstance(AppConstant.CourseWare, AppConstant.NotBuy, AppConstant.MATH);
        logicFragment = AllWorkFragment.newInstance(AppConstant.CourseWare, AppConstant.NotBuy, AppConstant.LOGIC);
        writingFragment = AllWorkFragment.newInstance(AppConstant.CourseWare, AppConstant.NotBuy, AppConstant.WRITING);
        majorFragment = AllWorkFragment.newInstance(AppConstant.CourseWare, AppConstant.NotBuy, AppConstant.MAJOR);
        englishFragment = AllWorkFragment.newInstance(AppConstant.CourseWare, AppConstant.NotBuy, ENGLISH);
        otherFragment = AllWorkFragment.newInstance(AppConstant.CourseWare, AppConstant.NotBuy, OTHER);
        fragments = new ArrayList<>();
        fragments.add(mathFragment);
        fragments.add(logicFragment);
        fragments.add(writingFragment);
        fragments.add(majorFragment);
        fragments.add(englishFragment);
        fragments.add(otherFragment);
        List<String> titleList = Arrays.asList("数学", "逻辑", "写作", "专业课", "英语", "其他");
        ViewTabPagerAdapter viewPageFragmentAdapter = new ViewTabPagerAdapter(getChildFragmentManager(), fragments, titleList);
        viewPage.setOffscreenPageLimit(10);
        viewPage.setAdapter(viewPageFragmentAdapter);
        tabLayout.setupWithViewPager(viewPage);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“GO”键*/
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v
                            .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    String searchKey = etSearch.getText().toString().trim();
                    if (TextUtils.isEmpty(searchKey)) {
                        IToast.showShort("搜索内容不能为空");
                    } else {
                        Router.newIntent(context).to(SearchResultActivity.class)
                                .putString("searchKey", searchKey)
                                .launch();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_take_lessons;
    }

    @Override
    public Object newP() {
        return null;
    }

    @OnClick(R.id.iv_start_do_homework)
    public void onViewClicked() {
        Router.newIntent(context).to(VideoPlayActivity.class).launch();
    }
}
