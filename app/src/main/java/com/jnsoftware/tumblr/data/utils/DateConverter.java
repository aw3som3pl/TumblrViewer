package com.jnsoftware.tumblr.data.utils;

import java.util.Date;

import androidx.room.TypeConverter;


/**
 * Created on : Feb 07, 2020
 * Author     : JNsoftware
 */
public class DateConverter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}