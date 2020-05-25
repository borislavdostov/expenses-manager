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

    //showDatePickerDialog
    public static void showDatePickerDialog(Context context, final EditText editText) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_date_picker, null);

        final DatePicker datePicker = view.findViewById(R.id.datePicker);

        Button backButton = view.findViewById(R.id.backButton);
        Button okButton = view.findViewById(R.id.okButton);

        final Dialog dialog = new Dialog(context, R.style.AppTheme);
        dialog.setContentView(view);

        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getId() == R.id.backButton) {

                    dialog.dismiss();

                } else if (view.getId() == R.id.okButton) {

                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth();
                    int year = datePicker.getYear();

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);

                    Date date = new Date(calendar.getTimeInMillis());
                    editText.setText(formatDateToString(date));
                    dialog.dismiss();
                }
                editText.clearFocus();
            }
        };

        backButton.setOnClickListener(onClick);
        okButton.setOnClickListener(onClick);

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(600, 600);
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.dimAmount = 0.7f;
            lp.y = -140;
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
    }

    //formatDateToString
    public static String formatDateToString(Date date) {

        if (date == null)
            return "";

        return simpleDateFormat.format(date);
    }

    //formatIntToString
    public static String formatIntToString(int i) {

        if (i == 0)
            return "";

        return "" + i;
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

    //parseIntToBoolean
    public static boolean parseIntToBoolean(int i) {
        return i != 0;
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

    //setTypeFace
    public static void setTypeFace(Context context, TextView textView) {
        textView.setTypeface(EasyFonts.caviarDreams(context));
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
