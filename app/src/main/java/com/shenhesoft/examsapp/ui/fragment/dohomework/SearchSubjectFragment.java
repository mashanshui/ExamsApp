package com.shenhesoft.examsapp.ui.fragment.dohomework;


import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.SearchSubjectAdapter;
import com.shenhesoft.examsapp.network.model.AnswerModel;
import com.shenhesoft.examsapp.present.SearchSubjectPresent;
import com.shenhesoft.examsapp.ui.activity.dohomework.SearchResultActivity;
import com.shenhesoft.examsapp.ui.activity.dohomework.SearchSubjectActivity;
import com.shenhesoft.examsapp.view.SearchSubjectView;

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
 * @desc 搜索的题目结果
 */
public class SearchSubjectFragment extends XLazyFragment<SearchSubjectPresent> implements SearchSubjectView,BGARefreshLayout.BGARefreshLayoutDelegate {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.rv_search_subject)
    RecyclerView rvSearchSubject;
    @BindView(R.id.bgaRefreshLayout)
    BGARefreshLayout bgaRefreshLayout;

    private SearchSubjectAdapter adapter;
    private List<AnswerModel> searchSubjectList;
    private boolean isLoadMore;
    private int start = 0;
    private int length = 20;

    private String searchKey;

    private String mParam1;
    private String mParam2;

    public SearchSubjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchSubjectFragment.
     */
    public static SearchSubjectFragment newInstance(String param1, String param2) {
        SearchSubjectFragment fragment = new SearchSubjectFragment();
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
        searchKey = ((SearchResultActivity) getActivity()).getSearchKey();
        searchSubjectList = new ArrayList<>();
        adapter = new SearchSubjectAdapter(searchSubjectList);
        rvSearchSubject.setLayoutManager(new LinearLayoutManager(context));
        rvSearchSubject.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        rvSearchSubject.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Router.newIntent(context).putSerializable("answer", searchSubjectList.get(position))
                        .to(SearchSubjectActivity.class)
                        .launch();
            }
        });
        View notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) rvSearchSubject.getParent(), false);
        adapter.setEmptyView(notDataView);

        bgaRefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder(context, true);
        bgaRefreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
        getP().searchData(bgaRefreshLayout);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_subject;
    }

    @Override
    public SearchSubjectPresent newP() {
        return new SearchSubjectPresent();
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
    public void updateData(List<AnswerModel> subjectListBean) {
        if (!searchSubjectList.isEmpty()) {
            searchSubjectList.clear();
        }
        searchSubjectList.addAll(subjectListBean);
        refreshData();
    }

    @Override
    public void updateAddData(List<AnswerModel> subjectListBean) {
        searchSubjectList.addAll(subjectListBean);
        refreshData();
    }

    public void refreshData() {
        adapter.notifyDataSetChanged();
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
}
