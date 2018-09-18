package com.shenhesoft.examsapp.ui.activity.dohomework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.TestPagerAdapter;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.network.model.TestPagerListModel;
import com.shenhesoft.examsapp.present.TestPagerPresent;
import com.shenhesoft.examsapp.ui.fragment.dohomework.QuestionBankDetailFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/5/23
 * @desc 试卷列表
 */
public class TestPaperActivity extends XTitleActivity<TestPagerPresent> implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private static final int RequestCode = 101;
    private static final String TAG = "TestPaperActivity";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.bgaRefreshLayout)
    BGARefreshLayout bgaRefreshLayout;

    private TestPagerAdapter adapter;
    private List<TestPagerListModel> pagerListBeans;
    private ProductModel ProductModel;
    private int testPagerType;

    public boolean isLoadMore;
    public int start = 0;
    public int length = 15;

    @Override
    protected void initTitle() {
        testPagerType = getIntent().getIntExtra("testPagerType", QuestionBankDetailFragment.WritingQuestion);
        setBackAction();
        setTitle("试卷列表");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        pagerListBeans = new ArrayList<>();
        adapter = new TestPagerAdapter(pagerListBeans, testPagerType);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        View notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerView.getParent(), false);
        adapter.setEmptyView(notDataView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Router.newIntent(context).to(AnswerActivity.class)
                        .requestCode(RequestCode)
                        .putInt("startAnswerNum", testPagerType == QuestionBankDetailFragment.WritingQuestion ? pagerListBeans.get(position).getSchedule() : 0)
                        .putString("productId", pagerListBeans.get(position).getId())
                        .putInt(AppConstant.AnswerType, testPagerType == QuestionBankDetailFragment.WritingQuestion ? AppConstant.DoHomeWork : AppConstant.HomeAnalysis)
                        .launch();
            }
        });
        bgaRefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder(context, true);
        bgaRefreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEvent(ProductModel listBean) {
        if (listBean == null) {
            IToast.showShort("异常，请重新进入");
            finish();
        }
        ProductModel = listBean;
        getP().loadData(listBean.getId(), 1, bgaRefreshLayout);
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test_paper;
    }

    @Override
    public TestPagerPresent newP() {
        return new TestPagerPresent();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        isLoadMore = false;
        start = 0;
        getP().loadData(ProductModel.getId(), 1, bgaRefreshLayout);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        isLoadMore = true;
        start = start + length;
        getP().loadData(ProductModel.getId(), 1, bgaRefreshLayout);
        return true;
    }

    public void updateData(List<TestPagerListModel> listBean) {
        if (!listBean.isEmpty()) {
            pagerListBeans.clear();
        }
        pagerListBeans.addAll(listBean);
        refreshAdapter();
    }

    public void updateAddData(List<TestPagerListModel> listBean) {
        pagerListBeans.addAll(listBean);
        refreshAdapter();
    }

    public void refreshAdapter() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode && resultCode == RESULT_OK) {
            bgaRefreshLayout.beginRefreshing();
        }
    }
}
