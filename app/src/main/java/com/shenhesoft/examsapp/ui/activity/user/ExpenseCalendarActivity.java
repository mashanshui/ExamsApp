package com.shenhesoft.examsapp.ui.activity.user;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.ExpenseCalendarAdapter;
import com.shenhesoft.examsapp.network.model.ExpenseCalendarModel;
import com.shenhesoft.examsapp.present.ExpenseCalendarPresent;
import com.shenhesoft.examsapp.view.ExpenseCalendarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;

/**
 * @author mashanshui
 * @date 2018/5/19
 * @desc 消费记录
 */
public class ExpenseCalendarActivity extends XTitleActivity<ExpenseCalendarPresent> implements ExpenseCalendarView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public boolean isLoadMore;
    public int start = 0;
    public int length = 15;
    @BindView(R.id.bgaRefreshLayout)
    BGARefreshLayout bgaRefreshLayout;
    private ExpenseCalendarAdapter expenseCalendarAdapter;
    private List<ExpenseCalendarModel> expenseCalendarBeans;

    @Override
    protected void initTitle() {
        setBackAction();
        setTitle("消费记录");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        expenseCalendarBeans = new ArrayList<>();
        expenseCalendarAdapter = new ExpenseCalendarAdapter(expenseCalendarBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(expenseCalendarAdapter);
        bgaRefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder(context, true);
        bgaRefreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
        getP().loadData(bgaRefreshLayout);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_expense_calendar;
    }

    @Override
    public ExpenseCalendarPresent newP() {
        return new ExpenseCalendarPresent();
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
    public boolean isLoadingMore() {
        return isLoadMore;
    }

    @Override
    public void setStart(int start) {
        this.start = start;
    }


    @Override
    public void updateDate(List<ExpenseCalendarModel> expenseCalendarModels) {
        if (!expenseCalendarBeans.isEmpty()) {
            expenseCalendarBeans.clear();
        }
        expenseCalendarBeans.addAll(expenseCalendarModels);
        refreshData();
    }

    @Override
    public void updateAddDate(List<ExpenseCalendarModel> expenseCalendarModels) {
        expenseCalendarBeans.addAll(expenseCalendarModels);
        refreshData();
    }

    private void refreshData() {
        expenseCalendarAdapter.notifyDataSetChanged();
    }
}
