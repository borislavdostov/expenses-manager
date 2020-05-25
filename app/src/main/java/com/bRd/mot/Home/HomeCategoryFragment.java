package com.bRd.mot.Home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.bRd.mot.Entity.HomeCategory;
import com.bRd.mot.R;
import com.bRd.mot.Utils.DatabaseHelper;
import com.bRd.mot.Utils.Utility;

import java.util.ArrayList;

public class HomeCategoryFragment extends Fragment {

    private DatabaseHelper dbHelper;

    private Button backButton;
    private ListView listView;

    private ArrayList<HomeCategory> homeCategoryList;
    private HomeCategoryListViewAdapter homeCategoryListViewAdapter;

    private HomeCategory homeCategory;

    private OnFragmentInteractionListener activityCommander;

    public HomeCategoryFragment() {
    }

    public static HomeCategoryFragment newInstance() {
        HomeCategoryFragment fragment = new HomeCategoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_home_category, container, false);

        dbHelper = new DatabaseHelper(getContext());

        backButton = view.findViewById(R.id.backButton);
        listView = view.findViewById(R.id.listView);

        backButton.setOnClickListener(onClickListener);
        listView.setOnItemClickListener(onItemClickListener);

        resizeButtonDrawable();

        homeCategoryList = dbHelper.getHomeCategoryList();

        if (homeCategoryList.size() == 0) {
            insertHomeCategories();
            homeCategoryList = dbHelper.getHomeCategoryList();
        }

        homeCategoryListViewAdapter = new HomeCategoryListViewAdapter(this, homeCategoryList);
        listView.setAdapter(homeCategoryListViewAdapter);

        return view;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            activityCommander.popBackStack();
        }
    };

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            homeCategory = (HomeCategory) adapterView.getItemAtPosition(i);
            activityCommander.enterToHomeItemFragment(homeCategory);
        }
    };

    private void resizeButtonDrawable() {
        Drawable drawable = getResources().getDrawable(R.drawable.left_arrow_white);
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * 0.4),
                (int) (drawable.getIntrinsicHeight() * 0.4));
        ScaleDrawable sd = new ScaleDrawable(drawable, 0, 24, 24);
        backButton.setCompoundDrawables(sd.getDrawable(), null, null, null);
    }

    //insertHomeCategories
    private void insertHomeCategories() {
        HomeCategory homeCategory1 = new HomeCategory("Наем");
        HomeCategory homeCategory2 = new HomeCategory("Ток");
        HomeCategory homeCategory3 = new HomeCategory("Вода");
        HomeCategory homeCategory4 = new HomeCategory("Интернет и ТВ");
        HomeCategory homeCategory5 = new HomeCategory("Телефон");
        dbHelper.insertHomeCategory(homeCategory1);
        dbHelper.insertHomeCategory(homeCategory2);
        dbHelper.insertHomeCategory(homeCategory3);
        dbHelper.insertHomeCategory(homeCategory4);
        dbHelper.insertHomeCategory(homeCategory5);
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

        void enterToHomeItemFragment(HomeCategory homeCategory);
    }
}
