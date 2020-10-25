package com.steven.bean;

public class City {
    /*
                    String country = one.get("country");
                String adm1 = one.get("adm1");
                String adm2 = one.get("adm2");
                String name = one.get("name");
                String fxLink = one.get("fxLink");
//                lon 是经度 lat 是纬度
                String lon = one.get("lon");
                String lat = one.get("lat");*/

    private String country;
    private String province;
    private String city;
    private String town;
    private String fxLink;
    private String lon;
    private String lat;

    public City(String country, String province, String city, String town, String fxLink, String lon, String lat) {
        this.country = country;
        this.province = province;
        this.city = city;
        this.town = town;
        this.fxLink = fxLink;
        this.lon = lon;
        this.lat = lat;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getFxLink() {
        return fxLink;
    }

    public void setFxLink(String fxLink) {
        this.fxLink = fxLink;
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

    @Override
    public String toString() {
        return country + "-" + province + "-" + city + "-" + town;
    }
}
