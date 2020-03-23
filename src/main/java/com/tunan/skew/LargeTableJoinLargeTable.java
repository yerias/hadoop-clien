package com.tunan.skew;

import com.tunan.hadoop.pojo.JoinMain;
import com.tunan.hadoop.pojo.SkewResult;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class LargeTableJoinLargeTable extends Configured implements Tool {




    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        int run = ToolRunner.run(conf, new LargeTableJoinLargeTable(), args);
        System.exit(run);
    }

    @Override
    public int run(String[] strings) throws Exception {
        String in = "E:\\Java\\hadoop\\hadoop-client\\data\\join\\domain.txt";
        String out = "E:\\Java\\hadoop\\hadoop-client\\out";
        Configuration conf = super.getConf();
        FileUtil.checkFileIsExists(conf, out);

        Job job = Job.getInstance(conf);

        job.setJarByClass(LargeTableJoinLargeTable.class);

        job.setMapperClass(LagerJoinSmallMapper.class);

        job.setNumReduceTasks(0);

        job.addCacheFile(new Path("E:\\Java\\hadoop\\hadoop-client\\out2\\part-r-00000").toUri());
//        job.addCacheFile(new URI("E:\\Java\\hadoop\\hadoop-client\\out2\\part-r-00000"));

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
                //001	tunan
                String[] split = line.split("\t");
                map.put(split[0],split[1]);
            }
            IOUtils.closeStream(reader);
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            SkewResult result = new SkewResult();
            //001	100001	www.baidu.com
            String[] line = value.toString().split("\t");
            if (line.length == 3){
                result.setId(line[0]);
                result.setName(map.get(line[0]));
                result.setDomain(line[2]);
                context.write(new Text(result.toString()),NullWritable.get());
            }

        }
    }
}
