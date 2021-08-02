package com.bRd.mot.Car;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bRd.mot.Dialog.DatePickerDialog;
import com.bRd.mot.Entity.CarCategory;
import com.bRd.mot.Helper.DatePickerListener;
import com.bRd.mot.R;
import com.bRd.mot.Utils.DatabaseHelper;
import com.bRd.mot.Utils.Utility;

import java.util.ArrayList;
import java.util.Date;

public class CarCategoryFragment extends Fragment {

    private Activity activity;
    private Context context;
    private DatabaseHelper dbHelper;

    private Button backButton;
    private ListView listView;

    private ArrayList<CarCategory> carCategoryList;
    private CarCategoryListViewAdapter carCategoryListViewAdapter;

    private CarCategory carCategory;

    private OnFragmentInteractionListener activityCommander;

    public CarCategoryFragment() {
    }

    public static CarCategoryFragment newInstance() {
        CarCategoryFragment fragment = new CarCategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_category, container, false);

        activity = getActivity();
        context = getContext();
        dbHelper = new DatabaseHelper(getContext());

        backButton = view.findViewById(R.id.backButton);
        listView = view.findViewById(R.id.listView);

        backButton.setOnClickListener(onClickListener);
        listView.setOnItemLongClickListener(onItemLongClickListener);

        resizeButtonDrawable();

        carCategoryList = dbHelper.getCarCategoryList();

        if (carCategoryList.size() == 0) {
            insertCarCategories();
            carCategoryList = dbHelper.getCarCategoryList();
        }

        carCategoryListViewAdapter = new CarCategoryListViewAdapter(this, carCategoryList);
        listView.setAdapter(carCategoryListViewAdapter);

        return view;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            activityCommander.popBackStack();

        }
    };

    private AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            // TODO: 9.7.2019 г.
            carCategory = (CarCategory) adapterView.getItemAtPosition(i);
            showDialog(context, carCategory);

            return false;
        }
    };

    //showDialog
    private void showDialog(final Context context, final CarCategory carCategory) {

        View view = getLayoutInflater().inflate(R.layout.dialog_car_payment, null);
        TextView titleTextView = view.findViewById(R.id.titleTextView);
        final EditText paidDateEditText = view.findViewById(R.id.paidDateEditText);
        final EditText deadlineDateEditText = view.findViewById(R.id.deadlineDateEditText);
        final EditText sumEditText = view.findViewById(R.id.sumEditText);
        Button okButton = view.findViewById(R.id.ok_btn);
        Button cancelButton = view.findViewById(R.id.cancel_btn);

        titleTextView.setText(context.getString(R.string.car_payment_text, carCategory.getName()));

        final Dialog dialog = new Dialog(context, R.style.AppTheme);
        dialog.setContentView(view);

        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getId() == R.id.ok_btn) {

                    if (paidDateEditText.getText().length() == 0) {

                        Utility.setEditTextError(paidDateEditText, "Въведете дата на плащане");

                    } else if (deadlineDateEditText.getText().length() == 0) {

                        Utility.setEditTextError(deadlineDateEditText, "Въведете крайна дата");

                    } else if (sumEditText.getText().length() == 0) {

                        Utility.setEditTextError(sumEditText, "Въведете сума");

                    } else {

                        carCategory.setPaidDate(Utility.parseStringToDate(paidDateEditText.getText().toString()));
                        carCategory.setDeadlineDate(Utility.parseStringToDate(deadlineDateEditText.getText().toString()));
                        carCategory.setSum(Utility.parseStringToDouble(sumEditText.getText().toString()));
                        carCategory.setPaid(true);
                        dbHelper.editCarCategory(carCategory);

                        carCategoryListViewAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                        hideKeyboard(activity);
                    }

                } else if (view.getId() == R.id.cancel_btn) {

                    dialog.dismiss();
                    hideKeyboard(activity);
                }
            }
        };

        okButton.setOnClickListener(onClick);
        cancelButton.setOnClickListener(onClick);

        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (paidDateEditText.isFocused()){
                    new DatePickerDialog(context, new DatePickerListener() {
                        @Override
                        public void onDateSelected(Date date) {
                            // TODO: 31.7.2021 г. Implement logic
                            Toast.makeText(context, "Implement logic", Toast.LENGTH_SHORT).show();
                        }
                    });
//                    Utility.showDatePickerDialog(context, paidDateEditText);
                }
                else if (deadlineDateEditText.isFocused()){
                    new DatePickerDialog(context, new DatePickerListener() {
                        @Override
                        public void onDateSelected(Date date) {
                            // TODO: 31.7.2021 г. Implement logic
                            Toast.makeText(context, "Implement logic", Toast.LENGTH_SHORT).show();
                        }
                    });
//                    Utility.showDatePickerDialog(context, deadlineDateEditText);
                }
            }
        };

        paidDateEditText.setOnFocusChangeListener(onFocusChangeListener);
        deadlineDateEditText.setOnFocusChangeListener(onFocusChangeListener);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(600, 600);
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.dimAmount = 0.7f;
            lp.y = -140;
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    //hideKeyboard
    private void hideKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    //resizeButtonDrawable
    private void resizeButtonDrawable() {
        Drawable drawable = getResources().getDrawable(R.drawable.left_arrow_white);
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * 0.4),
                (int) (drawable.getIntrinsicHeight() * 0.4));
        ScaleDrawable sd = new ScaleDrawable(drawable, 0, 24, 24);
        backButton.setCompoundDrawables(sd.getDrawable(), null, null, null);
    }

    //insertCarCategories
    private void insertCarCategories() {
        CarCategory carCategory1 = new CarCategory("Данък");
        CarCategory carCategory2 = new CarCategory("Застраховка");
        CarCategory carCategory3 = new CarCategory("Винетка");
        CarCategory carCategory4 = new CarCategory("Преглед");
        dbHelper.insertCarCategory(carCategory1);
        dbHelper.insertCarCategory(carCategory2);
        dbHelper.insertCarCategory(carCategory3);
        dbHelper.insertCarCategory(carCategory4);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            activityCommander = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activityCommander = null;
    }

    public interface OnFragmentInteractionListener {

        void popBackStack();
    }
}
