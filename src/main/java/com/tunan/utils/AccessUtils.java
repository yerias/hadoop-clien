package com.tunan.utils;

import com.tunan.item.Access;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @description:
 * @author: tunan
 * @create: 2020-02-16 02:58
 * @since: 1.0.0
 **/
public class AccessUtils {


    public static Access logParse(String line) throws ParseException {
        Access access = new Access();
        //[16/02/2020:00:51:12 +0800]
        // 175.5.81.162
        // -
        // 249
        // -
        // get
        // https://www.bilibili.com/video/av76542615
        // 400
        // 5829
        // 7541
        // MISS

        String[] split = line.split("\t");
        //时间
        String time = split[0];
        SimpleDateFormat format = new SimpleDateFormat("[dd/MM/yyyy:HH:mm:ss +0800]");
        Date date = format.parse(split[0]);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        //year
        String year =String.valueOf(calendar.get(Calendar.YEAR));
        access.setYear(year);

        //month
        int  month = calendar.get(Calendar.MONTH)+1;
        access.setMonth(month < 10 ? "0"+month:String.valueOf(month));

        //day
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        access.setDay(day < 10 ? "0"+day:String.valueOf(day));


        //IP
        String ip = split[1];
        String[] ipParse = IpParseUtil.IpParse(ip);
        //country
        String country = ipParse[0];
        access.setCountry(country);
        //province
        String province = ipParse[1];
        access.setProvince(province);
        //city
        String city = ipParse[2];
        access.setCity(city);
        //area
        String area = ipParse[3];
        access.setArea(area);

        //proxyIp
        String proxyIp = split[2];
        access.setProxyIp(proxyIp);
        //requestTime
        Long reponseTime = Long.parseLong(split[3].trim());
        access.setResponseTime(reponseTime);
        //referer
        String referer = split[4];
        access.setReferer(referer);
        //method
        String method = split[5];
        access.setMethod(method);

        //"http://www.ruozedata.com"
        //"https://www.bilibili.com/video/av76542615"
        String uri = split[6];
        //http
        String[] uris = uri.split("/");
        String http="";
        if (StringUtils.isEmpty(uris[0])){
            http = "-";
        }else if (uris[0].contentEquals("s")){
            http = uris[0].substring(0,5);
        }else{
            http = uris[0].substring(0,4);
        }
        access.setHttp(http);

        //还可以按 "://" 切分

        //domain
        String domain="";
        if (StringUtils.isEmpty(uris[2])){
            domain = "-";
        }else {
            domain = uris[2];
        }
        access.setDomain(domain);


        //path  ==> 空的怎么处理
        String path ="";
        if (uris.length == 3){
            path = "-";
        }else{
            String urlTmp = uris[0] + "//" + uris[2];
            path = uri.substring(urlTmp.length()+1,uri.indexOf("?"));
        }
        access.setPath(path);

        //httpCode
        String httpCode = split[7];
        access.setHttpCode(httpCode);
        //requestSize
        Long requestSize = Long.parseLong(split[8].trim());
        access.setRequestSize(requestSize);
        //responseSize
        //要数据,设默认值
        /*long responseSize = 0L;
        try {
            responseSize = Long.parseLong(split[9].trim());
            access.setResponseSize(responseSize);
        } catch (Exception e) {
            access.setResponseSize(responseSize);
        }*/
        // 不要数据，产生异常直接返回
        Long responseSize = Long.parseLong(split[9].trim());
        access.setResponseSize(responseSize);
        //cache
        String cache = split[10];
        access.setCache(cache);

        //没有try/catch保护代码的话 到不了这里 返回不了数据
        return access;
    }
}

