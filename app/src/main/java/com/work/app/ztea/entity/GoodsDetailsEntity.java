package com.work.app.ztea.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by qiyunfeng on 2018/2/9.
 */

public class GoodsDetailsEntity extends BaseEntity<GoodsDetailsEntity.Details> {
    public static class Details {

        /**
         * id : 3
         * crdate : 1543806837
         * cruser_id : 1
         * tstamp : 1543829534
         * tuser_id : 1
         * deleted : 0
         * hidden : 0
         * sorting : 3
         * hit : 0
         * hot : 0
         * top : 0
         * number : 2000.489.563
         * title : 云南普洱
         * types : 1
         * level : 1
         * category : 1
         * brand : 八马茶叶
         * tag_id : 1
         * market_price : 0.00
         * supplier_price : 0.00
         * price : 469.00
         * image : http://zhongcha.hqdemo.cn/Uploads/Goods/thumb_0/201812/5c04dea6c04fa.png
         * video :
         * video_img :
         * remark : 商品摘要商品摘要商品摘要商品摘要商品摘要
         * content : <p>商品摘要商品摘要商品摘要商品摘要商品摘要商品摘要商品摘要商品摘要商品摘要商品摘要<label class="control-label">详细内容</label><br/></p><p><br/></p><p>品牌 ：</p><p>规格：</p><p>包装：</p><p>原料 ：</p><p>产品归属 ：</p><p>产地 ：</p><p>生产日期 ：</p><p>货号 ：</p><p>保质期 ：</p><p>存储条件&nbsp; ：</p><p>皇家一号</p><p>38KG</p><p>盒装</p><p>茶叶</p><p>公司</p><p>江西</p><p>2018年12月12日</p><p>20158945644</p><p>540天</p><p>干燥、阴凉处存储</p><p><br/></p>
         * start_time : 0
         * end_time : 0
         * activity_start : 0
         * activity_end : 0
         * stock : 9991
         * sell_num : 0
         * supplier_id : 0
         * guige :
         * xinghao :
         * integral :
         * comment_num : 0
         * is_free_ship : 0
         * buyer_shipmoney : 0.00
         * buyer_integral : 0
         * province : null
         * city : null
         * area : null
         * addr_str :
         * store_id : 0
         * buy_level :
         * addr_limit :
         * package : 包装
         * raw_material : 包装
         * product_ownership : 包装
         * place_of_origin : 包装
         * production_date : 1540224000
         * shelf_life : 包装
         * storage_conditions : 包装
         * level_1_num : 0
         * level_2_num : 0
         * level_3_num : 0
         * level_4_num : 0
         * level_5_num : 0
         * is_sample : 0
         * earnest : 0.00
         * promotion1 :
         * promotion2 :
         * promotion3 :
         * multiprice :
         * voice : 品牌：八马茶叶规格：包装：包装原料：包装产品归属：包装生产日期：1540224000保质期：包装产地：包装货号：2000.489.563储存条件：包装
         * images : ["http://zhongcha.hqdemo.cn/Uploads/Goods/thumb_0/201812/5c04dea6c04fa.png","http://zhongcha.hqdemo.cn/Uploads/Goods/attach/201812/5c04dea58000e.png","http://zhongcha.hqdemo.cn/Uploads/Goods/attach/201812/5c04dea59d142.png","http://zhongcha.hqdemo.cn/Uploads/Goods/attach/201812/5c04dea5c0426.png"]
         * is_favorites : 0
         * link : http://zhongcha.hqdemo.cn/index.php?m=&c=goods&a=goodsdtl&id=3
         */

