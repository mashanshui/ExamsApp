package com.shenhesoft.examsapp.ui.activity.user;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.AchieveAdapter;
import com.shenhesoft.examsapp.network.model.AchieveModel;
import com.shenhesoft.examsapp.present.MyAchievePresent;
import com.shenhesoft.examsapp.util.SnackbarUtils;
import com.shenhesoft.examsapp.util.UltimateBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;

/**
 * @author mashanshui
 * @date 2018/5/21
 * @desc 我的成就
 */
public class MyAchieveActivity extends XTitleActivity<MyAchievePresent> {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    Button titleButton;

    private AchieveAdapter achieveAdapter;
    private List<AchieveModel.ListBean> achieveBeans;

    @Override
    protected void initTitle() {
        setBackAction();
        setTitle("我的成就");
        setTitleBackgroundDrawable(R.drawable.achieve_backgroud);
        titleButton = setRightText("已解锁：" + "0" + "/" + "0");
        titleButton.setTextColor(Color.BLACK);
        titleButton.setTextSize(12);
        UltimateBar ultimateBar = new UltimateBar(context);
        ultimateBar.setColorBar(Color.parseColor("#EFE4D2"));
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        achieveBeans = new ArrayList<>();
        achieveAdapter = new AchieveAdapter(achieveBeans);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(achieveAdapter);
        achieveAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AchieveModel.ListBean listBean = achieveBeans.get(position);
                String type;
                String endTitle;
                if (listBean.getMedalSort() == 0) {
                    type = "做题";
                    endTitle = "道";
                } else if (listBean.getMedalSort() == 1) {
                    type = "写作";
                    endTitle = "篇";
                } else {
                    type = "听课";
                    endTitle = "节";
                }
                String count;
                if (TextUtils.isEmpty(listBean.getRemark())) {
                    count = "0";
                } else {
                    count = listBean.getCount();
                }
                if (listBean.getMedalType() == 0) {
                    //累计
                    SnackbarUtils.Long(recyclerView, "累计" + type + listBean.getOptNum() + endTitle)
                            .backColor(Color.parseColor("#F0DFC3"))
                            .messageColor(Color.BLACK)
                            .actionColor(Color.RED)
                            .setAction("已获得" + count + "次", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            }).show();
                } else {
                    //连续
                    SnackbarUtils.Long(recyclerView, "连续" + listBean.getContinuedDayNum() + "天" + type + listBean.getOptNum() + endTitle)
                            .backColor(Color.parseColor("#F0DFC3"))
                            .messageColor(Color.BLACK)
                            .actionColor(Color.RED)
                            .setAction("已获得" + count + "次", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            }).show();
                }
            }
        });
        getP().loadData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_achieve;
    }

    @Override
    public MyAchievePresent newP() {
        return new MyAchievePresent();
    }

    public void updateData(AchieveModel list) {
        titleButton.setText("已解锁：" + list.getSchedule().getDone() + "/" + list.getSchedule().getWhole());
        if (!achieveBeans.isEmpty()) {
            achieveBeans.clear();
        }
        achieveBeans.addAll(list.getList());
        achieveAdapter.notifyDataSetChanged();
    }
}
