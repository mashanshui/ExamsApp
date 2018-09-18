package com.shenhesoft.examsapp.ui.fragment.dohomework;

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
import com.shenhesoft.examsapp.present.AllWorkPresent;
import com.shenhesoft.examsapp.ui.activity.dohomework.QuestionBankDetailActivity;
import com.shenhesoft.examsapp.ui.activity.takelesson.CoursewareDetailActivity;
import com.shenhesoft.examsapp.view.AllWorkView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/5/10
 * @desc TODO
 */
public class AllWorkFragment extends XLazyFragment<AllWorkPresent> implements AllWorkView, BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener {
    private static final String TAG = "AllWorkFragment";
    private static final String KEY_PRODUCT_TYPE = "product_type";
    private static final String KEY_IF_BUY = "buy_if_buy";
    private static final String KEY_SUBJECT_TYPE = "subject_type";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.bgaRefreshLayout)
    BGARefreshLayout bgaRefreshLayout;

    private int productType;
    private int isBuy;
    private String subjectType;
    /**
     * 学术领域，按学术领域查找商品时使用
     */
    private String scienceDomainId;

    private SubjectListAdapter subjectListAdapter;
    private List<ProductModel> subjectListBeans;

    public boolean isLoadMore;
    public int start = 0;
    public int length = 15;


    public AllWorkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param productType 商品类型
     * @param isbuy       是否购买
     * @param subjectType 获取课程的科目类型，首页分类时使用
     * @return A new instance of fragment AllWorkPresent.
     */
    public static AllWorkFragment newInstance(int productType, int isbuy, String subjectType) {
        AllWorkFragment fragment = new AllWorkFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_PRODUCT_TYPE, productType);
        args.putInt(KEY_IF_BUY, isbuy);
        args.putString(KEY_SUBJECT_TYPE, subjectType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productType = getArguments().getInt(KEY_PRODUCT_TYPE);
            isBuy = getArguments().getInt(KEY_IF_BUY);
            subjectType = getArguments().getString(KEY_SUBJECT_TYPE);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        subjectListBeans = new ArrayList<>();
        subjectListAdapter = new SubjectListAdapter(subjectListBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(subjectListAdapter);
        subjectListAdapter.setOnItemClickListener(this);
        View notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerView.getParent(), false);
        subjectListAdapter.setEmptyView(notDataView);

        bgaRefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder(context, true);
        bgaRefreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
        //本来应该直接通过一个int值代表类型的，但是后台非要用ScienceDomainId，因此要先获取这个id，在通过这个id获取商品，明显的甩锅，摊手
        getP().getScienceDomainId(subjectType);
        //如果subjectType是空，则说明不是通过学术领域获取，直接获取
        if (TextUtils.isEmpty(subjectType)) {
            getP().loadData(productType, isBuy, bgaRefreshLayout);
        }
    }

    @Override
    public void getScienceDomainSuccess() {
        getP().loadData(productType, isBuy, bgaRefreshLayout);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (productType == AppConstant.QuestionBank) {
            EventBus.getDefault().postSticky(subjectListBeans.get(position));
            Router.newIntent(context).to(QuestionBankDetailActivity.class).launch();
        }
        if (productType == AppConstant.CourseWare) {
            EventBus.getDefault().postSticky(subjectListBeans.get(position));
            Router.newIntent(context).to(CoursewareDetailActivity.class).launch();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_all_work;
    }

    @Override
    public AllWorkPresent newP() {
        return new AllWorkPresent();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        isLoadMore = false;
        start = 0;
        getP().loadData(productType, isBuy, bgaRefreshLayout);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        isLoadMore = true;
        start = start + length;
        getP().loadData(productType, isBuy, bgaRefreshLayout);
        return true;
    }

    @Override
    public void updateData(List<ProductModel> listBean) {
        if (!subjectListBeans.isEmpty()) {
            subjectListBeans.clear();
        }
        subjectListBeans.addAll(listBean);
        refreshAdapter();
    }

    @Override
    public void updateAddData(List<ProductModel> listBean) {
        subjectListBeans.addAll(listBean);
        refreshAdapter();
    }

    @Override
    public void refreshAdapter() {
        subjectListAdapter.notifyDataSetChanged();
    }

    @Override
    public String getScienceDomainId() {
        return scienceDomainId;
    }

    @Override
    public void setScienceDomainId(String id) {
        scienceDomainId = id;
    }
}
