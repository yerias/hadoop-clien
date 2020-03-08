/**
 * Copyright (C), 2015-2020
 * FileName: Client
 * Author:   Tunan
 * Date:     2020/1/20 22:23
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.tunan.hadoop.api;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class Client {
    private static FileSystem fileSystem;

    @BeforeClass
    public static void Before() throws URISyntaxException, IOException, InterruptedException {
        URI uri = new URI("hdfs://aliyun:9000");
        Configuration conf = new Configuration();
        conf.set("dfs.client.use.datanode.hostname","true");
        conf.set("dfs.replication", "1");
        String userName = "hadoop";
        fileSystem = FileSystem.get(uri,conf,userName);

    };
    /**
     * @describe : 在文件系统创建目录/文件
     * @author : tunan
     * @param
     * @return : void
     * @date : 2020/1/22 11:34
     */
    @Test
    public void createDir() throws  IOException, InterruptedException {
//        fileSystem.mkdirs(new Path("/win2hadoop"));
//        fileSystem.create(new Path("/win2hadoop.txt"));
        boolean b = FileSystem.areSymlinksEnabled();
        System.out.println(b);
    }

    /**
     * @describe : 上传文件
     * @author : tunan
     * @param
     * @return : void
     * @date : 2020/1/22 12:01
     */
    @Test
    public void putFile() throws IOException {
        fileSystem.copyFromLocalFile(new Path("data/student.txt"), new Path("/student.txt"));
    }

    /**
     * @describe : 下载文件
     * @author : tunan
     * @param
     * @return : void
     * @date : 2020/1/22 11:53
     */
    @Test
    public void getFile() throws IOException {
        fileSystem.copyToLocalFile(new Path("/student.txt"), new Path("student3"));
    }

    /**
     * @describe : 获取所有文件
     * @author : tunan
     * @param
     * @return : void
     * @date : 2020/1/22 12:23
     */
    @Test
    public void getListFile() throws IOException {
        RemoteIterator<LocatedFileStatus> listFiles = fileSystem.listFiles(new Path("/user"), true);
        while(listFiles.hasNext()){
            LocatedFileStatus next = listFiles.next();
            //Path path = next.getPath();
            System.out.println(next.getPath().toString()+"\t"+next.getBlockSize()+"\t"+next.getLen()+"\t"+next.getBlockLocations());
        }
    }

    /**
     * @describe : 删除文件
     * @author : tunan
     * @param
     * @return : void
     * @date : 2020/1/22 12:29
     */
    @Test
    public void removeFile() throws IOException {
        fileSystem.delete(new Path("/data"), true);
        fileSystem.delete(new Path("/student.txt"), true);
        fileSystem.delete(new Path("/student.txt.txt"), true);
        fileSystem.deleteOnExit(new Path("/student1.txt"));
        fileSystem.delete(new Path("/win2hadoop"), true);
        fileSystem.delete(new Path("/win2hadoop"), true);
        fileSystem.delete(new Path("/win2hadoop.txt"), true);
        fileSystem.delete(new Path("/windowstohadoop"), true);
        fileSystem.delete(new Path("/wordcount.txt"), true);
    }


    @Test
    public void test() throws IOException {
        Client client = new Client();
        client.streamGetFile();
        client.streamGetFile2();
        client.streamGetFile3();
        client.streamGetFile4();
    }
    /**
     * @describe : 流读取文件并上传到hdfs
     * @author : tunan
     * @param
     * @return : void
     * @date : 2020/1/22 13:08
     */
    @Test
    public void streamPutFile() throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File("data/student.txt")));
        FSDataOutputStream out = fileSystem.create(new Path("student.txt"));
        //hadoop的拷贝流工具类
        IOUtils.copyBytes(in, out, 1024);
        IOUtils.closeStream(in);
        IOUtils.closeStream(out);
    }

    @Test
    public void streamGetFile() throws IOException {
        //输入流
        FSDataInputStream in = fileSystem.open(new Path("/item/offline-dw/data/userid.txt"));
        //输出流
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File("./out/wordcount.txt")));

        byte[] buff = new byte[1024*8];
        for (int i = 0; i < 128 * 1024; i++) {
            in.read(buff);
            out.write(buff);
        }
        IOUtils.closeStream(in);
        IOUtils.closeStream(out);
    }
    @Test
    public void streamGetFile2() throws IOException {
        //输入流
        FSDataInputStream in = fileSystem.open(new Path("/hadoop-2.6.0-cdh5.16.2.tar.gz"));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File("E:/hadoop111")));
        in.seek(1024*1024*128);
        byte[] buff = new byte[1024*8];
        for (int i = 0; i < 128 * 1024; i++) {
            in.read(buff);
            out.write(buff);
        }
        IOUtils.closeStream(in);
        IOUtils.closeStream(out);
    }
    @Test
    public void streamGetFile3() throws IOException {
        //输入流
        FSDataInputStream in = fileSystem.open(new Path("/hadoop-2.6.0-cdh5.16.2.tar.gz"));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File("E:/hadoop111")));
        in.seek(1024*1024*128*2);
        byte[] buff = new byte[1024*8];
        for (int i = 0; i < 128 * 1024; i++) {
            in.read(buff);
            out.write(buff);
        }
        IOUtils.closeStream(in);
        IOUtils.closeStream(out);
    }
    @Test
    public void streamGetFile4() throws IOException {
        //输入流
        FSDataInputStream in = fileSystem.open(new Path("/hadoop-2.6.0-cdh5.16.2.tar.gz"));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File("E:/hadoop111")));
        in.seek(1024*1024*128*3);

        IOUtils.copyBytes(in,out,1024);

        IOUtils.closeStream(in);
        IOUtils.closeStream(out);
    }

    @AfterClass
    public static void After() throws IOException {
        if (fileSystem!=null){
            fileSystem.close();
        }
    };
}

