package com.shenhesoft.examsapp.network.model;

/**
 * @author mashanshui
 * @date 2018/6/25
 * @desc TODO
 */
public class SearchTypeModel {

    /**
     * id : 1
     * text : 论证有效性分析
     */

    /**
     * 搜索题目的筛选条件
     * 类型0
     * 题源1
     * 年份2
     */
    private int type;
    private String id;
    private String text;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
