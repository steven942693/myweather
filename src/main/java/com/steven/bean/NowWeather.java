package com.steven.bean;

public class NowWeather {
    /*
    *       String obsTime = one_weather.get("obsTime");
            String temp = one_weather.get("temp");
            String icon = one_weather.get("icon");
            String text = one_weather.get("text");
            String windDir = one_weather.get("windDir");
            String windSpeed = one_weather.get("windSpeed");
            String humidity = one_weather.get("humidity");
    * */
    private String obsTime;
    private String temp;
    private String wend;
    private String icon;
    private String text;
    private String windDir;
    private String windSpeed;
    private String humidity;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getObsTime() {
        return obsTime;
    }

    public void setObsTime(String obsTime) {
        this.obsTime = obsTime;
    }

    public String getWend() {
        return wend;
    }

    public void setWend(String wend) {
        this.wend = wend;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
