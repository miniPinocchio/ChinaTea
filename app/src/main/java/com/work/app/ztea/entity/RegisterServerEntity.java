package com.work.app.ztea.entity;

/**
 * Created by liu on 2018/1/15.
 */

public class RegisterServerEntity extends BaseEntity<RegisterServerEntity.Server> {

    public static class Server {


        /**
         * title : 注册协议
         * link : http://zhongcha.hqdemo.cn/index.php?m=mobile&c=index&a=content&type=agb
         */

        private String title;
        private String link;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
