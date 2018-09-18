package com.shenhesoft.examsapp.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.MessageAdapter;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.network.model.PushMessageModel;
import com.shenhesoft.examsapp.present.MessagePresent;
import com.shenhesoft.examsapp.ui.activity.PushActivity;
import com.shenhesoft.examsapp.ui.activity.modifyhomework.InteractiveActivity;
import com.shenhesoft.examsapp.view.MessageView;

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
 * @date 2018/6/11
 * @desc 展示所有消息推送页面
 */
public class MessageActivity extends XTitleActivity<MessagePresent> implements MessageView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.bgaRefreshLayout)
    BGARefreshLayout bgaRefreshLayout;

    private MessageAdapter adapter;
    private List<PushMessageModel> messageBeanList;

    public boolean isLoadMore;
    public int start = 0;
    public int length = 15;

    @Override
    protected void initTitle() {
        setBackAction();
        setTitle("消息列表");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        messageBeanList = new ArrayList<>();
        adapter = new MessageAdapter(messageBeanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PushMessageModel messageModel = messageBeanList.get(position);
                //作文批改消息
                if (messageModel.getStatus() == 2) {
                    ProductModel productModel2 = new ProductModel();
                    productModel2.setOrderId(messageModel.getContent());
                    EventBus.getDefault().postSticky(productModel2);
                    Router.newIntent(context).to(InteractiveActivity.class).launch();
                }
                //师生对话消息
                else if (messageModel.getStatus() == 3) {
                    ProductModel productModel = new ProductModel();
                    productModel.setOrderId(messageModel.getContent());
                    EventBus.getDefault().postSticky(productModel);
                    Router.newIntent(context).to(InteractiveActivity.class).launch();
                }
                //广播消息
                else if (messageModel.getStatus() == 4) {
                    Intent intent = new Intent(context, PushActivity.class);
                    intent.putExtra("data", messageModel.getRemark());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        bgaRefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder(context, true);
        bgaRefreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
        getP().loadData(bgaRefreshLayout);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public MessagePresent newP() {
        return new MessagePresent();
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
    public void updateDate(List<PushMessageModel> pushMessageModels) {
        if (!messageBeanList.isEmpty()) {
            messageBeanList.clear();
        }
        messageBeanList.addAll(pushMessageModels);
        refreshData();
    }

    @Override
    public void updateAddDate(List<PushMessageModel> pushMessageModels) {
        messageBeanList.addAll(pushMessageModels);
        refreshData();
    }

    private void refreshData() {
        adapter.notifyDataSetChanged();
    }
}
