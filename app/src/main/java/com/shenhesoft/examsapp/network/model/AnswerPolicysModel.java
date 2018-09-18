package com.shenhesoft.examsapp.network.model;

/**
 * @author mashanshui
 * @date 2018/5/24
 * @desc TODO
 */
public class AnswerPolicysModel {

    /**
     * answerPolicy : 2012
     * createUserId : null
     * createTime : null
     * updateUserId : null
     * name : 试卷年份
     * remark : null
     * updateTime : null
     * id : 1
     * status : 1
     */

    private String answerPolicy;
    private Object createUserId;
    private Object createTime;
    private Object updateUserId;
    private String name;
    private Object remark;
    private Object updateTime;
    private String id;
    private int status;

    public String getAnswerPolicy() {
        return answerPolicy;
    }

    public void setAnswerPolicy(String answerPolicy) {
        this.answerPolicy = answerPolicy;
    }

    public Object getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Object createUserId) {
        this.createUserId = createUserId;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Object updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
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
}
