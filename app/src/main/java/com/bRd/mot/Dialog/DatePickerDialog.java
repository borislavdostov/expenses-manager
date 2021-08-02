package com.bRd.mot.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;


import com.bRd.mot.Helper.DatePickerListener;
import com.bRd.mot.R;

import java.util.Calendar;
import java.util.Date;

public class DatePickerDialog extends Dialog {

    private final DatePickerListener datePickerListener;

    DatePicker datePicker;

    public DatePickerDialog(Context context, DatePickerListener datePickerListener) {
        super(context);
        this.datePickerListener = datePickerListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_date_picker);
        setCancelable(false);

        datePicker = findViewById(R.id.datePicker);

        Button backButton = findViewById(R.id.backButton);
        Button okButton = findViewById(R.id.ok_btn);

        backButton.setOnClickListener(onClick);
        okButton.setOnClickListener(onClick);
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.backButton) {

                dismiss();

            } else if (view.getId() == R.id.ok_btn) {

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                Date date = new Date(calendar.getTimeInMillis());
                datePickerListener.onDateSelected(date);
                dismiss();
            }
        }
    };
}
