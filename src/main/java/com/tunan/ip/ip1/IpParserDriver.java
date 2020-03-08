package com.tunan.ip.ip1;

import com.tunan.utils.FileUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

/**
 * @description:
 * @author: tunan
 * @create: 2020-02-12 14:58
 * @since: 1.0.0
 **/
public class IpParserDriver extends Configured implements Tool {
    private String in = "data/access.txt";
    private String out = "out/";

    //入口函数
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        int run = ToolRunner.run(conf, new IpParserDriver(), args);
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
        job.setJarByClass(IpParserDriver.class);

        //设置Map/Reducer类
        //job.setMapperClass(IpParserMapper.class);

        // 设置Partitoner
        job.setNumReduceTasks(0);

        //设置Reducer参数类型
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);

        //设置文件的输入输出
        FileInputFormat.setInputPaths(job, new Path(in));
        FileOutputFormat.setOutputPath(job, new Path(out));

        //提交任务
        boolean b = job.waitForCompletion(true);
        return b ? 0 : 1;
    }

    /*public static class IpParserMapper extends Mapper<LongWritable, Text, NullWritable,Text>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] line = value.toString().split("\t");
            String ip = line[1];
            try {
                String info = parseIP(ip);
                context.write(NullWritable.get(),new Text(info));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static String parseIP(String ip) throws Exception {
            final IPLocation ipLocation = new IPLocation(IPLocation.class.getResource("/qqwry.dat").getPath());
            Location loc = ipLocation.fetchIPLocation(ip);
            return loc.country +":"+ loc.area;
        }
    }*/
}

