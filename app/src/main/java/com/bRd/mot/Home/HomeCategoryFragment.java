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

import com.bRd.mot.Entity.HouseCategory;
import com.bRd.mot.R;
import com.bRd.mot.Utils.DatabaseHelper;

import java.util.ArrayList;

public class HomeCategoryFragment extends Fragment {

    private DatabaseHelper dbHelper;

    private Button backButton;
    private ListView listView;

    private ArrayList<HouseCategory> houseCategoryList;
    private HomeCategoryListViewAdapter homeCategoryListViewAdapter;

    private HouseCategory houseCategory;

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

        houseCategoryList = dbHelper.getHouseCategories();

        if (houseCategoryList.size() == 0) {
            insertHomeCategories();
            houseCategoryList = dbHelper.getHouseCategories();
        }

        homeCategoryListViewAdapter = new HomeCategoryListViewAdapter(this, houseCategoryList);
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

            houseCategory = (HouseCategory) adapterView.getItemAtPosition(i);
            activityCommander.enterToHomeItemFragment(houseCategory);
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
        HouseCategory houseCategory1 = new HouseCategory("Наем");
        HouseCategory houseCategory2 = new HouseCategory("Ток");
        HouseCategory houseCategory3 = new HouseCategory("Вода");
        HouseCategory houseCategory4 = new HouseCategory("Интернет и ТВ");
        HouseCategory houseCategory5 = new HouseCategory("Телефон");
        dbHelper.insertHomeCategory(houseCategory1);
        dbHelper.insertHomeCategory(houseCategory2);
        dbHelper.insertHomeCategory(houseCategory3);
        dbHelper.insertHomeCategory(houseCategory4);
        dbHelper.insertHomeCategory(houseCategory5);
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

        void enterToHomeItemFragment(HouseCategory houseCategory);
    }
}
