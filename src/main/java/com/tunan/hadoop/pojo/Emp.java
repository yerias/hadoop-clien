package com.tunan.hadoop.pojo;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @description:
 * @author: tunan
 * @create: 2020-01-29 16:40
 * @since: 1.0.0
 **/
public class Emp implements WritableComparable<Emp> {

    private Integer empno;
    private String ename;
    private String job;
    private Integer mgr;
    private String hiredate;
    private Double sal;
    private Double comm;
    private Integer deptno;
    private Integer flag;

    public Emp() {
    }

    public Emp(Integer empno, String ename, String job, Integer mgr, String hiredate, Double sal, Double comm, Integer deptno) {
        this.empno = empno;
        this.ename = ename;
        this.job = job;
        this.mgr = mgr;
        this.hiredate = hiredate;
        this.sal = sal;
        this.comm = comm;
        this.deptno = deptno;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(empno);
        out.writeUTF(ename);
        out.writeUTF(job);
        out.writeInt(mgr);
        out.writeUTF(hiredate);
        out.writeDouble(sal);
        out.writeDouble(comm);
        out.writeInt(deptno);
        out.writeInt(flag);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.empno = in.readInt();
        this.ename = in.readUTF();
        this.job = in.readUTF();
        this.mgr = in . readInt();
        this.hiredate = in.readUTF();
        this.sal = in.readDouble();
        this.comm = in.readDouble();
        this.deptno = in.readInt();
        this.flag = in.readInt();
    }

    @Override
    public int compareTo(Emp o) {
        return this.deptno > o.deptno ? -1 : 1;
    }
}

