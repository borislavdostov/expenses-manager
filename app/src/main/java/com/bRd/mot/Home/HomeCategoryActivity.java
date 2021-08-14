package com.bRd.mot.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.bRd.mot.Entity.HomeItem;
import com.bRd.mot.Entity.HomeCategory;
import com.bRd.mot.R;
import com.bRd.mot.Helper.DatabaseHelper;

import java.util.ArrayList;

public class HomeCategoryActivity extends AppCompatActivity {

    RecyclerView category_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_category);

        category_rv = findViewById(R.id.category_rv);

        DatabaseHelper db = new DatabaseHelper(this);
        ArrayList<HomeCategory> houseCategories = db.getHouseCategories();
        HomeCategoryAdapter homeCategoryAdapter =
                new HomeCategoryAdapter(houseCategories, homeCategory -> {
//                    Intent intent = new Intent(HomeCategoryActivity.this, HomeItem.class);
//                    startActivity(intent);
                });

        category_rv.setAdapter(homeCategoryAdapter);
        category_rv.setLayoutManager(new LinearLayoutManager(this));
    }
}