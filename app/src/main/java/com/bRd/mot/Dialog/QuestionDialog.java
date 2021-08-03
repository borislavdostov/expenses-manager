package com.bRd.mot.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bRd.mot.Helper.DialogListener;
import com.bRd.mot.R;

public class QuestionDialog extends Dialog {

    private final String message;
    private final DialogListener dialogListener;

    TextView message_tv;
    Button ok_btn, cancel_btn;

    public QuestionDialog(@NonNull Context context, String message, DialogListener dialogListener) {
        super(context);
        this.message = message;
        this.dialogListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_question);
        setCancelable(false);

        message_tv = findViewById(R.id.message_tv);
        ok_btn = findViewById(R.id.ok_btn);
        cancel_btn = findViewById(R.id.cancel_btn);

        ok_btn.setOnClickListener(onClick);
        cancel_btn.setOnClickListener(onClick);

        message_tv.setText(message);
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.backButton) {
                dismiss();
            } else if (view.getId() == R.id.ok_btn) {
                dialogListener.onAccept();
            }
        }
    };
}
