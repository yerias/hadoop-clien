package com.tunan.job;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @description: 修改整理规范化hdfs中的文件名
 * @author: tunan
 * @create: 2020-02-03 19:26
 * @since: 1.0.0
 **/
public class MyRename {

    private static FileSystem fileSystem;

    public static void Before() throws URISyntaxException, IOException, InterruptedException {
        URI uri = new URI("hdfs://aliyun:9000");
        Configuration conf = new Configuration();
        conf.set("dfs.client.use.datanode.hostname", "true");
        conf.set("dfs.replication", "1");
        String userName = "hadoop";
        fileSystem = FileSystem.get(uri, conf, userName);
    }

    public static void rename(String day) throws IOException {
        RemoteIterator<LocatedFileStatus> files = fileSystem.listFiles(new Path("/change/" + day), true);
        int num = 0;
        while (files.hasNext()) {
            //String src = files.next().getPath().toString();
            String src = "/change/" + day + "/" + files.next().getPath().getName();
            String dst = "/change/" + day + "/" + day + "-" + num + ".txt";
            fileSystem.rename(new Path(src), new Path(dst));
            num++;
        }
    }

    public static void After() throws IOException {
        if (fileSystem != null) {
            fileSystem.close();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        Before();
        String[] days = {"20201011","20201012"};
        for (String day : days) {
            rename(day);
        }
        After();
    }
}