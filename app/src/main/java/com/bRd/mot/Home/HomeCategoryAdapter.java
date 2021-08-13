package com.bRd.mot.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bRd.mot.Entity.HomeCategory;
import com.bRd.mot.Helper.CategoryClickListener;
import com.bRd.mot.R;

import java.util.ArrayList;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {

    ArrayList<HomeCategory> houseCategories;
    private final CategoryClickListener categoryClickListener;

    public HomeCategoryAdapter(ArrayList<HomeCategory> houseCategories,
                               CategoryClickListener categoryClickListener) {
        this.houseCategories = houseCategories;
        this.categoryClickListener = categoryClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_home_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeCategory homeCategory = houseCategories.get(position);

        holder.name_tv.setText(homeCategory.getName());

        holder.itemView.setOnClickListener(view -> categoryClickListener.onCategoryClick(homeCategory));
    }

    @Override
    public int getItemCount() {
        return houseCategories.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.name_tv);
        }
    }
}
