package com.shenhesoft.examsapp.view;

import com.shenhesoft.examsapp.network.model.KnowledgeModel;
import com.shenhesoft.examsapp.present.KnowledgePresent;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * @author mashanshui
 * @date 2018/6/13
 * @desc TODO
 */
public interface KnowledgeView extends IView<KnowledgePresent> {
    void updateData(List<KnowledgeModel> rows);
}
