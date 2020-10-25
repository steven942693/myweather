package com.steven.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.steven.bean.City;
import com.steven.bean.NowWeather;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeatherSpider {
    String key = "b0fcc28e6d524d44843c30aa4ca9727c";

    public List<City> citySearch(String sear_loc) throws IOException {
        ArrayList<City> res_city = new ArrayList<>();
//        System.out.println(new Date());
        String location_site = "https://geoapi.heweather.net/v2/city/lookup?key=" + key + "&location=" + sear_loc;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(location_site);

        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
//            System.out.println(entity.toString());
            String resp = EntityUtils.toString(entity, "utf-8");
//            System.out.println(resp);

            Map<String, Object> map = JSON.parseObject(resp, Map.class);
            Object location = map.get("location");
//            System.out.println(location);
            if (location == null){
                return null;
            }
            JSONArray jsonArray = JSON.parseArray(location.toString());
            for (Object o : jsonArray) {
                Map<String, String> one = JSON.parseObject(o.toString(), Map.class);
                String country = one.get("country");
                String adm1 = one.get("adm1");
                String adm2 = one.get("adm2");
                String name = one.get("name");
                String fxLink = one.get("fxLink");
//                lon 是经度 lat 是纬度
                String lon = one.get("lon");
                String lat = one.get("lat");
                res_city.add(new City(country,adm1,adm2,name,fxLink,lon,lat));
                System.out.println(country + "\t" + adm1 + "\t" + adm2 + "\t" + name + "\t" + fxLink + "\t" + lon + "\t" + lat);
                getNowWeather(lon, lat);


//                System.out.println(o.toString());
            }
//            System.out.println(location.toString());
//{"
// country":"中国",
// "fxLink":"http://hfx.link/2mw1",
// "utcOffset":"+08:00",
// "adm2":"保定",
// "tz":"Asia/Shanghai",
// "isDst":"0",
// "lon":"115.48233",
// "adm1":"河北",
// "type":"city",
// "name":"保定",
// "rank":"15",
// "id":"101090201",
// "lat":"38.86765"}
        }
        return res_city;
    }

    public NowWeather getNowWeather(String lon, String lat) throws IOException {
        NowWeather weather = new NowWeather();
        String now_weather_site = "https://devapi.heweather.net/v7/weather/now?key=" + key + "&location=" + lon + "," + lat;
        CloseableHttpClient now_weather_client = HttpClients.createDefault();
        HttpGet now_weather_get = new HttpGet(now_weather_site);
        CloseableHttpResponse now_weather_response = now_weather_client.execute(now_weather_get);
        if (now_weather_response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = now_weather_response.getEntity();
//            System.out.println(entity.toString());
            String now_weather_resp = EntityUtils.toString(entity, "utf-8");
            Map<String, Object> map = JSON.parseObject(now_weather_resp, Map.class);
            Object now = map.get("now");

            Map<String, String> one_weather = JSON.parseObject(now.toString(), Map.class);
            String obsTime = one_weather.get("obsTime");
            weather.setObsTime(obsTime);
            String temp = one_weather.get("temp");
            weather.setTemp(temp);
            String icon = one_weather.get("icon");
            weather.setIcon(icon);
            String text = one_weather.get("text");
            weather.setText(text);
            String windDir = one_weather.get("windDir");
            weather.setWindDir(windDir);
            String windSpeed = one_weather.get("windSpeed");
            weather.setWindSpeed(windSpeed);
            String humidity = one_weather.get("humidity");
            weather.setHumidity(humidity);
            System.out.println("查询时间:" + obsTime + "\t温度:" + temp + "\t图标:" + icon + "\t天气:" + text + "\t风向:" + windDir + "\t风速:" + windSpeed + "\t湿度:" + humidity);

//            System.out.println(now_weather_resp);
        }
//        {"code":"200",
//        "updateTime":"2020-09-30T22:36+08:00",
//        "fxLink":"http://hfx.link/2mw1",

//        "now":{"obsTime":"2020-09-30T22:15+08:00",
//        "temp":"16",
//        "feelsLike":"16",
//        "icon":"154",
//        "text":"阴",
//        "wind360":"206",
//        "windDir":"西南风",
//        "windScale":"1",
//        "windSpeed":"5",
//        "humidity":"92",
//        "precip":"0.0",
//        "pressure":"1011",
//        "vis":"16",
//        "cloud":"91",
//        "dew":"12"},
//        "refer":{"sources":["Weather China"],"license":["no commercial use"]}}
        return weather;
    }
}
