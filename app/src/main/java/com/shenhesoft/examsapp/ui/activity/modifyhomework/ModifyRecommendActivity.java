package com.shenhesoft.examsapp.ui.activity.modifyhomework;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.ModifyWorkRecommendAdapter;
import com.shenhesoft.examsapp.adapter.OnlineSearchAdapter;
import com.shenhesoft.examsapp.adapter.bean.ModifyWorkBean;
import com.shenhesoft.examsapp.network.model.SearchTypeModel;
import com.shenhesoft.examsapp.present.ModifyWorkPresent;
import com.shenhesoft.examsapp.view.ModifyWorkView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/5/30
 * @desc 推荐的作文列表（全部)
 */
public class ModifyRecommendActivity extends XTitleActivity<ModifyWorkPresent> implements ModifyWorkView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.bgaRefreshLayout)
    BGARefreshLayout bgaRefreshLayout;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_source)
    TextView tvSource;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.popup_title)
    ConstraintLayout popupTitle;
    @BindView(R.id.imageView)
    ImageView imageView;

    private ModifyWorkRecommendAdapter workRecommendAdapter;
    private List<ModifyWorkBean> workRecommendList;
    private PopupWindow popupLevel2;
    private RecyclerView popupRecyclerView;
    private OnlineSearchAdapter searchAdapter;
    private List<SearchTypeModel> searchTypeModels;
    private String searchType;
    private String searchSource;
    private String searchDate;

    private boolean isLoadMore;
    private int start = 0;
    private int length = AppConstant.PageLength;

    @Override
    protected void initTitle() {
        setBackAction();
        setTitle("推荐作文批改");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        QMUIStatusBarHelper.translucent(context);
        QMUIStatusBarHelper.setStatusBarLightMode(context);
        searchTypeModels = new ArrayList<>();
        workRecommendList = new ArrayList<>();
        workRecommendAdapter = new ModifyWorkRecommendAdapter(workRecommendList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(workRecommendAdapter);
        workRecommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ModifyWorkBean modifyWorkBean = workRecommendList.get(position);
                EventBus.getDefault().postSticky(modifyWorkBean.t);
                Router.newIntent(context).to(TeacherDetailsActivity.class).launch();
            }
        });

        bgaRefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder(context, true);
        bgaRefreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
        getP().loadRecommendDataRefresh(start, length, bgaRefreshLayout);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_recommend;
    }

    @Override
    public ModifyWorkPresent newP() {
        return new ModifyWorkPresent();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        isLoadMore = false;
        start = 0;
        getP().loadRecommendDataRefresh(start, length, bgaRefreshLayout);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        isLoadMore = true;
        start = start + length;
        getP().loadRecommendDataRefresh(start, length, bgaRefreshLayout);
        return true;
    }

    @Override
    public void updateAlreadyBuy(List<ModifyWorkBean> modifyWorkBeans) {

    }

    @Override
    public void updateRecommend(List<ModifyWorkBean> modifyWorkBeans) {
        if (!workRecommendList.isEmpty()) {
            workRecommendList.clear();
        }
        workRecommendList.addAll(modifyWorkBeans);
        refreshRecommendData();
    }

    @Override
    public void updateAddAlreadyBuy(List<ModifyWorkBean> modifyWorkBeans) {

    }

    @Override
    public void updateAddRecommend(List<ModifyWorkBean> modifyWorkBeans) {
        workRecommendList.addAll(modifyWorkBeans);
        refreshRecommendData();
    }

    @Override
    public void refreshAlreadyBuyData() {

    }

    @Override
    public void refreshRecommendData() {
        workRecommendAdapter.notifyDataSetChanged();
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

    @OnClick({R.id.tv_type, R.id.tv_source, R.id.tv_year})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_type:
                tvType.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                getP().getPopupType("11");
                break;
            case R.id.tv_source:
                tvSource.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                getP().getPopupType("12");
                break;
            case R.id.tv_year:
                tvYear.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                getP().getPopupDate();
                break;
            default:
                break;
        }
    }

    private void showPopupTwo() {
        if (popupLevel2 == null) {
            popupLevel2 = new PopupWindow(context);
        }
        if (popupLevel2.isShowing()) {
            popupLevel2.dismiss();
        }
        View view2 = LayoutInflater.from(context).inflate(R.layout.popup_level_two, null);
        popupRecyclerView = view2.findViewById(R.id.popup_recyclerView);
        searchAdapter = new OnlineSearchAdapter(searchTypeModels);
        popupRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        popupRecyclerView.setAdapter(searchAdapter);
        searchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SearchTypeModel typeModels = searchTypeModels.get(position);
                switch (typeModels.getType()) {
                    case 0:
                        searchType = typeModels.getId();
                        break;
                    case 1:
                        searchSource = typeModels.getId();
                        break;
                    case 2:
                        searchDate = typeModels.getId();
                        break;
                    default:
                        break;
                }
                getP().filterData(searchType, searchSource, searchDate, bgaRefreshLayout);
                dismissPopup();
            }
        });
        popupLevel2.setContentView(view2);
        popupLevel2.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupLevel2.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupLevel2.setOutsideTouchable(false);
        popupLevel2.setFocusable(true);
        popupLevel2.setBackgroundDrawable(null);
        imageView.setVisibility(View.VISIBLE);
        popupLevel2.showAsDropDown(popupTitle);
        popupLevel2.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                imageView.setVisibility(View.GONE);
                tvType.setTextColor(Color.BLACK);
                tvSource.setTextColor(Color.BLACK);
                tvYear.setTextColor(Color.BLACK);
            }
        });
    }

    private void dismissPopup() {
        popupLevel2.dismiss();
    }

    @Override
    public void updatePopupData(List<SearchTypeModel> Models) {
        if (!searchTypeModels.isEmpty()) {
            searchTypeModels.clear();
        }
        searchTypeModels.addAll(Models);
        showPopupTwo();
    }
}
