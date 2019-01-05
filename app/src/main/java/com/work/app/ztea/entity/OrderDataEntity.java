package com.work.app.ztea.entity;

/**
 * 下单之后返回数据
 * Created by liu on 2018/8/3.
 */

public class OrderDataEntity extends BaseEntity<OrderDataEntity.Order> {

    public static class Order {


        /**
         * appid : wx8ca5e4be3f8e4d76
         * mch_id : 1520951551
         * nonce_str : uCAdjFVtA0vwtkxo
         * prepay_id : wx031704214517568059cb8f962937503978
         * result_code : SUCCESS
         * return_code : SUCCESS
         * return_msg : OK
         * sign : 80EC2739E694999D37A5E7A0B8C37840
         * trade_type : APP
         * total : 0.01
         * orders_id : 13
         */

        private String appid;
        private String mch_id;
        private String nonce_str;
        private String prepay_id;
        private String result_code;
        private String return_code;
        private String return_msg;
        private String sign;
        private String trade_type;
        private String total;
        //支付宝返回数据
        private String alipayAppParam;
        private String orders_id;
        private int is_ask;

        public int getIs_ask() {
            return is_ask;
        }

        public void setIs_ask(int is_ask) {
            this.is_ask = is_ask;
        }

        public String getAlipayAppParam() {
            return alipayAppParam;
        }

        public void setAlipayAppParam(String alipayAppParam) {
            this.alipayAppParam = alipayAppParam;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getPrepay_id() {
            return prepay_id;
        }

        public void setPrepay_id(String prepay_id) {
            this.prepay_id = prepay_id;
        }

        public String getResult_code() {
            return result_code;
        }

        public void setResult_code(String result_code) {
            this.result_code = result_code;
        }

        public String getReturn_code() {
            return return_code;
        }

        public void setReturn_code(String return_code) {
            this.return_code = return_code;
        }

        public String getReturn_msg() {
            return return_msg;
        }

        public void setReturn_msg(String return_msg) {
            this.return_msg = return_msg;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getOrders_id() {
            return orders_id;
        }

        public void setOrders_id(String orders_id) {
            this.orders_id = orders_id;
        }
    }
}
