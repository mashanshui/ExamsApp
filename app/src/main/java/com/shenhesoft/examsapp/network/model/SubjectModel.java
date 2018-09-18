package com.shenhesoft.examsapp.network.model;

import com.shenhesoft.examsapp.AppConstant;

import java.io.Serializable;

/**
 * @author mashanshui
 * @date 2018/5/23
 * @desc TODO
 */
public class SubjectModel implements Serializable{

    /**
     * scienceDomainId : 1
     * createUserId : null
     * productCover : null
     * year : null
     * updateUserId : null
     * scienceDomainName : 数学
     * yearEnd : null
     * newCreated : null
     * remark : null
     * updateTime : null
     * recommend : null
     * productTitle : 214214
     * itemName : 待审核
     * authorUserId : test
     * createTime : null
     * productIntroduction : 214214
     * id : 999107726724300800
     * productType : 1
     * productPrice : 214214
     * yearStart : null
     * status : 1
     */
    private Integer buyStatus;
    private String scienceDomainId;
    private Object createUserId;
    private Object productCover;
    private Object year;
    private Object updateUserId;
    private String scienceDomainName;
    private Object yearEnd;
    private Object newCreated;
    private Object remark;
    private Object updateTime;
    private Object recommend;
    private String productTitle;
    private String itemName;
    private String authorUserId;
    private Object createTime;
    private String productIntroduction;
    private String id;
    private int productType;
    private String productPrice;
    private Object yearStart;
    private int status;

    /**
     * 默认状态为未购买
     */
    public Integer getBuyStatus() {
        return buyStatus == null ? AppConstant.NotBuy : buyStatus;
    }

    public void setBuyStatus(Integer buyStatus) {
        this.buyStatus = buyStatus;
    }

    public String getScienceDomainId() {
        return scienceDomainId;
    }

    public void setScienceDomainId(String scienceDomainId) {
        this.scienceDomainId = scienceDomainId;
    }

    public Object getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Object createUserId) {
        this.createUserId = createUserId;
    }

    public Object getProductCover() {
        return productCover;
    }

    public void setProductCover(Object productCover) {
        this.productCover = productCover;
    }

    public Object getYear() {
        return year;
    }

    public void setYear(Object year) {
        this.year = year;
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

    public Object getYearEnd() {
        return yearEnd;
    }

    public void setYearEnd(Object yearEnd) {
        this.yearEnd = yearEnd;
    }

    public Object getNewCreated() {
        return newCreated;
    }

    public void setNewCreated(Object newCreated) {
        this.newCreated = newCreated;
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

    public Object getRecommend() {
        return recommend;
    }

    public void setRecommend(Object recommend) {
        this.recommend = recommend;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAuthorUserId() {
        return authorUserId;
    }

    public void setAuthorUserId(String authorUserId) {
        this.authorUserId = authorUserId;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public String getProductIntroduction() {
        return productIntroduction;
    }

    public void setProductIntroduction(String productIntroduction) {
        this.productIntroduction = productIntroduction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public Object getYearStart() {
        return yearStart;
    }

    public void setYearStart(Object yearStart) {
        this.yearStart = yearStart;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
