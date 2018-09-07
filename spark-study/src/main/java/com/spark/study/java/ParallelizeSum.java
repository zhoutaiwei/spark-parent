package com.spark.study.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;


/**
 * 用并行集合创建RDD
 * @author HP
 *
 */
public class ParallelizeSum {
	@SuppressWarnings("resource")
	public static void main(String[] args) {

		SparkConf sparkConf = new SparkConf().setAppName("parallelizeSum").setMaster("local");
		JavaSparkContext sp = new JavaSparkContext(sparkConf);

		List<Integer> list=Arrays.asList(1,2,3,4,5,6,7,8,9);
		//通过并行化集合创建rdd
		JavaRDD<Integer> rdd = sp.parallelize(list);
		Integer reduce = rdd.reduce(new Function2<Integer, Integer, Integer>() {

			public Integer call(Integer num1, Integer num2) throws Exception {
				return num1+num2;
			}
		});
		System.out.println(reduce);
	}
}
