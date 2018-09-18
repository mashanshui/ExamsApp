package com.shenhesoft.examsapp.network.model;

import com.shenhesoft.examsapp.AppConstant;

import java.io.Serializable;
import java.util.List;

/**
 * @author mashanshui
 * @date 2018/5/24
 * @desc TODO
 */
public class AnswerModel implements Serializable{

    /**
     * createUserId : null
     * productId : 994816195997728768
     * questionNo : 1
     * updateUserId : null
     * testPaperId : 999820889367248896
     * knowledgePointId : 生物
     * remark : 花儿为什么这样红
     * updateTime : null
     * title : null
     * content : 花儿为什么这样红
     * contentImg : null
     * questionStatis : 送分题
     * createTime : null
     * options : [{"createUserId":null,"questionId":"999821096146436096","productId":"994816195997728768","updateUserId":null,"testPaperId":"999820889367248896","remark":null,"updateTime":null,"content":"红花红","contentImg":"undefined","optionAnswered":1,"createTime":null,"id":"999821096796553216","optionNo":null,"status":null}]
     * id : 999821096146436096
     * questionType : null
     * status : 1
     */

    private String createUserId;
    private String productId;
    private int questionNo;
    private String updateUserId;
    private String testPaperId;
    private String knowledgePointId;
    private String remark;
    private String updateTime;
    private String title;
    private String paperTitle;
    private String knowledgePointName;
    private String content;
    private String contentImg;
    private String questionStatis;
    private String createTime;
    private String id;
    private String questionType;
    private int status;
    private String rate;
    private Integer collectStatus;
    private List<OptionsBean> options;

    public String getKnowledgePointName() {
        return knowledgePointName;
    }

    public void setKnowledgePointName(String knowledgePointName) {
        this.knowledgePointName = knowledgePointName;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(int questionNo) {
        this.questionNo = questionNo;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getTestPaperId() {
        return testPaperId;
    }

    public void setTestPaperId(String testPaperId) {
        this.testPaperId = testPaperId;
    }

    public String getKnowledgePointId() {
        return knowledgePointId;
    }

    public void setKnowledgePointId(String knowledgePointId) {
        this.knowledgePointId = knowledgePointId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    public String getQuestionStatis() {
        return questionStatis;
    }

    public void setQuestionStatis(String questionStatis) {
        this.questionStatis = questionStatis;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Integer getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(Integer collectStatus) {
        this.collectStatus = collectStatus;
    }

    public List<OptionsBean> getOptions() {
        return options;
    }

    public void setOptions(List<OptionsBean> options) {
        this.options = options;
    }

    public static class OptionsBean implements Serializable{
        /**
         * createUserId : null
         * questionId : 999821096146436096
         * productId : 994816195997728768
         * updateUserId : null
         * testPaperId : 999820889367248896
         * remark : null
         * updateTime : null
         * content : 红花红
         * contentImg : undefined
         * optionAnswered : 1
         * createTime : null
         * id : 999821096796553216
         * optionNo : null
         * status : null
         */

        private Integer optionSelect;
        private String createUserId;
        private String questionId;
        private String productId;
        private String updateUserId;
        private String testPaperId;
        private String remark;
        private String updateTime;
        private String content;
        private String contentImg;
        private int optionAnswered;
        private String createTime;
        private String id;
        private String optionNo;
        private String status;

        public Integer getOptionSelect() {
            return optionSelect == null ? AppConstant.NotSelect : optionSelect;
        }

        public void setOptionSelect(Integer optionSelect) {
            this.optionSelect = optionSelect;
        }

        public String getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getUpdateUserId() {
            return updateUserId;
        }

        public void setUpdateUserId(String updateUserId) {
            this.updateUserId = updateUserId;
        }

        public String getTestPaperId() {
            return testPaperId;
        }

        public void setTestPaperId(String testPaperId) {
            this.testPaperId = testPaperId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContentImg() {
            return contentImg;
        }

        public void setContentImg(String contentImg) {
            this.contentImg = contentImg;
        }

        public int getOptionAnswered() {
            return optionAnswered;
        }

        public void setOptionAnswered(int optionAnswered) {
            this.optionAnswered = optionAnswered;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOptionNo() {
            return optionNo;
        }

        public void setOptionNo(String optionNo) {
            this.optionNo = optionNo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
