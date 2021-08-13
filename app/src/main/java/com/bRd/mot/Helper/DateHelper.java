package com.bRd.mot.Helper;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
    private static final SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public static String getMonthToBulgarian(int month) {
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
        String monthString = dateFormatSymbols.getMonths()[month - 1];
        return monthString.substring(0, 1).toUpperCase() + monthString.substring(1);
    }

    public static String formatDateToString(Date date) {

        if (date == null)
            return "";

        return simpleDateFormat.format(date);
    }

    public static Date parseStringToDate(String s) {
        try {
            return simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Date getDateBeforeSevenDays(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        return calendar.getTime();
    }

    //getFirstDayOfCurrentYear
    public static Date getFirstDayOfCurrentYear() {
        Calendar firstDayOfCurrentYear = Calendar.getInstance();
        firstDayOfCurrentYear.set(Calendar.DATE, 1);
        firstDayOfCurrentYear.set(Calendar.MONTH, 0);
        return firstDayOfCurrentYear.getTime();
    }

    //getLastDayOfCurrentYear
    public static Date getLastDayOfCurrentYear(){
        Calendar lastDayOfCurrentYear = Calendar.getInstance();
        lastDayOfCurrentYear.add(Calendar.YEAR, 1);
        lastDayOfCurrentYear.set(Calendar.DATE, 1);
        lastDayOfCurrentYear.set(Calendar.MONTH, 0);
        return lastDayOfCurrentYear.getTime();
    }
}
