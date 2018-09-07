package com.spark.study.java;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;

/**
 * Broadcast是只能读不能改的，Accumuter是可以拿来计算的
 * @author HP
 *
 */
public class AccumuterTest {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("accumuter").setMaster("local");
		 JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		JavaRDD<Integer> rdd = sc.parallelize(list);
		final int num=3;
		final Accumulator<Integer> accumulator = sc.accumulator(num);
		
		rdd.foreach(new VoidFunction<Integer>() {
			
			public void call(Integer arg0) throws Exception {
				accumulator.add(arg0);
				
			}
		});
		System.out.println(accumulator.value());
	}
}
