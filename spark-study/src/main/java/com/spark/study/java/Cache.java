package com.spark.study.java;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.storage.StorageLevel;

import scala.Tuple2;

/**
 * 缓存的使用
 * @author HP
 *
 */
public class Cache {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("cache").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		JavaRDD<String> rdd = sc.textFile("test.txt");
		
		long start = System.currentTimeMillis();
		long count = rdd.count();
		long end = System.currentTimeMillis();
		System.out.println(end-start);
		
		 start = System.currentTimeMillis();
		 count = rdd.count();
		 end = System.currentTimeMillis();
		System.out.println(end-start);
	}
}
