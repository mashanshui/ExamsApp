package com.shenhesoft.examsapp.ui.activity.modifyhomework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.ModifyWorkBuyAdapter;
import com.shenhesoft.examsapp.adapter.bean.ModifyWorkBean;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.network.model.SearchTypeModel;
import com.shenhesoft.examsapp.present.ModifyWorkPresent;
import com.shenhesoft.examsapp.view.ModifyWorkView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/5/30
 * @desc 已经购买的作文列表（全部)
 */
public class ModifyAlreadyBuyActivity extends XTitleActivity<ModifyWorkPresent> implements ModifyWorkView, BGARefreshLayout.BGARefreshLayoutDelegate {
    public static final int RequestCode = 101;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.bgaRefreshLayout)
    BGARefreshLayout bgaRefreshLayout;

    private ModifyWorkBuyAdapter workBuyAdapter;
    private List<ModifyWorkBean> workBuyList;

    private boolean isLoadMore;
    private int start = 0;
    private int length = 20;

    @Override
    protected void initTitle() {
        setBackAction();
        setTitle("已购作文批改");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        QMUIStatusBarHelper.translucent(context);
        QMUIStatusBarHelper.setStatusBarLightMode(context);
        workBuyList = new ArrayList<>();
        workBuyAdapter = new ModifyWorkBuyAdapter(workBuyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(workBuyAdapter);
        workBuyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ModifyWorkBean modifyWorkBean = workBuyList.get(position);
                ProductModel productModel = modifyWorkBean.t;
                EventBus.getDefault().postSticky(productModel);
                Router.newIntent(context).to(TeacherDetailsActivity.class)
                        .putString("isBuy", "buy")
                        .requestCode(RequestCode)
                        .launch();
            }
        });

        bgaRefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder(context, true);
        bgaRefreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
        getP().loadBuyDataRefresh(0, 6, bgaRefreshLayout);

        setResult(RESULT_OK);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_already_buy;
    }

    @Override
    public ModifyWorkPresent newP() {
        return new ModifyWorkPresent();
    }

    @Override
    public void updateAlreadyBuy(List<ModifyWorkBean> modifyWorkBeans) {
        if (!workBuyList.isEmpty()) {
            workBuyList.clear();
        }
        workBuyList.addAll(modifyWorkBeans);
        refreshAlreadyBuyData();
    }

    @Override
    public void updateRecommend(List<ModifyWorkBean> modifyWorkBeans) {

    }

    @Override
    public void updateAddAlreadyBuy(List<ModifyWorkBean> modifyWorkBeans) {
        workBuyList.addAll(modifyWorkBeans);
        refreshAlreadyBuyData();
    }

    @Override
    public void updateAddRecommend(List<ModifyWorkBean> modifyWorkBeans) {

    }

    @Override
    public void refreshAlreadyBuyData() {
        workBuyAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshRecommendData() {

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
    public void updatePopupData(List<SearchTypeModel> searchTypeModels) {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        isLoadMore = false;
        start = 0;
        getP().loadBuyDataRefresh(start, length, bgaRefreshLayout);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        isLoadMore = true;
        start = start + length;
        getP().loadBuyDataRefresh(start, length, bgaRefreshLayout);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode && resultCode == RESULT_OK) {
            bgaRefreshLayout.beginRefreshing();
        }
    }
}
