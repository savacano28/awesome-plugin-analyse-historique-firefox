package com.casanova.firy.domain;

import scala.Serializable;

import java.util.Date;

public class DimSite implements Serializable {

    private Integer id;

    private String url;

    private String title;

    private Integer visit_count;

    private Integer hidden;

    private Integer typed;

    private Integer frecency;

    private String description;

    private Date last_visit_date_simple;

    private Integer origin_id;

    public DimSite(final Integer id,
                   final String url,
                   final String title,
                   final Integer visit_count,
                   final Integer hidden,
                   final Integer typed,
                   final Integer frecency,
                   final String description,
                   final Date last_visit_date_simple,
                   final Integer origin_id) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.visit_count = visit_count;
        this.hidden = hidden;
        this.typed = typed;
        this.frecency = frecency;
        this.description = description;
        this.last_visit_date_simple = last_visit_date_simple;
        this.origin_id = origin_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getVisit_count() {
        return visit_count;
    }

    public void setVisit_count(Integer visit_count) {
        this.visit_count = visit_count;
    }

    public Integer getHidden() {
        return hidden;
    }

    public void setHidden(Integer hidden) {
        this.hidden = hidden;
    }

    public Integer getTyped() {
        return typed;
    }

    public void setTyped(Integer typed) {
        this.typed = typed;
    }

    public Integer getFrecency() {
        return frecency;
    }

    public void setFrecency(Integer frecency) {
        this.frecency = frecency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLast_visit_date_simple() {
        return last_visit_date_simple;
    }

    public void setLast_visit_date_simple(Date last_visit_date_simple) {
        this.last_visit_date_simple = last_visit_date_simple;
    }

    public Integer getOrigin_id() {
        return origin_id;
    }

    public void setOrigin_id(Integer origin_id) {
        this.origin_id = origin_id;
    }

    @Override public String toString() {
        return "DimSite{" +
               "id=" + id +
               ", url='" + url + '\'' +
               ", title='" + title + '\'' +
               ", visit_count=" + visit_count +
               ", hidden=" + hidden +
               ", typed=" + typed +
               ", frecency=" + frecency +
               ", description='" + description + '\'' +
               ", last_visit_date_simple=" + last_visit_date_simple +
               ", origin_id=" + origin_id +
               '}';
    }
}
