package com.shenhesoft.examsapp.adapter.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.shenhesoft.examsapp.adapter.LessonsListAdapter;

/**
 * @author mashanshui
 * @date 2018/5/16
 * @desc TODO
 */
public class LessonsListBean1 extends AbstractExpandableItem<LessonsListBean2> implements MultiItemEntity {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LessonsListBean1(String message) {
        this.message = message;
    }

    @Override
    public int getItemType() {
        return LessonsListAdapter.LESSONS_LIST_1;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
