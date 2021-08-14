package com.bRd.mot.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ParseException;
import android.util.Log;

import com.bRd.mot.Entity.CarCategory;
import com.bRd.mot.Entity.HomeCategory;
import com.bRd.mot.Entity.HomeItem;
import com.bRd.mot.Entity.JobDay;
import com.bRd.mot.Helper.DateHelper;

import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "mot.db";
    private SQLiteDatabase db;

    //TABLE_HOME_CATEGORY
    private static final String TABLE_HOME_CATEGORY = "HomeCategory";

    private static final String HOME_CATEGORY_ID = "Id";
    private static final String HOME_CATEGORY_NAME = "Name";
    private static final String HOME_CATEGORY_CAME_OUT_DAY = "CameOutDay";
    private static final String HOME_CATEGORY_DEADLINE_DAY = "DeadlineDay";
    private static final String HOME_CATEGORY_HAS_CONSTANT_PRICE = "hasConstantPrice";
    private static final String HOME_CATEGORY_CONSTANT_PRICE = "ConstantPrice";

    private static final String CREATE_TABLE_HOME_CATEGORY =
            "CREATE TABLE " + TABLE_HOME_CATEGORY +
                    "('" + HOME_CATEGORY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT,'" +
                    HOME_CATEGORY_CAME_OUT_DAY + "' INTEGER,'" +
                    HOME_CATEGORY_DEADLINE_DAY + "' INTEGER,'" +
                    HOME_CATEGORY_NAME + "' TEXT,'" +
                    HOME_CATEGORY_HAS_CONSTANT_PRICE + "' BOOLEAN,'" +
                    HOME_CATEGORY_CONSTANT_PRICE + "' NUMERIC(10,2))";

    //TABLE_HOME_ITEM
    private static final String TABLE_HOME_ITEM = "HomeItem";

    private static final String HOME_ITEM_ID = "Id";
    private static final String HOME_ITEM_HOME_CATEGORY_ID = "HomeCategoryId";
    private static final String HOME_ITEM_MONTH = "Month";
    private static final String HOME_ITEM_PAID_DATE = "PaidDate";
    private static final String HOME_ITEM_IS_PAID = "IsPaid";
    private static final String HOME_ITEM_SUM = "Sum";
    private static final String HOME_ITEM_NOTE = "Note";

    private static final String CREATE_TABLE_HOME_ITEM =
            "CREATE TABLE " + TABLE_HOME_ITEM +
                    "('" + HOME_ITEM_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT,'" +
                    HOME_ITEM_HOME_CATEGORY_ID + "' INTEGER NOT NULL,'" +
                    HOME_ITEM_MONTH + "' TEXT NOT NULL,'" +
                    HOME_ITEM_PAID_DATE + "' DATETIME,'" +
                    HOME_ITEM_IS_PAID + "' BOOLEAN DEFAULT 0,'" +
                    HOME_ITEM_SUM + "' NUMERIC(10,2),'" +
                    HOME_ITEM_NOTE + "' TEXT," +
                    "FOREIGN KEY(" + HOME_ITEM_HOME_CATEGORY_ID + ") REFERENCES " + TABLE_HOME_CATEGORY + "(" + HOME_CATEGORY_ID + "))";

    //TABLE_CAR_CATEGORY
    private static final String TABLE_CAR_CATEGORY = "CarCategory";

    private static final String CAR_CATEGORY_ID = "Id";
    private static final String CAR_CATEGORY_NAME = "Name";
    private static final String CAR_CATEGORY_PAID_DATE = "PaidDate";
    private static final String CAR_CATEGORY_DEADLINE_DATE = "DeadlineDate";
    private static final String CAR_CATEGORY_IS_PAID = "IsPaid";
    private static final String CAR_CATEGORY_SUM = "Sum";
    private static final String CAR_CATEGORY_CONSTANT_PRICE = "ConstantPrice";
    private static final String CAR_CATEGORY_NOTE = "Note";

    private static final String CREATE_TABLE_CAR_CATEGORY =
            "CREATE TABLE " + TABLE_CAR_CATEGORY +
                    "('" + CAR_CATEGORY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT,'" +
                    CAR_CATEGORY_NAME + "' TEXT,'" +
                    CAR_CATEGORY_PAID_DATE + "' DATETIME,'" +
                    CAR_CATEGORY_DEADLINE_DATE + "' DATETIME,'" +
                    CAR_CATEGORY_IS_PAID + "' BOOLEAN DEFAULT 0,'" +
                    CAR_CATEGORY_SUM + "' NUMERIC(10,2),'" +
                    CAR_CATEGORY_CONSTANT_PRICE + "' NUMERIC(10,2),'" +
                    CAR_CATEGORY_NOTE + "' TEXT)";

    //TABLE_JOB_DAY
    private static final String TABLE_JOB_DAY = "JobDay";

    private static final String JOB_DAY_ID = "Id";
    private static final String JOB_DAY_DATE = "Date";
    private static final String JOB_DAY_IS_VISITED = "IsVisited";
