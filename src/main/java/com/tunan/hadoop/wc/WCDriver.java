package com.tunan.hadoop.wc;

import com.tunan.hadoop.utils.FileUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


import java.io.IOException;

/**
 * @description:
 * @author: tunan
 * @create: 2020-01-23 15:17
 * @since: 1.0.0
 **/

public class WCDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

     /*   String in = args[0];
        String out = args[1];*/

        String in = "data/wordcount.txt";
        String out = "out";

        Configuration conf = new Configuration();

        //检查文件
        FileUtil.checkFileIsExists(conf,out);

        //获取job
        Job job = Job.getInstance();

        //获取驱动Jar
        job.setJarByClass(WCDriver.class);

        //获取Map和Reduce类
        job.setMapperClass(WCMapper.class);
        job.setReducerClass(WCReducer.class);

        //获取Map的输出类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //获取Reduce的数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //获取输入输出
        FileInputFormat.setInputPaths(job, new Path(in));
        FileOutputFormat.setOutputPath(job, new Path(out));

        //提交
        boolean b = job.waitForCompletion(true);
        System.out.println(b ? 0 : 1);
    }
}