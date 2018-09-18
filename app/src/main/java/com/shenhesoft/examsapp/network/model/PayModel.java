package com.shenhesoft.examsapp.network.model;

/**
 * @author mashanshui
 * @date 2018/6/4
 * @desc TODO
 */
public class PayModel {

    /**
     * serialVersionUID : -3184811397243560502
     * orderId : 1003469780629323776
     * price : 50
     */

    private long serialVersionUID;
    private String orderId;
    private String price;

    private String aliStr;

    public long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setSerialVersionUID(long serialVersionUID) {
        this.serialVersionUID = serialVersionUID;
    }

    public String getAliStr() {
        return aliStr;
    }

    public void setAliStr(String aliStr) {
        this.aliStr = aliStr;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
