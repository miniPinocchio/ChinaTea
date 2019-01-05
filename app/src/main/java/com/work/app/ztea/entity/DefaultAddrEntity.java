package com.work.app.ztea.entity;

/**
 * 默认地址
 * Created by liu on 2018/8/3.
 */

public class DefaultAddrEntity extends BaseEntity<DefaultAddrEntity.AddressBean> {

    public static class AddressBean {


        /**
         * id : 1202
         * name : 高显程
         * mobile : 18727839636
         * province : 湖北省
         * city : 咸宁市
         * area : 赤壁市
         * address : 赤马港天佑小区李晓超市旁
         */

        private String id;
        private String name;
        private String mobile;
        private String province;
        private String city;
        private String area;
        private String address;

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
    }
}
