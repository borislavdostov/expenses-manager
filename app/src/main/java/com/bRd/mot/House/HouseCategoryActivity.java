package com.bRd.mot.House;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bRd.mot.Entity.HomeItem;
import com.bRd.mot.Entity.HouseCategory;
import com.bRd.mot.Helper.CategoryClickListener;
import com.bRd.mot.R;
import com.bRd.mot.Utils.DatabaseHelper;

import java.util.ArrayList;

public class HouseCategoryActivity extends AppCompatActivity {

    RecyclerView category_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_category);

        category_rv = findViewById(R.id.category_rv);

        DatabaseHelper db = new DatabaseHelper(this);
        ArrayList<HouseCategory> houseCategories = db.getHouseCategories();
        HouseCategoryAdapter houseCategoryAdapter =
                new HouseCategoryAdapter(houseCategories, () -> goToActivity(HomeItem.class));

        category_rv.setAdapter(houseCategoryAdapter);
        category_rv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void goToActivity(Class<?> activity){
        Intent intent = new Intent(HouseCategoryActivity.this, activity);
        startActivity(intent);
    }
}