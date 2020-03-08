package com.tunan.ip;

import com.tunan.utils.FileUtil;
import com.tunan.utils.IpParseUtil;
import org.apache.commons.lang3.StringUtils;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @description:
 * @author: tunan
 * @create: 2020-02-17 12:41
 * @since: 1.0.0
 **/
public class ipParseDriver extends Configured implements Tool {

    static String ipAddr;
    private static Logger logger = LoggerFactory.getLogger(ipParseDriver.class);

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        int run = ToolRunner.run(conf, new ipParseDriver(), args);
        System.exit(run);
    }


    @Override
    public int run(String[] args) throws Exception {

        String in = "ip/ip.txt";
        String out = "out";

        /*String in = args[0];
        String out =args[1];*/
        try {
            ipAddr = args[2];
        } catch (Exception e) {
            ipAddr ="";
        }

        Configuration conf = super.getConf();

        FileUtil.checkFileIsExists(conf, out);

        Job job = Job.getInstance(conf);

        job.setJarByClass(ipParseDriver.class);

        job.setMapperClass(ipParseMapper.class);

        job.setNumReduceTasks(0);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job, new Path(in));
        FileOutputFormat.setOutputPath(job, new Path(out));

        boolean b = job.waitForCompletion(true);

        return b ? 0 : 1;

    }

    public static class ipParseMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String ip = value.toString();
            String[] ipParse;
            if (StringUtils.isEmpty(ipAddr)){
                ipParse = IpParseUtil.IpParse(ip);
            }else{
                ipParse = IpParseUtil.IpParse(ip);
            }

            logger.info("国家: " + ipParse[0] + "\t" + "省份: " + ipParse[1] + "\t" + "城市: " + ipParse[2] + "\t" + "运营商: " + ipParse[3]);
            Text text = new Text("国家: "+ipParse[0]+"\t"+"省份: "+ipParse[1]+"\t"+"城市: "+ipParse[2]+"\t"+"运营商: "+ipParse[3]);
            context.write(text, NullWritable.get());
        }
    }
}

