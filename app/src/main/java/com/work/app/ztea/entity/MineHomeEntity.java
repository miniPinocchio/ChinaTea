package com.work.app.ztea.entity;

import java.io.Serializable;

/**
 * 个人中心首页
 * Created by liu on 2018/8/3.
 */

public class MineHomeEntity extends BaseEntity<MineHomeEntity.Mine> implements Serializable{

    public static class Mine implements Serializable{

        /**
         * id : 3
         * crdate : 1545124438
         * number : S0000003
         * username : 13871445159
         * name : 王代喜
         * image :
         * level : 1
         * levelname : 尊享会员
         * idcard : 420582199209227523
         * integral : 0
         * amount : 0.00
         * recommend_num : 0
         * bank :
         * cardno :
         * cardholder :
         * holdermobile :
         */

        private String id;
        private String crdate;
        private String number;
        private String username;
        private String name;
        private String image;
        private String level;
        private String levelname;
        private String idcard;
        private String integral;
        private String amount;
        private String recommend_num;
        private String bank;
        private String cardno;
        private String cardholder;
        private String holdermobile;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCrdate() {
            return crdate;
        }

        public void setCrdate(String crdate) {
            this.crdate = crdate;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getLevelname() {
            return levelname;
        }

        public void setLevelname(String levelname) {
            this.levelname = levelname;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getRecommend_num() {
            return recommend_num;
        }

        public void setRecommend_num(String recommend_num) {
            this.recommend_num = recommend_num;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getCardno() {
            return cardno;
        }

        public void setCardno(String cardno) {
            this.cardno = cardno;
        }

        public String getCardholder() {
            return cardholder;
        }

        public void setCardholder(String cardholder) {
            this.cardholder = cardholder;
        }

        public String getHoldermobile() {
            return holdermobile;
        }

        public void setHoldermobile(String holdermobile) {
            this.holdermobile = holdermobile;
        }
    }
}
