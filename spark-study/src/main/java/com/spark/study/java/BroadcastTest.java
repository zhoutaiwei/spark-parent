package com.spark.study.java;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.broadcast.Broadcast;

/**
 * 将各个task公用变量放到Broadcast中 ，这样的话这个变量会一次性放到所有的task中 ，不用每个task都取一次
 * sc.broadcast(num);设置值
 * 调用value()方法 获取值
 * @author HP
 *
 */
public class BroadcastTest {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("boradcast").setMaster("local");
		 JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		JavaRDD<Integer> rdd = sc.parallelize(list);
		final int num=3;
		final Broadcast<Integer> broadcast = sc.broadcast(num);
		rdd.foreach(new VoidFunction<Integer>() {
			
			private static final long serialVersionUID = 1L;

			public void call(Integer arg0) throws Exception {
				System.out.println(arg0*broadcast.value());
				
			}
		});
		
	}
}
