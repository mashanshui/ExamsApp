package com.shenhesoft.examsapp.network.model;

import com.shenhesoft.examsapp.AppConstant;

import java.io.Serializable;

/**
 * @author mashanshui
 * @date 2018/5/29
 * @desc TODO
 */
public class ProductModel implements Serializable{

    /**
     * scienceDomainId : 7
     * createUserId : null
     * resourceId : null
     * year : null
     * remark : null
     * recommend : 0
     * salNum : 0
     * itemName : 已上线
     * authorUserId : wangpan980
     * payNum : null
     * articleYear : null
     * articleType : null
     * id : 999859924458536960
     * scoreNum : null
     * productType : 2
     * yearStart : null
     * salNumAddon : 0
     * scoreValue : null
     * articleYearName : null
     * productCover : 999859923812614144
     * updateUserId : 2
     * scienceDomainName : 其他
     * yearEnd : null
     * newCreated : 0
     * updateTime : 2018-05-29 09:50:10
     * scoreAvgManager : 0
     * articleTypeName : null
     * scoreAvg : 0
     * productTitle : 猪猪侠
     * optType : null
     * articleSource : null
     * createTime : null
     * productIntroduction : you哟哟哟
     * scoreAvgShow : 0
     * salNumShow : 0
     * productPrice : 90
     * status : 4
     */
    private Integer buyStatus;
    private String scienceDomainId;
    private String createUserId;
    private String resourceId;
    private String year;
    private String remark;
    private int recommend;
    private int salNum;
    private String itemName;
    private String authorUserId;
    private String payNum;
    private String articleYear;
    private String articleType;
    private String id;
    private String scoreNum;
    private int productType;
    private String yearStart;
    private int salNumAddon;
    private String scoreValue;
    private String articleYearName;
    private String productCover;
    private String updateUserId;
    private String scienceDomainName;
    private String yearEnd;
    private int newCreated;
    private String updateTime;
    private int scoreAvgManager;
    private String articleTypeName;
    private float scoreAvg;
    private String productTitle;
    private String optType;
    private String articleSource;
    private String createTime;
    private String productIntroduction;
    private float scoreAvgShow;
    private String salNumShow;
    private String productPrice;
    private int status;
    private String productNo;
    private String authorUserName;
    /**
     * receiveOrderTime : 2018-05-25 10:04:38
     * articleTitle : 测试标题1
     * orderNo : 86efab6a-bb26-4a7f-8f21-38198638
     * productId : 1001297394341511168
     * year : null
     * articleId : a70ee179-2cf5-4945-8477-4a7a82e5
     * remark : null
     * limitAlterTnum : 3
     * alterTnum : 1
     * orderTime : 2018-05-24 10:04:57
     * teacherUserId : 22
     * studentUserId : 22
     * lastChatTime : 2018-05-25 10:05:07
     * overdueTime : 2018-05-30 17:54:50
     */

    private String receiveOrderTime;
    private String articleTitle;
    private String orderNo;
    private String productId;
    private String articleId;
    private int limitAlterTnum;
    private int alterTnum;
    private String orderTime;
    private String teacherUserId;
    private String studentUserId;
    private String lastChatTime;
    private String overdueTime;
    private String chatId;
    private String orderId;
    private String teacherName;
    private String orderPic;

    private int statusOrder;
    private int productFree;
    private String productFreeName;
    private String statusOrderName;

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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getSalNumShow() {
        return salNumShow;
    }

