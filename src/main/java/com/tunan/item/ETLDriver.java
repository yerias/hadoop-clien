package com.tunan.item;


import com.tunan.utils.AccessUtils;
import com.tunan.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.CounterGroup;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @description:
 * @author: tunan
 * @create: 2020-02-16 01:39
 * @since: 1.0.0
 **/
public class ETLDriver extends Configured implements Tool {

    static Configuration conf;
    private static Logger logger = LoggerFactory.getLogger(ETLDriver.class);

    public static void main(String[] args) throws Exception {
        //System.setProperty("HADOOP_USER_NAME","hadoop");
        FileSystem.enableSymlinks();
        conf = new Configuration();
        /*conf.set("fs.defaultFS","hdfs://aliyun:9000");
        conf.set("dfs.client.use.datanode.hostname", "true");*/
        int run = ToolRunner.run(conf, new ETLDriver(), args);
        System.exit(run);
    }

    @Override
    public int run(String[] args) throws Exception {
        String in = args[0];
        String out = args[1];
        /*String in = "ip/access.txt";
        String out = "out";*/
       /* String in = "/item/offline-dw/raw/access/20200217";
        String out = "/out";*/

        Configuration conf = super.getConf();

        FileUtil.checkFileIsExists(conf, out);

        Job job = Job.getInstance(conf);

        job.setJarByClass(ETLDriver.class);

        //添加一个放入内存的小文件
        job.addCacheFile(new URI("hdfs://hadoop:9000/item/offline-dw/data/userid.txt#userid.txt"));

        job.setMapperClass(ETLMapper.class);

        job.setNumReduceTasks(0);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job, new Path(in));
        FileOutputFormat.setOutputPath(job, new Path(out));

        boolean b = job.waitForCompletion(true);

        //通过迭代器获取mapper中的计数器
        CounterGroup group = job.getCounters().getGroup("ETL");
        Iterator<Counter> iterator = group.iterator();
        while (iterator.hasNext()) {
            Counter counter = iterator.next();
            System.out.println(counter.getName() + "====>" + counter.getValue());
        }
        return b ? 0 : 1;
    }

    public static class ETLMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
        Access access;
        Map<String, String> map;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            logger.info("=================进入setup方法====================");
            map = new HashMap<>();
            if (context.getCacheFiles() != null && context.getCacheFiles().length > 0) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("userid.txt")));

                String line;
                while (StringUtils.isNotEmpty(line = reader.readLine())) {
                    String[] lines = line.split(",");
                    map.put(lines[2], lines[0]);

                    logger.info(lines[2] + "=========>" + lines[0]);
                }
                IOUtils.closeStream(reader);
            }
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            context.getCounter("ETL", "TOTALS").increment(1);

            String line = value.toString();
            //保护
            try {
                access = AccessUtils.logParse(line);

                String userId = map.get(access.getDomain());
                if (StringUtils.isEmpty(userId)) {
                    access.setUserId("-");
                } else {
                    access.setUserId(userId);
                }

                context.getCounter("ETL", "SUCCEED").increment(1);
                context.write(new Text(this.access.toString()), NullWritable.get());
            } catch (Exception e) {
                //e.printStackTrace();
                context.getCounter("ETL", "FAILURE").increment(1);
            }
            //不保护
            /*access = AccessUtils.logParse(line, ipAddr);
            context.write(new Text(this.access.toString()),NullWritable.get());*/
        }
    }
}

