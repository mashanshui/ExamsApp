package com.shenhesoft.examsapp.network.model;

/**
 * @author mashanshui
 * @date 2018/5/31
 * @desc TODO
 */
public class ChatMsgModel {

    /**
     * createUserId : 2
     * chatId : 1001632857627557888
     * createTime : 2018-05-30 09:33:17
     * updateUserId : 2
     * chatUserId : 2
     * chatTime : 2018-05-30 09:33:17
     * remark : null
     * updateTime : 2018-05-30 09:33:17
     * id : 1001637637921964032
     * chatContent : 神魔恋
     * chaterType : 1
     * status : 1
     */

    private String createUserId;
    private String chatId;
    private String createTime;
    private String updateUserId;
    private String chatUserId;
    private String chatTime;
    private Object remark;
    private String updateTime;
    private String id;
    private String chatContent;
    private int chaterType;
    private int status;
    private String chatUserName;

    public String getChatUserName() {
        return chatUserName;
    }

    public void setChatUserName(String chatUserName) {
        this.chatUserName = chatUserName;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getChatUserId() {
        return chatUserId;
    }

    public void setChatUserId(String chatUserId) {
        this.chatUserId = chatUserId;
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }

    public int getChaterType() {
        return chaterType;
    }

    public void setChaterType(int chaterType) {
        this.chaterType = chaterType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
