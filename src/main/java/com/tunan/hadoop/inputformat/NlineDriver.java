package com.tunan.hadoop.inputformat;

import com.tunan.hadoop.flow.FlowDriver;
import com.tunan.hadoop.pojo.Flow;
import com.tunan.utils.FileUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @description: 多行inputformat
 * @author: tunan
 * @create: 2020-01-26 00:43
 * @since: 1.0.0
 **/
public class NlineDriver extends Configured implements Tool {
    private String in = "data/flow.txt";
    private String out = "out/";

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        int run = ToolRunner.run(conf, new NlineDriver(), args);
        System.exit(run);
    }

    @Override
    public int run(String[] strings) throws Exception {
        Configuration conf = super.getConf();
        Job job = Job.getInstance(conf);

        FileUtil.checkFileIsExists(conf, out);
        //设置驱动类
        job.setJarByClass(NlineDriver.class);

        //设置Map/Reduce类
        job.setMapperClass(FlowDriver.FlowMapper.class);
        job.setReducerClass(FlowDriver.FlowReducer.class);

        //设置Map的参数
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Flow.class);

        //设置Reducer的参数
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Flow.class);

        // 设置指定的InputFormat(重点)
        NLineInputFormat.setNumLinesPerSplit(job, 3);
        job.setInputFormatClass(NLineInputFormat.class);

        //设置输入输出文件
        FileInputFormat.setInputPaths(job, new Path(in));
        FileOutputFormat.setOutputPath(job, new Path(out));

        //提交任务
        boolean b = job.waitForCompletion(true);
        return b?0:1;
    }
}

