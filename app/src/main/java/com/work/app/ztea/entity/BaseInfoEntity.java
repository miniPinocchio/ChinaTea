package com.work.app.ztea.entity;

import java.io.Serializable;

/**
 * 个人中心首页
 * Created by liu on 2018/8/3.
 */

public class BaseInfoEntity extends BaseEntity<BaseInfoEntity.BaseInfo> implements Serializable{

    public static class BaseInfo implements Serializable{

        /**
         * id : 3
         * crdate : 1545124438
         * name : 王代喜
         * mobile : 15377242115
         * image :
         * gender : 0
         * age : 0
         * birthday : 0
         * province :
         * city :
         * area :
         * store_address :
         * idcard : 420582199209227523
         * cardno :
         * addr_province : 湖北省
         * addr_city : 咸宁市
         * addr_area : 赤壁市
         */

        private String id;
        private String crdate;
        private String name;
        private String mobile;
        private String image;
        private String gender;
        private String age;
        private String birthday;
        private String province;
        private String city;
        private String area;
        private String store_address;
        private String idcard;
        private String cardno;
        private String addr_province;
        private String addr_city;
        private String addr_area;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getStore_address() {
            return store_address;
        }

        public void setStore_address(String store_address) {
            this.store_address = store_address;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getCardno() {
            return cardno;
        }

        public void setCardno(String cardno) {
            this.cardno = cardno;
        }

        public String getAddr_province() {
            return addr_province;
        }

        public void setAddr_province(String addr_province) {
            this.addr_province = addr_province;
        }

        public String getAddr_city() {
            return addr_city;
        }

        public void setAddr_city(String addr_city) {
            this.addr_city = addr_city;
        }

        public String getAddr_area() {
            return addr_area;
        }

        public void setAddr_area(String addr_area) {
            this.addr_area = addr_area;
        }
    }
}
