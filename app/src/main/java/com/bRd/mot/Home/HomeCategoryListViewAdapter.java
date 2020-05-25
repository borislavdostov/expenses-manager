package com.bRd.mot.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bRd.mot.Entity.HomeCategory;
import com.bRd.mot.R;

import java.util.ArrayList;

public class HomeCategoryListViewAdapter extends BaseAdapter {

    private Context context;
    private HomeCategoryFragment homeCategoryFragment;
    private ArrayList<HomeCategory> homeCategoryList;

    HomeCategoryListViewAdapter(HomeCategoryFragment homeCategoryFragment, ArrayList<HomeCategory> homeCategoryList) {
        this.homeCategoryFragment = homeCategoryFragment;
        this.context = homeCategoryFragment.getContext();
        this.homeCategoryList = homeCategoryList;
    }

    @Override
    public int getCount() {
        return homeCategoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return homeCategoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return homeCategoryList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        HomeCategory homeCategory = homeCategoryList.get(i);

        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null)
            view = layoutInflater.inflate(R.layout.list_item_home_category, null);

        TextView nameTextView = view.findViewById(R.id.nameTextView);
        nameTextView.setText(homeCategory.getName());

        return view;
    }
}
