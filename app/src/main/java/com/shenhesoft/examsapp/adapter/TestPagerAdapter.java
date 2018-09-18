package com.shenhesoft.examsapp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.TestPagerListModel;
import com.shenhesoft.examsapp.ui.fragment.dohomework.QuestionBankDetailFragment;

import java.util.List;

/**
 * @author mashanshui
 * @date 2018/5/23
 * @desc TODO
 */
public class TestPagerAdapter extends BaseQuickAdapter<TestPagerListModel, BaseViewHolder> {
    public static final int WritingQuestion = QuestionBankDetailFragment.WritingQuestion;
    public static final int LookQuestion = QuestionBankDetailFragment.LookQuestion;

    public TestPagerAdapter(@Nullable List<TestPagerListModel> data, final int type) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<TestPagerListModel>() {
            @Override
            protected int getItemType(TestPagerListModel testPagerListModel) {
                return type;
            }
        });
        getMultiTypeDelegate().registerItemType(WritingQuestion, R.layout.recycler_list_test_pager1)
                .registerItemType(LookQuestion, R.layout.recycler_list_test_pager2);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestPagerListModel item) {
        switch (helper.getItemViewType()) {
            case WritingQuestion:
                helper.setText(R.id.tv_testpager_name, item.getPaperTitle())
                        .setText(R.id.tv_progress_done, item.getSchedule() + "")
                        .setText(R.id.tv_progress_total, item.getAllCount() + "");
                break;
            case LookQuestion:
                helper.setText(R.id.tv_testpager_name, item.getPaperTitle());
                break;
            default:
                break;
        }
    }
}