        private String id;
        private String crdate;
        private String cruser_id;
        private String tstamp;
        private String tuser_id;
        private String deleted;
        private String hidden;
        private String sorting;
        private String hit;
        private String hot;
        private String top;
        private String number;
        private String title;
        private String types;
        private String level;
        private String category;
        private String brand;
        private String tag_id;
        private String market_price;
        private String supplier_price;
        private String price;
        private String image;
        private String image_small;
        private String video;
        private String video_img;
        private String remark;
        private String content;
        private String start_time;
        private String end_time;
        private String activity_start;
        private String activity_end;
        private String stock;
        private String sell_num;
        private String supplier_id;
        private String guige;
        private String xinghao;
        private String integral;
        private String comment_num;
        private String is_free_ship;
        private String buyer_shipmoney;
        private String buyer_integral;
        private Object province;
        private Object city;
        private Object area;
        private String addr_str;
        private String store_id;
        private String buy_level;
        private String addr_limit;
        @SerializedName("package")
        private String packageX;
        private String raw_material;
        private String product_ownership;
        private String place_of_origin;
        private String production_date;
        private String shelf_life;
        private String storage_conditions;
        private String level_1_num;
        private String level_2_num;
        private String level_3_num;
        private String level_4_num;
        private String level_5_num;
        private String is_sample;
        private String earnest;
        private String promotion1;
        private String promotion2;
        private String promotion3;
        private String multiprice;
        private String voice;
        private String is_favorites;
        private String link;
        private List<String> images;

        public String getImage_small() {
            return image_small;
        }

