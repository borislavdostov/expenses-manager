package com.bRd.mot.Utils;

import android.app.Activity;
import android.view.WindowManager;
import android.widget.EditText;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utility {
    private static final DecimalFormat decimalFormat = new DecimalFormat("##.00");

    public static String formatDoubleToString(double d) {
        return decimalFormat.format(d);
    }

    //parseStringToDouble
    public static double parseStringToDouble(String str) {

        if (str.isEmpty()) {
            return 0.00;
        }

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
}