    public void setSalNumShow(String salNumShow) {
        this.salNumShow = salNumShow;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public int getSalNum() {
        return salNum;
    }

    public String getAuthorUserName() {
        return authorUserName;
    }

    public void setAuthorUserName(String authorUserName) {
        this.authorUserName = authorUserName;
    }

    public void setSalNum(int salNum) {
        this.salNum = salNum;
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

    public String getPayNum() {
        return payNum;
    }

    public void setPayNum(String payNum) {
        this.payNum = payNum;
    }

    public String getArticleYear() {
        return articleYear;
    }

    public void setArticleYear(String articleYear) {
        this.articleYear = articleYear;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(String scoreNum) {
        this.scoreNum = scoreNum;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public String getYearStart() {
        return yearStart;
    }

    public void setYearStart(String yearStart) {
        this.yearStart = yearStart;
    }

    public int getSalNumAddon() {
        return salNumAddon;
    }

    public void setSalNumAddon(int salNumAddon) {
        this.salNumAddon = salNumAddon;
    }

    public String getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(String scoreValue) {
        this.scoreValue = scoreValue;
    }

    public String getArticleYearName() {
        return articleYearName;
    }

    public void setArticleYearName(String articleYearName) {
        this.articleYearName = articleYearName;
    }

    public String getProductCover() {
        return productCover;
    }

    public void setProductCover(String productCover) {
        this.productCover = productCover;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getScienceDomainName() {
        return scienceDomainName;
    }

    public void setScienceDomainName(String scienceDomainName) {
        this.scienceDomainName = scienceDomainName;
    }

    public String getYearEnd() {
        return yearEnd;
    }

    public void setYearEnd(String yearEnd) {
        this.yearEnd = yearEnd;
    }

    public int getNewCreated() {
        return newCreated;
    }

    public void setNewCreated(int newCreated) {
        this.newCreated = newCreated;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getScoreAvgManager() {
        return scoreAvgManager;
    }

    public void setScoreAvgManager(int scoreAvgManager) {
        this.scoreAvgManager = scoreAvgManager;
    }

    public String getArticleTypeName() {
        return articleTypeName;
    }

    public void setArticleTypeName(String articleTypeName) {
        this.articleTypeName = articleTypeName;
    }

    public float getScoreAvg() {
        return scoreAvg;
    }

    public void setScoreAvg(float scoreAvg) {
        this.scoreAvg = scoreAvg;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getArticleSource() {
        return articleSource;
    }

    public void setArticleSource(String articleSource) {
        this.articleSource = articleSource;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProductIntroduction() {
        return productIntroduction;
    }

    public void setProductIntroduction(String productIntroduction) {
        this.productIntroduction = productIntroduction;
    }

    public float getScoreAvgShow() {
        return scoreAvgShow;
    }

    public void setScoreAvgShow(float scoreAvgShow) {
        this.scoreAvgShow = scoreAvgShow;
    }


    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReceiveOrderTime() {
        return receiveOrderTime;
    }

    public void setReceiveOrderTime(String receiveOrderTime) {
        this.receiveOrderTime = receiveOrderTime;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }


    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public int getLimitAlterTnum() {
        return limitAlterTnum;
    }

    public void setLimitAlterTnum(int limitAlterTnum) {
        this.limitAlterTnum = limitAlterTnum;
    }

    public int getAlterTnum() {
        return alterTnum;
    }

    public void setAlterTnum(int alterTnum) {
        this.alterTnum = alterTnum;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getTeacherUserId() {
        return teacherUserId;
    }

    public void setTeacherUserId(String teacherUserId) {
        this.teacherUserId = teacherUserId;
    }

    public String getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(String studentUserId) {
        this.studentUserId = studentUserId;
    }

    public String getLastChatTime() {
        return lastChatTime;
    }

    public void setLastChatTime(String lastChatTime) {
        this.lastChatTime = lastChatTime;
    }

    public String getOverdueTime() {
        return overdueTime;
    }

    public void setOverdueTime(String overdueTime) {
        this.overdueTime = overdueTime;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public int getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(int statusOrder) {
        this.statusOrder = statusOrder;
    }

    public int getProductFree() {
        return productFree;
    }

    public void setProductFree(int productFree) {
        this.productFree = productFree;
    }

    public String getProductFreeName() {
        return productFreeName;
    }

    public void setProductFreeName(String productFreeName) {
        this.productFreeName = productFreeName;
    }

    public String getOrderPic() {
        return orderPic;
    }

    public void setOrderPic(String orderPic) {
        this.orderPic = orderPic;
    }

    public String getStatusOrderName() {
        return statusOrderName;
    }

    public void setStatusOrderName(String statusOrderName) {
        this.statusOrderName = statusOrderName;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }
}
