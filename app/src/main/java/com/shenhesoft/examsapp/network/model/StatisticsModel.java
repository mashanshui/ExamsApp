package com.shenhesoft.examsapp.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author mashanshui
 * @date 2018-06-17
 * @desc 由于gson解析数据时是根据字母排序的，为了保证数据顺序（从一月到十二月），在数据前加字母
 */
public class StatisticsModel {

    /**
     * 标题 : 题库做题量
     * 1月 : 0
     * 2月 : 0
     * 3月 : 0
     * 4月 : 0
     * 5月 : 0
     * 6月 : 18
     * 7月 : 0
     * 8月 : 0
     * 9月 : 0
     * 10月 : 0
     * 11月 : 0
     * 12月 : 0
     * 合计 : 18
     */

    @SerializedName("标题")
    private String a_title;
    @SerializedName("1月")
    private int b_oneMonth;
    @SerializedName("2月")
    private int c_twoMonth;
    @SerializedName("3月")
    private int d_threeMonth;
    @SerializedName("4月")
    private int e_fourMonth;
    @SerializedName("5月")
    private int f_fiveMonth;
    @SerializedName("6月")
    private int g_sixMonth;
    @SerializedName("7月")
    private int h_sevenMonth;
    @SerializedName("8月")
    private int j_eightMonth;
    @SerializedName("9月")
    private int k_nineMonth;
    @SerializedName("10月")
    private int l_tenMonth;
    @SerializedName("11月")
    private int m_elevenMonth;
    @SerializedName("12月")
    private int n_twelveMonth;
    @SerializedName("合计")
    private int o_total;

    public String getA_title() {
        return a_title;
    }

    public void setA_title(String a_title) {
        this.a_title = a_title;
    }

    public int getB_oneMonth() {
        return b_oneMonth;
    }

    public void setB_oneMonth(int b_oneMonth) {
        this.b_oneMonth = b_oneMonth;
    }

    public int getC_twoMonth() {
        return c_twoMonth;
    }

    public void setC_twoMonth(int c_twoMonth) {
        this.c_twoMonth = c_twoMonth;
    }

    public int getD_threeMonth() {
        return d_threeMonth;
    }

    public void setD_threeMonth(int d_threeMonth) {
        this.d_threeMonth = d_threeMonth;
    }

    public int getE_fourMonth() {
        return e_fourMonth;
    }

    public void setE_fourMonth(int e_fourMonth) {
        this.e_fourMonth = e_fourMonth;
    }

    public int getF_fiveMonth() {
        return f_fiveMonth;
    }

    public void setF_fiveMonth(int f_fiveMonth) {
        this.f_fiveMonth = f_fiveMonth;
    }

    public int getG_sixMonth() {
        return g_sixMonth;
    }

    public void setG_sixMonth(int g_sixMonth) {
        this.g_sixMonth = g_sixMonth;
    }

    public int getH_sevenMonth() {
        return h_sevenMonth;
    }

    public void setH_sevenMonth(int h_sevenMonth) {
        this.h_sevenMonth = h_sevenMonth;
    }

    public int getJ_eightMonth() {
        return j_eightMonth;
    }

    public void setJ_eightMonth(int j_eightMonth) {
        this.j_eightMonth = j_eightMonth;
    }

    public int getK_nineMonth() {
        return k_nineMonth;
    }

    public void setK_nineMonth(int k_nineMonth) {
        this.k_nineMonth = k_nineMonth;
    }

    public int getL_tenMonth() {
        return l_tenMonth;
    }

    public void setL_tenMonth(int l_tenMonth) {
        this.l_tenMonth = l_tenMonth;
    }

    public int getM_elevenMonth() {
        return m_elevenMonth;
    }

    public void setM_elevenMonth(int m_elevenMonth) {
        this.m_elevenMonth = m_elevenMonth;
    }

    public int getN_twelveMonth() {
        return n_twelveMonth;
    }

    public void setN_twelveMonth(int n_twelveMonth) {
        this.n_twelveMonth = n_twelveMonth;
    }

    public int getO_total() {
        return o_total;
    }

    public void setO_total(int o_total) {
        this.o_total = o_total;
    }
}
