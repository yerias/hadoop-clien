package com.tunan.skew;

import com.amazonaws.services.dynamodbv2.xspec.M;
import com.tunan.hadoop.pojo.JoinMain;
import com.tunan.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
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

public class LargeTableJoinSmallTable extends Configured implements Tool {

    private static String in = "E:\\Java\\hadoop\\hadoop-client\\data\\join\\emp.txt";
    private static String out = "E:\\Java\\hadoop\\hadoop-client\\out";


    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        int run = ToolRunner.run(conf, new LargeTableJoinSmallTable(), args);
        System.exit(run);
    }

    @Override
    public int run(String[] strings) throws Exception {
        Configuration conf = super.getConf();
        FileUtil.checkFileIsExists(conf, out);

        Job job = Job.getInstance(conf);

        job.setJarByClass(LargeTableJoinSmallTable.class);

        job.setMapperClass(LagerJoinSmallMapper.class);

        job.setNumReduceTasks(0);

        job.addCacheFile(new Path("E:\\Java\\hadoop\\hadoop-client\\data\\join\\dept.txt").toUri());
//        job.addCacheFile(new URI("file://"));
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
            String path = context.getCacheFiles()[0].getPath();

            map = new HashMap<>();

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String line = "";
            while (StringUtils.isNotEmpty(line = reader.readLine())) {
                //10	ACCOUNTING	1700
                String[] split = line.split("\t");
                map.put(split[0],split[1]);
            }
            IOUtils.closeStream(reader);
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            JoinMain main = new JoinMain();
            //7369	SMITH	CLERK	7902	1980-12-17	800.00  0.0 20
            String[] split = value.toString().split("\t");
            if (split.length == 8){
                main.setEmpno(Integer.parseInt(split[0].trim()));
                main.setEname(split[1]);
                main.setDeptno(Integer.parseInt(split[split.length-1].trim()));
                main.setDname(map.get(split[split.length-1]));
                context.write(new Text(main.toString()),NullWritable.get());
            }

        }
    }
}
