package com.bRd.mot.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.bRd.mot.R;
import com.vstechlab.easyfonts.EasyFonts;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utility {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private static final DecimalFormat decimalFormat = new DecimalFormat("##.00");

    //getMonthToBulgarian
    public static String getMonthToBulgarian(int month) {
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
        String monthString = dateFormatSymbols.getMonths()[month - 1];
        return monthString.substring(0, 1).toUpperCase() + monthString.substring(1);
    }

    //formatDateToString
    public static String formatDateToString(Date date) {

        if (date == null)
            return "";

        return simpleDateFormat.format(date);
    }

    //formatDoubleToString
    public static String formatDoubleToString(double d) {
        return decimalFormat.format(d);
    }

    //parseStringToDate
    public static Date parseStringToDate(String s) {
        try {
            return simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    //getDateBeforeSevenDays
    public static Date getDateBeforeSevenDays(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        return calendar.getTime();
    }

    //parseStringToDouble
    public static double parseStringToDouble(String str) {

        if (str.isEmpty())
            return 0.00;

        str = str.replace(",", ".");
        return Double.parseDouble(str);
    }

    //setEditTextError
    public static void setEditTextError(EditText editText, String error) {

        editText.setError(error);
        editText.requestFocus();
    }

    //hideKeyboard
    public static void hideKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
