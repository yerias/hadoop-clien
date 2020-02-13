package com.tunan.job;

import com.tunan.hadoop.utils.FileUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

/**
 * @description: 对文件中的数据计数
 * @author: tunan
 * @create: 2020-02-03 21:50
 * @since: 1.0.0
 **/
public class MyCount extends Configured implements Tool {
    String in = "data/skew/access.txt.txt";
    String out = "out";

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        int run = ToolRunner.run(conf, new MyCount(), args);
        System.exit(run);
    }

    @Override
    public int run(String[] strings) throws Exception {
        Configuration conf = super.getConf();
        FileUtil.checkFileIsExists(conf,out);

        Job job = Job.getInstance(conf);

        job.setJarByClass(MyCount.class);

        job.setMapperClass(CountMapper.class);
        job.setReducerClass(CountReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //job.setNumReduceTasks(0);
        //job.setCombinerClass(CountReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.setInputPaths(job, new Path(in));
        FileOutputFormat.setOutputPath(job, new Path(out));

        boolean b = job.waitForCompletion(true);

        return b ? 0 : 1;
    }

    public static class CountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        private IntWritable one = new IntWritable(1);

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            context.write(new Text("计数"), one);
        }
    }

    private static int count;
    public static class CountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private int count;

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            values.forEach(value -> count += value.get());
            context.write(new Text("计数"), new IntWritable(count));
        }
    }
}

