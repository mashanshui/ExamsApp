package com.shenhesoft.examsapp.adapter.bean;

/**
 * @author mashanshui
 * @date 2018/5/21
 * @desc TODO
 */
public class AchieveBean {
    private int isUnlock;
    private String achieveName;
    private String achieveDescribe;
    private String getTimes;

    public int getIsUnlock() {
        return isUnlock;
    }

    public void setIsUnlock(int isUnlock) {
        this.isUnlock = isUnlock;
    }

    public String getAchieveName() {
        return achieveName;
    }

    public void setAchieveName(String achieveName) {
        this.achieveName = achieveName;
    }

    public String getAchieveDescribe() {
        return achieveDescribe;
    }

    public void setAchieveDescribe(String achieveDescribe) {
        this.achieveDescribe = achieveDescribe;
    }

    public String getGetTimes() {
        return getTimes;
    }

    public void setGetTimes(String getTimes) {
        this.getTimes = getTimes;
    }
}
