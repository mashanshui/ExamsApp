package com.shenhesoft.examsapp.network.model;

/**
 * @author mashanshui
 * @date 2018/6/19
 * @desc TODO
 */
public class PayPswModel {

    /**
     * lastPayTime : null
     * errorTime : null
     * payChkcode : e10adc3949ba59abbe56e057f20f883e
     * payPasswd : e10adc3949ba59abbe56e057f20f883e
     * remark : null
     * id : 1006161134660616192
     * userId : 1006063800341757952
     * status : 1
     */

    private Object lastPayTime;
    private Object errorTime;
    private String payChkcode;
    private String payPasswd;
    private Object remark;
    private String id;
    private String userId;
    private int status;

    public Object getLastPayTime() {
        return lastPayTime;
    }

    public void setLastPayTime(Object lastPayTime) {
        this.lastPayTime = lastPayTime;
    }

    public Object getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(Object errorTime) {
        this.errorTime = errorTime;
    }

    public String getPayChkcode() {
        return payChkcode;
    }

    public void setPayChkcode(String payChkcode) {
        this.payChkcode = payChkcode;
    }

    public String getPayPasswd() {
        return payPasswd;
    }

    public void setPayPasswd(String payPasswd) {
        this.payPasswd = payPasswd;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
