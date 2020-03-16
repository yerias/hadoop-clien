package com.tunan.json.role;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @description: 将这个json字符串转化为List对象
 * @author: tunan
 * @create: 2020-03-07 10:47
 * @since: 1.0.0
 **/
public class RoleTest {
    public static void main(String[] args) throws IOException {
        RoleTest roleTest = new RoleTest();
        List<Query> queries = roleTest.JsonToObject();
        queries.forEach(System.out::println);
    }
    public  List<Query> JsonToObject() throws IOException {
        ClassLoader loader = this.getClass().getClassLoader();
        InputStream in = loader.getResourceAsStream("query.json");
        //这里是一次全部读出来了，大数据处理时需要按行读取
        String jsonTxet = IOUtils.toString(in, "utf8");
        List<Query> query = JSON.parseArray(jsonTxet, Query.class);
        return query;
    }
}

