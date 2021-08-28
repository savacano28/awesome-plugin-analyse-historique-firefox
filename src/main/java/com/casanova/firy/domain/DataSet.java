package com.casanova.firy.domain;

import java.util.List;

public class DataSet {

    private String label;

    private List<Integer> data;

    private String backgroundColor;

    private String borderColor;

    private Integer borderWidth;

    private boolean stepped;

    private boolean fill;

    public DataSet(String label, List<Integer> data, String backgroundColor, String borderColor, Integer borderWidth, boolean stepped, boolean fill) {
        this.label = label;
        this.data = data;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
        this.stepped = stepped;
        this.fill = fill;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public boolean isStepped() {
        return stepped;
    }

    public void setStepped(boolean stepped) {
        this.stepped = stepped;
    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Integer getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(Integer borderWidth) {
        this.borderWidth = borderWidth;
    }
}
