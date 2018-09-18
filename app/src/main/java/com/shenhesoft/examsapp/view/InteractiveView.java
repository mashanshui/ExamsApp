package com.shenhesoft.examsapp.view;

import com.shenhesoft.examsapp.network.model.InteractiveModel;
import com.shenhesoft.examsapp.present.InteractivePresent;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * @author mashanshui
 * @date 2018/5/30
 * @desc TODO
 */
public interface InteractiveView extends IView<InteractivePresent> {
    void updateData(List<InteractiveModel> interactiveModels);

    void setDownloadId(int downloadId);

    void setBtnDownloadText(String text);

    void beginRefresh();
}
