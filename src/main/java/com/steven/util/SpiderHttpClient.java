package com.steven.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SpiderHttpClient {
    private String request_method = "GET";
    private String request_url;
    private String decode_charset = "UTF-8";
    private LinkedHashMap<String, String> params;
    private LinkedHashMap<String, String> request_headers;
    private String response;
    private HttpEntity response_entity;

    public SpiderHttpClient() {
    }

    public SpiderHttpClient(String request_url) {
        this.request_url = request_url;
    }

    public SpiderHttpClient(String request_url, String request_method) {
        this.request_method = request_method;
        this.request_url = request_url;
    }

    public SpiderHttpClient(String request_url, String request_method, String decode_charset) {
        this.request_method = request_method;
        this.request_url = request_url;
        this.decode_charset = decode_charset;
    }

    public SpiderHttpClient(String request_url, String request_method, LinkedHashMap<String, String> params) {
        this.request_method = request_method;
        this.request_url = request_url;
        this.params = params;
    }

    public SpiderHttpClient(String request_url, String request_method, String decode_charset, LinkedHashMap<String, String> params) {
        this.request_method = request_method;
        this.request_url = request_url;
        this.decode_charset = decode_charset;
        this.params = params;
    }

    public SpiderHttpClient( String request_url,String request_method, String decode_charset, LinkedHashMap<String, String> params, LinkedHashMap<String, String> request_headers) {
        this.request_method = request_method;
        this.request_url = request_url;
        this.decode_charset = decode_charset;
        this.params = params;
        this.request_headers = request_headers;
    }

    private void execute() throws UnsupportedEncodingException {
        HttpRequestBase requestBase = null;
        if (request_url != null) {
            switch (request_method.toUpperCase()) {
                case "GET":
                    requestBase = new HttpGet(request_url);
                    break;
                case "POST":
                    requestBase = new HttpPost(request_url);
                    // ==============================================添加请求参数=============================================
                    if (params != null) {
                        //创建参数集合
                        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
                        //添加参数
                        for (Map.Entry<String, String> entry : params.entrySet()) {
                            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                        }
                        //把参数放入请求对象，，post发送的参数list，指定格式
                        HttpPost post = (HttpPost) requestBase;
                        post.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
                        requestBase = post;
                    }
                    break;
                default:
                    response = "请求方式错误,支持GET/POST";
                    return;
            }
            //================================================= 添加请求头==============================================
            if (request_headers != null) {
                for (Map.Entry<String, String> entry : request_headers.entrySet()) {
                    requestBase.setHeader(entry.getKey(), entry.getValue());
                }
            }

            CloseableHttpClient httpClient = HttpClients.createDefault();
            try {
                CloseableHttpResponse httpResponse = httpClient.execute(requestBase);
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    response_entity = httpResponse.getEntity();
                    response = EntityUtils.toString(response_entity, decode_charset);
                } else {
                    response = "请求异常 , 响应码:" + statusCode;
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                response = "IOException,请求路径错误,记得加上HTTP协议!";
                return;
            }
        } else {
            response = "请求URL为空!";
            return;
        }
    }

    public String getResponseStr() {
        try {
            execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return response;
    }

    public HttpEntity getEntity() {
        try {
            execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return response_entity;
    }
}
