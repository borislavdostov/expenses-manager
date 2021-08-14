package com.bRd.mot.Helper;

import com.bRd.mot.Entity.HomeCategory;

public class DatabaseSeeder {

    private final DatabaseHelper db;

    public DatabaseSeeder(DatabaseHelper db){

        this.db = db;
    }

    public void seed() {

        if (db.isHomeCategoryTableEmpty()){
            HomeCategory homeCategory1 = new HomeCategory("Наем");
            HomeCategory homeCategory2 = new HomeCategory("Ток");
            HomeCategory homeCategory3 = new HomeCategory("Вода");
            HomeCategory homeCategory4 = new HomeCategory("Интернет и ТВ");
            HomeCategory homeCategory5 = new HomeCategory("Телефон");
            db.insertHomeCategory(homeCategory1);
            db.insertHomeCategory(homeCategory2);
            db.insertHomeCategory(homeCategory3);
            db.insertHomeCategory(homeCategory4);
            db.insertHomeCategory(homeCategory5);
        }

    }
}
