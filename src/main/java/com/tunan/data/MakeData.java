package com.tunan.data;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @description:
 * @author: tunan
 * @create: 2020-02-09 17:52
 * @since: 1.0.0
 **/

/*
"http://www.ruozedata.com",
"https://ruoze.ke.qq.com",
"https://www.bilibili.com/video/av76542615",
"https://www.bilibili.com/video/av80522857",
"https://www.bilibili.com/video/av73376233",
"https://www.bilibili.com/video/av52167219",
"https://www.bilibili.com/video/av30031910",
"https://www.bilibili.com/video/av34829124"


[09/02/2020:14:31:50 +0800]	210.35.184.9	-	594	-	POST	https://www.bilibili.com/video/av52167219	404	26	-	MISS
[09/02/2020:14:31:50 +0800]	222.26.76.40	-	271	-	GET https://www.bilibili.com/video/av80522857
 */
public class MakeData {

/*    String time;    //时间
    String ip;      //访问ip
    String x_ip;    //代理ip
    Long response_time;  //响应时间
    String referer; //来源
    String method;  //请求方式
    String uri;     //访问url
    int http_code;  //http响应码
    Long request_size;  //请求大小
    Long response_size; //响应大小
    String cache;       //命中状态*/



    //造数据
    public static void main(String[] args) {

        MakeData makeData = new MakeData();
        makeData.log_down();

    }

    // 造数据 日志下载
    public void log_down(){
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


        //时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss");
        //[09/02/2020:14:31:50 +0800]
        time = format.format(date);
        //210.35.184.9  61.128.0.0 143.255.255
        //String ip_1 = new Random().nextInt(3)
        //ip
        ip = "210.35.184.9";

        //代理ip
        x_ip = "-";

        //响应时间
        response_time = Long.valueOf(new Random().nextInt(100));

        //来源
        referer = "-";

        //请求方式
        String[] methods = {"get","put"};
        int i = new Random().nextInt(1);
        if (i%2==0){
            method = "get";
        }else {
            method = "put";
        }

        //访问url
        String[] uris = {"http://www.ruozedata.com",
                "https://ruoze.ke.qq.com",
                "https://www.bilibili.com/video/av76542615",
                "https://www.bilibili.com/video/av80522857",
                "https://www.bilibili.com/video/av73376233",
                "https://www.bilibili.com/video/av52167219",
                "https://www.bilibili.com/video/av30031910",
                "https://www.bilibili.com/video/av34829124"};
        int anInt = new Random().nextInt(10);
        int r = anInt % 8;
        uri = uris[r];

        //http响应码
        int httpR = new Random().nextInt(10);
        if (httpR % 2 ==0){
            http_code = 200;
        }else {
            http_code = 400;
        }

        //request_size
        //request_size = Long.valueOf(new Random().nextInt(100));
        request_size =Long.valueOf(new Random().nextInt(10000));

        //response_size
        response_size =Long.valueOf(new Random().nextInt(10000));

        int cacheR = new Random().nextInt();
        if (cacheR % 5 ==0){
            cache = "-";
        }else{
            cache = "1";
        }

        AccessLog log = new AccessLog(time, ip, x_ip, response_time, referer, method, uri, http_code, request_size, response_size, cache);
        System.out.println(log);
    }

}