        public void setImage_small(String image_small) {
            this.image_small = image_small;
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

        public String getCruser_id() {
            return cruser_id;
        }

        public void setCruser_id(String cruser_id) {
            this.cruser_id = cruser_id;
        }

        public String getTstamp() {
            return tstamp;
        }

        public void setTstamp(String tstamp) {
            this.tstamp = tstamp;
        }

        public String getTuser_id() {
            return tuser_id;
        }

        public void setTuser_id(String tuser_id) {
            this.tuser_id = tuser_id;
        }

        public String getDeleted() {
            return deleted;
        }

        public void setDeleted(String deleted) {
            this.deleted = deleted;
        }

        public String getHidden() {
            return hidden;
        }

        public void setHidden(String hidden) {
            this.hidden = hidden;
        }

        public String getSorting() {
            return sorting;
        }

        public void setSorting(String sorting) {
            this.sorting = sorting;
        }

        public String getHit() {
            return hit;
        }

        public void setHit(String hit) {
            this.hit = hit;
        }

        public String getHot() {
            return hot;
        }

        public void setHot(String hot) {
            this.hot = hot;
        }

        public String getTop() {
            return top;
        }

        public void setTop(String top) {
            this.top = top;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
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

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getTag_id() {
            return tag_id;
        }

        public void setTag_id(String tag_id) {
            this.tag_id = tag_id;
        }

        public String getMarket_price() {
            return market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public String getSupplier_price() {
            return supplier_price;
        }

        public void setSupplier_price(String supplier_price) {
            this.supplier_price = supplier_price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getVideo_img() {
            return video_img;
        }

        public void setVideo_img(String video_img) {
            this.video_img = video_img;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getActivity_start() {
            return activity_start;
        }

        public void setActivity_start(String activity_start) {
            this.activity_start = activity_start;
        }

        public String getActivity_end() {
            return activity_end;
        }

        public void setActivity_end(String activity_end) {
            this.activity_end = activity_end;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getSell_num() {
            return sell_num;
        }

        public void setSell_num(String sell_num) {
            this.sell_num = sell_num;
        }

        public String getSupplier_id() {
            return supplier_id;
        }

        public void setSupplier_id(String supplier_id) {
            this.supplier_id = supplier_id;
        }

        public String getGuige() {
            return guige;
        }

        public void setGuige(String guige) {
            this.guige = guige;
        }

        public String getXinghao() {
            return xinghao;
        }

        public void setXinghao(String xinghao) {
            this.xinghao = xinghao;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getComment_num() {
            return comment_num;
        }

        public void setComment_num(String comment_num) {
            this.comment_num = comment_num;
        }

        public String getIs_free_ship() {
            return is_free_ship;
        }

        public void setIs_free_ship(String is_free_ship) {
            this.is_free_ship = is_free_ship;
        }

        public String getBuyer_shipmoney() {
            return buyer_shipmoney;
        }

        public void setBuyer_shipmoney(String buyer_shipmoney) {
            this.buyer_shipmoney = buyer_shipmoney;
        }

        public String getBuyer_integral() {
            return buyer_integral;
        }

        public void setBuyer_integral(String buyer_integral) {
            this.buyer_integral = buyer_integral;
        }

        public Object getProvince() {
            return province;
        }

        public void setProvince(Object province) {
            this.province = province;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public Object getArea() {
            return area;
        }

        public void setArea(Object area) {
            this.area = area;
        }

        public String getAddr_str() {
            return addr_str;
        }

        public void setAddr_str(String addr_str) {
            this.addr_str = addr_str;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getBuy_level() {
            return buy_level;
        }

        public void setBuy_level(String buy_level) {
            this.buy_level = buy_level;
        }

        public String getAddr_limit() {
            return addr_limit;
        }

        public void setAddr_limit(String addr_limit) {
            this.addr_limit = addr_limit;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getRaw_material() {
            return raw_material;
        }

        public void setRaw_material(String raw_material) {
            this.raw_material = raw_material;
        }

        public String getProduct_ownership() {
            return product_ownership;
        }

        public void setProduct_ownership(String product_ownership) {
            this.product_ownership = product_ownership;
        }

        public String getPlace_of_origin() {
            return place_of_origin;
        }

        public void setPlace_of_origin(String place_of_origin) {
            this.place_of_origin = place_of_origin;
        }

        public String getProduction_date() {
            return production_date;
        }

        public void setProduction_date(String production_date) {
            this.production_date = production_date;
        }

        public String getShelf_life() {
            return shelf_life;
        }

        public void setShelf_life(String shelf_life) {
            this.shelf_life = shelf_life;
        }

        public String getStorage_conditions() {
            return storage_conditions;
        }

        public void setStorage_conditions(String storage_conditions) {
            this.storage_conditions = storage_conditions;
        }

        public String getLevel_1_num() {
            return level_1_num;
        }

        public void setLevel_1_num(String level_1_num) {
            this.level_1_num = level_1_num;
        }

        public String getLevel_2_num() {
            return level_2_num;
        }

        public void setLevel_2_num(String level_2_num) {
            this.level_2_num = level_2_num;
        }

        public String getLevel_3_num() {
            return level_3_num;
        }

        public void setLevel_3_num(String level_3_num) {
            this.level_3_num = level_3_num;
        }

        public String getLevel_4_num() {
            return level_4_num;
        }

        public void setLevel_4_num(String level_4_num) {
            this.level_4_num = level_4_num;
        }

        public String getLevel_5_num() {
            return level_5_num;
        }

        public void setLevel_5_num(String level_5_num) {
            this.level_5_num = level_5_num;
        }

        public String getIs_sample() {
            return is_sample;
        }

        public void setIs_sample(String is_sample) {
            this.is_sample = is_sample;
        }

        public String getEarnest() {
            return earnest;
        }

        public void setEarnest(String earnest) {
            this.earnest = earnest;
        }

        public String getPromotion1() {
            return promotion1;
        }

        public void setPromotion1(String promotion1) {
            this.promotion1 = promotion1;
        }

        public String getPromotion2() {
            return promotion2;
        }

        public void setPromotion2(String promotion2) {
            this.promotion2 = promotion2;
        }

        public String getPromotion3() {
            return promotion3;
        }

        public void setPromotion3(String promotion3) {
            this.promotion3 = promotion3;
        }

        public String getMultiprice() {
            return multiprice;
        }

        public void setMultiprice(String multiprice) {
            this.multiprice = multiprice;
        }

        public String getVoice() {
            return voice;
        }

        public void setVoice(String voice) {
            this.voice = voice;
        }

        public String getIs_favorites() {
            return is_favorites;
        }

        public void setIs_favorites(String is_favorites) {
            this.is_favorites = is_favorites;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
