package com.work.app.ztea.entity;

import java.util.List;

/**
 * Created by liu on 2018/3/5.
 */

public class GoodsListEntity extends BaseEntity<GoodsListEntity.GoodsData> {
    public static class GoodsData {


        private List<GoodsListBean> goods_list;

        private List<SlideShowBean> slideShow;

        public List<SlideShowBean> getSlideShow() {
            return slideShow;
        }

        public void setSlideShow(List<SlideShowBean> slideShow) {
            this.slideShow = slideShow;
        }

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        public static class GoodsListBean {
            /**
             * id : 3
             * crdate : 1543806837
             * title : 云南普洱
             * price : 469.00
             * remark : 商品摘要商品摘要商品摘要商品摘要商品摘要
             * image : http://zhongcha.hqdemo.cn/Uploads/Goods/thumb_0/201812/5c04dea6c04fa.png
             * integral :
             */

            private String id;
            private String crdate;
            private String title;
            private String price;
            private String remark;
            private String image;

            private String integral;

            private String is_sample;
            private String activity_start;
            private String activity_end;

            public String getIs_sample() {
                return is_sample;
            }

            public void setIs_sample(String is_sample) {
                this.is_sample = is_sample;
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

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getIntegral() {
                return integral;
            }

            public void setIntegral(String integral) {
                this.integral = integral;
            }
        }

        public static class SlideShowBean{

            /**
             * id : 23
             * crdate : 1546428701
             * cruser_id : 1
             * tstamp : 1546428701
             * tuser_id : 1
             * deleted : 0
             * hidden : 0
             * sorting : 23
             * hit : 0
             * hot : 0
             * top : 0
             * title : 积分商城
             * position : 3
             * remark :
             * link :
             * activity : 0
             * type2 : 0
             * type : 0
             * target :
             * text :
             * code :
             * flash_file :
             * flash_url :
             * image : http://zhongcha.hqdemo.cn/Uploads/Banner/5c2ca11d5ed8c.jpg
             * start_time : 0
             * end_time : 0
             * category : 0
             * pid : 0
             * linkmodel : 0
             * linkid : 0
             * html :
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
            private String title;
            private String position;
            private String remark;
            private String link;
            private String activity;
            private String type2;
            private String type;
            private String target;
            private String text;
            private String code;
            private String flash_file;
            private String flash_url;
            private String image;
            private String start_time;
            private String end_time;
            private String category;
            private String pid;
            private String linkmodel;
            private String linkid;
            private String html;

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getActivity() {
                return activity;
            }

            public void setActivity(String activity) {
                this.activity = activity;
            }

            public String getType2() {
                return type2;
            }

            public void setType2(String type2) {
                this.type2 = type2;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTarget() {
                return target;
            }

            public void setTarget(String target) {
                this.target = target;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getFlash_file() {
                return flash_file;
            }

            public void setFlash_file(String flash_file) {
                this.flash_file = flash_file;
            }

            public String getFlash_url() {
                return flash_url;
            }

            public void setFlash_url(String flash_url) {
                this.flash_url = flash_url;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
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

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getLinkmodel() {
                return linkmodel;
            }

            public void setLinkmodel(String linkmodel) {
                this.linkmodel = linkmodel;
            }

            public String getLinkid() {
                return linkid;
            }

            public void setLinkid(String linkid) {
                this.linkid = linkid;
            }

            public String getHtml() {
                return html;
            }

            public void setHtml(String html) {
                this.html = html;
            }
        }
    }
}
