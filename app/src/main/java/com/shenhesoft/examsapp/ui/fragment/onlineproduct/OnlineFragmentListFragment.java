package com.shenhesoft.examsapp.ui.fragment.onlineproduct;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.ProductAdapter;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.present.OnlineProductPresent;
import com.shenhesoft.examsapp.ui.activity.dohomework.QuestionBankDetailActivity;
import com.shenhesoft.examsapp.ui.activity.modifyhomework.TeacherDetailsActivity;
import com.shenhesoft.examsapp.ui.activity.onlineproduct.SearchResultActivity;
import com.shenhesoft.examsapp.ui.activity.takelesson.CoursewareDetailActivity;
import com.shenhesoft.examsapp.view.OnlineProductView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.event.Event;
import cn.droidlover.xdroidmvp.event.EventBusUtils;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/5/18
 * @desc 线上产品展示页面
 */
public class OnlineFragmentListFragment extends XLazyFragment<OnlineProductPresent> implements BGARefreshLayout.BGARefreshLayoutDelegate, OnlineProductView {

    private static final String KEY_PRODUCT_TYPE = "product_type";
    private static final String SORT = "sort";
    private static final String SEARCH_KEY = "search_key";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.bgaRefreshLayout)
    BGARefreshLayout bgaRefreshLayout;

    private ProductAdapter productAdapter;
    private List<ProductModel> productBeans;

    private int productType;
    private String sort;

    private boolean isLoadMore;
    private int start = 0;
    private int length = AppConstant.PageLength;

    /**
     * 当前页面是否是搜索页面
     */
    private boolean isSearchPage = false;
    private String firstSearchKey;
    private String searchKey;
    private String searchType;
    private String searchSource;
    private String searchDate;

    public OnlineFragmentListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param productType 产品类型
     * @param sort        排序方式
     * @return A new instance of fragment OnlineFragmentListFragment.
     */
    public static OnlineFragmentListFragment newInstance(int productType, String sort, String firstSearchKey) {
        OnlineFragmentListFragment fragment = new OnlineFragmentListFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_PRODUCT_TYPE, productType);
        args.putString(SORT, sort);
        args.putString(SEARCH_KEY, firstSearchKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productType = getArguments().getInt(KEY_PRODUCT_TYPE);
            sort = getArguments().getString(SORT);
            firstSearchKey = getArguments().getString(SEARCH_KEY);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        productBeans = new ArrayList<>();
        productAdapter = new ProductAdapter(productBeans);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(productAdapter);
        View notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerView.getParent(), false);
        productAdapter.setEmptyView(notDataView);
        productAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int productType = productBeans.get(position).getProductType();
                if (productType == AppConstant.QuestionBank) {
                    EventBus.getDefault().postSticky(productBeans.get(position));
                    Router.newIntent(context).to(QuestionBankDetailActivity.class).launch();
                } else if (productType == AppConstant.CourseWare) {
                    EventBus.getDefault().postSticky(productBeans.get(position));
                    Router.newIntent(context).to(CoursewareDetailActivity.class).launch();
                } else if (productType == AppConstant.WritingCheck) {
                    EventBus.getDefault().postSticky(productBeans.get(position));
                    Router.newIntent(context).to(TeacherDetailsActivity.class).launch();
                }
            }
        });

        bgaRefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder(context, true);
        bgaRefreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
        if (!TextUtils.isEmpty(firstSearchKey)) {
            isSearchPage = true;
            searchKey = firstSearchKey;
            firstSearchKey = null;
            getP().searchData(bgaRefreshLayout);
        } else {
            getP().loadData(productType, sort, bgaRefreshLayout);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_online_fragment_list;
    }

    @Override
    public OnlineProductPresent newP() {
        return new OnlineProductPresent();
    }

    @Override
    public void receiveEvent(Event event) {
        //只有搜索页面才会去搜索
        if (event.getCode() == EventBusUtils.EventCode.SEARCH_QUESTION && isSearchPage) {
            searchKey = ((SearchResultActivity) getActivity()).getSearchKey();
            searchType = ((SearchResultActivity) getActivity()).getSearchType();
            searchSource = ((SearchResultActivity) getActivity()).getSearchSource();
            searchDate = ((SearchResultActivity) getActivity()).getSearchDate();
            getP().searchData(bgaRefreshLayout);
        }
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        isLoadMore = false;
        start = 0;
        if (isSearchPage) {
            getP().searchData(bgaRefreshLayout);
        } else {
            getP().loadData(productType, sort, bgaRefreshLayout);
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        isLoadMore = true;
        start = start + length;
        if (isSearchPage) {
            getP().searchData(bgaRefreshLayout);
        } else {
            getP().loadData(productType, sort, bgaRefreshLayout);
        }
        return true;
    }

    @Override
    public int getStart() {
        return start;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public boolean getIsLoadingMore() {
        return isLoadMore;
    }

    @Override
    public void setStart(int start) {
        this.start = start;
    }

    @Override
    public void updateData(List<ProductModel> productModelList) {
        if (!productBeans.isEmpty()) {
            productBeans.clear();
        }
        productBeans.addAll(productModelList);
        refreshAdapter();
    }

    @Override
    public void updateAddData(List<ProductModel> productModelList) {
        productBeans.addAll(productModelList);
        refreshAdapter();
    }

    @Override
    public void refreshAdapter() {
        productAdapter.notifyDataSetChanged();
    }

    /**
     * 刷新排序方式
     *
     * @param upOrDown
     */
    @Override
    public void setUpOrDown(int upOrDown) {
        sort = String.valueOf(upOrDown);
        getP().loadData(productType, sort, bgaRefreshLayout);
    }

    @Override
    public String getSearchKey() {
        return searchKey;
    }

    @Override
    public int getProductType() {
        return productType;
    }

    @Override
    public String getSearchType() {
        return searchType;
    }

    @Override
    public String getSearchSource() {
        return searchSource;
    }

    @Override
    public String getSearchDate() {
        return searchDate;
    }
}
