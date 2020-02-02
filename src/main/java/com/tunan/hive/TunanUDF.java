package com.tunan.hive;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @description:
 * @author: tunan
 * @create: 2020-01-31 16:29
 * @since: 1.0.0
 **/
public class TunanUDF extends UDF {

    public void printf(){
        System.out.println("老全，你要老婆不，要老婆给你送老婆");
    }
    public void printf(String name){
        System.out.println(name+"，你要老婆不，要老婆给你送老婆");
    }

    public static void main(String[] args) {
        TunanUDF tunanUDF = new TunanUDF();
        tunanUDF.printf();
        tunanUDF.printf("老李");
    }
}