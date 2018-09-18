package com.shenhesoft.examsapp.network.model;

/**
 * @author mashanshui
 * @date 2018/6/8
 * @desc TODO
 */
public class ScienceDomainsModel {

    /**
     * sortNo : 2
     * createUserId : null
     * createTime : null
     * updateUserId : null
     * scienceDomainName : 逻辑
     * remark : null
     * updateTime : null
     * id : 2
     * status : 1
     */

    private int sortNo;
    private Object createUserId;
    private Object createTime;
    private Object updateUserId;
    private String scienceDomainName;
    private Object remark;
    private Object updateTime;
    private String id;
    private int status;

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
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

    public String getScienceDomainName() {
        return scienceDomainName;
    }

    public void setScienceDomainName(String scienceDomainName) {
        this.scienceDomainName = scienceDomainName;
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
