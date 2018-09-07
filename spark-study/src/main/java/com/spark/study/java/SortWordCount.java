package com.spark.study.java;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;import org.apache.spark.api.java.function.DoubleFunction;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.rdd.RDD;

import scala.Tuple2;

public class SortWordCount {
	public static void main(String[] args) {
		SparkConf config = new SparkConf().setAppName("sortWordCount").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(config);
		JavaRDD<String> rdd = sc.textFile("test.txt");
		JavaRDD<String> words = rdd.flatMap(new FlatMapFunction<String, String>() {

			private static final long serialVersionUID = 1L;

			public Iterable<String> call(String t) throws Exception {
				
				return Arrays.asList(t.split(" "));
			}
		});
		JavaPairRDD<String, Integer> wordTuple = words.mapToPair(new PairFunction<String, String, Integer>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Tuple2<String, Integer> call(String t) throws Exception {
				
				return new Tuple2<String, Integer>(t, 1);
			}
		});
		JavaPairRDD<String, Integer> reduce = wordTuple.reduceByKey(new Function2<Integer, Integer, Integer>() {
			
			public Integer call(Integer v1, Integer v2) throws Exception {
				return v1+v2;
			}
		});
		//将tuple的 key和value 进行反转
		JavaPairRDD<Integer, String> inversionTuple = reduce.mapToPair(new PairFunction<Tuple2<String,Integer>, Integer, String>() {

			private static final long serialVersionUID = 1L;

			public Tuple2<Integer, String> call(Tuple2<String, Integer> t) throws Exception {
				return new Tuple2<Integer,String>(t._2,t._1);
			}
		});
		//排序
		JavaPairRDD<Integer, String> sortByKey = inversionTuple.sortByKey(false);
		JavaPairRDD<String, Integer> result = sortByKey.mapToPair(new PairFunction<Tuple2<Integer,String>, String, Integer>() {
			private static final long serialVersionUID = 1L;

			public Tuple2<String, Integer> call(Tuple2<Integer, String> t) throws Exception {
				
				return new Tuple2<String, Integer>(t._2,t._1);
			}
		});
		result.foreach(new VoidFunction<Tuple2<String,Integer>>() {
			
			public void call(Tuple2<String, Integer> t) throws Exception {
				System.out.println(t._1+":"+t._2);
				
			}
		});
	}
}
