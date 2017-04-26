package com.zxb.libsdemo.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mrzhou on 2017/4/26.
 */
public class PointsLine implements Serializable {

    public int result;
    public int errorcode;
    public DataEntity data;

    public static class DataEntity implements Serializable {
        public List<FuyingItem> fyList;
    }
}
