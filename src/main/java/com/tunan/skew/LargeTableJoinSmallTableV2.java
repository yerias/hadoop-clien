package com.tunan.skew;

import com.tunan.hadoop.pojo.JoinMain;
import com.tunan.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class LargeTableJoinSmallTableV2 extends Configured implements Tool {

    private static String in = "/data/emp.txt";
    private static String out = "/out";


    public static void main(String[] args) throws Exception {
        FileSystem.enableSymlinks();
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://hadoop:9000");
        conf.set("dfs.client.use.datanode.hostname", "true");


        int run = ToolRunner.run(conf, new LargeTableJoinSmallTableV2(), args);
        System.exit(run);
    }

    @Override
    public int run(String[] strings) throws Exception {
        Configuration conf = super.getConf();
        FileUtil.checkFileIsExists(conf, out);

        Job job = Job.getInstance(conf);

        job.setJarByClass(LargeTableJoinSmallTableV2.class);

        job.setMapperClass(LagerJoinSmallMapper.class);

        job.setNumReduceTasks(0);


        //job.addCacheFile(new Path("/data/dept.txt#dept.txt").toUri());
        //new Path().toUri() 这种方式不能创建链接，不适用于hdfs上面的路径
        job.addCacheFile(new URI("/data/dept.txt#dept.txt"));

        job.setOutputKeyClass(Text.class);

        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job, new Path(in));
        FileOutputFormat.setOutputPath(job, new Path(out));

        boolean b = job.waitForCompletion(true);

        return b ? 0 : 1;
    }

    public static class LagerJoinSmallMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
        private static Map<String, String> map = null;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {

            map = new HashMap<>();
            if (context.getCacheFiles() != null && context.getCacheFiles().length > 0) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("dept.txt"))));
                String line = "";
                while (StringUtils.isNotEmpty(line = reader.readLine())) {
                    //10	ACCOUNTING	1700
                    String[] split = line.split("\t");
                    map.put(split[0], split[1]);
                }
                IOUtils.closeStream(reader);
            }
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            JoinMain main = new JoinMain();
            //7369	SMITH	CLERK	7902	1980-12-17	800.00  0.0 20
            String[] split = value.toString().split("\t");
            if (split.length == 8) {
                main.setEmpno(Integer.parseInt(split[0].trim()));
                main.setEname(split[1]);
                main.setDeptno(Integer.parseInt(split[split.length - 1].trim()));
                main.setDname(map.get(split[split.length - 1]));
                context.write(new Text(main.toString()), NullWritable.get());
            }

        }
    }


}
