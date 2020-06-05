package com.tunan.data;


import com.tunan.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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

    private static Logger logger = LoggerFactory.getLogger(MakeData.class);

/*  String time;    //时间
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
    public static void main(String[] args) throws IOException {

        String file = "E:\\Java\\spark\\tunan-spark\\tunan-spark-core\\ip\\access.txt";

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(file),true)));

        MakeData makeData = new MakeData();
        int max = 5000000;
        for (int i = 0; i < max; i++) {
            String line = makeData.log_down();
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }
    // 造数据 日志下载
    public String log_down(){

        Random r = new Random();

        String time;    //时间
        StringBuilder ip;      //访问ip
        String x_ip;    //代理ip
        Long response_time;  //响应时间
        String referer; //来源
        String method;  //请求方式
        String uri;     //访问url
        int http_code;  //http响应码
        String request_size;  //请求大小
        String  response_size; //响应大小
        String cache;       //命中状态


        //时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss");
        //[09/02/2020:14:31:50 +0800]
        time = format.format(date);

        //湖南
        String ip_1 = String.valueOf(r.nextInt(255));
        String ip_2 = String.valueOf(r.nextInt(255));
        String ip_3 = String.valueOf(r.nextInt(255));
        String ip_4 = String.valueOf(r.nextInt(255));

        ip = new StringBuilder();
        ip.append(ip_1).append(".").append(ip_2).append(".").append(ip_3).append(".").append(ip_4);
        //代理ip
        x_ip = "-";

        //响应时间
        response_time = Long.valueOf(r.nextInt(500));

        //来源
        referer = "-";

        //请求方式
        String[] methods = {"get","put"};
        int i = new Random().nextInt(10);
        if (i%2==0){
            method = "get";
        }else {
            method = "put";
        }

        //访问url
        String[] uris = {"http://www.ruozedata.com",
                "https://ruoze.ke.qq.com",
                "https://www.aiqiyi.com/video/av76542615?wd=flume",
                "https://www.baidu.com/video/av80522857?wd=apache",
                "https://www.tianmao.com/video/av73376233?wd=hive",
                "https://www.jd.com/video/av52167219?wd=spark",
                "https://www.taobao.com/video/av30031910?wd=flink",
                "https://www.bilibili.com/video/av34829124?wd=yarn"};
        int anInt = new Random().nextInt(100);
        int r1 = anInt % 8;
        uri = uris[r1];

        //http响应码
        int httpR = new Random().nextInt(10);
        if (httpR % 4 ==0){
            http_code = 400;
        }else {
            http_code = 200;
        }

        //request_size
        //request_size = Long.valueOf(new Random().nextInt(100));
        request_size =String.valueOf(r.nextInt(10000));

        //response_size
        int i1 = r.nextInt(10);
        if (i1 % 4 ==0){
            response_size ="-";
        }else{
            response_size =String.valueOf(r.nextInt(10000));
        }

        int cacheR = new Random().nextInt(1000);
        if (cacheR % 4 ==0){
            cache = "MISS";
        }else{
            cache = "HIT";
        }

        AccessLog log = new AccessLog(time, ip.toString(), x_ip, response_time, referer, method, uri, http_code, request_size, response_size, cache);
        return log.toString();
    }
}

