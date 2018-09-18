package com.shenhesoft.examsapp.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.AdviceAdapter;
import com.shenhesoft.examsapp.network.model.AdviceModel;
import com.shenhesoft.examsapp.present.AdvicePresent;
import com.shenhesoft.examsapp.view.AdviceView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/6/2
 * @desc 投诉建议
 */
public class AdviceActivity extends XTitleActivity<AdvicePresent> implements AdviceView, BGARefreshLayout.BGARefreshLayoutDelegate {
    public static final int RequestCode = 1;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.bgaRefreshLayout)
    BGARefreshLayout bgaRefreshLayout;

    private AdviceAdapter adviceAdapter;
    private List<AdviceModel> adviceBeans;
    public boolean isLoadMore;
    public int start = 0;
    public int length = 15;

    @Override
    protected void initTitle() {
        setBackAction();
        setTitle("投诉建议");
        QMUIAlphaImageButton imageButton = setRightImageButton(R.drawable.add_advice);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.newIntent(context).to(AddAdviceActivity.class)
                        .requestCode(RequestCode)
                        .launch();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        adviceBeans = new ArrayList<>();
        adviceAdapter = new AdviceAdapter(adviceBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adviceAdapter);
        adviceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
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
        return R.layout.activity_advice;
    }

    @Override
    public AdvicePresent newP() {
        return new AdvicePresent();
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
    public void updateDate(List<AdviceModel> adviceModels) {
        if (!adviceBeans.isEmpty()) {
            adviceBeans.clear();
        }
        adviceBeans.addAll(adviceModels);
        refreshData();
    }

    @Override
    public void updateAddDate(List<AdviceModel> adviceModels) {
        adviceBeans.addAll(adviceModels);
        refreshData();
    }

    private void refreshData() {
        adviceAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCode && resultCode == RESULT_OK) {
            bgaRefreshLayout.beginRefreshing();
        }
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
}
