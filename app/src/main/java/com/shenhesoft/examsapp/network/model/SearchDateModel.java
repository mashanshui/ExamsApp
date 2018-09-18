package com.shenhesoft.examsapp.network.model;

import java.util.List;
import java.util.Map;

/**
 * @author mashanshui
 * @date 2018/6/25
 * @desc TODO
 */
public class SearchDateModel {

    /**
     * hour : 6
     * id : 1006798837001486336
     * listYear : [{"mbaOctoberYear":{"1997":"2010"},"mbaJanuaryYear":{"1997":"2008"},"economyExamyear":{"2011":"2017"},"managerExamyear":{"2010":"2018"}}]
     */

    private int hour;
    private String id;
    private List<ListYearBean> listYear;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ListYearBean> getListYear() {
        return listYear;
    }

    public void setListYear(List<ListYearBean> listYear) {
        this.listYear = listYear;
    }

    public static class ListYearBean {
        /**
         * mbaOctoberYear : {"1997":"2010"}
         * mbaJanuaryYear : {"1997":"2008"}
         * economyExamyear : {"2011":"2017"}
         * managerExamyear : {"2010":"2018"}
         */

        private Map<String, String> mbaOctoberYear;
        private Map<String, String> mbaJanuaryYear;
        private Map<String, String> economyExamyear;
        private Map<String, String> managerExamyear;

        public Map<String, String> getMbaOctoberYear() {
            return mbaOctoberYear;
        }

        public void setMbaOctoberYear(Map<String, String> mbaOctoberYear) {
            this.mbaOctoberYear = mbaOctoberYear;
        }

        public Map<String, String> getMbaJanuaryYear() {
            return mbaJanuaryYear;
        }

        public void setMbaJanuaryYear(Map<String, String> mbaJanuaryYear) {
            this.mbaJanuaryYear = mbaJanuaryYear;
        }

        public Map<String, String> getEconomyExamyear() {
            return economyExamyear;
        }

        public void setEconomyExamyear(Map<String, String> economyExamyear) {
            this.economyExamyear = economyExamyear;
        }

        public Map<String, String> getManagerExamyear() {
            return managerExamyear;
        }

        public void setManagerExamyear(Map<String, String> managerExamyear) {
            this.managerExamyear = managerExamyear;
        }
    }
}
