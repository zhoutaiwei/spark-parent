package com.spark.study.java;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;

/**
 * 统计 文件内的文字个数
 * @author HP
 *
 */
public class File_FontSum {
	public static void main(String[] args) {
		
		
		SparkConf conf = new SparkConf().setAppName("fontSUm").setMaster("local");
		
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		JavaRDD<String> rdd = sc.textFile("test.txt");
		
		JavaRDD<Integer> lineSum = rdd.map(new Function<String, Integer>() {

			private static final long serialVersionUID = 1L;

			public Integer call(String v1) throws Exception {
				return v1.length();
			}
		});
		Integer reduce = lineSum.reduce(new Function2<Integer, Integer, Integer>() {
			
			public Integer call(Integer v1, Integer v2) throws Exception {
				return v1+v2;
			}
		});
		System.out.println(reduce);
	}
	
}
