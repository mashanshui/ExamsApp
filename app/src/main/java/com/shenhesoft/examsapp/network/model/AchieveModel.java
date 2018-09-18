package com.shenhesoft.examsapp.network.model;

import java.util.List;

/**
 * @author mashanshui
 * @date 2018/6/23
 * @desc TODO
 */
public class AchieveModel {

    /**
     * schedule : {"whole":3,"done":1}
     * list : [{"createUserId":null,"medalName":"闻鸡起舞","medalIntroduction":null,"updateUserId":"995273458071896064","remark":"已获得","updateTime":"2018-06-23 09:54:39","medalImg":null,"medalStyle":"public/imgs/card1.png","createTime":null,"continuedDayNum":1111,"medalType":0,"optNum":11,"dayNum":null,"id":"1001417044282834944","overdueTime":null,"medalSort":2,"status":1},{"createUserId":null,"medalName":"汗牛充栋","medalIntroduction":null,"updateUserId":null,"remark":null,"updateTime":null,"medalImg":null,"medalStyle":"public/imgs/card2.png","createTime":null,"continuedDayNum":111,"medalType":0,"optNum":111,"dayNum":null,"id":"1001410398798217216","overdueTime":null,"medalSort":0,"status":1},{"createUserId":null,"medalName":"2","medalIntroduction":null,"updateUserId":null,"remark":null,"updateTime":null,"medalImg":null,"medalStyle":"public/imgs/card2.png","createTime":null,"continuedDayNum":111,"medalType":0,"optNum":111,"dayNum":null,"id":"1001405297455005696","overdueTime":null,"medalSort":0,"status":1}]
     */

    private ScheduleBean schedule;
    private List<ListBean> list;

    public ScheduleBean getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleBean schedule) {
        this.schedule = schedule;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ScheduleBean {
        /**
         * whole : 3
         * done : 1
         */

        private int whole;
        private int done;

        public int getWhole() {
            return whole;
        }

        public void setWhole(int whole) {
            this.whole = whole;
        }

        public int getDone() {
            return done;
        }

        public void setDone(int done) {
            this.done = done;
        }
    }

    public static class ListBean {
        /**
         * createUserId : null
         * medalName : 闻鸡起舞
         * medalIntroduction : null
         * updateUserId : 995273458071896064
         * remark : 已获得
         * updateTime : 2018-06-23 09:54:39
         * medalImg : null
         * medalStyle : public/imgs/card1.png
         * createTime : null
         * continuedDayNum : 1111
         * medalType : 0
         * optNum : 11
         * dayNum : null
         * id : 1001417044282834944
         * overdueTime : null
         * medalSort : 2
         * status : 1
         */

        private String createUserId;
        private String medalName;
        private String medalIntroduction;
        private String updateUserId;
        private String remark;
        private String updateTime;
        private String medalImg;
        private String medalStyle;
        private String createTime;
        private int continuedDayNum;
        private int medalType;
        private int optNum;
        private String dayNum;
        private String id;
        private String overdueTime;
        private int medalSort;
        private int status;
        private String count;

        /**
         * @return 默认的数量为1（前提是已经获取了成就了）
         */
        public String getCount() {
            return count == null ? "1" : count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }

        public String getMedalName() {
            return medalName;
        }

        public void setMedalName(String medalName) {
            this.medalName = medalName;
        }

        public String getMedalIntroduction() {
            return medalIntroduction;
        }

        public void setMedalIntroduction(String medalIntroduction) {
            this.medalIntroduction = medalIntroduction;
        }

        public String getUpdateUserId() {
            return updateUserId;
        }

        public void setUpdateUserId(String updateUserId) {
            this.updateUserId = updateUserId;
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

        public String getMedalImg() {
            return medalImg;
        }

        public void setMedalImg(String medalImg) {
            this.medalImg = medalImg;
        }

        public String getMedalStyle() {
            return medalStyle;
        }

        public void setMedalStyle(String medalStyle) {
            this.medalStyle = medalStyle;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getContinuedDayNum() {
            return continuedDayNum;
        }

        public void setContinuedDayNum(int continuedDayNum) {
            this.continuedDayNum = continuedDayNum;
        }

        public int getMedalType() {
            return medalType;
        }

        public void setMedalType(int medalType) {
            this.medalType = medalType;
        }

        public int getOptNum() {
            return optNum;
        }

        public void setOptNum(int optNum) {
            this.optNum = optNum;
        }

        public String getDayNum() {
            return dayNum;
        }

        public void setDayNum(String dayNum) {
            this.dayNum = dayNum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOverdueTime() {
            return overdueTime;
        }

        public void setOverdueTime(String overdueTime) {
            this.overdueTime = overdueTime;
        }

        public int getMedalSort() {
            return medalSort;
        }

        public void setMedalSort(int medalSort) {
            this.medalSort = medalSort;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
