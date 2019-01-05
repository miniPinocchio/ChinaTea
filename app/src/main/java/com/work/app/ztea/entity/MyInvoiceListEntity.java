package com.work.app.ztea.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liu on 2018/3/21.
 */

public class MyInvoiceListEntity extends BaseEntity<List<MyInvoiceListEntity.Invoice>> implements Serializable{

    public static class Invoice implements Serializable{

        /**
         * id : 7
         * tstamp : 0
         * crdate : 0
         * cruser_id : 0
         * deleted : 0
         * hidden : 0
         * sorting : 0
         * user_id : 5
         * title : 测试公司发票
         * types : 0
         * tax_number : Hvcxdfghhj
         * bank_name : 中国银行
         * bank_card : 6222123412341234
         * address : 光谷广场附近
         * mobile : 13712345678
         * tel : 56684123
         * email : 14562
         * is_default : 0
         */

        private String id;
        private String tstamp;
        private String crdate;
        private String cruser_id;
        private String deleted;
        private String hidden;
        private String sorting;
        private String user_id;
        private String title;
        private String types;
        private String tax_number;
        private String bank_name;
        private String bank_card;
        private String address;
        private String mobile;
        private String tel;
        private String email;
        private String is_default;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTstamp() {
            return tstamp;
        }

        public void setTstamp(String tstamp) {
            this.tstamp = tstamp;
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

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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

        public String getTax_number() {
            return tax_number;
        }

        public void setTax_number(String tax_number) {
            this.tax_number = tax_number;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getBank_card() {
            return bank_card;
        }

        public void setBank_card(String bank_card) {
            this.bank_card = bank_card;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }
    }
}
