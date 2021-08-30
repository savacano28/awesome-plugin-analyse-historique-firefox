package com.casanova.firy.domain;

public class Visit {

    private Integer id;

    private Integer from_visit;

    private Integer place_id;

    private Integer visit_date;

    private Integer visit_type;

    private Integer session;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFrom_visit() {
        return from_visit;
    }

    public void setFrom_visit(Integer from_visit) {
        this.from_visit = from_visit;
    }

    public Integer getPlace_id() {
        return place_id;
    }

    public void setPlace_id(Integer place_id) {
        this.place_id = place_id;
    }

    public Integer getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(Integer visit_date) {
        this.visit_date = visit_date;
    }

    public Integer getVisit_type() {
        return visit_type;
    }

    public void setVisit_type(Integer visit_type) {
        this.visit_type = visit_type;
    }

    public Integer getSession() {
        return session;
    }

    public void setSession(Integer session) {
        this.session = session;
    }
}
