package com.casanova.firy.domain;

import scala.Serializable;

public class DimDate implements Serializable {

    private Integer id;

    private Integer day;

    private Integer month;

    private Integer week;

    private Integer year;

    public DimDate(Integer id, Integer day, Integer month, Integer week, Integer year) {
        this.id = id;
        this.day = day;
        this.month = month;
        this.week = week;
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override public String toString() {
        return "DimDate{" +
               "id=" + id +
               ", day=" + day +
               ", month=" + month +
               ", week=" + week +
               ", year=" + year +
               '}';
    }
}
