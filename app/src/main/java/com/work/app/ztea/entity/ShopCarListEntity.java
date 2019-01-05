package com.work.app.ztea.entity;

import java.util.List;

/**
 * Created by qiyunfeng on 2018/2/9.
 */

public class ShopCarListEntity extends BaseEntity<List<ShopCarListEntity.ShopCar>> {
    public static class ShopCar {


        /**
         * cart_id : 100
         * goods_id : 1
         * goods_num : 2
         * goods_attr : 饼
         * title : 神秘村落240g湖北老青砖茶 精致送礼佳品
         * price : 0.01
         * types : 1
         * img_url : http://zhongcha.hqdemo.cn/Uploads/Goods/thumb_0/201607/5c00ad55eb3a9.png
         */

        private String cart_id;
        private String goods_id;
        private String goods_num;
        private String goods_attr;
        private String title;
        private String price;
        private String types;
        private String img_url;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getCart_id() {
            return cart_id;
        }

        public void setCart_id(String cart_id) {
            this.cart_id = cart_id;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }
}
