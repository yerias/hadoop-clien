package com.tunan.random;

import java.util.Random;

/**
 * @description:
 * @author: tunan
 * @create: 2020-02-14 17:45
 * @since: 1.0.0
 **/
public class RandomTest {

    public static void main(String[] args) {
        Random r = new Random();
//        Random r = new Random(10);
        System.out.println("int型随机数 " + r.nextInt());
        System.out.println("int型随机数 " + r.nextInt());
        System.out.println("int型随机数 " + r.nextInt());
        System.out.println("int型固定范围随机数 " + r.nextInt(10));
        System.out.println("int型固定范围随机数 " + r.nextInt(10));
        System.out.println("int型固定范围随机数 " + r.nextInt(10));
        System.out.println("double型随机数 " + r.nextDouble());
        System.out.println("double型随机数 " + r.nextDouble());
        System.out.println("double型随机数 " + r.nextDouble());
        System.out.println("float型随机数 " + r.nextFloat());
        System.out.println("float型随机数 " + r.nextFloat());
        System.out.println("float型随机数 " + r.nextFloat());
    }

}

