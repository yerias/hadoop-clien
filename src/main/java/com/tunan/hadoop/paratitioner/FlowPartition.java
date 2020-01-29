package com.tunan.hadoop.paratitioner;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @description: 流量分析实体类 hadoop需要实现Writable接口
 * @author: tunan
 * @create: 2020-01-25 22:17
 * @since: 1.0.0
 **/
public class FlowPartition implements Writable {

    //变量
    private String phone;
    private Long up;
    private Long down;
    private Long sum;

    //构造器
    public FlowPartition() {
    }
    public FlowPartition(String phone, Long up, Long down) {
        this.phone = phone;
        this.up = up;
        this.down = down;
        this.sum = up+down;
    }

    //方法

    @Override
    public String toString() {
        return phone +"\t"+ up +"\t"+down +"\t"+sum ;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getUp() {
        return up;
    }

    public void setUp(Long up) {
        this.up = up;
    }

    public Long getDown() {
        return down;
    }

    public void setDown(Long down) {
        this.down = down;
    }

    //实现序列化的读写
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(phone);
        out.writeLong(up);
        out.writeLong(down);
        out.writeLong(sum);
    };

    @Override
    public void readFields(DataInput in) throws IOException {
        this.phone = in.readUTF();
        this.up = in.readLong();
        this.down = in.readLong();
        this.sum = in.readLong();
    };
}

