package com.work.app.ztea.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 订单详情
 * Created by qiyunfeng on 2018/2/8.
 */

public class GoodsOrderEntity extends BaseEntity<GoodsOrderEntity.Order> implements Serializable{

    public static class Order implements Serializable{

        /**
         * goods_list : [{"goods_id":"3","goods_num":"2","goods_attr":"","title":"云南普洱","types":"1","category":"1","price":"469.00","integral":"","img_url":"http://zhongcha.hqdemo.cn/Uploads/Goods/thumb_0/201812/5c04dea6c04fa.png"}]
         * money : 938
         * total_money : 938
         * total_integral :
         * ship_money : 0
         * total_earnest : 0
         * form : 1
         * goods_count : 2
         * order_type : 1
         * amount : 0.00
         * invoice : 5
         */

        private String money;
        private String total_money;
        private String total_integral;
        private String ship_money;
        private String total_earnest;
        private String form;
        private String goods_count;
        private String order_type;
        private String amount;
        private String invoice;
        private String agreement;
        private List<OrderDetailEntity.OrderDetail.GoodsListBean> goods_list;

        public String getAgreement() {
            return agreement;
        }

        public void setAgreement(String agreement) {
            this.agreement = agreement;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
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

        public String getForm() {
            return form;
        }

        public void setForm(String form) {
            this.form = form;
        }

        public String getGoods_count() {
            return goods_count;
        }

        public void setGoods_count(String goods_count) {
            this.goods_count = goods_count;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getInvoice() {
            return invoice;
        }

        public void setInvoice(String invoice) {
            this.invoice = invoice;
        }

        public List<OrderDetailEntity.OrderDetail.GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<OrderDetailEntity.OrderDetail.GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        public static class GoodsListBean implements Serializable{
            /**
             * goods_id : 3
             * goods_num : 2
             * goods_attr :
             * title : 云南普洱
             * types : 1
             * category : 1
             * price : 469.00
             * integral :
             * img_url : http://zhongcha.hqdemo.cn/Uploads/Goods/thumb_0/201812/5c04dea6c04fa.png
             */

            private String goods_id;
            private String goods_num;
            private String goods_attr;
            private String title;
            private String types;
            private String category;
            private String price;
            private String integral;
            private String img_url;

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

            public String getGoods_attr() {
                return goods_attr;
            }

            public void setGoods_attr(String goods_attr) {
                this.goods_attr = goods_attr;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTypes() {
                return types;
            }

            public void setTypes(String types) {
                this.types = types;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
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

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }
        }
    }
}
