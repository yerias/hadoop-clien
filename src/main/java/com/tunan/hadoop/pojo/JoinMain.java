package com.tunan.hadoop.pojo;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @description:
 * @author: tunan
 * @create: 2020-01-29 17:10
 * @since: 1.0.0
 **/
public class JoinMain implements Writable {

    private Integer empno;
    private String ename;
    private Integer deptno;
    private String dname;
    private Integer flag;

    @Override
    public String toString() {
        return empno+"\t"+ ename+"\t"+deptno+"\t"+dname;
    }

    public JoinMain() {
    }

    public JoinMain(Integer empno, String ename, Integer deptno, String dname, Integer flag) {
        this.empno = empno;
        this.ename = ename;
        this.deptno = deptno;
        this.dname = dname;
        this.flag = flag;
    }

    public Integer getEmpno() {
        return empno;
    }

    public void setEmpno(Integer empno) {
        this.empno = empno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Integer getDeptno() {
        return deptno;
    }

    public void setDeptno(Integer deptno) {
        this.deptno = deptno;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(empno);
        out.writeUTF(ename);
        out.writeInt(deptno);
        out.writeUTF(dname);
        out.writeInt(flag);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.empno = in.readInt();
        this.ename = in.readUTF();
        this.deptno = in.readInt();
        this.dname = in.readUTF();
        this.flag = in.readInt();
    }
}

