package com.tunan.ip;

import com.tunan.utils.IpParseUtil;

import java.util.List;

/**
 * @description:
 * @author: tunan
 * @create: 2020-02-17 02:13
 * @since: 1.0.0
 **/
public class IpApplication {

    public static void main(String[] args) {

        String ip = "121.196.143.220";
        String[] ipParse = IpParseUtil.IpParse(ip);
            System.out.println("国家: "+ ipParse[0]);
            System.out.println("省份: "+ ipParse[1]);
            System.out.println("城市: "+ ipParse[2]);
            System.out.println("运营商: "+ ipParse[3]);
    }
}

