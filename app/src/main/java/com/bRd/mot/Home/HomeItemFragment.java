package com.bRd.mot.Home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bRd.mot.Dialog.QuestionDialog;
import com.bRd.mot.Entity.HouseCategory;
import com.bRd.mot.Entity.HomeItem;
import com.bRd.mot.Helper.DialogListener;
import com.bRd.mot.R;
import com.bRd.mot.Utils.DatabaseHelper;
import com.bRd.mot.Utils.Utility;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeItemFragment extends Fragment {

    private Activity activity;
    private Context context;
    private DatabaseHelper dbHelper;

    private Button backButton;
    private TextView titleTextView;
    private ListView listView;

    private ArrayList<HomeItem> homeItemList;
    private HomeItemListViewAdapter homeItemListViewAdapter;

    public HouseCategory houseCategory;

    private OnFragmentInteractionListener activityCommander;

    public HomeItemFragment() {
    }

    public static HomeItemFragment newInstance() {
        HomeItemFragment fragment = new HomeItemFragment();
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
        View view = inflater.inflate(R.layout.fragment_home_item, container, false);

        activity = getActivity();
        context = getContext();
        dbHelper = new DatabaseHelper(getContext());

        backButton = view.findViewById(R.id.backButton);
        titleTextView = view.findViewById(R.id.titleTextView);
        listView = view.findViewById(R.id.listView);

        backButton.setOnClickListener(onClickListener);
        listView.setOnItemClickListener(onItemClickListener);
        listView.setOnItemLongClickListener(onItemLongClickListener);

        titleTextView.setText(houseCategory.getName());

        resizeButtonDrawable();

        homeItemList = dbHelper.getHomeItemList(houseCategory.getId());
        if (homeItemList.size() == 0) {
            for (int i = 1; i <= 12; i++) {
                HomeItem homeItem = new HomeItem(houseCategory.getId(), Utility.getMonthToBulgarian(i));
                dbHelper.insertHomeItem(homeItem);
            }
            homeItemList = dbHelper.getHomeItemList(houseCategory.getId());
        }

        homeItemListViewAdapter = new HomeItemListViewAdapter(this, homeItemList);
        listView.setAdapter(homeItemListViewAdapter);

        return view;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.backButton) {
                activityCommander.popBackStack();
            }
        }
    };

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            HomeItem homeItem = (HomeItem) adapterView.getItemAtPosition(i);
            showDialog(context, homeItem);
        }
    };

    private AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            HomeItem homeItem = (HomeItem) adapterView.getItemAtPosition(i);
            if (homeItem.isPaid()) {
                new QuestionDialog(context, "Наистина ли искате да отбележите тази сметка като неплатена?", () -> {
                    homeItem.setPaid(false);
                    homeItem.setPaidDate(null);
                    homeItem.setSum(0.00);
                    dbHelper.editHomeItem(homeItem);
                    homeItemListViewAdapter.notifyDataSetChanged();
                });
            }

            return true;
        }
    };

    //resizeButtonDrawable
    private void resizeButtonDrawable() {
        Drawable drawable = getResources().getDrawable(R.drawable.left_arrow_white);
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * 0.4),
                (int) (drawable.getIntrinsicHeight() * 0.4));
        ScaleDrawable sd = new ScaleDrawable(drawable, 0, 24, 24);
        backButton.setCompoundDrawables(sd.getDrawable(), null, null, null);
    }

    //showDialog
    private void showDialog(final Context context, final HomeItem homeItem) {

        View view = getLayoutInflater().inflate(R.layout.dialog_home_payment, null);
        final ConstraintLayout editTextLayout = view.findViewById(R.id.editTextLayout);
        TextView titleTextView = view.findViewById(R.id.titleTextView);
        TextView sumTextView = view.findViewById(R.id.sumTextView);
        final EditText sumEditText = view.findViewById(R.id.sumEditText);
        Button okButton = view.findViewById(R.id.ok_btn);
        Button cancelButton = view.findViewById(R.id.cancel_btn);

        titleTextView.setText(context.getString(R.string.home_payment_text, houseCategory.getName().toLowerCase(), homeItem.getMonth().toLowerCase()));

        sumEditText.requestFocus();
        final InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && editTextLayout.getVisibility() == View.VISIBLE)
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        if (homeItem.isPaid()) {
            sumEditText.setText(Utility.formatDoubleToString(homeItem.getSum()));
            sumEditText.selectAll();
        }

        final Dialog dialog = new Dialog(context, R.style.AppTheme);
        dialog.setContentView(view);

        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getId() == R.id.ok_btn) {

                    if (sumEditText.getText().length() == 0) {

                        Utility.setEditTextError(sumEditText, "Въведете сума");

                    } else {

                        homeItem.setSum(Utility.parseStringToDouble(sumEditText.getText().toString()));
                        homeItem.setPaid(true);
                        homeItem.setPaidDate(Calendar.getInstance().getTime());
                        dbHelper.editHomeItem(homeItem);

                        homeItemListViewAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                        Utility.hideKeyboard(activity);
                    }

                } else if (view.getId() == R.id.cancel_btn) {

                    dialog.dismiss();
                    Utility.hideKeyboard(activity);

                } else if (view.getId() == R.id.sumTextView) {

                    sumEditText.requestFocus();
                    if (inputMethodManager != null)
                        inputMethodManager.showSoftInput(sumEditText, InputMethodManager.SHOW_IMPLICIT);

                }
            }
        };

        okButton.setOnClickListener(onClick);
        cancelButton.setOnClickListener(onClick);
        sumTextView.setOnClickListener(onClick);

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
