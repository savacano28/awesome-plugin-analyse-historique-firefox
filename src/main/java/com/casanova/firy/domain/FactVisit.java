package com.casanova.firy.domain;

import scala.Serializable;

public class FactVisit implements Serializable {

    private Integer id;

    private Integer place_id;

    private Integer date_id;

    private Integer type_id;

    private Integer nb_visits;

    private Double dur_max_vis;

    private Double dur_mean_vis;

    private Double dur_min_vis;

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

    public Double getDur_max_vis() {
        return dur_max_vis;
    }

    public void setDur_max_vis(Double dur_max_vis) {
        this.dur_max_vis = dur_max_vis;
    }

    public Double getDur_mean_vis() {
        return dur_mean_vis;
    }

    public void setDur_mean_vis(Double dur_mean_vis) {
        this.dur_mean_vis = dur_mean_vis;
    }

    public Double getDur_min_vis() {
        return dur_min_vis;
    }

    public void setDur_min_vis(Double dur_min_vis) {
        this.dur_min_vis = dur_min_vis;
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
               '}';
    }
}
