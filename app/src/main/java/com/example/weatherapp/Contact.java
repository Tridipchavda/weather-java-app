package com.example.weatherapp;

public class Contact {

    String date;
    String minTemp;
    String maxTemp;
    String imgURL;
    String haal;

    public Contact(String date, String minTemp, String maxTemp, String imgURL, String haal) {
        this.date = date;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.imgURL = imgURL;
        this.haal = haal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getHaal() {
        return haal;
    }

    public void setHaal(String haal) {
        this.haal = haal;
    }
}
