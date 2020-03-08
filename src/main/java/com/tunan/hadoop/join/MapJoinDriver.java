package com.tunan.hadoop.join;

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

/**
 * @description:
 * @author: tunan
 * @create: 2020-02-01 23:10
 * @since: 1.0.0
 **/
public class MapJoinDriver extends Configured implements Tool {

    private String in = "data/join/emp.txt";
    private String out = "out";

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        int run = ToolRunner.run(conf, new MapJoinDriver(), args);
        System.exit(run);
    }

    /**
     * @param strings
     * @return : int
     * @describe : 设置配置文件，不用设置reduce
     * @author : tunan
     * @date : 2020/2/1 23:14
     */
    @Override
    public int run(String[] strings) throws Exception {

        Configuration conf = super.getConf();
        FileUtil.checkFileIsExists(conf,out);

        Job job = Job.getInstance(conf);

        job.setJarByClass(MapJoinDriver.class);

        job.addCacheFile(new URI("data/join/dept.txt"));

        job.setMapperClass(MapperJoin.class);

        job.setOutputKeyClass(JoinMain.class);
        job.setOutputValueClass(NullWritable.class);

        job.setNumReduceTasks(0);

        FileInputFormat.setInputPaths(job, new Path(in));
        FileOutputFormat.setOutputPath(job, new Path(out));

        boolean b = job.waitForCompletion(true);
        return b ? 0 : 1;
    }

    private static class MapperJoin extends Mapper<LongWritable, Text, JoinMain, NullWritable> {
        private HashMap<Integer, String> hashMap = new HashMap<>();
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            //得到缓存文件路径
            //String path = context.getCacheFiles()[0].getPath().toString();
            String path = context.getCacheFiles()[0].getPath();
            /*URI[] files = context.getCacheFiles();   //URI 通过getPath()解码 没有toString()方法
            String s = files[0].getPath();*/
            //得到文件
            //File file = new File(cacheFiles[0]);
            //String path = file.getPath();
            //得到文件的流        InputStreamReader将字节转换为字符
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            //读取文件为字符串
            String line ;
            while(StringUtils.isNotEmpty(line=br.readLine())){
                //切分字符串得到字符串数组
                String[] split = line.split("\t");
                hashMap.put(Integer.parseInt(split[0]),split[1]);
        }
            IOUtils.closeStream(br);
        }

        /**
         * @describe :
         * @author : tunan
         * @param key
         * @param value
         * @param context
         * @return : void
         * @date : 2020/2/1 23:38
         */

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] line = value.toString().split("\t");
            if (line.length >= 8){
                Integer empno = Integer.parseInt(line[0].trim());
                String ename = line[1];
                Integer deptno = Integer.parseInt(line[line.length-1].trim());
                String dname = hashMap.get(deptno);
                context.write(new JoinMain(empno,ename,deptno,dname),NullWritable.get());
            }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            super.cleanup(context);
        }
    }
}