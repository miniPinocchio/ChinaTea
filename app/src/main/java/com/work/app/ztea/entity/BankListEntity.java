package com.work.app.ztea.entity;

import java.util.List;

/**
 * Created by liu on 2018/3/5.
 */

public class BankListEntity extends BaseEntity<List<BankListEntity.Bank>> {

    public static class Bank {

        /**
         * title : 中国银行
         */

        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
