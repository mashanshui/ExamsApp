package com.shenhesoft.examsapp.network.model;

/**
 * @author mashanshui
 * @date 2018/6/4
 * @desc TODO
 */
public class PayResultModel {

    /**
     * serialVersionUID : 2971526397629626897
     * param : {"appid":"wxd3f214c324a79a39","partnerid":"1505204101","prepayid":"wx04185326989622c79b3b6ce72910055497","packageStr":"Sign=WXPay","noncestr":"jSKfT91ku6781p00O6Gq20hxK86ZyF","timestamp":"1528109607","sign":"25717590428C0EBB739235D75DAE5A14"}
     * prepayId : wx04185326989622c79b3b6ce72910055497
     * nonceStr : jSKfT91ku6781p00O6Gq20hxK86ZyF
     */

    private long serialVersionUID;
    private ParamBean param;
    private String prepayId;
    private String nonceStr;

    public long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setSerialVersionUID(long serialVersionUID) {
        this.serialVersionUID = serialVersionUID;
    }

    public ParamBean getParam() {
        return param;
    }

    public void setParam(ParamBean param) {
        this.param = param;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public static class ParamBean {
        /**
         * appid : wxd3f214c324a79a39
         * partnerid : 1505204101
         * prepayid : wx04185326989622c79b3b6ce72910055497
         * packageStr : Sign=WXPay
         * noncestr : jSKfT91ku6781p00O6Gq20hxK86ZyF
         * timestamp : 1528109607
         * sign : 25717590428C0EBB739235D75DAE5A14
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        private String packageStr;
        private String noncestr;
        private String timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageStr() {
            return packageStr;
        }

        public void setPackageStr(String packageStr) {
            this.packageStr = packageStr;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
