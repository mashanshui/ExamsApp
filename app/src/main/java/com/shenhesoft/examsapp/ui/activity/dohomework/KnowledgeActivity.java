package com.shenhesoft.examsapp.ui.activity.dohomework;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.KnowledgeAdapter;
import com.shenhesoft.examsapp.network.model.KnowledgeModel;
import com.shenhesoft.examsapp.present.KnowledgePresent;
import com.shenhesoft.examsapp.view.KnowledgeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/6/8
 * @desc 做题——》选择知识点1
 */
public class KnowledgeActivity extends XTitleActivity<KnowledgePresent> implements KnowledgeView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private KnowledgeAdapter adapter;
    private List<KnowledgeModel> knowledgeModelList;

    @Override
    protected void initTitle() {
        setBackAction();
        setTitle("知识点");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        knowledgeModelList = new ArrayList<>();
        adapter = new KnowledgeAdapter(knowledgeModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.recycler_menu_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Router.newIntent(context).to(KnowledgeActivity2.class)
                        .putString("knowledgePointCode", knowledgeModelList.get(position).getKnowledgePointCode())
                        .launch();
            }
        });
        getP().loadDataByPid("0");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_knowledge;
    }

    @Override
    public KnowledgePresent newP() {
        return new KnowledgePresent();
    }

    @Override
    public void updateData(List<KnowledgeModel> rows) {
        if (!knowledgeModelList.isEmpty()) {
            knowledgeModelList.clear();
        }
        knowledgeModelList.addAll(rows);
        adapter.notifyDataSetChanged();
    }
}
