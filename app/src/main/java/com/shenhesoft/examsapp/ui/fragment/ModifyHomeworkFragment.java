package com.shenhesoft.examsapp.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.ModifyWorkBuyAdapter;
import com.shenhesoft.examsapp.adapter.ModifyWorkRecommendAdapter;
import com.shenhesoft.examsapp.adapter.bean.ModifyWorkBean;
import com.shenhesoft.examsapp.network.model.SearchTypeModel;
import com.shenhesoft.examsapp.present.ModifyWorkPresent;
import com.shenhesoft.examsapp.ui.activity.modifyhomework.ModifyAlreadyBuyActivity;
import com.shenhesoft.examsapp.ui.activity.modifyhomework.ModifyRecommendActivity;
import com.shenhesoft.examsapp.ui.activity.modifyhomework.SearchResultActivity;
import com.shenhesoft.examsapp.ui.activity.modifyhomework.TeacherDetailsActivity;
import com.shenhesoft.examsapp.util.GridItemDecoration;
import com.shenhesoft.examsapp.view.ModifyWorkView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.router.Router;

import static android.app.Activity.RESULT_OK;

/**
 * @author mashanshui
 * @date 2018/5/3
 * @desc 改作业
 */
public class ModifyHomeworkFragment extends XFragment<ModifyWorkPresent> implements ModifyWorkView {
    public static final int RequestCode = 101;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rc_buy)
    RecyclerView rcBuy;
    @BindView(R.id.rc_recommend)
    RecyclerView rcRecommend;

    private ModifyWorkBuyAdapter workBuyAdapter;
    private ModifyWorkRecommendAdapter workRecommendAdapter;

    private List<ModifyWorkBean> workBuyList;
    private List<ModifyWorkBean> workRecommendList;

    public ModifyHomeworkFragment() {
        // Required empty public constructor
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“GO”键*/
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v
                            .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });
        workBuyList = new ArrayList<>();
        workRecommendList = new ArrayList<>();
        workBuyAdapter = new ModifyWorkBuyAdapter(workBuyList);
        rcBuy.setLayoutManager(new LinearLayoutManager(context));
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
//        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.recycler_light_diviver));
//        rcBuy.addItemDecoration(dividerItemDecoration);
        rcBuy.setAdapter(workBuyAdapter);
        workBuyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ModifyWorkBean workBean = workBuyList.get(position);
                if (workBean.isHeader) {
                    Intent intent = new Intent(context, ModifyAlreadyBuyActivity.class);
                    startActivityForResult(intent, RequestCode);
                } else {
                    EventBus.getDefault().postSticky(workBean.t);
                    Intent intent = new Intent(context, TeacherDetailsActivity.class);
                    intent.putExtra("isBuy", "buy");
                    startActivityForResult(intent, RequestCode);
                }
            }
        });

        workRecommendAdapter = new ModifyWorkRecommendAdapter(workRecommendList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        rcRecommend.setLayoutManager(gridLayoutManager);
        rcRecommend.setAdapter(workRecommendAdapter);
        workRecommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ModifyWorkBean workBean = workRecommendList.get(position);
                if (workBean.isHeader) {
                    Router.newIntent(context).to(ModifyRecommendActivity.class).launch();
                } else {
                    EventBus.getDefault().postSticky(workBean.t);
                    Router.newIntent(context).to(TeacherDetailsActivity.class).launch();
                }
            }
        });
        getP().loadBuyData(0, 6, "已购作文批改");
        getP().loadRecommendData(0, 6, "推荐作文批改");
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                int type = workListAdapter.getItemViewType(position);
//                if (type == ModifyWorkBuyAdapter.NOT_BUY_LIST) {
//                    return 1;
//                } else {
//                    return gridLayoutManager.getSpanCount();
//                }
//            }
//        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“GO”键*/
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v
                            .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    String searchKey = etSearch.getText().toString().trim();
                    if (TextUtils.isEmpty(searchKey)) {
                        IToast.showShort("搜索内容不能为空");
                    } else {
                        Router.newIntent(context).to(SearchResultActivity.class)
                                .putString("searchKey", searchKey)
                                .launch();
                    }
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_modify_homework;
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

    }

    @Override
    public void refreshAlreadyBuyData() {
        workBuyAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshRecommendData() {
        workRecommendAdapter.notifyDataSetChanged();
    }

    @Override
    public int getStart() {
        return 0;
    }

    @Override
    public int getLength() {
        return 0;
    }

    @Override
    public boolean getIsLoadingMore() {
        return false;
    }

    @Override
    public void setStart(int start) {

    }

    @Override
    public void updatePopupData(List<SearchTypeModel> searchTypeModels) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode && resultCode == RESULT_OK) {
            getP().loadBuyData(0, 6, "已购作文批改");
        }
    }
}
