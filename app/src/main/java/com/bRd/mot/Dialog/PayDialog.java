package com.bRd.mot.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bRd.mot.Entity.HomeItem;
import com.bRd.mot.Helper.ErrorProvider;
import com.bRd.mot.Helper.PayDialogListener;
import com.bRd.mot.R;
import com.bRd.mot.Utils.Utility;

public class PayDialog extends Dialog {

    private final HomeItem homeItem;
    private final PayDialogListener payDialogListener;

    TextView title_tv;
    EditText sum_et;
    Button ok_btn, cancel_btn;

    public PayDialog(@NonNull Context context, HomeItem homeItem, PayDialogListener payDialogListener) {
        super(context);
        this.homeItem = homeItem;
        this.payDialogListener = payDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_home_payment);

        title_tv = findViewById(R.id.title_tv);
        sum_et = findViewById(R.id.sum_et);
        ok_btn = findViewById(R.id.ok_btn);
        cancel_btn = findViewById(R.id.cancel_btn);

        ok_btn.setOnClickListener(onClick);
        cancel_btn.setOnClickListener(onClick);

        if (homeItem.isPaid()) {
            sum_et.setText(Utility.formatDoubleToString(homeItem.getSum()));
            sum_et.selectAll();
        }
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.backButton) {
                dismiss();
            } else if (view.getId() == R.id.ok_btn) {

                if (sum_et.getText().length() == 0) {

                    ErrorProvider.setError(sum_et, "Въведете сума");

                }

                payDialogListener.onAccept(Utility.parseStringToDouble(sum_et.getText().toString()));
            }
        }
    };
}
