package com.tunan.skew;

import com.tunan.utils.FileUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.chain.ChainReducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.Random;

/**
 * @description:
 * @author: tunan
 * @create: 2020-02-04 14:50
 * @since: 1.0.0
 **/
public class GroupByResolveSkew extends Configured implements Tool {
    private static Random r = null;

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        int run = ToolRunner.run(conf, new GroupByResolveSkew(), args);
        System.exit(run);
    }

    @Override
    public int run(String[] strings) throws Exception {
        String in = "E:\\Java\\hadoop\\hadoop-client\\data\\skew\\access.txt";
        String out1 = "E:\\Java\\hadoop\\hadoop-client\\out1";
        String out2 = "E:\\Java\\hadoop\\hadoop-client\\out2";

        Configuration conf = super.getConf();
        FileUtil.checkFileIsExists(conf, out1);
        FileUtil.checkFileIsExists(conf, out2);

        Job job1 = Job.getInstance(conf);
        Job job2 = Job.getInstance(conf);

        job1.setJarByClass(GroupByResolveSkew.class);
        job2.setJarByClass(GroupByResolveSkew.class);

        ChainMapper.addMapper(job1, GroupByResolveSkew.incRanDomMapper.class, LongWritable.class, Text.class, Text.class, IntWritable.class, conf);
        ChainReducer.setReducer(job1, GroupByResolveSkew.incRanDomReducer.class, Text.class, IntWritable.class, Text.class, IntWritable.class, conf);

        ChainMapper.addMapper(job2, GroupByResolveSkew.decRanDomMapper.class, LongWritable.class, Text.class, Text.class, IntWritable.class, conf);
        ChainReducer.setReducer(job2, GroupByResolveSkew.decRanDomReduver.class, Text.class, IntWritable.class, Text.class, IntWritable.class, conf);


        FileInputFormat.setInputPaths(job1, new Path(in));
        FileOutputFormat.setOutputPath(job1, new Path(out1));
        FileInputFormat.setInputPaths(job2, new Path(out1));
        FileOutputFormat.setOutputPath(job2, new Path(out2));

        //提交job1和job2 job1-->job2 必须按照顺序提交
        boolean b = job1.waitForCompletion(true);
        if (b) {
            boolean b1 = job2.waitForCompletion(true);
            return b1 ? 0 : 1;
        }
        return 1;
    }

    public static class incRanDomMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        @Override
        protected void setup(Context context) {
            //初始化对象
            r = new Random();
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            //把数据读出来，加盐  www.baidu.com   2
            String[] line = value.toString().split("\t");
            String incR = r.nextInt(10) +"_"+line[0];
            int number = Integer.parseInt(line[1]);
            context.write(new Text(incR), new IntWritable(number));
        }

        @Override
        protected void cleanup(Context context) {
            if (r != null) {
                //回收对象
                r = null;
            }
        }
    }

    public static class incRanDomReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sums = 0;
            for (IntWritable sum : values) {
                sums += sum.get();
            }
            context.write(key, new IntWritable(sums));
        }
    }

    public static class decRanDomMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        //去盐 聚合

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] line = value.toString().split("\t");
            String decWord = line[0].split("_")[1];
            context.write(new Text(decWord), new IntWritable(Integer.parseInt(line[1])));
        }
    }

    public static class decRanDomReduver extends Reducer<Text, IntWritable, Text, IntWritable> {
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sums = 0;
            for (IntWritable sum : values) {
                sums += sum.get();
            }
            context.write(key, new IntWritable(sums));
        }
    }
}

