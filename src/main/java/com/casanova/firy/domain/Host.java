package com.casanova.firy.domain;

import scala.Serializable;

public class Host implements Serializable {

    private Integer id;

    private String prefix;

    private String host;

    private Integer frecency;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getFrecency() {
        return frecency;
    }

    public void setFrecency(Integer frecency) {
        this.frecency = frecency;
    }

    @Override public String toString() {
        return "Host{" +
               "id=" + id +
               ", prefix='" + prefix + '\'' +
               ", host='" + host + '\'' +
               ", frecency=" + frecency +
               '}';
    }
}
