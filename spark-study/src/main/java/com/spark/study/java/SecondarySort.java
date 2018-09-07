package com.spark.study.java;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

/**
 * 二次排序
 * @author HP
 *
 */
public class SecondarySort {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("secondarySort").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		JavaRDD<String> rdd = sc.textFile("sort.txt");
		
		JavaPairRDD<SecondarySortDao, String> tuple = rdd.mapToPair(new PairFunction<String, SecondarySortDao, String>() {

			
			private static final long serialVersionUID = 1L;

			public Tuple2<SecondarySortDao, String> call(String t) throws Exception {
				String[] line = t.split(" ");
				SecondarySortDao ssd = new SecondarySortDao(Integer.valueOf(line[0]),Integer.valueOf(line[1]));
				
				return new Tuple2<SecondarySortDao, String>(ssd, t);
			}
		});
		JavaPairRDD<SecondarySortDao, String> sortTuple = tuple.sortByKey();
		JavaRDD<String> map = sortTuple.map(new Function<Tuple2<SecondarySortDao,String>, String>() {

			private static final long serialVersionUID = 1L;

			public String call(Tuple2<SecondarySortDao, String> v1) throws Exception {
				return v1._2;
			}
		});
		map.foreach(new VoidFunction<String>() {
			
			public void call(String t) throws Exception {
				System.out.println(t);
				
			}
		});
	}
}
