package com.shenhesoft.examsapp.present;

import android.text.TextUtils;

import com.shenhesoft.examsapp.network.UserRetrofit;
import com.shenhesoft.examsapp.network.UserService;
import com.shenhesoft.examsapp.ui.activity.user.AddAdviceActivity;
import com.shenhesoft.examsapp.util.PatternUtil;

import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import io.reactivex.Observable;

/**
 * @author mashanshui
 * @date 2018-06-01
 * @desc TODO
 */
public class AddAdvicePresent extends XPresent<AddAdviceActivity> {
    private UserService userService;

    public AddAdvicePresent() {
        userService = HttpRequestUtil.getRetrofitClient(UserService.class.getName());
    }

    public void addAdvice(String type, String object, String title, String orderCode, String content, String contactType, String contactMethod) {
        if (!checkData(type, object, title, orderCode, content, contactMethod, contactType)) {
            return;
        }
        Observable<RequestResults> observable = userService.addAdvice(UserRetrofit.getInstance().addAdvice(type, title, orderCode, content, contactType, contactMethod));
        HttpObserver httpObserver = new HttpObserver<RequestResults>(new HttpObserver.OnNextListener<RequestResults>() {
            @Override
            public void onNext(RequestResults requestResults) {
                if (requestResults.getState() != 201) {
                    IToast.showShort(requestResults.getMsg());
                    return;
                }
                getV().finishPage();
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    private boolean checkData(String type, String object, String title, String orderCode, String content, String contactMethod, String contactType) {
        if (TextUtils.isEmpty(type)) {
            IToast.showShort("请选择投诉类型");
            return false;
        }
        if (TextUtils.isEmpty(object)) {
            IToast.showShort("请选择投诉对象");
            return false;
        }
        if (TextUtils.isEmpty(title)) {
            IToast.showShort("请填写标题");
            return false;
        }
        if (TextUtils.isEmpty(orderCode) && !"系统".equals(object)) {
            IToast.showShort("请填写订单号");
            return false;
        }
        if (TextUtils.isEmpty(content)) {
            IToast.showShort("请填写内容");
            return false;
        }
        if (TextUtils.isEmpty(contactType)) {
            IToast.showShort("请选择联系方式");
            return false;
        }
        if (TextUtils.isEmpty(contactMethod)) {
            IToast.showShort("请填写联系方式");
            return false;
        }
        if (TextUtils.equals("手机", contactType)) {
            if (!PatternUtil.isPhone(contactMethod)) {
                IToast.showShort("手机号格式错误");
                return false;
            }
        }
        if (TextUtils.equals("邮箱", contactType)) {
            if (!PatternUtil.isEmail(contactMethod)) {
                IToast.showShort("邮箱格式错误");
                return false;
            }
        }
        if (TextUtils.equals("QQ", contactType)) {
            if (!PatternUtil.isQQ(contactMethod)) {
                IToast.showShort("QQ格式错误");
                return false;
            }
        }
        return true;
    }

}
