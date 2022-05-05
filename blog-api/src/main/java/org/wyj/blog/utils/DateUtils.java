package org.wyj.blog.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义时间格式转换工具
 */
public class DateUtils {

    private DateUtils(){}

    public static String dateToStr(Date date, String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String dateToStr(Long timestamp, String pattern){
        return new SimpleDateFormat(pattern).format(new Date(timestamp));
    }
}
