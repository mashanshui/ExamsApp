package com.shenhesoft.examsapp.ui.activity.modifyhomework;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.ui.fragment.modifyhomework.SearchResultFragment;
import com.shenhesoft.examsapp.util.ActivityUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.event.Event;
import cn.droidlover.xdroidmvp.event.EventBusUtils;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * @author mashanshui
 * @date 2018/6/15
 * @desc 作文批改搜索结果
 */
public class SearchResultActivity extends XActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_topbar)
    ImageView ivTopbar;
    @BindView(R.id.fragment)
    FrameLayout fragment;

    /**
     * 搜索的作文批改展示界面
     */
    private SearchResultFragment questionFragment;

    private String searchKey;

    @Override
    public void initData(Bundle savedInstanceState) {
        searchKey = getIntent().getStringExtra("searchKey");
        QMUIStatusBarHelper.translucent(context);
        QMUIStatusBarHelper.setStatusBarLightMode(context);
        questionFragment = SearchResultFragment.newInstance(searchKey, AppConstant.WritingCheck);
        ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), questionFragment, R.id.fragment);
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
        return R.layout.activity_search_result4;
    }

    @Override
    public Object newP() {
        return null;
    }

    public String getSearchKey() {
        return searchKey;
    }

    @OnClick(R.id.iv_topbar)
    public void onViewClicked() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyboardUtils.fixSoftInputLeaks(context);
    }
}
