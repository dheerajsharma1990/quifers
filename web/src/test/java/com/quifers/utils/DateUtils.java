package com.quifers.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public static Date getYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public static String getYesterdayAsString() {
        Date date = getYesterdayDate();
        return DAY_FORMAT.format(date);
    }

    public static String getTodayString() {
        Date date = getCurrentDate();
        return DAY_FORMAT.format(date);
    }
}
