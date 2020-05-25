package com.bRd.mot.Entity;

import java.util.Date;

public class CarCategory {

    private int id;
    private String name;
    private Date paidDate;
    private Date deadlineDate;
    private boolean isPaid;
    private double sum;
    private double constantPrice;
    private String note;

    public CarCategory(){
    }

    public CarCategory(String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
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

    public double getConstantPrice() {
        return constantPrice;
    }

    public void setConstantPrice(double constantPrice) {
        this.constantPrice = constantPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
