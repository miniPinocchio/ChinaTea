package com.work.app.ztea.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liu on 2018/3/21.
 */

public class MyOrderListEntity extends BaseEntity<List<MyOrderListEntity.MyOrder>> implements Serializable{

    public static class MyOrder implements Serializable{

        /**
         * id : 2
         * goods_id : 1
         * number : Y-1812240000001-1
         * state : 0
         * title :
         * total_money : 238.00
         * money :
         * goods_num : 1
         * attribute :
         * tdintegral : 0
         * tdvouchers : 0.00
         * pay_money : 238.00
         * image : http://zhongcha.hqdemo.cn//Uploads/Goods/thumb_0/201607/5c00ad55eb3a9.png
         */

        private String id;
        private String goods_id;
        private String number;
        private String state;
        private String title;
        private String total_money;
        private String money;
        private String goods_num;
        private String attribute;
        private String tdintegral;
        private String tdvouchers;
        private String pay_money;
        private String image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTotal_money() {
            return total_money;
        }

        public void setTotal_money(String total_money) {
            this.total_money = total_money;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getAttribute() {
            return attribute;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }

        public String getTdintegral() {
            return tdintegral;
        }

        public void setTdintegral(String tdintegral) {
            this.tdintegral = tdintegral;
        }

        public String getTdvouchers() {
            return tdvouchers;
        }

        public void setTdvouchers(String tdvouchers) {
            this.tdvouchers = tdvouchers;
        }

        public String getPay_money() {
            return pay_money;
        }

        public void setPay_money(String pay_money) {
            this.pay_money = pay_money;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
