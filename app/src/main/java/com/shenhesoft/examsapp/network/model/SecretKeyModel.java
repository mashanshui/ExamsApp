package com.shenhesoft.examsapp.network.model;

/**
 * @author mashanshui
 * @date 2018/6/26
 * @desc TODO
 */
public class SecretKeyModel {

    private String modulus;
    private String exponent;

    public String getModulus() {
        return modulus;
    }

    public void setModulus(String modulus) {
        this.modulus = modulus;
    }

    public String getExponent() {
        return exponent;
    }

    public void setExponent(String exponent) {
        this.exponent = exponent;
    }
}
