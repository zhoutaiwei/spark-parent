  
package com.spark.study.java;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

public class SorAndTake {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("sorAndTake").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer> list=Arrays.asList(4,5,7,56,2,4,6);
		JavaRDD<Integer> rdd = sc.parallelize(list);
		JavaPairRDD<Integer, Integer> tuple = rdd.mapToPair(new PairFunction<Integer, Integer, Integer>() {

			private static final long serialVersionUID = 1L;

			public Tuple2<Integer, Integer> call(Integer t) throws Exception {
				
				return new Tuple2<Integer, Integer>(t, t);
			}
		});
		JavaPairRDD<Integer, Integer> sortResult= tuple.sortByKey(false);
		JavaRDD<Integer> result = sortResult.map(new Function<Tuple2<Integer,Integer>, Integer>() {
			private static final long serialVersionUID = 1L;

			public Integer call(Tuple2<Integer, Integer> v1) throws Exception {
				
				return v1._1;
			}
		});
		result.foreach(new VoidFunction<Integer>() {
			
			private static final long serialVersionUID = 1L;

			public void call(Integer t) throws Exception {
				System.out.println(t);
				
			}
		});
	}
}
