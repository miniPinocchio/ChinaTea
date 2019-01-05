package com.work.app.ztea.entity;

import java.util.List;

/**
 * Created by liu on 2018/2/23.
 */

public class MessageEntity extends BaseEntity<MessageEntity.Messages> {
    public static class Messages {

        /**
         * no_read_num : 0
         * data : [{"id":"118","addresser":"6","read_uids":"378,403,1","crdate":"2017-12-29","title":"6","read_status":1},{"id":"117","addresser":"5","read_uids":"378,403,1","crdate":"2017-12-29","title":"5","read_status":1},{"id":"116","addresser":"4","read_uids":"378,403,1","crdate":"2017-12-29","title":"4","read_status":1},{"id":"115","addresser":"3","read_uids":"403,378","crdate":"2017-12-29","title":"3","read_status":1},{"id":"114","addresser":"2","read_uids":"403,378","crdate":"2017-12-29","title":"2","read_status":1},{"id":"113","addresser":"1","read_uids":"403,378","crdate":"2017-12-29","title":"1","read_status":1}]
         */

        public int no_read_num;
        public List<DataBean> data;

        public static class DataBean {
            /**
             * id : 118
             * addresser : 6
             * read_uids : 378,403,1
             * crdate : 2017-12-29
             * title : 6
             * read_status : 1
             */

            public String id;
            public String addresser;
            public String read_uids;
            public String crdate;
            public String title;
            public String read_status;
            public String link;
        }
    }
}
