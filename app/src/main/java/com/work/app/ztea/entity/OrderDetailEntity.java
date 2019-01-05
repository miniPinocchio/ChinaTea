package com.work.app.ztea.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 订单详情
 * Created by liu on 2018/8/3.
 */

public class OrderDetailEntity extends BaseEntity<OrderDetailEntity.OrderDetail> implements Serializable{

    public static class OrderDetail implements Serializable{

        /**
         * id : 68
         * crdate : 1546597525
         * number : Y-1901040000068
         * province : 新疆
         * city : 克拉玛依市
         * area : 克拉玛依区
         * address : 长数据地址测试下
         * mobile : 13712345678
         * name : 测试下
         * is_receipt : 0
         * total_money : 0.01
         * total_integral :
         * ship_money :
         * total_earnest :
         * goods_count : 1
         * amount : 0.01
         * agreement : http://zhongcha.hqdemo.cn/index.php?m=mobile&c=index&a=content&type=agreement
         * goods_list : [{"orders_id":"68","goods_id":"1","goods_num":"1","total_money":"0.01","goods_attr":"饼","price":"0.01","integral":"","ship_money":"0.00","total_earnest":"0.00","title":"神秘村落240g湖北老青砖茶 精致送礼佳品 ","category":"1","img_url":"http://zhongcha.hqdemo.cn//Uploads/Goods/thumb_0/201607/5c00ad55eb3a9.png"}]
         */

        private String id;
        private String order_id;
        private String crdate;
        private String number;
        private String province;
        private String city;
        private String area;
        private String address;
        private String mobile;
        private String name;
        private String is_receipt;
        private String total_money;
        private String total_integral;
        private String ship_money;
        private String total_earnest;
        private String goods_count;
        private String amount;
        private String agreement;
        private List<GoodsListBean> goods_list;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIs_receipt() {
            return is_receipt;
        }

        public void setIs_receipt(String is_receipt) {
            this.is_receipt = is_receipt;
        }

        public String getTotal_money() {
            return total_money;
        }

        public void setTotal_money(String total_money) {
            this.total_money = total_money;
        }

        public String getTotal_integral() {
            return total_integral;
        }

        public void setTotal_integral(String total_integral) {
            this.total_integral = total_integral;
        }

        public String getShip_money() {
            return ship_money;
        }

        public void setShip_money(String ship_money) {
            this.ship_money = ship_money;
        }

        public String getTotal_earnest() {
            return total_earnest;
        }

        public void setTotal_earnest(String total_earnest) {
            this.total_earnest = total_earnest;
        }

        public String getGoods_count() {
            return goods_count;
        }

        public void setGoods_count(String goods_count) {
            this.goods_count = goods_count;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getAgreement() {
            return agreement;
        }

        public void setAgreement(String agreement) {
            this.agreement = agreement;
        }

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        public static class GoodsListBean implements Serializable{
            /**
             * orders_id : 68
             * goods_id : 1
             * goods_num : 1
             * total_money : 0.01
             * goods_attr : 饼
             * price : 0.01
             * integral :
             * ship_money : 0.00
             * total_earnest : 0.00
             * title : 神秘村落240g湖北老青砖茶 精致送礼佳品
             * category : 1
             * img_url : http://zhongcha.hqdemo.cn//Uploads/Goods/thumb_0/201607/5c00ad55eb3a9.png
             */

            private String orders_id;
            private String goods_id;
            private String goods_num;
            private String total_money;
            private String goods_attr;
            private String price;
            private String integral;
            private String ship_money;
            private String total_earnest;
            private String title;
            private String category;
            private String img_url;

            public String getOrders_id() {
                return orders_id;
            }

            public void setOrders_id(String orders_id) {
                this.orders_id = orders_id;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(String goods_num) {
                this.goods_num = goods_num;
            }

            public String getTotal_money() {
                return total_money;
            }

            public void setTotal_money(String total_money) {
                this.total_money = total_money;
            }

            public String getGoods_attr() {
                return goods_attr;
            }

            public void setGoods_attr(String goods_attr) {
                this.goods_attr = goods_attr;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getIntegral() {
                return integral;
            }

            public void setIntegral(String integral) {
                this.integral = integral;
            }

            public String getShip_money() {
                return ship_money;
            }

            public void setShip_money(String ship_money) {
                this.ship_money = ship_money;
            }

            public String getTotal_earnest() {
                return total_earnest;
            }

            public void setTotal_earnest(String total_earnest) {
                this.total_earnest = total_earnest;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }
        }
    }
}
