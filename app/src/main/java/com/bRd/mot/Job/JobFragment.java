package com.bRd.mot.Job;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bRd.mot.Entity.JobDay;
import com.bRd.mot.Helper.DateHelper;
import com.bRd.mot.R;
import com.bRd.mot.Helper.DatabaseHelper;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

public class JobFragment extends Fragment {

    private Context context;
    private DatabaseHelper dbHelper;

    private Button backButton;
    private CalendarPickerView calendarPickerView;

    private OnFragmentInteractionListener activityCommander;

    public JobFragment() {
    }

    public static JobFragment newInstance() {
        JobFragment fragment = new JobFragment();
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
        View view = inflater.inflate(R.layout.fragment_job, container, false);

        dbHelper = new DatabaseHelper(getContext());

        backButton = view.findViewById(R.id.backButton);
        calendarPickerView = view.findViewById(R.id.calendarPickerView);

        backButton.setOnClickListener(onClickListener);

        ////////////////////////////////////////////////////////////////////////

        calendarPickerView.init(DateHelper.getFirstDayOfCurrentYear(), DateHelper.getLastDayOfCurrentYear())
                .withSelectedDate(Calendar.getInstance().getTime());

        calendarPickerView.highlightDates(dbHelper.getJobDayDateList());

        calendarPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {

            Date oldDate;

            @Override
            public void onDateSelected(final Date date) {

                JobDay jobDay = dbHelper.getJobDayByDate(date);

                if (oldDate == date) {
                    if (jobDay == null) {

                        String dateDay = DateFormat.format("EEEE", date).toString();

                        if (dateDay.equals(getString(R.string.saturday)) || dateDay.equals(getString(R.string.sunday))) {
                            return;
                        }

                        new AlertDialog.Builder(context)
                                .setTitle("dobavqne")
                                .setMessage("zapisvane na denq")
                                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        JobDay jobDay = new JobDay();
                                        jobDay.setDate(date);
                                        jobDay.setVisited(true);
                                        dbHelper.insertJobDay(jobDay);
                                        calendarPickerView.highlightDates(dbHelper.getJobDayDateList());
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } else {
                        Toast.makeText(context, "Pokaji info", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onDateUnselected(Date date) {
                oldDate = date;
            }
        });
        ////////////////////////////////////////////////////////////////////////

        resizeButtonDrawable();

        return view;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            activityCommander.popBackStack();
        }
    };

    private void resizeButtonDrawable() {
        Drawable drawable = getResources().getDrawable(R.drawable.left_arrow_white);
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * 0.4),
                (int) (drawable.getIntrinsicHeight() * 0.4));
        ScaleDrawable sd = new ScaleDrawable(drawable, 0, 24, 24);
        backButton.setCompoundDrawables(sd.getDrawable(), null, null, null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            activityCommander = (OnFragmentInteractionListener) context;
            this.context = context;
        } else
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
