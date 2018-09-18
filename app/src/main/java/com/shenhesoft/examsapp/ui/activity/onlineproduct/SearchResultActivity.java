package com.shenhesoft.examsapp.ui.activity.onlineproduct;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.OnlineSearchAdapter;
import com.shenhesoft.examsapp.adapter.ViewTabPagerAdapter;
import com.shenhesoft.examsapp.network.model.SearchTypeModel;
import com.shenhesoft.examsapp.present.OnlineSearchPresent;
import com.shenhesoft.examsapp.ui.fragment.onlineproduct.OnlineFragmentListFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.event.Event;
import cn.droidlover.xdroidmvp.event.EventBusUtils;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * @author mashanshui
 * @date 2018/5/22
 * @desc 线上作业搜索结果
 */
public class SearchResultActivity extends XActivity<OnlineSearchPresent> {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager viewPage;
    @BindView(R.id.iv_topbar)
    ImageView ivTopbar;
    private PopupWindow popupLevel1;
    private int popupHeight;
    private PopupWindow popupLevel2;
    private RecyclerView popupRecyclerView;

    private List<Fragment> fragments;
    private OnlineFragmentListFragment questionBankFragment;
    private OnlineFragmentListFragment couresWareFragment;
    private OnlineFragmentListFragment writingCheckFragment;

    private TextView tvType;
    private TextView tvSource;
    private TextView tvYear;

    private String searchKey;
    private String searchType;
    private String searchSource;
    private String searchDate;

    private OnlineSearchAdapter searchAdapter;
    private List<SearchTypeModel> searchTypeModels;

    @Override
    public void initData(Bundle savedInstanceState) {
        searchKey = getIntent().getStringExtra("searchKey");
        etSearch.setText(searchKey);

        QMUIStatusBarHelper.translucent(context);
        QMUIStatusBarHelper.setStatusBarLightMode(context);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“GO”键*/
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
        fragments = new ArrayList<>();
        questionBankFragment = OnlineFragmentListFragment.newInstance(1, "", searchKey);
        couresWareFragment = OnlineFragmentListFragment.newInstance(2, "", searchKey);
        writingCheckFragment = OnlineFragmentListFragment.newInstance(3, "", searchKey);
        fragments.add(questionBankFragment);
        fragments.add(couresWareFragment);
        fragments.add(writingCheckFragment);
        List<String> titleList = Arrays.asList("题库", "课件", "作文批改");
        ViewTabPagerAdapter viewPageFragmentAdapter = new ViewTabPagerAdapter(getSupportFragmentManager(), fragments, titleList);
        viewPage.setOffscreenPageLimit(10);
        viewPage.setAdapter(viewPageFragmentAdapter);
        tabLayout.setupWithViewPager(viewPage);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() != 2) {
                    return;
                }
                showPopup();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() != 2) {
                    return;
                }
                showPopup();
            }
        });
        searchTypeModels = new ArrayList<>();
    }

    private void showPopup() {
        if (popupLevel1 == null) {
            popupLevel1 = new PopupWindow(context);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.popup_level_one, null);
        popupLevel1.setContentView(view);
        popupLevel1.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupLevel1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupLevel1.setOutsideTouchable(false);
        popupLevel1.setFocusable(true);
        popupLevel1.setBackgroundDrawable(null);
        popupLevel1.showAsDropDown(tabLayout);
        popupHeight = view.getHeight();
        tvType = view.findViewById(R.id.tv_type);
        tvSource = view.findViewById(R.id.tv_source);
        tvYear = view.findViewById(R.id.tv_year);
        tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvType.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                getP().getPopupType("11");
            }
        });
        tvSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSource.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                getP().getPopupType("12");
            }
        });
        tvYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvYear.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                getP().getPopupDate();
            }
        });
        popupLevel1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (tvType != null && tvSource != null && tvYear != null) {
                    tvType.setTextColor(Color.BLACK);
                    tvSource.setTextColor(Color.BLACK);
                    tvYear.setTextColor(Color.BLACK);
                }
            }
        });
    }

    private void showPopupTwo() {
        if (popupLevel2 == null) {
            popupLevel2 = new PopupWindow(context);
        }
        if (popupLevel2.isShowing()) {
            popupLevel2.dismiss();
        }
        View view2 = LayoutInflater.from(context).inflate(R.layout.popup_level_two, null);
        popupRecyclerView = view2.findViewById(R.id.popup_recyclerView);
        searchAdapter = new OnlineSearchAdapter(searchTypeModels);
        popupRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        popupRecyclerView.setAdapter(searchAdapter);
        searchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SearchTypeModel typeModels = searchTypeModels.get(position);
                switch (typeModels.getType()) {
                    case 0:
                        searchType = typeModels.getId();
                        break;
                    case 1:
                        searchSource = typeModels.getId();
                        break;
                    case 2:
                        searchDate = typeModels.getId();
                        break;
                    default:
                        break;
                }
                EventBusUtils.sendEvent(new Event(EventBusUtils.EventCode.SEARCH_QUESTION, searchKey));
                dismissPopup();
            }
        });
        popupLevel2.setContentView(view2);
        popupLevel2.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupLevel2.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupLevel2.setOutsideTouchable(false);
        popupLevel2.setFocusable(true);
        popupLevel2.setBackgroundDrawable(null);
        popupLevel2.showAsDropDown(tabLayout, 0, dp2px(36));
        popupLevel2.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (tvType != null && tvSource != null && tvYear != null) {
                    tvType.setTextColor(Color.BLACK);
                    tvSource.setTextColor(Color.BLACK);
                    tvType.setTextColor(Color.BLACK);
                }
            }
        });
    }

    private void dismissPopup() {
        popupLevel1.dismiss();
        popupLevel2.dismiss();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_result2;
    }

    @Override
    public OnlineSearchPresent newP() {
        return new OnlineSearchPresent();
    }

    @OnClick(R.id.iv_topbar)
    public void onViewClicked() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
        finish();
    }

    public void updatePopupData(List<SearchTypeModel> Models) {
        if (!searchTypeModels.isEmpty()) {
            searchTypeModels.clear();
        }
        searchTypeModels.addAll(Models);
        showPopupTwo();
    }

    public String getSearchKey() {
        return searchKey;
    }

    public String getSearchType() {
        return searchType;
    }

    public String getSearchSource() {
        return searchSource;
    }

    public String getSearchDate() {
        return searchDate;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyboardUtils.fixSoftInputLeaks(context);
    }
}
