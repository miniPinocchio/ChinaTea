package com.work.app.ztea.entity;

import java.util.List;

/**
 * Created by qiyunfeng on 2018/2/9.
 */

public class HomeGoodsEntity extends BaseEntity<HomeGoodsEntity.Goods> {

    public static class Goods {


        private List<SlideShowBean> slideShow;
        private List<CategoryBean> category;
        private List<TagsBean> tags;

        public List<SlideShowBean> getSlideShow() {
            return slideShow;
        }

        public void setSlideShow(List<SlideShowBean> slideShow) {
            this.slideShow = slideShow;
        }

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public static class SlideShowBean {
            /**
             * id : 22
             * crdate : 1500966799
             * cruser_id : 0
             * tstamp : 1543550896
             * tuser_id : 1
             * deleted : 0
             * hidden : 0
             * sorting : 11
             * hit : 0
             * hot : 0
             * top : 0
             * title : 预售商品
             * position : 1
             * remark :
             * link : zhongcha.hqdemo.cn/index.php?m=&c=goods&a=info&id=2
             * activity : 0
             * type2 : 0
             * type : 0
             * target : null
             * text : null
             * code : null
             * flash_file : null
             * flash_url : null
             * image : http://zhongcha.hqdemo.cn/Uploads/Banner/5c00b7b01b6f1.png
             * start_time : 1525158792
             * end_time : 1557936000
             * category : 0
             * pid : 0
             * linkmodel : 1
             * linkid : 2
             * html : <a href="javascript:void(0)" title="预售商品" target=""><img src="/Uploads/Banner/5c00b7b01b6f1.png" title="预售商品" alt="预售商品" /></a>
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
            private Object target;
            private Object text;
            private Object code;
            private Object flash_file;
            private Object flash_url;
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

            public Object getTarget() {
                return target;
            }

            public void setTarget(Object target) {
                this.target = target;
            }

            public Object getText() {
                return text;
            }

            public void setText(Object text) {
                this.text = text;
            }

            public Object getCode() {
                return code;
            }

            public void setCode(Object code) {
                this.code = code;
            }

            public Object getFlash_file() {
                return flash_file;
            }

            public void setFlash_file(Object flash_file) {
                this.flash_file = flash_file;
            }

            public Object getFlash_url() {
                return flash_url;
            }

            public void setFlash_url(Object flash_url) {
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

        public static class CategoryBean {
            /**
             * id : 3
             * image : 5c00b38f6c557.png
             * name : 年份茶
             * pid : 0
             * url : http://zhongcha.hqdemo.cn/index.php?m=&c=goods&a=goodslist&id=3
             * img_url : http://zhongcha.hqdemo.cn/Uploads/Goodscategory/5c00b38f6c557.png
             */

            private String id;
            private String image;
            private String name;
            private String pid;
            private String url;
            private String img_url;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }
        }

        public static class TagsBean {
            /**
             * id : 1
             * name : 新品推荐
             * remark : 每一款新品都是我们精选
             * goods : [{"id":"1","title":"神秘村落240g湖北老青砖茶  精致送礼佳品 ","crdate":"1469857407","image":"http://zhongcha.hqdemo.cn/Uploads/Goods/thumb_0/201607/5c00ad55eb3a9.png","remark":"商品摘要商品摘要商品摘要商品摘要商品摘要商品摘要","price":"0.01","integral":""},{"id":"3","title":"云南普洱","crdate":"1543806837","image":"http://zhongcha.hqdemo.cn/Uploads/Goods/thumb_0/201812/5c04dea6c04fa.png","remark":"商品摘要商品摘要商品摘要商品摘要商品摘要","price":"469.00","integral":""},{"id":"4","title":"测试茶品一号","crdate":"1545288597","image":"http://zhongcha.hqdemo.cn/Uploads/Goods/thumb_0/201812/5c1b3b9398405.png","remark":"这个茶很好喝","price":"2999.00","integral":""},{"id":"7","title":"年份茶","crdate":"1545294017","image":"http://zhongcha.hqdemo.cn/Uploads/Goods/thumb_0/201812/5c1b50c0b9e15.png","remark":"商品摘要商品摘要商品摘要","price":"469.00","integral":""}]
             */

            private String id;
            private String name;
            private String remark;
            private List<GoodsBean> goods;

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

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            public static class GoodsBean {
                /**
                 * id : 1
                 * title : 神秘村落240g湖北老青砖茶  精致送礼佳品
                 * crdate : 1469857407
                 * image : http://zhongcha.hqdemo.cn/Uploads/Goods/thumb_0/201607/5c00ad55eb3a9.png
                 * remark : 商品摘要商品摘要商品摘要商品摘要商品摘要商品摘要
                 * price : 0.01
                 * integral :
                 */

                private String id;
                private String title;
                private String crdate;
                private String image;
                private String remark;
                private String price;
                private String integral;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getCrdate() {
                    return crdate;
                }

                public void setCrdate(String crdate) {
                    this.crdate = crdate;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
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
            }
        }
    }
}
