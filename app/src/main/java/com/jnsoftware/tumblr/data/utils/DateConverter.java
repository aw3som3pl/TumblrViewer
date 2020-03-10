package com.jnsoftware.tumblr.data.utils;

import java.util.Date;

/**
 * Created on : Feb 07, 2020
 * Author     : JNsoftware
 */
public class DateConverter {

    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }


    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}