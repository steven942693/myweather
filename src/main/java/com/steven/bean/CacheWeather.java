package com.steven.bean;

public class CacheWeather {
    private String lon;
    private String lat;
    private NowWeather nowWeather;

    public CacheWeather() {
    }

    public CacheWeather(String lon, String lat, NowWeather nowWeather) {
        this.lon = lon;
        this.lat = lat;
        this.nowWeather = nowWeather;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public NowWeather getNowWeather() {
        return nowWeather;
    }

    public void setNowWeather(NowWeather nowWeather) {
        this.nowWeather = nowWeather;
    }
}
