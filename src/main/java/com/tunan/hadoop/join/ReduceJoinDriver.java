package com.tunan.hadoop.join;

import com.tunan.hadoop.pojo.JoinMain;
import com.tunan.utils.FileUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: tunan
 * @create: 2020-01-29 16:39
 * @since: 1.0.0
 **/
public class ReduceJoinDriver extends Configured implements Tool {

    String in = "data/join/";
    String out = "out/";

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        int run = ToolRunner.run(conf, new ReduceJoinDriver(), args);
        System.exit(run);
    }

    @Override
    public int run(String[] strings) throws Exception {
        //获得configuration
        Configuration conf = super.getConf();

        //检查文件夹
        FileUtil.checkFileIsExists(conf, out);

        //使用新方法这里怎么操作?
        Job job = Job.getInstance(conf);

        //设置驱动类
        job.setJarByClass(ReduceJoinDriver.class);

        //设置Map/Reducer类
        job.setMapperClass(JoinMapper.class);
        job.setReducerClass(JoinReducer.class);

        //设置Map参数类型
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(JoinMain.class);

        //job.setNumReduceTasks(3);

        //设置Reducer参数类型
        job.setOutputKeyClass(JoinMain.class);
        job.setOutputValueClass(NullWritable.class);

        //设置文件的输入输出
        FileInputFormat.setInputPaths(job, new Path(in));
        FileOutputFormat.setOutputPath(job, new Path(out));

        //提交任务
        boolean b = job.waitForCompletion(true);
        return b ? 0 : 1;
    }

    public static class JoinMapper extends Mapper<LongWritable, Text, IntWritable, JoinMain> {
        private String name;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            FileSplit fileSplit = (FileSplit) context.getInputSplit();
            name = fileSplit.getPath().getName();
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            //做一个传入的表的判断
            if (name.contains("emp")){  //emp
                //读取数据
                String[] lines = value.toString().split("\t");
                if (lines.length ==8){
                    //细粒度划分
                    Integer empno = Integer.parseInt(lines[0].trim());
                    String ename = lines[1];
                    Integer deptno = Integer.parseInt(lines[lines.length-1].trim());
                    //写入数据
                    context.write(new IntWritable(deptno),new JoinMain(empno,ename,deptno,"",1));
                }
            }else{      //dept
                //读取数据
                String[] lines = value.toString().split("\t");
                if (lines.length ==3){
                    int deptno = Integer.parseInt(lines[0].trim());
                    String dname = lines[1];
                    context.write(new IntWritable(deptno),new JoinMain(0, "", deptno, dname, 2));
                }
            }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            super.cleanup(context);
        }
    }

    public static class JoinReducer extends Reducer<IntWritable, JoinMain,JoinMain, NullWritable> {
        //核心思路在 每个deptno组 进一次reduce ，前提是map中的key是deptno
        @Override
        protected void reduce(IntWritable key, Iterable<JoinMain> values, Context context) throws IOException, InterruptedException {
            List<JoinMain> list = new ArrayList<>();
            String dname="";
            // 1.取出map中每行数据，判断flag值
            // 2.取出所有的emp中数据放入list中
            // 3.取出dept中的dname赋值给变量
            // 4.取出属于这个deptno中的所有数据，并给dname赋值
            // 5.每条赋值dname的数据写入reduce
            for (JoinMain main : values){
                // emp表
                if (main.getFlag() == 1){
                    //给emp表全部行重新赋值
                    JoinMain m = new JoinMain();
                    m.setDeptno(main.getDeptno());
                    m.setEmpno(main.getEmpno());
                    m.setEname(main.getEname());
                    //写出到list
                    list.add(m);
                }else if (main.getFlag() ==2 ){ //dept
                    //拿到dept表中的dname
                    dname = main.getDname();
                }
            }
            //循环赋值
            for (JoinMain bean : list) {
                bean.setDname(dname);
                context.write(bean,NullWritable.get());
            }
        }
    }
}