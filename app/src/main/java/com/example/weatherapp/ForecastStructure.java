package com.example.weatherapp;

public class ForecastStructure {
    private String condition;
    private String conditionImg;
    private String hourTemp;
    private String datetime;

    public ForecastStructure(String condition, String conditionImg, String hourTemp, String datetime) {
        this.condition = condition;
        this.conditionImg = conditionImg;
        this.hourTemp = hourTemp;
        this.datetime = datetime;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getConditionImg() {
        return conditionImg;
    }

    public void setConditionImg(String conditionImg) {
        this.conditionImg = conditionImg;
    }

    public String getHourTemp() {
        return hourTemp;
    }

    public void setHourTemp(String hourTemp) {
        this.hourTemp = hourTemp;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
