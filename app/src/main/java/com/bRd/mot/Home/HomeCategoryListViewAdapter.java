package com.bRd.mot.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bRd.mot.Entity.HouseCategory;
import com.bRd.mot.R;

import java.util.ArrayList;

public class HomeCategoryListViewAdapter extends BaseAdapter {

    private Context context;
    private HomeCategoryFragment homeCategoryFragment;
    private ArrayList<HouseCategory> houseCategoryList;

    HomeCategoryListViewAdapter(HomeCategoryFragment homeCategoryFragment, ArrayList<HouseCategory> houseCategoryList) {
        this.homeCategoryFragment = homeCategoryFragment;
        this.context = homeCategoryFragment.getContext();
        this.houseCategoryList = houseCategoryList;
    }

    @Override
    public int getCount() {
        return houseCategoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return houseCategoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return houseCategoryList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        HouseCategory houseCategory = houseCategoryList.get(i);

        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null)
            view = layoutInflater.inflate(R.layout.list_item_house_category, null);

        TextView nameTextView = view.findViewById(R.id.name_tv);
        nameTextView.setText(houseCategory.getName());

        return view;
    }
}
