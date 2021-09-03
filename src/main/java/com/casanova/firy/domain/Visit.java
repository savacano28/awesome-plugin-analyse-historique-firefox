package com.casanova.firy.domain;

import scala.Serializable;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Random;

public class Visit implements Serializable {

    private Integer id;

    private Integer from_visit;

    private Integer place_id;

    private Long visit_date;

    private LocalDate visit_date_simple;

    private Integer visit_type;

    private Integer session;

    private long duration;

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

    public Long getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(Long visit_date) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Random r = new Random();

        if (this.id < 2050) {
            timestamp.setTime(timestamp.getTime() - 259200000 + (r.nextInt(15000) + 5000));
        } else if (this.id < 5014 && this.id > 2050) {
            timestamp.setTime(timestamp.getTime() - 172800000 + (r.nextInt(20000) + 5000));
        } else if (this.id < 7865 && this.id > 5014) {
            timestamp.setTime(timestamp.getTime() - 86400000 + (r.nextInt(8000) + 5000));
        }
        this.visit_date = timestamp.getTime();
        this.visit_date_simple = timestamp.toLocalDateTime().toLocalDate();
    }

    public LocalDate getVisit_date_simple() {
        return visit_date_simple;
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override public String toString() {
        return "Visit{" +
               "id=" + id +
               ", from_visit=" + from_visit +
               ", place_id=" + place_id +
               ", visit_date=" + visit_date +
               ", visit_date_simple=" + visit_date_simple +
               ", visit_type=" + visit_type +
               ", session=" + session +
               ", duration=" + duration +
               '}';
    }
}
