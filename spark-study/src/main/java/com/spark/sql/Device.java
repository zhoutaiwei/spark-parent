package com.spark.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.util.Arrays;
import java.util.List;

public class Device {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("Device").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        List<String> list = Arrays.asList("2018-08-23 10:13:00  a,b,c", "2018-08-23 10:13:00  b,c", "2018-08-23 10:13:00  b", "2018-08-23 10:13:00  a");
        JavaRDD<String> lines = sc.parallelize(list);
        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            private static final long serialVersionUID = 1L;

            public Iterable<String> call(String text) throws Exception {
                String[] split = text.split("\t");
                String[] device = split[1].split(",");
                for (String str : device) {
                    System.out.println(str);
                }
                return Arrays.asList(device);
            }
        });
    }
}
 