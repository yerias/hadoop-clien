package com.tunan.hadoop.paratitioner;

import com.tunan.hadoop.pojo.Flow;
import org.apache.directory.shared.kerberos.codec.transitedEncoding.TransitedEncodingStatesEnum;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @description:
 * @author: tunan
 * @create: 2020-01-28 18:45
 * @since: 1.0.0
 **/
public class Partitoner extends Partitioner<Text, Flow> {

    @Override
    public int getPartition(Text text, Flow flow, int i) {
        String phone = text.toString();
        if ("137".equals(phone.substring(0,3))){
            return 0;
        }else if ("136".equals(phone.substring(0,3))){
            return 1;
        }else{
            return 2;
        }
    }
}

