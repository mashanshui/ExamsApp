package com.shenhesoft.examsapp.view;

import com.shenhesoft.examsapp.network.model.ExpenseCalendarModel;
import com.shenhesoft.examsapp.present.ExpenseCalendarPresent;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * @author mashanshui
 * @date 2018/6/7
 * @desc TODO
 */
public interface ExpenseCalendarView extends IView<ExpenseCalendarPresent> {
    int getStart();

    int getLength();

    boolean isLoadingMore();

    void setStart(int start);

    void updateDate(List<ExpenseCalendarModel> expenseCalendarModels);

    void updateAddDate(List<ExpenseCalendarModel> expenseCalendarModels);
}
