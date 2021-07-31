package com.bRd.mot.House;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bRd.mot.Entity.HouseCategory;
import com.bRd.mot.Helper.CategoryClickListener;
import com.bRd.mot.R;

import java.util.ArrayList;

public class HouseCategoryAdapter extends RecyclerView.Adapter<HouseCategoryAdapter.ViewHolder> {

    ArrayList<HouseCategory> houseCategories;
    private CategoryClickListener categoryClickListener;

    public HouseCategoryAdapter(ArrayList<HouseCategory> houseCategories,
                                CategoryClickListener categoryClickListener) {
        this.houseCategories = houseCategories;
        this.categoryClickListener = categoryClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_house_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HouseCategory houseCategory = houseCategories.get(position);

        holder.name_tv.setText(houseCategory.getName());

        holder.itemView.setOnClickListener(view -> categoryClickListener.onCategoryClick());
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
