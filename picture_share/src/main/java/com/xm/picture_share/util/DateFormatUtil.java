package com.xm.picture_share.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateFormatUtil {
    public static String SYS_DEFAULT_DATE_FORMAT_STRING1 = "MM/dd/yyyy";
    public static String SYS_DEFAULT_DATE_FORMAT_STRING2 = "yyyy-MM-dd";
    public static String SYS_DEFAULT_DATE_FORMAT_STRING3 = "yyyyMM";
    public static String SYS_DEFAULT_DATE_FORMAT_STRING4 = "MM-dd";
    public static String SYS_DEFAULT_DATE_FORMAT_STRING5 = "yyyyMMdd";
    public static String SYS_DEFAULT_DATE_FORMAT_STRING6 = "yyyy年M月d日";
    public static String SYS_DEFAULT_DATE_FORMAT_STRING7 = "yyyyMMdd HH:mm:ss";
    public static String SYS_DEFAULT_DATE_FORMAT_STRING8 = "yyyy-MM";
    public static String SYS_DEFAULT_DATE_FORMAT_STRING9 = "yyyy/MM/dd E";
    public static String SYS_DEFAULT_DATE_FORMAT_STRING10 = "yyyyMMddHH";
    public static String SYS_DEFAULT_TIMESTAMP_FORMAT_STRING1 = "MM/dd/yyyy HH:mm:ss";
    public static String SYS_DEFAULT_TIMESTAMP_FORMAT_STRING2 = "yyyy-MM-dd HH:mm:ss";
    public static String SYS_DEFAULT_TIMESTAMP_FORMAT_STRING3 = "yyyy-MM-dd HH:mm:ss.SSS";
    public static String SYS_DEFAULT_TIMESTAMP_FORMAT_STRING4 = "yyyy-MM-dd HH:mm";
    public static String SYS_DEFAULT_TIMESTAMP_FORMAT_STRING5 = "MM-dd HH:mm";


    public static String DEFAULT_DATE_FORMAT_STRING = "yyyy/MM/dd";
    public static String DEFAULT_TIMESTAMP_FORMAT_STRING = "yyyy/MM/dd HH:mm:ss";

    public static String DEFAULT_TIMESTAMP_FORMAT_STRING_NEW = "yyyyMMddHHmmss";
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");


    //	public final static SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(PAS_DATE_FORMAT_STRING);
    //	public final static SimpleDateFormat DEFAULT_MMdd_FORMAT = new SimpleDateFormat("MM-dd");
    //	public final static SimpleDateFormat DEFAULT_TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //	public final static SimpleDateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

    public final static Calendar DEFAULT_START_DATE_CALENDAR = new GregorianCalendar(1900, Calendar.JANUARY, 1);
    public final static Calendar DEFAULT_END_DATE_CALENDAR = new GregorianCalendar(3000, Calendar.JANUARY, 1);

    public static final Timestamp DEFAULT_START_TIMESTAMP = new Timestamp(DEFAULT_START_DATE_CALENDAR.getTimeInMillis());
    public static final Timestamp DEFAULT_END_TIMESTAMP = new Timestamp(DEFAULT_END_DATE_CALENDAR.getTimeInMillis());

    public static final Date DEFAULT_START_DATE = DEFAULT_START_DATE_CALENDAR.getTime();
    public static final Date DEFAULT_END_DATE = DEFAULT_END_DATE_CALENDAR.getTime();

    public static String formatDate(Date date) {
        if (date == null)
            return "";

        DateFormat dateFormat = new SimpleDateFormat(SYS_DEFAULT_TIMESTAMP_FORMAT_STRING2);
        String result = dateFormat.format(date);

        return result;
    }
}