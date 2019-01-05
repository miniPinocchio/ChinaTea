package com.work.app.ztea.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liu on 2018/3/21.
 */

public class MyAddressListEntity extends BaseEntity<List<MyAddressListEntity.Address>> implements Serializable{

    public static class Address implements Serializable{

        /**
         * id : 4
         * name : 安卓测试
         * mobile : 13712345678
         * province : 湖北省
         * city : 武汉市
         * area : 硚口区
         * address : 测试地址
         * is_default : 1
         */

        private String id;
        private String name;
        private String mobile;
        private String province;
        private String city;
        private String area;
        private String address;
        private String is_default;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }
    }
}
