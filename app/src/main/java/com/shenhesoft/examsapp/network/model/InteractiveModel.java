package com.shenhesoft.examsapp.network.model;

import com.shenhesoft.examsapp.adapter.InteractiveListAdapter;

/**
 * @author mashanshui
 * @date 2018/6/1
 * @desc TODO
 */
public class InteractiveModel {

    /**
     * writeTime : null
     * articleTitle : 测试标题1
     * scoreUserId : null
     * createUserId : null
     * chatId : 1002479865867272192
     * orderId : 86efab6a-bb26-4a7f-8f21-38198638
     * updateUserId : 22
     * attachOriginTitle : 通用下拉框调用方式
     * remarkTime : null
     * articleScoreLimit : 90
     * orderStatus : 5
     * articleRemark : 作文还可以
     * remark : null
     * updateTime : 2018-06-01 14:48:20
     * articleAttach : 1002423551510183936
     * authorUserId : null
     * articleScore : 80
     * createTime : null
     * articleContent : 测试内容
     * articleImg : null
     * articleVersion : null
     * id : a70ee179-2cf5-4945-8477-4a7a82e5
     * status : 5
     */

    //标识学生或老师
    private Integer InteractiveType;
    private String writeTime;
    private String articleTitle;
    private String scoreUserId;
    private String createUserId;
    private String chatId;
    private String orderId;
    private String updateUserId;
    private String attachOriginTitle;
    private String remarkTime;
    private int articleScoreLimit;
    private int orderStatus;
    private String articleRemark;
    private String remark;
    private String updateTime;
    private String articleAttach;
    private String authorUserId;
    private int articleScore;
    private String createTime;
    private String articleContent;
    private String articleImg;
    private String articleVersion;
    private String id;
    private int status;
    private String articleAttachReply;
    private String attachPostfixReply;
    private String refuseReson;
    /**
     * scoreUserName : q123456
     * authorUserName : mashanshui
     */
    private String scoreUserName;
    private String authorUserName;

    /**
     * @return 默认是学生
     */
    public Integer getInteractiveType() {
        return InteractiveType == null ? InteractiveListAdapter.STUDENT : InteractiveListAdapter.TEACHER;
    }

    public void setInteractiveType(Integer interactiveType) {
        InteractiveType = interactiveType;
    }

    public String getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(String writeTime) {
        this.writeTime = writeTime;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getScoreUserId() {
        return scoreUserId;
    }

    public void setScoreUserId(String scoreUserId) {
        this.scoreUserId = scoreUserId;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getAttachOriginTitle() {
        return attachOriginTitle;
    }

    public void setAttachOriginTitle(String attachOriginTitle) {
        this.attachOriginTitle = attachOriginTitle;
    }

    public String getRemarkTime() {
        return remarkTime;
    }

    public void setRemarkTime(String remarkTime) {
        this.remarkTime = remarkTime;
    }

    public int getArticleScoreLimit() {
        return articleScoreLimit;
    }

    public void setArticleScoreLimit(int articleScoreLimit) {
        this.articleScoreLimit = articleScoreLimit;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getArticleRemark() {
        return articleRemark;
    }

    public void setArticleRemark(String articleRemark) {
        this.articleRemark = articleRemark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getArticleAttach() {
        return articleAttach;
    }

    public void setArticleAttach(String articleAttach) {
        this.articleAttach = articleAttach;
    }

    public String getAuthorUserId() {
        return authorUserId;
    }

    public void setAuthorUserId(String authorUserId) {
        this.authorUserId = authorUserId;
    }

    public int getArticleScore() {
        return articleScore;
    }

    public void setArticleScore(int articleScore) {
        this.articleScore = articleScore;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleImg() {
        return articleImg;
    }

    public void setArticleImg(String articleImg) {
        this.articleImg = articleImg;
    }

    public String getArticleVersion() {
        return articleVersion;
    }

    public void setArticleVersion(String articleVersion) {
        this.articleVersion = articleVersion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getArticleAttachReply() {
        return articleAttachReply;
    }

    public void setArticleAttachReply(String articleAttachReply) {
        this.articleAttachReply = articleAttachReply;
    }

    public String getAttachPostfixReply() {
        return attachPostfixReply;
    }

    public void setAttachPostfixReply(String attachPostfixReply) {
        this.attachPostfixReply = attachPostfixReply;
    }

    public String getScoreUserName() {
        return scoreUserName;
    }

    public void setScoreUserName(String scoreUserName) {
        this.scoreUserName = scoreUserName;
    }

    public String getAuthorUserName() {
        return authorUserName;
    }

    public void setAuthorUserName(String authorUserName) {
        this.authorUserName = authorUserName;
    }

    public String getRefuseReson() {
        return refuseReson;
    }

    public void setRefuseReson(String refuseReson) {
        this.refuseReson = refuseReson;
    }
}
