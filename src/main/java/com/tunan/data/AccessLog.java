package com.tunan.data;

/**
 * @description:
 * @author: tunan
 * @create: 2020-02-09 18:11
 * @since: 1.0.0
 **/
public class AccessLog {

    String time;    //时间
    String ip;      //访问ip
    String x_ip;    //代理ip
    Long response_time;  //响应时间
    String referer; //来源
    String method;  //请求方式
    String uri;     //访问url
    int http_code;  //http响应码
    Long request_size;  //请求大小
    Long response_size; //响应大小
    String cache;       //命中状态

    /*
     [09/02/2020:14:31:50 +0800]	210.35.184.9	-	594	-	POST	https://www.bilibili.com/video/av52167219	404	26	-	MISS
     */
    @Override
    public String toString() {
        return "["+time+" +0800]\t"+
                ip+"\t"+
                x_ip+"\t"+
                response_time+"\t"+
                referer+"\t"+
                method+"\t"+
                uri+"\t"+
                http_code+"\t"+
                request_size+"\t"+
                response_size+"\t"+
                cache;
    }

    public AccessLog() {
    }

    public AccessLog(String time, String ip, String x_ip, Long response_time, String referer, String method, String uri, int http_code, Long request_size, Long response_size, String cache) {
        this.time = time;
        this.ip = ip;
        this.x_ip = x_ip;
        this.response_time = response_time;
        this.referer = referer;
        this.method = method;
        this.uri = uri;
        this.http_code = http_code;
        this.request_size = request_size;
        this.response_size = response_size;
        this.cache = cache;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getX_ip() {
        return x_ip;
    }

    public void setX_ip(String x_ip) {
        this.x_ip = x_ip;
    }

    public Long getResponse_time() {
        return response_time;
    }

    public void setResponse_time(Long response_time) {
        this.response_time = response_time;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getHttp_code() {
        return http_code;
    }

    public void setHttp_code(int http_code) {
        this.http_code = http_code;
    }

    public Long getRequest_size() {
        return request_size;
    }

    public void setRequest_size(Long request_size) {
        this.request_size = request_size;
    }

    public Long getResponse_size() {
        return response_size;
    }

    public void setResponse_size(Long response_size) {
        this.response_size = response_size;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }
}

