package com.shenhesoft.examsapp.present;

import android.text.TextUtils;

import com.shenhesoft.examsapp.network.UserRetrofit;
import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.network.model.EvaluateModel;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.ui.fragment.takelessons.CoursewareDetailFragment;

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
 * @date 2018/6/6
 * @desc TODO
 */
public class CoursewareDetailPresent extends XPresent<CoursewareDetailFragment> {
    private UserService userService;

    public CoursewareDetailPresent() {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
    }

    public void writeEvaluate(String content, int numStars) {
        Observable<RequestResults> observable = userService.writeEvaluate(UserRetrofit.getInstance().writeEvaluate(getV().getProductId(), numStars, content));
        HttpObserver httpObserver = new HttpObserver<RequestResults>(new HttpObserver.OnNextListener<RequestResults>() {
            @Override
            public void onNext(RequestResults data) {
                if (data.getState() != 201) {
                    IToast.showShort(data.getMsg());
                } else {
                    IToast.showShort("评价成功");
                }
                getV().dismissDialog();
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    public void loadData(String productNo) {
        if (TextUtils.isEmpty(productNo)) {
            return;
        }
        Observable<RequestResults<ProductModel>> observable = userService.getProductDetail(UserRetrofit.getInstance().getProductDetail(productNo));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ProductModel>>(new HttpObserver.OnNextListener<RequestResults<ProductModel>>() {
            @Override
            public void onNext(RequestResults<ProductModel> data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                }
                getV().updateData(data.getObj());
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    public void isWriteEvaluate() {
        Observable<RequestResults<ListALLResults<EvaluateModel>>> observable = userService.isWritingEvaluate(UserRetrofit.getInstance().isEvaluate(0, 1, getV().getProductId()));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<EvaluateModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<EvaluateModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<EvaluateModel>> data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                getV().startEvaluate();
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
