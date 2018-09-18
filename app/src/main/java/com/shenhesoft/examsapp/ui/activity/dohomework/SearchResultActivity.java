package com.shenhesoft.examsapp.ui.activity.dohomework;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.ViewTabPagerAdapter;
import com.shenhesoft.examsapp.ui.fragment.SearchResultFragment;
import com.shenhesoft.examsapp.ui.fragment.dohomework.SearchSubjectFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.event.Event;
import cn.droidlover.xdroidmvp.event.EventBusUtils;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/5/14
 * @desc 展示题目搜索结果
 */
public class SearchResultActivity extends XActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager viewPage;
    @BindView(R.id.iv_start_do_homework)
    ImageView ivStartDoHomework;
    @BindView(R.id.iv_topbar)
    ImageView ivTopbar;

    private List<Fragment> fragmentlist;
    /**
     * 搜索的题库展示界面
     */
    private SearchResultFragment questionFragment;
    /**
     * 搜索的题目展示界面
     */
    private SearchSubjectFragment subjectFragment;

    private String searchKey;

    @Override
    public void initData(Bundle savedInstanceState) {
        searchKey = getIntent().getStringExtra("searchKey");
        etSearch.setText(searchKey);

        QMUIStatusBarHelper.translucent(context);
        QMUIStatusBarHelper.setStatusBarLightMode(context);
        fragmentlist = new ArrayList<>();
        questionFragment = SearchResultFragment.newInstance(searchKey, AppConstant.QuestionBank);
        subjectFragment = new SearchSubjectFragment();
        fragmentlist.add(questionFragment);
        fragmentlist.add(subjectFragment);
        List<String> titleList = Arrays.asList("题库", "题目");
        ViewTabPagerAdapter viewPageFragmentAdapter = new ViewTabPagerAdapter(getSupportFragmentManager(), fragmentlist, titleList);
        viewPage.setAdapter(viewPageFragmentAdapter);
        tabLayout.setupWithViewPager(viewPage);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“search”键*/
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    searchKey = etSearch.getText().toString();
                    EventBusUtils.sendEvent(new Event(EventBusUtils.EventCode.SEARCH_QUESTION, searchKey));
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_result;
    }

    @Override
    public Object newP() {
        return null;
    }

    public String getSearchKey() {
        return searchKey;
    }

    @OnClick({R.id.iv_start_do_homework, R.id.iv_topbar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_start_do_homework:
                Router.newIntent(context).to(StartDoSubjectActivity.class).launch();
                break;
            case R.id.iv_topbar:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyboardUtils.fixSoftInputLeaks(context);
    }
}
