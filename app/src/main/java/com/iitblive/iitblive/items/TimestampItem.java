package com.iitblive.iitblive.items;

import android.content.Context;

import com.iitblive.iitblive.R;

import java.util.Calendar;

/**
 * Created by Bijoy on 5/25/2015.
 */
public class TimestampItem {
    public static String SPACE = " ";
    public static String PM = "p.m.";
    public static String AM = "a.m.";
    public static String MIDNIGHT = "Midnight";
    public static String NOON = "Noon";
    public static String COLON = ":";
    public static Integer ONE_HOUR = 1000 * 60 * 60;

    public String timestamp, time, date;
    public Calendar calender;

    public TimestampItem(Context context, String timestamp) {
        this.timestamp = timestamp;
        //yyyy-MM-ddThh:mm:ss
        //0123456789012345678

        String yyyy = getYear();
        String MM = getMonth();
        String dd = getDate();

        String MMMM = TimestampItem.getMonthStr(
                context,
                Integer.parseInt(MM));

        String hh = getHour();
        String mm = getMinute();

        date = dd + SPACE + MMMM + SPACE + yyyy;
        time = TimestampItem.getTimeStr(hh, mm);
        calender = getCalendar(yyyy, MM, dd, hh, mm);
    }

    public static String getMonthStr(Context context, Integer month) {
        switch (month) {
            case 1:
                return context.getString(R.string.month_jan);
            case 2:
                return context.getString(R.string.month_feb);
            case 3:
                return context.getString(R.string.month_mar);
            case 4:
                return context.getString(R.string.month_apr);
            case 5:
                return context.getString(R.string.month_may);
            case 6:
                return context.getString(R.string.month_jun);
            case 7:
                return context.getString(R.string.month_jul);
            case 8:
                return context.getString(R.string.month_aug);
            case 9:
                return context.getString(R.string.month_sep);
            case 10:
                return context.getString(R.string.month_oct);
            case 11:
                return context.getString(R.string.month_nov);
            case 12:
                return context.getString(R.string.month_dec);
            default:
                return null;
        }
    }

    public static String getTimeStr(String hh, String mm) {
        Integer hr = Integer.parseInt(hh);
        Integer min = Integer.parseInt(mm);

        if (hr == 0 && min == 0) {
            return MIDNIGHT;
        } else if (hr < 12) {
            return hh + COLON + mm + SPACE + AM;
        } else if (hr == 12 && min == 0) {
            return NOON;
        } else {
            return (hr - 12) + COLON + mm + SPACE + PM;
        }
    }

    public static Calendar getCalendar(
            String yyyy,
            String MM,
            String dd,
            String hh,
            String mm
    ) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(
                Integer.parseInt(yyyy),
                Integer.parseInt(MM),
                Integer.parseInt(dd),
                Integer.parseInt(hh),
                Integer.parseInt(mm)
        );
        return calendar;
    }

    public String getYear() {
        return timestamp.substring(0, 4);
    }

    public String getMonth() {
        return timestamp.substring(5, 7);
    }

    public String getDate() {
        return timestamp.substring(8, 10);
    }

    public String getHour() {
        return timestamp.substring(11, 13);
    }

    public String getMinute() {
        return timestamp.substring(14, 16);
    }
}
