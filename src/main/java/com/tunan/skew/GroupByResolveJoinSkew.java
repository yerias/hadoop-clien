package com.tunan.skew;

import com.tunan.hadoop.pojo.User;
import com.tunan.utils.FileUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
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
public class GroupByResolveJoinSkew extends Configured implements Tool {
    private static Random r = null;
    private static User user = null;
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        int run = ToolRunner.run(conf, new GroupByResolveJoinSkew(), args);
        System.exit(run);

    }

    @Override
    public int run(String[] strings) throws Exception {
        String in = "E:\\Java\\hadoop\\hadoop-client\\data\\join\\user.txt";
        String out1 = "E:\\Java\\hadoop\\hadoop-client\\out1";
        String out2 = "E:\\Java\\hadoop\\hadoop-client\\out2";

        Configuration conf = super.getConf();
        FileUtil.checkFileIsExists(conf, out1);
        FileUtil.checkFileIsExists(conf, out2);

        Job job1 = Job.getInstance(conf);
        Job job2 = Job.getInstance(conf);

        job1.setJarByClass(GroupByResolveJoinSkew.class);
        job2.setJarByClass(GroupByResolveJoinSkew.class);

        ChainMapper.addMapper(job1, GroupByResolveJoinSkew.incRanDomMapper.class, LongWritable.class, Text.class, Text.class, NullWritable.class, conf);
        ChainReducer.setReducer(job1, GroupByResolveJoinSkew.incRanDomReducer.class, Text.class, NullWritable.class, Text.class, NullWritable.class, conf);

        ChainMapper.addMapper(job2, GroupByResolveJoinSkew.decRanDomMapper.class, LongWritable.class, Text.class, Text.class, NullWritable.class, conf);
        ChainReducer.setReducer(job2, GroupByResolveJoinSkew.decRanDomReducer.class, Text.class, NullWritable.class, Text.class, NullWritable.class, conf);


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

    public static class incRanDomMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
        @Override
        protected void setup(Context context) {
            //初始化对象
            r = new Random();
            user = new User();
        }



        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            //把数据读出来，加盐  001	tunan
            String[] line = value.toString().split("\t");
            String incR = r.nextInt(10) +"_"+line[0];
            user.setId(incR);
            user.setName(line[1]);
            context.write(new Text(user.toString()), NullWritable.get());
        }

        @Override
        protected void cleanup(Context context) {
            if (r != null) {
                //回收对象
                r = null;
            }
        }
    }

    public static class incRanDomReducer extends Reducer<Text, NullWritable, Text, NullWritable> {
        @Override
        protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            context.write(key, NullWritable.get());
        }
    }

    public static class decRanDomMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
        //去盐 聚合

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] line = value.toString().split("\t");
            String decWord = line[0].split("_")[1];
            user.setId(decWord);
            user.setName(line[1]);
            context.write(new Text(user.toString()), NullWritable.get());
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            if (user != null) {
                //回收对象
                user = null;
            }
        }
    }

    public static class decRanDomReducer extends Reducer<Text, NullWritable, Text, NullWritable> {
        @Override
        protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            context.write(key, NullWritable.get());
        }
    }
}

