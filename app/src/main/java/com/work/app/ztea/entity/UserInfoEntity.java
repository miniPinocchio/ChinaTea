package com.work.app.ztea.entity;

import java.util.List;

/**
 * Created by qiyunfeng on 2018/2/8.
 */

public class UserInfoEntity extends BaseEntity<UserInfoEntity.UserInfo> {

    public static class UserInfo {
        public String username;
        public String skin;
        public int is_admin;
        public String id;
        public int always_ol;
        public String expiration;
        public String customer_qq;
        public String online;
        public String nickname;
        public String group_id;
        public LevelBean level;
        public String notice;
        public String users_max;
        public int users;
        public int group_online;
        public int account;
        public int admins_max;
        public String admins;
        public int user_identity;
        public int sends;
        public String remind_content;
        public String group_name;
        public String group_qq;
        public String interval;
        public List<String> authority;

        public static class LevelBean {

            public String name;

        }
    }
}
