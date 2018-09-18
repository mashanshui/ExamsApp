package com.shenhesoft.examsapp.util.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.ViewHolder;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.QuestionNumAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mashanshui
 * @date 2018/7/24
 * @desc TODO
 */
public class SelectQuestionDialog extends BaseNiceDialog {

    private int questionNum;
    private List<String> questionNumList;
    private OnItemClickListener onItemClickListener;

    public static SelectQuestionDialog newInstance(int questionNum) {
        Bundle bundle = new Bundle();
        bundle.putInt("questionNum", questionNum);
        SelectQuestionDialog dialog = new SelectQuestionDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionNum = getArguments().getInt("questionNum");
        }
    }

    @Override
    public int intLayoutId() {
        return R.layout.dialog_question_num_list;
    }

    @Override
    public void convertView(final ViewHolder holder, final BaseNiceDialog dialog) {
        questionNumList = new ArrayList<>();
        RecyclerView recyclerView = holder.getView(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 6);
        recyclerView.setLayoutManager(gridLayoutManager);
        createData();
        QuestionNumAdapter adapter = new QuestionNumAdapter(questionNumList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                onItemClickListener.onItemClick(position + 1);
                dismiss();
            }
        });
    }

    private void createData() {
        int num;
        for (int i = 0; i < questionNum; i++) {
            num = i + 1;
            questionNumList.add(String.valueOf(num));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int num);
    }

    public SelectQuestionDialog setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }
}
