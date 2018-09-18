package com.shenhesoft.examsapp.network.model;

import java.io.Serializable;

/**
 * @author mashanshui
 * @date 2018/6/9
 * @desc TODO
 */
public class AddressModel implements Serializable{

    /**
     * address : China
     * defaultFlag : 1
     * receiverName : mashanshui
     * contactWay : 18895368589
     * remark : null
     * id : 1005386801470242816
     * title :
     * priority : null
     * userId : 2
     * status : 1
     */

    private String address;
    /**
     * 是否为默认地址
     * 1默认
     * 0非默认
     */
    private int defaultFlag;
    private String receiverName;
    private String contactWay;
    private Object remark;
    private String id;
    private String title;
    private Object priority;
    private String userId;
    private int status;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(int defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getPriority() {
        return priority;
    }

    public void setPriority(Object priority) {
        this.priority = priority;
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
