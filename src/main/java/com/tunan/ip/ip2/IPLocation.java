package com.tunan.ip.ip2;

import org.apache.commons.lang3.StringUtils;

public class IPLocation {
    private String country;//国家
    private String province;//省
    private String city;//市
    private String district;//县
    private String area;//运营商

    public IPLocation() {
        country = area = "";
    }

    public IPLocation getCopy() {
        IPLocation ret = new IPLocation();
        ret.country = country;
        ret.area = area;
        return ret;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setIPLocationInfo(String country){
        if(StringUtils.isNotBlank(country)){
            if(country.contains("省")){
                this.country="中国";
                this.province=country.substring(0,country.indexOf("省")+1);
                this.city=country.substring(country.indexOf("省")+1);
            }
        }
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        //如果为局域网，纯真IP地址库的地区会显示CZ88.NET,这里把它去掉
        if(area.trim().equals("CZ88.NET")){
            this.area="本机或本网络";
        }else{
            this.area = area;
        }
    }

}
