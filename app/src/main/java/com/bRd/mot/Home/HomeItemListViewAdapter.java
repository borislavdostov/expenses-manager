package com.bRd.mot.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bRd.mot.Entity.HomeItem;
import com.bRd.mot.R;
import com.bRd.mot.Utils.Utility;

import java.util.ArrayList;

public class HomeItemListViewAdapter extends BaseAdapter {

    private Context context;
    private HomeItemFragment homeItemFragment;
    private ArrayList<HomeItem> homeItemList;

    HomeItemListViewAdapter(HomeItemFragment homeItemFragment, ArrayList<HomeItem> homeItemList) {
        this.homeItemFragment = homeItemFragment;
        this.context = homeItemFragment.getContext();
        this.homeItemList = homeItemList;
    }

    @Override
    public int getCount() {
        return homeItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return homeItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return homeItemList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        HomeItem homeItem = homeItemList.get(i);

        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null)
            view = layoutInflater.inflate(R.layout.list_item_home_item, null);

        TextView monthTextView = view.findViewById(R.id.monthTextView);
        monthTextView.setText(homeItem.getMonth());

        TextView paidDateTextView = view.findViewById(R.id.paidDateTextView);
        ImageView failureImageView = view.findViewById(R.id.failureImageView);
        ImageView successImageView = view.findViewById(R.id.successImageView);
        TextView sumTextView = view.findViewById(R.id.sumTextView);

        if (homeItem.isPaid()) {
            paidDateTextView.setText(Utility.formatDateToString(homeItem.getPaidDate()));
            failureImageView.setVisibility(View.GONE);
            successImageView.setVisibility(View.VISIBLE);
            sumTextView.setVisibility(View.VISIBLE);

            String sum = homeItem.getSum() == 0.00 ? "0,00" : Utility.formatDoubleToString(homeItem.getSum());
            sumTextView.setText(context.getString(R.string.placeholder_2, sum, " лв."));
        } else {
            paidDateTextView.setText("Неплатено");
            failureImageView.setVisibility(View.VISIBLE);
            successImageView.setVisibility(View.GONE);
            sumTextView.setVisibility(View.GONE);
        }

        return view;
    }
}
