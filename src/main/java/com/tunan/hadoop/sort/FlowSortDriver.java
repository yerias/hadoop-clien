package com.tunan.hadoop.sort;

import com.tunan.utils.FileUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
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
 * @description: 流量分析
 * @author: tunan
 * @create: 2020-01-25 22:14
 * @since: 1.0.0
 **/
public class FlowSortDriver extends Configured implements Tool {
    private String in = "data/flow.txt";
    private String out = "out/";

    //入口函数
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        int run = ToolRunner.run(conf, new FlowSortDriver(), args);
        System.exit(run);
    }

    //推介的运行方法
    @Override
    public int run(String[] strings) throws Exception {
        //获得configuration
        Configuration conf = super.getConf();

        //检查文件夹
        FileUtil.checkFileIsExists(conf, out);

        //使用新方法这里怎么操作?
        Job job = Job.getInstance(conf);

        //设置驱动类
        job.setJarByClass(FlowSortDriver.class);

        //设置Map/Reducer类
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);

        //设置Map参数类型
        job.setMapOutputKeyClass(FlowSort.class);
        job.setMapOutputValueClass(Text.class);

        job.setNumReduceTasks(3);

        //设置Reducer参数类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowSort.class);

        //设置文件的输入输出
        FileInputFormat.setInputPaths(job, new Path(in));
        FileOutputFormat.setOutputPath(job, new Path(out));

        //提交任务
        boolean b = job.waitForCompletion(true);
        return b ? 0 : 1;
    }

    public static class FlowMapper extends Mapper<LongWritable, Text, FlowSort,Text> {
        /**
         * @param key
         * @param value
         * @param context
         * @return : void
         * @describe : 实现流量分析的map接口
         * @author : tunan
         * @date : 2020/1/25 22:35
         * 输入、处理、输出
         */
        @Override               //key,value,context
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            //输入
            String[] words = value.toString().split("\t");
            //做简单处理
            int len = words.length;
            if (len >= 9) {
                String phone = words[1];
                Long up = Long.parseLong(words[len - 3]);
                Long down = Long.parseLong(words[len - 2]);
                //输出
                context.write(new FlowSort(phone, up, down),new Text(phone));
            }
        }
    }

    public static class FlowReducer extends Reducer<FlowSort, Text, Text, FlowSort> {
        @Override
        protected void reduce(FlowSort key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for (Text text : values) {
                context.write(text,key);
            }
        }
    }
}