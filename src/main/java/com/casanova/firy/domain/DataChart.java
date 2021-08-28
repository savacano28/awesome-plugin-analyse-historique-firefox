package com.casanova.firy.domain;

import java.util.List;

public class DataChart {

    private List<String> labels;

    private List<DataSet> datasets;

    public DataChart() {
    }

    public DataChart(List<String> labels, List<DataSet> datasets) {
        this.labels = labels;
        this.datasets = datasets;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<DataSet> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<DataSet> datasets) {
        this.datasets = datasets;
    }
}
