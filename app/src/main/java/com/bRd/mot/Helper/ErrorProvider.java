package com.bRd.mot.Helper;

import android.widget.EditText;

public class ErrorProvider {
    public static void setError(EditText editText, String errorMessage) {
        editText.setError(errorMessage);
        editText.requestFocus();
    }
}
