package com.tunan.json.weather;

import com.alibaba.fastjson.JSON;
import com.tunan.hadoop.utils.FileUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;


/**
 * @description:
 * @author: tunan
 * @create: 2020-02-09 19:37
 * @since: 1.0.0
 **/
public class Mapper extends Configured implements Tool {

    private String in = "data/json/json2.txt";
    private String out = "out";

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        int run = ToolRunner.run(conf, new Mapper(), args);
        System.exit(run);
    }

    @Override
    public int run(String[] strings) throws Exception {

        Configuration conf = super.getConf();
        FileUtil.checkFileIsExists(conf,out);

        Job job = Job.getInstance(conf);

        job.setJarByClass(Mapper.class);

        job.setMapperClass(WeatherMapper.class);

        job.setNumReduceTasks(0);

        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Weather.class);

        job.setNumReduceTasks(0);

        FileInputFormat.setInputPaths(job, new Path(in));
        FileOutputFormat.setOutputPath(job, new Path(out));

        boolean b = job.waitForCompletion(true);
        return b ? 0 : 1;
    }

    private static class WeatherMapper extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, NullWritable,Weather > {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String json = value.toString();
            Weather weather = JSON.parseObject(json, Weather.class);
            context.write(NullWritable.get(), weather);
        }
    }
}

