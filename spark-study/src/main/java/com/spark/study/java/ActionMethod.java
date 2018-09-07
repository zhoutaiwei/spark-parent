package com.spark.study.java;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

public class ActionMethod {
	
	public static void main(String[] args) {
	//	collect();
	//	count();
	//	take();
		countByValue();
	//	saveToTextFile();
	//	getObjectFile();
	}
	
	
	static void getObjectFile(){
		SparkConf conf = new SparkConf().setAppName("map").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		JavaRDD<Object> objectFile = sc.objectFile("");
		JavaRDD<String > rrd = sc.objectFile("E:\\objectFile.txt\\*000");
		List<String> take = rrd.take(5);
		for (int i = 0; i < take.size(); i++) {
			System.out.println(take.get(i));
			
			
		}
		
	}
	
	/**
	 * saveAsObjectFile
	 * 将文件保存至指定路径而且用指定类封装（hadoop的 Writer类）
	 */
	static void saveAsObjectFile(){
		SparkConf conf = new SparkConf().setAppName("map").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		JavaRDD<Object> objectFile = sc.objectFile("");
		List<String> list = Arrays.asList("zs","df","ew","zs","df","ew");
		JavaRDD<String> rdd = sc.parallelize(list);
		rdd.saveAsObjectFile( "E:\\objectFile");
		sc.close();
	}
	
	
	static void countByValue(){
		SparkConf conf = new SparkConf().setAppName("map").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<String> list = Arrays.asList("zs","df","ew","zs","df","ew");
		JavaRDD<String> rdd = sc.parallelize(list);
		Map<String, Long> result = rdd.countByValue();
		Set<String> set = result.keySet();
		for (String str : set) {
			System.out.println(str);
			System.out.println(result.get(str));
			
		}
		
	}
	
	/**
	 * take
	 * 获取指定个数的结果
	 */
	static void take(){
		SparkConf conf = new SparkConf().setAppName("map").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
		JavaRDD<Integer> rdd = sc.parallelize(list);
		List<Integer> take = rdd.take(5);
		for (int i = 0; i < take.size(); i++) {
			System.out.println(take.get(i));
			
			
		}
		
	}
	
	/**
	 * count
	 * 计算rdd个数
	 */
	static void count(){
		SparkConf conf = new SparkConf().setAppName("map").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

		JavaRDD<Integer> rdd = sc.parallelize(list);
		long count = rdd.count();
		System.out.println(count);
		
	}
	
	
	
	/**
	 * collect
	 * 直接将远程的数据拉取过来，不建议使用，因为数量大时性能很差，而且可能产生oom异常
	 */
	static void collect() {
		SparkConf conf = new SparkConf().setAppName("map").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

		JavaRDD<Integer> rdd = sc.parallelize(list);
		JavaRDD<Integer> doubleCount = rdd.map(new Function<Integer, Integer>() {

			private static final long serialVersionUID = 1L;

			public Integer call(Integer v1) throws Exception {
				return v1 * 2;
			}
		});
		List<Integer> collect = doubleCount.collect();
		for (int i = 0; i < collect.size(); i++) {
			System.out.println(collect.get(i));
		}
	}
}