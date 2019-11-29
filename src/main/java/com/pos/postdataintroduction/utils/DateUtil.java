package com.pos.postdataintroduction.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    /**
     * 将时间字符串转换成短时间戳（yyyy-MM-dd）
     * @param data
     * @return String
     */
    public static Long str2Time(String data) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        final Date datetime = sdf.parse(data);//将你的日期转换为时间戳
        return datetime.getTime()/1000;
    }
}
