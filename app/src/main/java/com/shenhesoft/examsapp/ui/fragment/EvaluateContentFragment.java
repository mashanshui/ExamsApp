package com.shenhesoft.examsapp.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.EvaluateAdapter;
import com.shenhesoft.examsapp.network.model.EvaluateModel;
import com.shenhesoft.examsapp.present.EvaluateContentPresent;
import com.shenhesoft.examsapp.view.EvaluateContentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;

/**
 * @author mashanshui
 * @date 2018/5/15
 * @desc 评价的具体内容（好评、中评、差评）
 */
public class EvaluateContentFragment extends XLazyFragment<EvaluateContentPresent> implements EvaluateContentView, BGARefreshLayout.BGARefreshLayoutDelegate {

    private static final String ARG_PARAM1 = "PRODUCT_ID";
    private static final String ARG_PARAM2 = "EVALUATE_TYPE";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.bgaRefreshLayout)
    BGARefreshLayout bgaRefreshLayout;

    private EvaluateAdapter evaluateAdapter;
    private List<EvaluateModel> evaluateBeanList;

    private String productId;
    private String evaluateType;

    private boolean isLoadMore;
    private int start = 0;
    private int length = 20;

    public EvaluateContentFragment() {
        // Required empty public constructor
    }

    public static EvaluateContentFragment newInstance(String productId, String evaluateType) {
        EvaluateContentFragment fragment = new EvaluateContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, productId);
        args.putString(ARG_PARAM2, evaluateType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productId = getArguments().getString(ARG_PARAM1);
            evaluateType = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        evaluateBeanList = new ArrayList<>();
        evaluateAdapter = new EvaluateAdapter(evaluateBeanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(evaluateAdapter);
        evaluateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        bgaRefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder(context, true);
        bgaRefreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
        getP().loadData(bgaRefreshLayout);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_evaluate_content;
    }

    @Override
    public EvaluateContentPresent newP() {
        return new EvaluateContentPresent();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        isLoadMore = false;
        start = 0;
        getP().loadData(bgaRefreshLayout);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        isLoadMore = true;
        start = start + length;
        getP().loadData(bgaRefreshLayout);
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
    public String getProductId() {
        return productId;
    }

    @Override
    public String getEvaluateType() {
        return evaluateType;
    }

    @Override
    public void updateData(List<EvaluateModel> evaluateModels) {
        if (!evaluateBeanList.isEmpty()) {
            evaluateBeanList.clear();
        }
        evaluateBeanList.addAll(evaluateModels);
        refreshData();
    }

    @Override
    public void updateAddData(List<EvaluateModel> evaluateModels) {
        evaluateBeanList.addAll(evaluateModels);
        refreshData();
    }

    private void refreshData() {
        evaluateAdapter.notifyDataSetChanged();
    }
}
