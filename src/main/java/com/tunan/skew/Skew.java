package com.tunan.skew;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @description: 模拟解决数据倾斜问题
 * @author: tunan
 * @create: 2020-02-03 01:26
 * @since: 1.0.0
 **/

public class Skew {
    private List<String> newList = new ArrayList<>();
    private Random r = new Random();

    public void incRandom(List list){
        newList.clear();
        list.forEach( word ->{
            int i = r.nextInt(10);
            newList.add(i+"_"+word);
        });
        newList.forEach(System.out::println);
    }
    public void decRandom(){
        newList.forEach(word->{
            System.out.println(word.substring(word.lastIndexOf("_")+1));
        });
    }
    public static void main(String[] args) {
        Skew skew = new Skew();
        List<String> arr = new ArrayList<>();
        arr.add("老王");
        arr.add("狗子");
        arr.add("张三");
        skew.incRandom(arr);
        System.out.println("-------------------");
        skew.decRandom();
    }
}

