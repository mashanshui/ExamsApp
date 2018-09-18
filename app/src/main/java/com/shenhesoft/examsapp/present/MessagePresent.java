package com.shenhesoft.examsapp.present;

import android.util.Log;

import com.shenhesoft.examsapp.network.UserRetrofit;
import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.network.model.PushMessageModel;
import com.shenhesoft.examsapp.view.MessageView;

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
 * @date 2018/6/11
 * @desc TODO
 */
public class MessagePresent extends XPresent<MessageView> {
    private UserService userService;

    public MessagePresent() {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
    }

    public void loadData(BGARefreshLayout bgaRefreshLayout) {
        Observable<RequestResults<ListALLResults<PushMessageModel>>> observable = userService.getPushMessage(UserRetrofit.getInstance().getPushMessage(getV().getStart(), getV().getLength()));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<PushMessageModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<PushMessageModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<PushMessageModel>> data) {
                if (data.getState() != 200) {
                    //加载失败将start重置到加载之前
                    if (getV().isLoadingMore()) {
                        getV().setStart(getV().getStart() - getV().getLength());
                    }
                    IToast.showShort(data.getMsg());
                    return;
                }
                List<PushMessageModel> pushModels = data.getObj().getRows();
                //下拉刷新
                if (!getV().isLoadingMore()) {
                    getV().updateDate(pushModels);
                }
                //上拉加载后有数据
                else if (getV().isLoadingMore() && !pushModels.isEmpty()) {
                    getV().updateAddDate(pushModels);
                }
                //上拉加载后没有数据
                else if (getV().isLoadingMore() && pushModels.isEmpty()) {
                    getV().setStart(getV().getStart() - getV().getLength());
                    IToast.showShort("没有更多数据");
                }
            }
        });
        httpObserver.setRefreshLayout(bgaRefreshLayout);
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
