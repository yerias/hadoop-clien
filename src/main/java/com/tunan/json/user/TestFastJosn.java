package com.tunan.json.user;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: tunan
 * @create: 2020-03-07 09:58
 * @since: 1.0.0
 **/
public class TestFastJosn {

    public static void main(String[] args) {
//        objectToJson();
        JsonToObject();
    }



    //java对象转 json字符串
    static public void objectToJson(){
        //简单java类转json
        User user = new User("tunan","123456");
        String userJson = JSON.toJSONString(user);
        System.out.println("简单java类转字符串： "+userJson);

        //List<Object>转Json字符串
        User user1 = new User("xiaoqi", "961228");
        User user2 = new User("tunan", "123456");
        List<User> users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        String listJson = JSON.toJSONString(users);
        System.out.println("List<Object>转Json字符串： "+listJson);

        //复杂java转json字符串
        UserGroup userGroup = new UserGroup("student", users);
        String userGroupJson = JSON.toJSONString(userGroup);
        System.out.println("复杂java转json字符串： "+userGroupJson);
    }


    //Json字符串 转Java类
    //注：字符串中使用双引号需要转义 (" --> \"),这里使用的是单引号
    static public void JsonToObject(){
        /*
         * json字符串转简单java对象
         * 字符串：{"password":"123456","username":"dmego"}
         */
        String userJson = "{'password':'123456','username':'dmego'}";
        User user = JSON.parseObject(userJson, User.class);
        System.out.println("json字符串转简单java对象: "+user.toString());

        /*
         * json字符串转List<Object>对象
         * 字符串：[{"password":"123123","username":"zhangsan"},{"password":"321321","username":"lisi"}]
         */
        String listUserJson = "[{'password':'123123','username':'zhangsan'},{'password':'321321','username':'lisi'}]";
        List<User> userList = JSON.parseArray(listUserJson, User.class);
        System.out.println("json字符串转List<Object>对象: "+userList.toString());

        /*json字符串转复杂java对象
         * 字符串：{"name":"userGroup","users":[{"password":"123123","username":"zhangsan"},{"password":"321321","username":"lisi"}]}
         * */
        String userGroupJson = "{'name':'userGroup','users':[{'password':'123123','username':'zhangsan'},{'password':'321321','username':'lisi'}]}";
        UserGroup userGroup = JSON.parseObject(userGroupJson, UserGroup.class);
        System.out.println("json字符串转复杂java对象: "+userGroup.toString());
    }

}

