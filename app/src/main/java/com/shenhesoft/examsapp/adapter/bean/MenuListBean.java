package com.shenhesoft.examsapp.adapter.bean;

/**
 * @author mashanshui
 * @date 2018/5/9
 * @desc TODO
 */
public class MenuListBean {
    private int resIconId;
    private int resTitleId;
    private String messageCount;

    public MenuListBean(int resIconId, int resTitleId, String messageCount) {
        this.resIconId = resIconId;
        this.resTitleId = resTitleId;
        this.messageCount = messageCount;
    }

    public MenuListBean(int resTitleId) {
        this.resTitleId = resTitleId;
    }

    public int getResIconId() {
        return resIconId;
    }

    public void setResIconId(int resIconId) {
        this.resIconId = resIconId;
    }

    public int getResTitleId() {
        return resTitleId;
    }

    public void setResTitleId(int resTitleId) {
        this.resTitleId = resTitleId;
    }

    public String getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(String messageCount) {
        this.messageCount = messageCount;
    }
}
