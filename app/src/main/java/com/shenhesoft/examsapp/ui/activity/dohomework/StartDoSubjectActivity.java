package com.shenhesoft.examsapp.ui.activity.dohomework;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.AnswerPolicysAdapter;
import com.shenhesoft.examsapp.network.model.AnswerPolicysModel;
import com.shenhesoft.examsapp.present.StartDoSubjectPresent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/5/15
 * @desc 开始做题
 */
public class StartDoSubjectActivity extends XTitleActivity<StartDoSubjectPresent> {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private AnswerPolicysAdapter adapter;
    private List<AnswerPolicysModel> modelList;

    @Override
    protected void initTitle() {
        setBackAction();
        setTitle("做题预览");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        modelList = new ArrayList<>();
        adapter = new AnswerPolicysAdapter(modelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.recycler_menu_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (modelList.get(position).getName()) {
                    case AppConstant.AnswerPolicy1:
                        Router.newIntent(context).to(QuestionBankActivity.class).launch();
                        break;
                    case AppConstant.AnswerPolicy2:
                        Router.newIntent(context).to(KnowledgeActivity.class).launch();
                        break;
                    case AppConstant.AnswerPolicy3:
                        Router.newIntent(context).to(ScienceDomainsActivity.class)
                                .putString("answerPolicyName", AppConstant.AnswerPolicy3)
                                .launch();
                        break;
                    case AppConstant.AnswerPolicy4:
                        Router.newIntent(context).to(ScienceDomainsActivity.class)
                                .putString("answerPolicyName", AppConstant.AnswerPolicy4)
                                .launch();
                        break;
                    default:
                        break;
                }
                SharedPref.getInstance(context).putString(AppConstant.AnswerPolicy, modelList.get(position).getId());
            }
        });
        getP().loadData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_start_do_subject;
    }

    @Override
    public StartDoSubjectPresent newP() {
        return new StartDoSubjectPresent();
    }

    public void updateData(List<AnswerPolicysModel> rows) {
        if (!modelList.isEmpty()) {
            modelList.clear();
        }
        modelList.addAll(rows);
        adapter.notifyDataSetChanged();
    }
}
