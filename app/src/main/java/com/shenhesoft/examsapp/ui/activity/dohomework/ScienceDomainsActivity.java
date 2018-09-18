package com.shenhesoft.examsapp.ui.activity.dohomework;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.ScienceDomainsAdapter;
import com.shenhesoft.examsapp.network.model.ScienceDomainsModel;
import com.shenhesoft.examsapp.present.ScienceDomainsPresent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/6/8
 * @desc 按收藏或错题做题——》学术领域
 */
public class ScienceDomainsActivity extends XTitleActivity<ScienceDomainsPresent> {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ScienceDomainsAdapter adapter;
    private List<ScienceDomainsModel> scienceDomainsModelList;
    private String answerPolicyName;

    @Override
    protected void initTitle() {
        answerPolicyName = getIntent().getStringExtra("answerPolicyName");
        setBackAction();
        setTitle("学术领域");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        scienceDomainsModelList = new ArrayList<>();
        adapter = new ScienceDomainsAdapter(scienceDomainsModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.recycler_menu_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (TextUtils.equals(answerPolicyName, AppConstant.AnswerPolicy3)) {
                    Router.newIntent(context).to(AnswerActivity.class)
                            .putInt(AppConstant.AnswerType, AppConstant.DoHomeWork)
                            .putString("answerPolicyName", AppConstant.AnswerPolicy3)
                            .putString("productId", scienceDomainsModelList.get(position).getId())
                            .launch();
                } else if (TextUtils.equals(answerPolicyName, AppConstant.AnswerPolicy4)) {
                    Router.newIntent(context).to(AnswerActivity.class)
                            .putInt(AppConstant.AnswerType, AppConstant.DoHomeWork)
                            .putString("answerPolicyName", AppConstant.AnswerPolicy4)
                            .putString("productId", scienceDomainsModelList.get(position).getId())
                            .launch();
                }
            }
        });
        getP().loadData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_science_domains;
    }

    @Override
    public ScienceDomainsPresent newP() {
        return new ScienceDomainsPresent();
    }

    public void updateData(List<ScienceDomainsModel> rows) {
        if (!scienceDomainsModelList.isEmpty()) {
            scienceDomainsModelList.clear();
        }
        scienceDomainsModelList.addAll(rows);
        adapter.notifyDataSetChanged();
    }
}
