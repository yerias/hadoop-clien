package com.tunan.hadoop.ip;

import com.tunan.ip.ip2.IPLocation;
import com.tunan.ip.ip2.IPSeeker;
import org.junit.Test;

public class IPTestApp {

    @Test
    public void testIp(){
        //指定纯真数据库的文件名，所在文件夹
        IPSeeker ip=new IPSeeker("qqwry.dat","ip/");
        IPLocation ipLocation = ip.getIPLocation("58.20.43.13");
        ipLocation.setIPLocationInfo(ipLocation.getCountry());
        //测试IP 58.20.43.13
        System.out.println(ipLocation.getCountry()+":"+ipLocation.getProvince()+":"+ipLocation.getCity()+":"+ipLocation.getArea());
    }

}
