package com.work.app.ztea.entity;

/**
 * Created by liu on 2018/1/15.
 */

public class LoginEntity extends BaseEntity<LoginEntity.Login> {
    public static class Login {

        /**
         * id : 2
         * mobile : 13871445159
         * number : S0000002
         * username : 13871445159
         * level : 1
         * token : MisxNTQ1MTIxNjgyTWl4REoqJmZUQTghZktLcw==
         */

        private String id;
        private String mobile;
        private String number;
        private String username;
        private String level;
        private String token;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
