package com.shenhesoft.examsapp.adapter.bean;

import com.shenhesoft.examsapp.adapter.RechargeAdapter;

/**
 * @author mashanshui
 * @date 2018/5/19
 * @desc TODO
 */
public class RechargeBean {
    private Integer isSelect;
    private String amount;
    private String donate;
    private String goldDiscountPolicyId;

    public Integer getIsSelect() {
        return isSelect == null ? RechargeAdapter.NOTSELECT : isSelect;
    }

    public void setIsSelect(Integer isSelect) {
        this.isSelect = isSelect;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDonate() {
        return donate;
    }

    public void setDonate(String donate) {
        this.donate = donate;
    }

    public String getGoldDiscountPolicyId() {
        return goldDiscountPolicyId;
    }

    public void setGoldDiscountPolicyId(String goldDiscountPolicyId) {
        this.goldDiscountPolicyId = goldDiscountPolicyId;
    }
}
