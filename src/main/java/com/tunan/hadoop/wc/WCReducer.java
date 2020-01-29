package com.tunan.hadoop.wc;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * @description: reduce端处理数据的分区合并
 * @author: tunan
 * @create: 2020-01-23 15:00
 * @since: 1.0.0
 **/
public class WCReducer extends Reducer<Text,IntWritable,Text,IntWritable> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count=0;
        Iterator<IntWritable> iterator = values.iterator();
        while(iterator.hasNext()) {
            IntWritable next = iterator.next();
            //注意点3
            count += next.get();
        }
        //注意点4
        context.write(key, new IntWritable(count));
    }
}

