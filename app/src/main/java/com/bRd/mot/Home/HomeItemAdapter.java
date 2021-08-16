package com.bRd.mot.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bRd.mot.Entity.HomeItem;
import com.bRd.mot.Helper.DateHelper;
import com.bRd.mot.R;
import com.bRd.mot.Utils.Utility;

import java.util.ArrayList;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.ViewHolder> {

    Context context;
    ArrayList<HomeItem> homeItems;
    private final HomeItemListener homeItemListener;

    public HomeItemAdapter(Context context, ArrayList<HomeItem> homeItems, HomeItemListener homeItemListener) {
        this.context = context;
        this.homeItems = homeItems;
        this.homeItemListener = homeItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeItem homeItem = homeItems.get(position);

        holder.month_tv.setText(homeItem.getMonth());

        if (homeItem.isPaid()) {
            holder.paid_date_tv.setText(DateHelper.formatDateToString(homeItem.getPaidDate()));
            holder.error_iv.setVisibility(View.GONE);
            holder.success_iv.setVisibility(View.VISIBLE);
            holder.sum_tv.setVisibility(View.VISIBLE);

            String sum = homeItem.getSum() == 0.00 ? "0,00" : Utility.formatDoubleToString(homeItem.getSum());
            holder.sum_tv.setText(context.getString(R.string.placeholder_2, sum, " лв."));
        } else {
            holder.paid_date_tv.setText("Неплатено");
            holder.error_iv.setVisibility(View.VISIBLE);
            holder.success_iv.setVisibility(View.GONE);
            holder.sum_tv.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(view -> homeItemListener.onHomeItemClick(homeItem));
        holder.itemView.setOnLongClickListener(view -> {
            homeItemListener.onHomeItemLongClick(homeItem);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return homeItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView month_tv, paid_date_tv, sum_tv;
        ImageView error_iv, success_iv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            month_tv = itemView.findViewById(R.id.month_tv);
            paid_date_tv = itemView.findViewById(R.id.paid_date_tv);
            sum_tv = itemView.findViewById(R.id.sum_tv);
            error_iv = itemView.findViewById(R.id.error_iv);
            success_iv = itemView.findViewById(R.id.success_iv);
        }
    }
}
