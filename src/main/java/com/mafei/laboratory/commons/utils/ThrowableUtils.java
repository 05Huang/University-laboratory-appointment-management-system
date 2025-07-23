package com.mafei.laboratory.commons.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author wutangsheng
 * @create 2021-02-10 21:18
 * @info
 */
public class ThrowableUtils {

    /**
     * 获取堆栈信息
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}