//    private static final String JOB_DAY_JOB_MONTH_ID = "DeadlineDate";

    private static final String CREATE_TABLE_JOB_DAY =
            "CREATE TABLE " + TABLE_JOB_DAY +
                    "('" + JOB_DAY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT,'" +
                    JOB_DAY_DATE + "' DATETIME,'" +
                    JOB_DAY_IS_VISITED + "' BOOLEAN)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_HOME_CATEGORY);
        sqLiteDatabase.execSQL(CREATE_TABLE_CAR_CATEGORY);
        sqLiteDatabase.execSQL(CREATE_TABLE_HOME_ITEM);
        sqLiteDatabase.execSQL(CREATE_TABLE_JOB_DAY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_HOME_CATEGORY);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_CAR_CATEGORY);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_HOME_ITEM);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_JOB_DAY);
    }

    public boolean isHomeCategoryTableEmpty(){
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_HOME_CATEGORY);
        db.close();
        return count == 0;
    }

    //insertHomeCategory
    public void insertHomeCategory(HomeCategory homeCategory) {

        try {
            db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(HOME_CATEGORY_NAME, homeCategory.getName());
            cv.put(HOME_CATEGORY_CAME_OUT_DAY, homeCategory.getCameOutDay());
            cv.put(HOME_CATEGORY_DEADLINE_DAY, homeCategory.getDeadlineDay());
            cv.put(HOME_CATEGORY_HAS_CONSTANT_PRICE, homeCategory.hasConstantPrice());

            db.insertOrThrow(TABLE_HOME_CATEGORY, null, cv);

        } catch (SQLException e) {
            Log.e("SQL Error", e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //editHomeCategorySettings
    public void editHomeCategorySettings(HomeCategory homeCategory) {

        try {
            db = getWritableDatabase();
            db.execSQL("UPDATE " + TABLE_HOME_CATEGORY + " SET " +
                    HOME_CATEGORY_CAME_OUT_DAY + "='" + homeCategory.getCameOutDay() + "'," +
                    HOME_CATEGORY_DEADLINE_DAY + "='" + homeCategory.getDeadlineDay() + "'," +
                    HOME_CATEGORY_HAS_CONSTANT_PRICE + "='" + homeCategory.hasConstantPrice() + "'," +
                    HOME_CATEGORY_CONSTANT_PRICE + "='" + homeCategory.getConstantPrice() +
                    "' WHERE " + HOME_CATEGORY_ID + "=" + homeCategory.getId());

        } catch (SQLException e) {
            Log.e("SQL Error", e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //getHomeCategoryById
    public HomeCategory getHomeCategoryById(int id) {

        HomeCategory homeCategory = new HomeCategory();

        db = getReadableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HOME_CATEGORY +
                " WHERE " + HOME_CATEGORY_ID + "=" + id, null)) {

            while (cursor.moveToFirst()) {

                homeCategory.setId(cursor.getInt(cursor.getColumnIndex(HOME_CATEGORY_ID)));
                homeCategory.setName(cursor.getString(cursor.getColumnIndex(HOME_CATEGORY_NAME)));
                homeCategory.setCameOutDay(cursor.getInt(cursor.getColumnIndex(HOME_CATEGORY_CAME_OUT_DAY)));
                homeCategory.setDeadlineDay(cursor.getInt(cursor.getColumnIndex(HOME_CATEGORY_DEADLINE_DAY)));

                boolean hasConstPrice = (cursor.getInt(cursor.getColumnIndex(HOME_CATEGORY_HAS_CONSTANT_PRICE)) == 1);
                homeCategory.setHasConstantPrice(hasConstPrice);

                homeCategory.setConstantPrice(cursor.getDouble(cursor.getColumnIndex(HOME_CATEGORY_CONSTANT_PRICE)));
            }
        } catch (SQLException e) {
            Log.e("SQL Error", e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
        return homeCategory;
    }

    //getHomeCategoryList
    public ArrayList<HomeCategory> getHouseCategories() {
        ArrayList<HomeCategory> homeCategoryList = new ArrayList<>();

        db = getReadableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HOME_CATEGORY, null)) {

            while (cursor.moveToNext()) {
                HomeCategory homeCategory = new HomeCategory();

                homeCategory.setId(cursor.getInt(cursor.getColumnIndex(HOME_CATEGORY_ID)));
                homeCategory.setName(cursor.getString(cursor.getColumnIndex(HOME_CATEGORY_NAME)));
                homeCategory.setCameOutDay(cursor.getInt(cursor.getColumnIndex(HOME_CATEGORY_CAME_OUT_DAY)));
                homeCategory.setDeadlineDay(cursor.getInt(cursor.getColumnIndex(HOME_CATEGORY_DEADLINE_DAY)));

                boolean hasConstPrice = (cursor.getInt(cursor.getColumnIndex(HOME_CATEGORY_HAS_CONSTANT_PRICE)) == 1);
                homeCategory.setHasConstantPrice(hasConstPrice);

                homeCategory.setConstantPrice(cursor.getDouble(cursor.getColumnIndex(HOME_CATEGORY_CONSTANT_PRICE)));

                homeCategoryList.add(homeCategory);

            }
        } catch (SQLException e) {
            Log.e("SQL Error", e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
        return homeCategoryList;
    }

    //insertHomeItem
    public void insertHomeItem(HomeItem homeItem) {

        try {
            db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(HOME_ITEM_HOME_CATEGORY_ID, homeItem.getHomeCategoryId());
            cv.put(HOME_ITEM_MONTH, homeItem.getMonth());
            cv.put(HOME_ITEM_PAID_DATE, DateHelper.formatDateToString(homeItem.getPaidDate()));
            cv.put(HOME_ITEM_IS_PAID, homeItem.isPaid());
            cv.put(HOME_ITEM_SUM, homeItem.getSum());
            cv.put(HOME_ITEM_NOTE, homeItem.getNote());

            db.insertOrThrow(TABLE_HOME_ITEM, null, cv);

        } catch (SQLException e) {
            Log.e("SQL Error", e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //editHomeItem
    public void editHomeItem(HomeItem homeItem) {

        int isPaid = homeItem.isPaid() ? 1 : 0;

        try {
            db = getWritableDatabase();
            db.execSQL("UPDATE " + TABLE_HOME_ITEM + " SET " +
                    HOME_ITEM_SUM + "='" + homeItem.getSum() + "'," +
                    HOME_ITEM_IS_PAID + "='" + isPaid + "'," +
                    HOME_ITEM_NOTE + "='" + homeItem.getNote() + "'," +
                    HOME_ITEM_PAID_DATE + "='" + DateHelper.formatDateToString(homeItem.getPaidDate()) +
                    "' WHERE " + HOME_ITEM_ID + "=" + homeItem.getId());

        } catch (SQLException e) {
            Log.e("SQL Error", e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //getLastHomeItem
    public HomeItem getLastHomeItem() {

        HomeItem homeItem = new HomeItem();

        db = getReadableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HOME_ITEM +
                " ORDER BY " + HOME_ITEM_ID + " DESC LIMIT 1", null)) {

            while (cursor.moveToNext()) {

                homeItem.setId(cursor.getInt(cursor.getColumnIndex(HOME_ITEM_ID)));
                homeItem.setHomeCategoryId(cursor.getInt(cursor.getColumnIndex(HOME_ITEM_HOME_CATEGORY_ID)));
                homeItem.setMonth(cursor.getString(cursor.getColumnIndex(HOME_ITEM_MONTH)));

                String s = cursor.getString(cursor.getColumnIndex(HOME_ITEM_PAID_DATE));
                Date date = DateHelper.parseStringToDate(s);
                homeItem.setPaidDate(date);

                boolean isPaid = (cursor.getInt(cursor.getColumnIndex(HOME_ITEM_IS_PAID)) == 1);
                homeItem.setPaid(isPaid);
                homeItem.setSum(cursor.getDouble(cursor.getColumnIndex(HOME_ITEM_SUM)));
                homeItem.setNote(cursor.getString(cursor.getColumnIndex(HOME_ITEM_NOTE)));
            }
        } catch (SQLException e) {
            Log.e("SQL Error", e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
        return homeItem;
    }

    //getHomeItemList
    public ArrayList<HomeItem> getHomeItemList(int id) {
        ArrayList<HomeItem> homeItemList = new ArrayList<>();

        db = getReadableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HOME_ITEM + " WHERE " +
                HOME_ITEM_HOME_CATEGORY_ID + "=" + id, null)) {

            while (cursor.moveToNext()) {
                HomeItem homeItem = new HomeItem();

                homeItem.setId(cursor.getInt(cursor.getColumnIndex(HOME_ITEM_ID)));
                homeItem.setHomeCategoryId(cursor.getInt(cursor.getColumnIndex(HOME_ITEM_HOME_CATEGORY_ID)));
                homeItem.setMonth(cursor.getString(cursor.getColumnIndex(HOME_ITEM_MONTH)));

                String s = cursor.getString(cursor.getColumnIndex(HOME_ITEM_PAID_DATE));
                Date date = DateHelper.parseStringToDate(s);
                homeItem.setPaidDate(date);

                boolean isPaid = (cursor.getInt(cursor.getColumnIndex(HOME_ITEM_IS_PAID)) == 1);
                homeItem.setPaid(isPaid);
                homeItem.setSum(cursor.getDouble(cursor.getColumnIndex(HOME_ITEM_SUM)));
                homeItem.setNote(cursor.getString(cursor.getColumnIndex(HOME_ITEM_NOTE)));

                homeItemList.add(homeItem);
            }
        } catch (SQLException e) {
            Log.e("SQL Error", e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
        return homeItemList;
    }

    //insertCarCategory
    public void insertCarCategory(CarCategory carCategory) {

        try {
            db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(CAR_CATEGORY_NAME, carCategory.getName());
            cv.put(CAR_CATEGORY_PAID_DATE, DateHelper.formatDateToString(carCategory.getPaidDate()));
            cv.put(CAR_CATEGORY_DEADLINE_DATE, DateHelper.formatDateToString(carCategory.getDeadlineDate()));
            cv.put(CAR_CATEGORY_IS_PAID, carCategory.isPaid());
            cv.put(CAR_CATEGORY_SUM, carCategory.getSum());
            cv.put(CAR_CATEGORY_NOTE, carCategory.getNote());

            db.insertOrThrow(TABLE_CAR_CATEGORY, null, cv);

        } catch (SQLException e) {
            Log.e("SQL Error", e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //editCarCategory
    public void editCarCategory(CarCategory carCategory) {

        int isPaid = carCategory.isPaid() ? 1 : 0;

        try {
            db = getWritableDatabase();
            db.execSQL("UPDATE " + TABLE_CAR_CATEGORY + " SET " +
                    CAR_CATEGORY_NAME + "='" + carCategory.getName() + "'," +
                    CAR_CATEGORY_PAID_DATE + "='" + DateHelper.formatDateToString(carCategory.getPaidDate()) + "'," +
                    CAR_CATEGORY_DEADLINE_DATE + "='" + DateHelper.formatDateToString(carCategory.getDeadlineDate()) + "'," +
                    CAR_CATEGORY_IS_PAID + "='" + isPaid + "'," +
                    CAR_CATEGORY_SUM + "='" + carCategory.getSum() + "'," +
                    CAR_CATEGORY_NOTE + "='" + carCategory.getNote() +
                    "' WHERE " + CAR_CATEGORY_ID + "=" + carCategory.getId());

        } catch (SQLException e) {
            Log.e("SQL Error", e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //getCarCategoryList
    public ArrayList<CarCategory> getCarCategoryList() {
        ArrayList<CarCategory> carCategoryList = new ArrayList<>();

        db = getReadableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CAR_CATEGORY, null)) {

            while (cursor.moveToNext()) {
                CarCategory carCategory = new CarCategory();

                carCategory.setId(cursor.getInt(cursor.getColumnIndex(CAR_CATEGORY_ID)));
                carCategory.setName(cursor.getString(cursor.getColumnIndex(CAR_CATEGORY_NAME)));

                String s1 = cursor.getString(cursor.getColumnIndex(CAR_CATEGORY_PAID_DATE));
                Date date1 = DateHelper.parseStringToDate(s1);
                carCategory.setPaidDate(date1);

                String s2 = cursor.getString(cursor.getColumnIndex(CAR_CATEGORY_DEADLINE_DATE));
                Date date2 = DateHelper.parseStringToDate(s2);
                carCategory.setDeadlineDate(date2);

                boolean isPaid = (cursor.getInt(cursor.getColumnIndex(CAR_CATEGORY_IS_PAID)) == 1);
                carCategory.setPaid(isPaid);

                carCategory.setSum(cursor.getDouble(cursor.getColumnIndex(CAR_CATEGORY_SUM)));
                carCategory.setConstantPrice(cursor.getDouble(cursor.getColumnIndex(CAR_CATEGORY_CONSTANT_PRICE)));
                carCategory.setNote(cursor.getString(cursor.getColumnIndex(CAR_CATEGORY_NOTE)));

                carCategoryList.add(carCategory);

            }
        } catch (SQLException e) {
            Log.e("SQL Error", e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
        return carCategoryList;
    }

    //insertJobDay
    public void insertJobDay(JobDay jobDay) {

        try {
            db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(JOB_DAY_DATE, DateHelper.formatDateToString(jobDay.getDate()));
            cv.put(JOB_DAY_IS_VISITED, jobDay.isVisited());

            db.insertOrThrow(TABLE_JOB_DAY, null, cv);

        } catch (SQLException e) {
            Log.e("SQL Error", e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //getJobDayList
    public ArrayList<JobDay> getJobDayList() {
        ArrayList<JobDay> jobDayList = new ArrayList<>();

        db = getReadableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_JOB_DAY, null)) {

            while (cursor.moveToNext()) {
                JobDay jobDay = new JobDay();

                jobDay.setId(cursor.getInt(cursor.getColumnIndex(JOB_DAY_ID)));

                String s1 = cursor.getString(cursor.getColumnIndex(JOB_DAY_DATE));
                Date date1 = DateHelper.parseStringToDate(s1);
                jobDay.setDate(date1);

                boolean isVisited = (cursor.getInt(cursor.getColumnIndex(JOB_DAY_IS_VISITED)) == 1);
                jobDay.setVisited(isVisited);

                jobDayList.add(jobDay);
            }
        } catch (SQLException e) {
            Log.e("SQL Error", e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
        return jobDayList;
    }

    //getJobDayList
    public ArrayList<Date> getJobDayDateList() {
        ArrayList<Date> result = new ArrayList<>();

        db = getReadableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_JOB_DAY, null)) {

            while (cursor.moveToNext()) {

                Date date = new Date();
                String s1 = cursor.getString(cursor.getColumnIndex(JOB_DAY_DATE));
                date = DateHelper.parseStringToDate(s1);

                result.add(date);
            }
        } catch (SQLException e) {
            Log.e("SQL Error", e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
        return result;
    }

    //getJobDayByDate
    public JobDay getJobDayByDate(Date date) {

        db = getReadableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_JOB_DAY +
                " WHERE " + JOB_DAY_DATE + "='" + DateHelper.formatDateToString(date) + "'", null)) {

            if (cursor.moveToFirst()) {

                JobDay jobDay = new JobDay();
                jobDay.setId(cursor.getInt(cursor.getColumnIndex(JOB_DAY_ID)));

                String s1 = cursor.getString(cursor.getColumnIndex(JOB_DAY_DATE));
                Date date1 = DateHelper.parseStringToDate(s1);
                jobDay.setDate(date1);

                boolean isVisited = (cursor.getInt(cursor.getColumnIndex(JOB_DAY_IS_VISITED)) == 1);
                jobDay.setVisited(isVisited);

                return jobDay;
            }
        } catch (SQLException e) {
            Log.e("SQL Error", e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
        return null;
    }
}
