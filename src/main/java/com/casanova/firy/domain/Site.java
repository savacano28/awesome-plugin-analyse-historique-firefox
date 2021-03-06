package com.casanova.firy.domain;

import scala.Serializable;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Site implements Serializable {

    private Integer id;

    private String url;

    private String title;

    private String rev_host;

    private Integer visit_count;

    private Integer hidden;

    private Integer typed;

    private Integer frecency;

    private String description;

    private String guid;

    private String preview_image_url;

    private long last_visit_date;

    private LocalDate last_visit_date_simple;

    private Integer url_hash;

    private Integer origin_id;

    private Integer foreign_count;

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

    public long getLast_visit_date() {
        return last_visit_date;
    }

    public void setLast_visit_date(long last_visit_date) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.last_visit_date = timestamp.getTime();
        this.last_visit_date_simple = timestamp.toLocalDateTime().toLocalDate();
    }

    public LocalDate getLast_visit_date_simple() {
        return last_visit_date_simple;
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

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPreview_image_url() {
        return preview_image_url;
    }

    public void setPreview_image_url(String preview_image_url) {
        this.preview_image_url = preview_image_url;
    }

    public Integer getUrl_hash() {
        return url_hash;
    }

    public void setUrl_hash(Integer url_hash) {
        this.url_hash = url_hash;
    }

    public Integer getOrigin_id() {
        return origin_id;
    }

    public void setOrigin_id(Integer origin_id) {
        this.origin_id = origin_id;
    }

    public Integer getForeign_count() {
        return foreign_count;
    }

    public void setForeign_count(Integer foreign_count) {
        this.foreign_count = foreign_count;
    }

    @Override public String toString() {
        return "Site{" +
               "id=" + id +
               ", url='" + url + '\'' +
               ", title='" + title + '\'' +
               ", rev_host='" + rev_host + '\'' +
               ", visit_count=" + visit_count +
               ", hidden=" + hidden +
               ", typed=" + typed +
               ", frecency=" + frecency +
               ", description='" + description + '\'' +
               ", guid='" + guid + '\'' +
               ", preview_image_url='" + preview_image_url + '\'' +
               ", last_visit_date=" + last_visit_date +
               ", last_visit_date_simple=" + last_visit_date_simple +
               ", url_hash=" + url_hash +
               ", origin_id=" + origin_id +
               ", foreign_count=" + foreign_count +
               '}';
    }
}
