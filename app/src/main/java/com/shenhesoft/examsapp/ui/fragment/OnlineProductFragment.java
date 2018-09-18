package com.shenhesoft.examsapp.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.MainViewPageFragmentAdapter;
import com.shenhesoft.examsapp.ui.activity.onlineproduct.SearchResultActivity;
import com.shenhesoft.examsapp.ui.fragment.onlineproduct.OnlineFragmentListFragment;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/5/3
 * @desc 线上产品
 */
public class OnlineProductFragment extends XFragment implements SuperTextView.OnSuperTextViewClickListener {
    private static final String TAG = "OnlineProductFragment";
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.comprehensive_sort)
    SuperTextView comprehensiveSort;
    @BindView(R.id.sales_sort)
    SuperTextView salesSort;
    @BindView(R.id.price_sort)
    SuperTextView priceSort;
    @BindView(R.id.viewPager)
    QMUIViewPager viewPager;

    //默认降序排列
    private boolean comprehensiveDown = true;
    private boolean salesDown = true;
    private boolean priceDown = true;

    private OnlineFragmentListFragment comprehensiveSortFragment;
    private OnlineFragmentListFragment salesSortFragment;
    private OnlineFragmentListFragment priceSortFragment;

    public OnlineProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        comprehensiveSort.setOnSuperTextViewClickListener(this);
        salesSort.setOnSuperTextViewClickListener(this);
        priceSort.setOnSuperTextViewClickListener(this);
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
                    String searchKey = etSearch.getText().toString().trim();
                    if (TextUtils.isEmpty(searchKey)) {
                        IToast.showShort("搜索内容不能为空");
                    } else {
                        Router.newIntent(context).to(SearchResultActivity.class)
                                .putString("searchKey",searchKey)
                                .launch();
                    }
                    return true;
                }
                return false;
            }
        });
        comprehensiveSortFragment = OnlineFragmentListFragment.newInstance(1, "", "");
        salesSortFragment = OnlineFragmentListFragment.newInstance(1, "0", "");
        priceSortFragment = OnlineFragmentListFragment.newInstance(1, "2", "");
        MainViewPageFragmentAdapter viewPageFragmentAdapter = new MainViewPageFragmentAdapter(getChildFragmentManager());
        viewPageFragmentAdapter.addFragment(comprehensiveSortFragment);
        viewPageFragmentAdapter.addFragment(salesSortFragment);
        viewPageFragmentAdapter.addFragment(priceSortFragment);
        //禁止滑动切换
        viewPager.setSwipeable(false);
        viewPager.setOffscreenPageLimit(10);
        viewPager.setAdapter(viewPageFragmentAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        comprehensiveSort.setCenterTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                        salesSort.setRightTextColor(ContextCompat.getColor(context, R.color.black));
                        priceSort.setRightTextColor(ContextCompat.getColor(context, R.color.black));
                        break;
                    case 1:
                        comprehensiveSort.setCenterTextColor(ContextCompat.getColor(context, R.color.black));
                        salesSort.setRightTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                        priceSort.setRightTextColor(ContextCompat.getColor(context, R.color.black));
                        break;
                    case 2:
                        comprehensiveSort.setCenterTextColor(ContextCompat.getColor(context, R.color.black));
                        salesSort.setRightTextColor(ContextCompat.getColor(context, R.color.black));
                        priceSort.setRightTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_online_product;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void onClickListener(SuperTextView superTextView) {
        switch (superTextView.getId()) {
            case R.id.comprehensive_sort:
//                if (viewPager.getCurrentItem() == 0) {
//                    comprehensiveSort.setRightIcon(comprehensiveDown ? R.drawable.arrow_up : R.drawable.arrow_down);
//                    comprehensiveDown = booleanSwitch(comprehensiveDown);
//                    comprehensiveSortFragment.setUpOrDown(comprehensiveDown ? AppConstant.SortDown : AppConstant.SortUp);
//                }
                viewPager.setCurrentItem(0);
                break;
            case R.id.sales_sort:
                if (viewPager.getCurrentItem() == 1) {
                    salesSort.setRightIcon(salesDown ? R.drawable.sort_up : R.drawable.sort_down);
                    salesDown = booleanSwitch(salesDown);
                    salesSortFragment.setUpOrDown(salesDown ? 0 : 1);
                }
                viewPager.setCurrentItem(1);
                break;
            case R.id.price_sort:
                if (viewPager.getCurrentItem() == 2) {
                    priceSort.setRightIcon(priceDown ? R.drawable.sort_up : R.drawable.sort_down);
                    priceDown = booleanSwitch(priceDown);
                    priceSortFragment.setUpOrDown(priceDown ? 2 : 3);
                }
                viewPager.setCurrentItem(2);
                break;
            default:
                break;
        }
    }

    /**
     * @param value
     * @return 将boolean值反转
     */
    private boolean booleanSwitch(boolean value) {
        return !value;
    }

}
