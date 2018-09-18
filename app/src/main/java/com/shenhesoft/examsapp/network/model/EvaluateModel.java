package com.shenhesoft.examsapp.network.model;

/**
 * @author mashanshui
 * @date 2018/6/6
 * @desc TODO
 */
public class EvaluateModel {

    /**
     * createUserId : null
     * productId : 1002730771938541568
     * updateUserId : null
     * count : {"scoreTotalGood":2,"scoreTotal":6,"scoreTotalMid":2,"scoreTotalLower":2}
     * scoreType : 0
     * remark : null
     * updateTime : null
     * scoreContent : 测试评价1
     * studentUserId : 测试人员1
     * createTime : null
     * scoreTime : 2018-06-04 10:09:21
     * id : f4b3e0e1-29ff-4fa9-a06b-1cc32353
     * scoreValue : 5
     * status : 1
     */

    private Object createUserId;
    private String productId;
    private Object updateUserId;
    private CountBean count;
    private int scoreType;
    private Object remark;
    private Object updateTime;
    private String scoreContent;
    private String studentUserId;
    private String studentUserName;
    private Object createTime;
    private String scoreTime;
    private String id;
    private int scoreValue;
    private int status;

    public Object getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Object createUserId) {
        this.createUserId = createUserId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Object getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Object updateUserId) {
        this.updateUserId = updateUserId;
    }

    public CountBean getCount() {
        return count;
    }

    public void setCount(CountBean count) {
        this.count = count;
    }

    public int getScoreType() {
        return scoreType;
    }

    public void setScoreType(int scoreType) {
        this.scoreType = scoreType;
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

    public String getScoreContent() {
        return scoreContent;
    }

    public void setScoreContent(String scoreContent) {
        this.scoreContent = scoreContent;
    }

    public String getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(String studentUserId) {
        this.studentUserId = studentUserId;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public String getScoreTime() {
        return scoreTime;
    }

    public void setScoreTime(String scoreTime) {
        this.scoreTime = scoreTime;
    }

    public String getStudentUserName() {
        return studentUserName;
    }

    public void setStudentUserName(String studentUserName) {
        this.studentUserName = studentUserName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class CountBean {
        /**
         * scoreTotalGood : 2
         * scoreTotal : 6
         * scoreTotalMid : 2
         * scoreTotalLower : 2
         */

        private String scoreTotalGood;
        private String scoreTotal;
        private String scoreTotalMid;
        private String scoreTotalLower;

        public String getScoreTotalGood() {
            return scoreTotalGood;
        }

        public void setScoreTotalGood(String scoreTotalGood) {
            this.scoreTotalGood = scoreTotalGood;
        }

        public String getScoreTotal() {
            return scoreTotal;
        }

        public void setScoreTotal(String scoreTotal) {
            this.scoreTotal = scoreTotal;
        }

        public String getScoreTotalMid() {
            return scoreTotalMid;
        }

        public void setScoreTotalMid(String scoreTotalMid) {
            this.scoreTotalMid = scoreTotalMid;
        }

        public String getScoreTotalLower() {
            return scoreTotalLower;
        }

        public void setScoreTotalLower(String scoreTotalLower) {
            this.scoreTotalLower = scoreTotalLower;
        }
    }
}
