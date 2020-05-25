package com.bRd.mot.Entity;

public class HomeCategory {

    private int id;
    private String name;
    private int cameOutDay;
    private int deadlineDay;
    private boolean hasConstantPrice;
    private double constantPrice;

    public HomeCategory() {
    }

    public HomeCategory(String name) {
        this.name = name;
    }

    public String getIdToString() {
        return String.valueOf(id);
    }

    public String getDeadlineDayToString() {
        return String.valueOf(deadlineDay);
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

    public int getCameOutDay() {
        return cameOutDay;
    }

    public void setCameOutDay(int cameOutDay) {
        this.cameOutDay = cameOutDay;
    }

    public int getDeadlineDay() {
        return deadlineDay;
    }

    public void setDeadlineDay(int deadlineDay) {
        this.deadlineDay = deadlineDay;
    }

    public double getConstantPrice() {
        return constantPrice;
    }

    public boolean hasConstantPrice() {
        return hasConstantPrice;
    }

    public void setHasConstantPrice(boolean hasConstantPrice) {
        this.hasConstantPrice = hasConstantPrice;
    }

    public void setConstantPrice(double constantPrice) {
        this.constantPrice = constantPrice;
    }
}
