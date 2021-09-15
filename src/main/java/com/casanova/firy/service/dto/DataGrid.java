package com.casanova.firy.service.dto;

import java.util.List;

public class DataGrid {

    private List<String> columns;

    private List<List<String>> data;

    public DataGrid() {
    }

    public DataGrid(List<String> columns, List<List<String>> data) {
        this.columns = columns;
        this.data = data;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }
}
