package com.shenhesoft.examsapp.network.model;

/**
 * @author mashanshui
 * @date 2018/5/24
 * @desc TODO
 */
public class TestPagerListModel {

    /**
     * schedule : 0
     * num : 1
     * id : 999820889367248896
     * allCount : 1
     * paperTitle : 五年高考三年模拟
     */

    private int schedule;
    private int num;
    private String id;
    private int allCount;
    private String paperTitle;

    public int getSchedule() {
        return schedule;
    }

    public void setSchedule(int schedule) {
        this.schedule = schedule;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }
}
