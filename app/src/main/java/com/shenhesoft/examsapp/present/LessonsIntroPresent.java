package com.shenhesoft.examsapp.present;

import com.shenhesoft.examsapp.network.LessonService;
import com.shenhesoft.examsapp.network.model.LessonsIntroModel;
import com.shenhesoft.examsapp.ui.fragment.takelessons.LessonsIntroFragment;

import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import io.reactivex.Observable;

/**
 * @author mashanshui
 * @date 2018/6/8
 * @desc TODO
 */
public class LessonsIntroPresent extends XPresent<LessonsIntroFragment> {
    private LessonService lessonService;

    public LessonsIntroPresent() {
        lessonService = HttpRequestUtil.getRetrofitClient(LessonService.class.getName());
    }

    public void loadData(String id) {
        Observable<RequestResults<LessonsIntroModel>> observable = lessonService.getLessonsIntro(id);
        HttpObserver httpObserver = new HttpObserver<RequestResults<LessonsIntroModel>>(false, new HttpObserver.OnNextListener<RequestResults<LessonsIntroModel>>() {
            @Override
            public void onNext(RequestResults<LessonsIntroModel> data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                getV().updateIntro(data.getObj());
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
