package com.work.app.ztea.entity;

/**
 * 我的二维码
 * Created by liu on 2018/8/3.
 */

public class QrCodeEntity extends BaseEntity<QrCodeEntity.QrCode> {

    public static class QrCode {


        /**
         * title : 尊享会
         * content : 尊享会
         * image : http://zhongcha.hqdemo.cn/Uploads/User/201812/3.png
         * share : http://zhongcha.hqdemo.cn/index.php?m=mobile&c=user&a=register&invite_code=S0000003
         */

        private String title;
        private String content;
        private String image;
        private String share;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getShare() {
            return share;
        }

        public void setShare(String share) {
            this.share = share;
        }
    }
}
