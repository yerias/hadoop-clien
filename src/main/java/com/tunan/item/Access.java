package com.tunan.item;

/**
 * @description: ETL数据的序列化类
 * @author: tunan
 * @create: 2020-02-16 00:57
 * @since: 1.0.0
 **/
public class Access {

    private String ip;      //访问ip(需要处理)
    private  String country; //国家
    private String province;    //省份
    private String city;    //城市
    private String area;    //运营商

    private String proxyIp;    //代理ip

    private String time;    //时间(需要处理)
    private String year;    //年
    private String month;   //月
    private String day;     //日

    private Long responseTime;  //响应时间
    private String referer; //来源

    private String method;  //请求方式

    private String uri;     //网址
    private String http;     //路径
    private String domain;  //域名
    private String path;    //参数

    private String httpCode;  //http响应码
    private Long requestSize;  //请求大小
    private Long responseSize; //响应大小
    private String cache;       //命中状态

    private String userId;

    public Access(String ip, String country, String province, String city, String area, String proxyIp, String time, String year, String month, String day, Long responseTime, String referer, String method, String uri, String http, String domain, String path, String httpCode, Long requestSize, Long responseSize, String cache, String userId) {
        this.ip = ip;
        this.country = country;
        this.province = province;
        this.city = city;
        this.area = area;
        this.proxyIp = proxyIp;
        this.time = time;
        this.year = year;
        this.month = month;
        this.day = day;
        this.responseTime = responseTime;
        this.referer = referer;
        this.method = method;
        this.uri = uri;
        this.http = http;
        this.domain = domain;
        this.path = path;
        this.httpCode = httpCode;
        this.requestSize = requestSize;
        this.responseSize = responseSize;
        this.cache = cache;
        this.userId = userId;
    }

    public Access() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {

        return  year+"\t"+
                month+"\t"+
                day+"\t"+
                country+"\t"+
                province+"\t"+
                city+"\t"+
                area+"\t"+
                proxyIp+"\t"+
                responseTime+"\t"+
                referer+"\t"+
                method+"\t"+
                http+"\t"+
                domain+"\t"+
                path+"\t"+
                httpCode+"\t"+
                requestSize+"\t"+
                responseSize+"\t"+
                cache+"\t"+
                userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProxyIp() {
        return proxyIp;
    }

    public void setProxyIp(String proxyIp) {
        this.proxyIp = proxyIp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getHttp() {
        return http;
    }

    public void setHttp(String http) {
        this.http = http;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(String httpCode) {
        this.httpCode = httpCode;
    }

    public Long getRequestSize() {
        return requestSize;
    }

    public void setRequestSize(Long requestSize) {
        this.requestSize = requestSize;
    }

    public Long getResponseSize() {
        return responseSize;
    }

    public void setResponseSize(Long responseSize) {
        this.responseSize = responseSize;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}

