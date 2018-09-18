package com.shenhesoft.examsapp.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.SubjectListAdapter;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.present.SearchQuestionPresent;
import com.shenhesoft.examsapp.ui.activity.dohomework.QuestionBankDetailActivity;
import com.shenhesoft.examsapp.ui.activity.dohomework.SearchResultActivity;
import com.shenhesoft.examsapp.ui.activity.modifyhomework.TeacherDetailsActivity;
import com.shenhesoft.examsapp.ui.activity.takelesson.CoursewareDetailActivity;
import com.shenhesoft.examsapp.view.SearchQuestionView;

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
 * @date 2018/6/7
 * @desc 搜索的题库结果
 */
public class SearchResultFragment extends XLazyFragment<SearchQuestionPresent> implements SearchQuestionView, BGARefreshLayout.BGARefreshLayoutDelegate{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.bgaRefreshLayout)
    BGARefreshLayout bgaRefreshLayout;

    private SubjectListAdapter subjectListAdapter;
    private List<ProductModel> subjectListBeans;

    private String firstSearchKey;
    private int productType;
    private boolean isLoadMore;
    private int start = 0;
    private int length = AppConstant.PageLength;

    private String searchKey;

    public SearchResultFragment() {
        // Required empty public constructor
    }

    public static SearchResultFragment newInstance(String firstSearchKey,int productType) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, firstSearchKey);
        args.putInt(ARG_PARAM2, productType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            firstSearchKey = getArguments().getString(ARG_PARAM1);
            productType = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        subjectListBeans = new ArrayList<>();
        subjectListAdapter = new SubjectListAdapter(subjectListBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(subjectListAdapter);
        subjectListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int productType = subjectListBeans.get(position).getProductType();
                if (productType == AppConstant.QuestionBank) {
                    EventBus.getDefault().postSticky(subjectListBeans.get(position));
                    Router.newIntent(context).to(QuestionBankDetailActivity.class).launch();
                } else if (productType == AppConstant.CourseWare) {
                    EventBus.getDefault().postSticky(subjectListBeans.get(position));
                    Router.newIntent(context).to(CoursewareDetailActivity.class).launch();
                } else if (productType == AppConstant.WritingCheck) {
                    EventBus.getDefault().postSticky(subjectListBeans.get(position));
                    Router.newIntent(context).to(TeacherDetailsActivity.class).launch();
                }
            }
        });

        View notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerView.getParent(), false);
        subjectListAdapter.setEmptyView(notDataView);

        bgaRefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder(context, true);
        bgaRefreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
        if (!TextUtils.isEmpty(firstSearchKey)) {
            searchKey = firstSearchKey;
            firstSearchKey = null;
            getP().searchData(bgaRefreshLayout);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_question_bank;
    }

    @Override
    public SearchQuestionPresent newP() {
        return new SearchQuestionPresent();
    }

    @Override
    public void receiveEvent(Event event) {
        if (event.getCode() == EventBusUtils.EventCode.SEARCH_QUESTION) {
            searchKey = ((SearchResultActivity) getActivity()).getSearchKey();
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
        getP().searchData(bgaRefreshLayout);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        isLoadMore = true;
        start = start + length;
        getP().searchData(bgaRefreshLayout);
        return true;
    }

    @Override
    public void updateData(List<ProductModel> listBean) {
        if (!subjectListBeans.isEmpty()) {
            subjectListBeans.clear();
        }
        subjectListBeans.addAll(listBean);
        refreshData();
    }

    @Override
    public void updateAddData(List<ProductModel> listBean) {
        subjectListBeans.addAll(listBean);
        refreshData();
    }

    public void refreshData() {
        subjectListAdapter.notifyDataSetChanged();
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
    public boolean isLoadingMore() {
        return isLoadMore;
    }

    @Override
    public void setStart(int start) {
        this.start = start;
    }

    @Override
    public String getSearchKey() {
        return searchKey;
    }

    @Override
    public int getProductType() {
        return productType;
    }

}
