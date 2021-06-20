package com.tunan.hadoop.wc;

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
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

/**
 * linux上测试用例
 * hadoop jar hadoop-client-1.0.0.jar com.tunan.hadoop.wc.RedDreamWC /data/redmansionsdream /out 林黛玉 贾探春 薛宝钗 妙玉
 */

public class RedDreamWC extends Configured implements Tool {


    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        int run = ToolRunner.run(conf, new RedDreamWC(), args);
        System.exit(run);
    }

    @Override
    public int run(String[] args) throws Exception {
        String in = args[0];
        String out = args[1];


        Configuration conf = super.getConf();

        for (int i = 2; i < args.length; i++) {
            conf.set("arg"+i,args[i]);
        }

        FileUtil.checkFileIsExists(conf, out);

        //这地方 要放conf配置
        Job job = Job.getInstance(conf);
        job.setJarByClass(RedDreamWC.class);

        job.setMapperClass(RedDreamMapper.class);
        job.setReducerClass(RedDreamReducer.class);

        job.setCombinerClass(RedDreamReducer.class);


        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.setInputPaths(job, new Path(in));
        FileOutputFormat.setOutputPath(job, new Path(out));

        boolean b = job.waitForCompletion(true);
        return b ? 0 : 1;
    }


    public static class RedDreamMapper extends Mapper<LongWritable, Text, Text, IntWritable> {


        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String arg2 = context.getConfiguration().get("arg2");
            String arg3 = context.getConfiguration().get("arg3");
            String arg4 = context.getConfiguration().get("arg4");
            String arg5 = context.getConfiguration().get("arg5");

/*
            List<String> REDLIST = new ArrayList<String>();
                REDLIST.add(arg2);
                REDLIST.add(arg3);
                REDLIST.add(arg4);
                REDLIST.add(arg5);
*/

            String[] lines = value.toString().split(",");
            for (String line : lines) {
                if (line.equals(arg2)) {
                    context.write(new Text(arg2), new IntWritable(1));
                } else if (line.equals(arg3)) {
                    context.write(new Text(arg3), new IntWritable(1));
                } else if (line.equals(arg4)) {
                    context.write(new Text(arg4), new IntWritable(1));
                } else if (line.equals(arg5)) {
                    context.write(new Text(arg5), new IntWritable(1));
                }
            }
        }
    }

    public static class RedDreamReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sums = 0;
            for (IntWritable value : values) {
                int sum = Integer.parseInt(value.toString());
                sums += sum;
            }
            context.write(key, new IntWritable(sums));
        }
    }
}
