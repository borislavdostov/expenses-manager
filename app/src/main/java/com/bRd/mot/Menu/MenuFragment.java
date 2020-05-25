package com.bRd.mot.Menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bRd.mot.R;
import com.bRd.mot.Utils.Utility;
import com.vstechlab.easyfonts.EasyFonts;

public class MenuFragment extends Fragment {

    private Context context;

    private Button homeButton;
    private Button carButton;
    private Button jobButton;
    private Button settingsButton;

    private OnFragmentInteractionListener activityCommander;

    public MenuFragment() {
    }

    public static MenuFragment newInstance() {
        MenuFragment fragment = new MenuFragment();
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
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        homeButton = view.findViewById(R.id.homeButton);
        carButton = view.findViewById(R.id.carButton);
        jobButton = view.findViewById(R.id.jobButton);
        settingsButton = view.findViewById(R.id.settingsButton);

        Utility.setTypeFace(context, homeButton);
        Utility.setTypeFace(context, carButton);
        Utility.setTypeFace(context, jobButton);
        Utility.setTypeFace(context, settingsButton);

        homeButton.setOnClickListener(onClickListener);
        carButton.setOnClickListener(onClickListener);
        jobButton.setOnClickListener(onClickListener);
        settingsButton.setOnClickListener(onClickListener);

        resizeButtonDrawable(homeButton, R.drawable.home_orange, R.drawable.right_arrow_orange);
        resizeButtonDrawable(carButton, R.drawable.car_blue, R.drawable.right_arrow_blue);
        resizeButtonDrawable(jobButton, R.drawable.job_green, R.drawable.right_arrow_green);
        resizeButtonDrawable(settingsButton, R.drawable.settings_red, R.drawable.right_arrow_red);

        return view;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.homeButton)
                activityCommander.enterToHomeCategoryFragment();
            else if (view.getId() == R.id.carButton)
                activityCommander.enterToCarCategoryFragment();
            else if (view.getId() == R.id.jobButton)
                activityCommander.enterToJobFragment();
            else if (view.getId() == R.id.settingsButton)
                activityCommander.enterToSettingsFragment();
        }
    };

    private void resizeButtonDrawable(Button button, int startDrawable, int endDrawable) {
        Drawable drawable1 = getResources().getDrawable(endDrawable);
        Drawable drawable2 = getResources().getDrawable(startDrawable);
        drawable1.setBounds(0, 0, (int) (drawable1.getIntrinsicWidth() * 0.4),
                (int) (drawable1.getIntrinsicHeight() * 0.4));
        ScaleDrawable sd1 = new ScaleDrawable(drawable1, 0, 24, 24);
        drawable2.setBounds(0, 0, (int) (drawable2.getIntrinsicWidth() * 0.2),
                (int) (drawable2.getIntrinsicHeight() * 0.2));
        ScaleDrawable sd2 = new ScaleDrawable(drawable2, 0, 24, 24);
        button.setCompoundDrawables(sd2.getDrawable(), null, sd1.getDrawable(), null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            activityCommander = (OnFragmentInteractionListener) context;
            this.context = context;
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

        void enterToHomeCategoryFragment();

        void enterToCarCategoryFragment();

        void enterToJobFragment();

        void enterToSettingsFragment();
    }
}
