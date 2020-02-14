package com.tunan.io;

import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * @description:
 * @author: tunan
 * @create: 2020-02-14 17:29
 * @since: 1.0.0
 **/

public class IOFile {

    public static void main(String[] args) throws IOException {
        IOFile file = new IOFile();
        file.write();

    }
    public void reader() throws IOException{
        String in = "data/access.txt";
        String out = "out";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(in)));
        String line;
        while(StringUtils.isNotEmpty(line=reader.readLine())){
            System.out.println(line);
        }
        reader.close();
    }

    public void write() throws IOException {
        String out = "data/file.txt";
        String line = "aaaaa";
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out,true)));

        for (int i = 0; i < 99; i++) {
            writer.write(line);
            writer.newLine();
        }

        writer.flush();
        writer.close();
    }
}

