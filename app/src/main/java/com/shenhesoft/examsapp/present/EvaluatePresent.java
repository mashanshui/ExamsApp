package com.shenhesoft.examsapp.present;

import com.shenhesoft.examsapp.network.UserRetrofit;
import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.network.model.EvaluateModel;
import com.shenhesoft.examsapp.ui.fragment.EvaluateFragment;

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
 * @date 2018/6/19
 * @desc TODO
 */
public class EvaluatePresent extends XPresent<EvaluateFragment> {
    private UserService userService;

    public EvaluatePresent() {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
    }

    public void loadData(String id) {
        Observable<RequestResults<ListALLResults<EvaluateModel>>> observable = userService.lookEvaluate(UserRetrofit.getInstance().lookEvaluate(0, 10, id, ""));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<EvaluateModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<EvaluateModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<EvaluateModel>> data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                if (!data.getObj().getRows().isEmpty() && data.getObj().getRows().get(0) != null) {
                    getV().updateNum(data.getObj().getRows().get(0));
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
