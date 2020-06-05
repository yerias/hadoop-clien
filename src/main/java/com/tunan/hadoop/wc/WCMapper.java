package com.tunan.hadoop.wc;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @description: map端处理数据分区排序
 * @author: tunan
 * @create: 2020-01-23 14:23
 * @since: 1.0.0
 **/
public class WCMapper extends Mapper<LongWritable,Text,Text, IntWritable> {

    //注意点1
    private Text k = new Text();
    private IntWritable v = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String name = context.getConfiguration().get("name");

        String[] words = value.toString().split(",");
        for (String word: words) {
            //注意点2
            k.set(word);
            context.write(new Text(word),v);
        }
    }
}

