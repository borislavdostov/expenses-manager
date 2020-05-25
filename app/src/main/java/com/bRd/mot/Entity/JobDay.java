package com.bRd.mot.Entity;

import java.util.Date;

public class JobDay {

    private int id;
    private Date date;
    private boolean isVisited;

    public JobDay() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }
}
