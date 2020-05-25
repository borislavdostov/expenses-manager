package com.bRd.mot.Entity;

import java.util.Date;

public class HomeItem {

    private int id;
    private int homeCategoryId;
    private String month;
    private Date paidDate;
    private boolean isPaid;
    private double sum;
    private String note;

    public HomeItem(){
    }

    public HomeItem(int homeCategoryId, String month) {
        this.homeCategoryId = homeCategoryId;
        this.month = month;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHomeCategoryId() {
        return homeCategoryId;
    }

    public void setHomeCategoryId(int homeCategoryId) {
        this.homeCategoryId = homeCategoryId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
