package com.casanova.firy.domain;

import scala.Serializable;

import java.util.Date;

public class FactVisit implements Serializable {

    private Integer id;

    private Integer place_id;

    private Integer date_id;

    private Integer type_id;

    private Integer nb_visits;

    private Integer dur_max_vis;

    private Double dur_mean_vis;

    private Integer dur_min_vis;

    private Date visit_date_simple;

    private String host;

    public FactVisit(Integer id,
                     Integer place_id,
                     Integer date_id,
                     Integer type_id,
                     Integer nb_visits,
                     Integer dur_max_vis,
                     Double dur_mean_vis,
                     Integer dur_min_vis,
                     Date visit_date_simple,
                     String host) {
        this.id = id;
        this.place_id = place_id;
        this.date_id = date_id;
        this.type_id = type_id;
        this.nb_visits = nb_visits;
        this.dur_max_vis = dur_max_vis;
        this.dur_mean_vis = dur_mean_vis;
        this.dur_min_vis = dur_min_vis;
        this.visit_date_simple = visit_date_simple;
        this.host = host;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlace_id() {
        return place_id;
    }

    public void setPlace_id(Integer place_id) {
        this.place_id = place_id;
    }

    public Integer getDate_id() {
        return date_id;
    }

    public void setDate_id(Integer date_id) {
        this.date_id = date_id;
    }

    public Integer getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    public Integer getNb_visits() {
        return nb_visits;
    }

    public void setNb_visits(Integer nb_visits) {
        this.nb_visits = nb_visits;
    }

    public Integer getDur_max_vis() {
        return dur_max_vis;
    }

    public void setDur_max_vis(Integer dur_max_vis) {
        this.dur_max_vis = dur_max_vis;
    }

    public Integer getDur_min_vis() {
        return dur_min_vis;
    }

    public void setDur_min_vis(Integer dur_min_vis) {
        this.dur_min_vis = dur_min_vis;
    }

    public Double getDur_mean_vis() {
        return dur_mean_vis;
    }

    public void setDur_mean_vis(Double dur_mean_vis) {
        this.dur_mean_vis = dur_mean_vis;
    }

    public Date getVisit_date_simple() {
        return visit_date_simple;
    }

    public void setVisit_date_simple(Date visit_date_simple) {
        this.visit_date_simple = visit_date_simple;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override public String toString() {
        return "FactVisit{" +
               "id=" + id +
               ", place_id=" + place_id +
               ", date_id=" + date_id +
               ", type_id=" + type_id +
               ", nb_visits=" + nb_visits +
               ", dur_max_vis=" + dur_max_vis +
               ", dur_mean_vis=" + dur_mean_vis +
               ", dur_min_vis=" + dur_min_vis +
               ", visit date=" + visit_date_simple +
               ", host=" + host +
               '}';
    }
}
