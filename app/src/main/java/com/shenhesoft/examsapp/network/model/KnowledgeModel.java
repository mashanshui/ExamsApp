package com.shenhesoft.examsapp.network.model;

/**
 * @author mashanshui
 * @date 2018/6/8
 * @desc TODO
 */
public class KnowledgeModel {

    /**
     * sortNo : 1
     * knowledgePointName : 英语
     * pname : 数学
     * pid : 1
     * remark : null
     * id : 4
     * knowledgePointCode : 00010001
     * status : 1
     */

    private int sortNo;
    private String knowledgePointName;
    private String pname;
    private String pid;
    private Object remark;
    private String id;
    private String knowledgePointCode;
    private int status;

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public String getKnowledgePointName() {
        return knowledgePointName;
    }

    public void setKnowledgePointName(String knowledgePointName) {
        this.knowledgePointName = knowledgePointName;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public String getKnowledgePointCode() {
        return knowledgePointCode;
    }

    public void setKnowledgePointCode(String knowledgePointCode) {
        this.knowledgePointCode = knowledgePointCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
