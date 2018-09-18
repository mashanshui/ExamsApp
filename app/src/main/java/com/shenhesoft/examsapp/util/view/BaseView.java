package com.shenhesoft.examsapp.util.view;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * @author mashanshui
 * @date 2018/5/4
 * @desc TODO
 */
public interface BaseView<P> extends IView<P> {
    void showFailedErrorDialog();

    void dismissErrorDialog();
}
