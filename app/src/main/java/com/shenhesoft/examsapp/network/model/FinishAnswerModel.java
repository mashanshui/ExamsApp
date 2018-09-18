package com.shenhesoft.examsapp.network.model;

import java.io.Serializable;

/**
 * @author mashanshui
 * @date 2018-05-28
 * @desc TODO
 */
public class FinishAnswerModel implements Serializable{

    /**
     * id : 2
     * studentUserId : null
     * answerPolicy : null
     * answerTimeStart : 2018-05-28 08:56:52
     * answerTimeEnd : 2018-05-28 08:56:57
     * answerSchedule : 1.0
     * rightRate : 0
     * rightQuestionNum : 0
     * answerQuestionNum : 2
     * usedTime : 0时0分4秒
     * remark : null
     * status : null
     * createTime : null
     * createUserId : null
     * updateTime : null
     * updateUserId : null
     */

    private String id;
    private Object studentUserId;
    private Object answerPolicy;
    private String answerTimeStart;
    private String answerTimeEnd;
    private String answerSchedule;
    private int rightRate;
    private int rightQuestionNum;
    private int answerQuestionNum;
    private String usedTime;
    private Object remark;
    private Object status;
    private Object createTime;
    private Object createUserId;
    private Object updateTime;
    private Object updateUserId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(Object studentUserId) {
        this.studentUserId = studentUserId;
    }

    public Object getAnswerPolicy() {
        return answerPolicy;
    }

    public void setAnswerPolicy(Object answerPolicy) {
        this.answerPolicy = answerPolicy;
    }

    public String getAnswerTimeStart() {
        return answerTimeStart;
    }

    public void setAnswerTimeStart(String answerTimeStart) {
        this.answerTimeStart = answerTimeStart;
    }

    public String getAnswerTimeEnd() {
        return answerTimeEnd;
    }

    public void setAnswerTimeEnd(String answerTimeEnd) {
        this.answerTimeEnd = answerTimeEnd;
    }

    public String getAnswerSchedule() {
        return answerSchedule;
    }

    public void setAnswerSchedule(String answerSchedule) {
        this.answerSchedule = answerSchedule;
    }

    public int getRightRate() {
        return rightRate;
    }

    public void setRightRate(int rightRate) {
        this.rightRate = rightRate;
    }

    public int getRightQuestionNum() {
        return rightQuestionNum;
    }

    public void setRightQuestionNum(int rightQuestionNum) {
        this.rightQuestionNum = rightQuestionNum;
    }

    public int getAnswerQuestionNum() {
        return answerQuestionNum;
    }

    public void setAnswerQuestionNum(int answerQuestionNum) {
        this.answerQuestionNum = answerQuestionNum;
    }

    public String getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public Object getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Object createUserId) {
        this.createUserId = createUserId;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public Object getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Object updateUserId) {
        this.updateUserId = updateUserId;
    }
}
