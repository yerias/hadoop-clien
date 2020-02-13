package com.tunan.ip.ip1;

import com.difeng.qqwry2.IPLocation;
import com.difeng.qqwry2.Location;

/**
 * @description:
 * @author: tunan
 * @create: 2020-02-10 17:34
 * @since: 1.0.0
 **/
public class IpParser {

    public static void main(String[] args) throws Exception {
        String ip = "121.196.220.143";      //120.196.145.58
        String parseIP = parseIP(ip);
        System.out.println(parseIP);

    }
    public static String parseIP(String ip) throws Exception {
        final IPLocation ipLocation = new IPLocation(IPLocation.class.getResource("/qqwry.dat").getPath());
        Location loc = ipLocation.fetchIPLocation(ip);
        return loc.country +":"+ loc.area;
    }
}

