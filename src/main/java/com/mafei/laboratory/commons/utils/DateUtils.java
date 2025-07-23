package com.mafei.laboratory.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wutangsheng
 * @create 2021-03-11 14:30
 * @info
 */
public class DateUtils {
    public static String getDateFormat() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-ddd hh:mm:ss");
        format.format(new Date());
        return format.toString();
    }

    public static Date getDate() {
        return new Date();
    }
}
