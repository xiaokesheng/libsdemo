package com.zxb.libsdemo.model;

import java.util.List;

/**
 * Created by yufangyuan on 2017/8/24.
 */

public class HLPointResult {

    public int result;
    public int errorcode;
    public DataBean data;

    public static class DataBean {
        public String amountType;
        public int nextPageNo;
        public int currentPageNo;
        public List<ListBean> list;

        public static class ListBean {
            public String time;
            public int num;
            public double amount;
        }
    }
}
