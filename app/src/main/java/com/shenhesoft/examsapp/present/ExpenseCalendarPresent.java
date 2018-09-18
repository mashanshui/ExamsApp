package com.shenhesoft.examsapp.present;

import com.shenhesoft.examsapp.network.UserRetrofit;
import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.network.model.ExpenseCalendarModel;
import com.shenhesoft.examsapp.view.ExpenseCalendarView;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import io.reactivex.Observable;

/**
 * @author mashanshui
 * @date 2018/6/7
 * @desc TODO
 */
public class ExpenseCalendarPresent extends XPresent<ExpenseCalendarView> {

    private UserService userService;

    public ExpenseCalendarPresent() {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
    }

    public void loadData(BGARefreshLayout bgaRefreshLayout) {
        Observable<RequestResults<ListALLResults<ExpenseCalendarModel>>> observable = userService.expenseCalendar(UserRetrofit.getInstance().expenseCalendar(getV().getStart(), getV().getLength()));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<ExpenseCalendarModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<ExpenseCalendarModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<ExpenseCalendarModel>> data) {
                if (data.getState() != 200) {
                    //加载失败将start重置到加载之前
                    if (getV().isLoadingMore()) {
                        getV().setStart(getV().getStart() - getV().getLength());
                    }
                    IToast.showShort(data.getMsg());
                    return;
                }
                List<ExpenseCalendarModel> expenseCalendarModels = data.getObj().getRows();
                //下拉刷新
                if (!getV().isLoadingMore()) {
                    getV().updateDate(expenseCalendarModels);
                }
                //上拉加载后有数据
                else if (getV().isLoadingMore() && !expenseCalendarModels.isEmpty()) {
                    getV().updateAddDate(expenseCalendarModels);
                }
                //上拉加载后没有数据
                else if (getV().isLoadingMore() && expenseCalendarModels.isEmpty()) {
                    getV().setStart(getV().getStart() - getV().getLength());
                    IToast.showShort("没有更多数据");
                }
            }
        });
        httpObserver.setRefreshLayout(bgaRefreshLayout);
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
