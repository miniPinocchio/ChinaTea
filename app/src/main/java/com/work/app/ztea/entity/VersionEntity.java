package com.work.app.ztea.entity;

/**
 * Created by liu on 2018/4/8.
 */

public class VersionEntity extends BaseEntity<VersionEntity.Version> {
    public static class Version {

        /**
         * version : 1.1.2
         * platform : 1
         * link : https://www.pgyer.com/r2SO
         * content : 更新
         * is_update : 1
         * versionnum : 1001002
         * title : 当前版本不再支持，请升级到最新版本
         */

        public String version;
        public String platform;
        public String link;
        public String content;
        public int is_update;
        public String versionnum;
        public String title;

    }

}
