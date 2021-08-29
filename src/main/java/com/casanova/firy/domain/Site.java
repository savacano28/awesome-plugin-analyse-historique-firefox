package com.casanova.firy.domain;

import java.time.LocalDate;

public class Site {

    private Integer id;

    private String url;

    private String title;

    private String rev_host;

    private Integer visit_count;

    private Integer frecency;

    private String description;

    private LocalDate last_visit_date;

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

    public String getRev_host() {
        return rev_host;
    }

    public void setRev_host(String rev_host) {
        this.rev_host = rev_host;
    }

    public Integer getVisit_count() {
        return visit_count;
    }

    public void setVisit_count(Integer visit_count) {
        this.visit_count = visit_count;
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

    public LocalDate getLast_visit_date() {
        return last_visit_date;
    }

    public void setLast_visit_date(LocalDate last_visit_date) {
        this.last_visit_date = last_visit_date;
    }
}
