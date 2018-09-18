package com.shenhesoft.examsapp.network.model;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;
import com.shenhesoft.examsapp.adapter.LessonsListAdapter;

import java.util.List;

/**
 * @author mashanshui
 * @date 2018/6/7
 * @desc TODO
 */
public class LessonsModel  extends AbstractExpandableItem<LessonsSection> implements MultiItemEntity {

    /**
     * scienceDomainId : 4
     * createUserId : null
     * classSection : []
     * productCover : undefined
     * year : null
     * updateUserId : null
     * scienceDomainName : 作文批改
     * yearEnd : null
     * newCreated : 1
     * remark : null
     * updateTime : null
     * recommend : 0
     * productTitle : 课件2
     * itemName : 已上线
     * authorUserId : wangpan980
     * createTime : null
     * productIntroduction : 复活甲好久估计公司的
     * id : 235313331
     * productNo : 9999
     * productType : 2
     * productPrice : 562
     * yearStart : null
     * status : 4
     */

    private String scienceDomainId;
    private Object createUserId;
    private String productCover;
    private Object year;
    private Object updateUserId;
    private String scienceDomainName;
    private Object yearEnd;
    private int newCreated;
    private Object remark;
    private Object updateTime;
    private int recommend;
    private String productTitle;
    private String itemName;
    private String authorUserId;
    private Object createTime;
    private String productIntroduction;
    private String id;
    private String productNo;
    private int productType;
    private int productPrice;
    private Object yearStart;
    private int status;

    @SerializedName("classSection")
    private List<LessonsSection> classSection;

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

    public String getProductCover() {
        return productCover;
    }

    public void setProductCover(String productCover) {
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

    public int getNewCreated() {
        return newCreated;
    }

    public void setNewCreated(int newCreated) {
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

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
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

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
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

    public List<LessonsSection> getClassSection() {
        return classSection;
    }

    public void setClassSection(List<LessonsSection> classSection) {
        this.classSection = classSection;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return LessonsListAdapter.LESSONS_LIST_1;
    }
}
