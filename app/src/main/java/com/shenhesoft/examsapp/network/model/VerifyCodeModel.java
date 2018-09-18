package com.shenhesoft.examsapp.network.model;

/**
 * @author mashanshui
 * @date 2018/5/21
 * @desc TODO
 */
public class VerifyCodeModel {

    /**
     * phone : 18895368589
     * checkCode : 496349
     * passwd : null
     * passwdAgain : null
     * action : null
     */

    private String phone;
    private String checkCode;
    private Object passwd;
    private Object passwdAgain;
    private Object action;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public Object getPasswd() {
        return passwd;
    }

    public void setPasswd(Object passwd) {
        this.passwd = passwd;
    }

    public Object getPasswdAgain() {
        return passwdAgain;
    }

    public void setPasswdAgain(Object passwdAgain) {
        this.passwdAgain = passwdAgain;
    }

    public Object getAction() {
        return action;
    }

    public void setAction(Object action) {
        this.action = action;
    }
}
