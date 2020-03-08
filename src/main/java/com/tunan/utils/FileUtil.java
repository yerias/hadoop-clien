package com.tunan.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

/**
 * @description:
 * @author: tunan
 * @create: 2020-01-23 15:32
 * @since: 1.0.0
 **/
public class FileUtil {

    private static FileSystem fileSystem = null;

    /**
     * @param conf
     * @param path
     * @return : void
     * @describe : 对文件系统进行初始化工作
     * @author : tunan
     * @date : 2020/1/23 15:40
     */
    private static void init(Configuration conf, Path path) throws IOException {

        fileSystem = FileSystem.get(conf);

    }

    /**
     * @param conf
     * @param
     * @return : void
     * @describe : 如果目标文件存在则删除文件
     * @author : tunan
     * @date : 2020/1/23 15:40
     */
    public static void checkFileIsExists(Configuration conf, String out) throws IOException {
        Path path = new Path(out);
        init(conf, path);
        if (fileSystem.exists(path)) {
            fileSystem.delete(path, true);
        }
        end();
    }

    /**
     * 关闭连接
     *
     * @param
     * @return : void
     * @describe :
     * @author : tunan
     * @date : 2020/1/23 15:44
     */
    public static void end() throws IOException {
        if (fileSystem != null) {
            fileSystem.close();
        }
    }
}

