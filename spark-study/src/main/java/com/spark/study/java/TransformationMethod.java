package com.spark.study.java;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

/**
 * 对transformation的方法进行测试
 * 
 * @author HP
 *
 */
public class TransformationMethod {
	public static void main(String[] args) {
		// map();
		// filter();
		 //groupByKey();
		// reduceByKey();
		// sortByKey();
		//join();
		cogroup();
	}

	static void cogroup() {
		SparkConf conf = new SparkConf().setAppName("cogroup").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		@SuppressWarnings("unchecked")
		List<Tuple2<String, String>> list1 = Arrays.asList(
				new Tuple2<String, String>("2", "黎明"), new Tuple2<String, String>("3", "学友"),
				new Tuple2<String, String>("1", "徐慧"));
		@SuppressWarnings("unchecked")
		List<Tuple2<String, Integer>> list2 = Arrays.asList(new Tuple2<String, Integer>("1", 90),
				new Tuple2<String, Integer>("1", 98), new Tuple2<String, Integer>("3", 58),
				new Tuple2<String, Integer>("2", 38), new Tuple2<String, Integer>("2", 95),
				new Tuple2<String, Integer>("3", 38),new Tuple2<String, Integer>("1", 38));
		// 创建两个rdd
		JavaPairRDD<String, String> rdd1 = sc.parallelizePairs(list1);
		JavaPairRDD<String, Integer> rdd2 = sc.parallelizePairs(list2);
		JavaPairRDD<String, Tuple2<Iterable<String>, Iterable<Integer>>> cogroup = rdd1.cogroup(rdd2);
		cogroup.foreach(new VoidFunction<Tuple2<String,Tuple2<Iterable<String>,Iterable<Integer>>>>() {

			public void call(Tuple2<String, Tuple2<Iterable<String>, Iterable<Integer>>> t) throws Exception {
				System.out.print(t._1+"->");
				Iterable<String> keys = t._2._1;
				Iterable<Integer> values = t._2._2;
				for (String str : keys) {
					System.out.print(str+":");
				}
				for (Integer value :values) {
					System.out.print(value+",");
					
				}
				System.out.println();
				System.out.println("---------------");
			}
		});
		
		
	}

	/**
	 * join 比如有(1, 1) (1, 2) (1, 3)的一个RDD 还有一个(1, 4) (2, 1) (2, 2)的一个RDD
	 * join以后，实际上会得到(1 (1, 4)) (1, (2, 4)) (1, (3, 4))
	 */
	static void join() {
		SparkConf conf = new SparkConf().setAppName("join").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Tuple2<Integer, Integer>> list1 = Arrays.asList(new Tuple2<Integer, Integer>(3, 95),
				new Tuple2<Integer, Integer>(1, 44), new Tuple2<Integer, Integer>(3, 56),
				new Tuple2<Integer, Integer>(4, 95), new Tuple2<Integer, Integer>(2, 77),
				new Tuple2<Integer, Integer>(1, 56));
		List<Tuple2<Integer, Integer>> list2 = Arrays.asList(new Tuple2<Integer, Integer>(3, 45),
				new Tuple2<Integer, Integer>(1, 56), new Tuple2<Integer, Integer>(1, 26),
				new Tuple2<Integer, Integer>(4, 34), new Tuple2<Integer, Integer>(1, 26),
				new Tuple2<Integer, Integer>(2, 98));
		// 创建两个rdd
		JavaPairRDD<Integer, Integer> rdd1 = sc.parallelizePairs(list1);
		JavaPairRDD<Integer, Integer> rdd2 = sc.parallelizePairs(list2);

		JavaPairRDD<Integer, Tuple2<Integer, Integer>> joinRdd = rdd1.join(rdd2);
		joinRdd.foreach(new VoidFunction<Tuple2<Integer, Tuple2<Integer, Integer>>>() {

			private static final long serialVersionUID = 1L;

			public void call(Tuple2<Integer, Tuple2<Integer, Integer>> t) throws Exception {
				System.out.print(t._1 + "->");
				System.out.println(t._2._1 + ":" + t._2._2);
			}
		});

		sc.close();
	}

	/**
	 * sortByKey 根据key来排序
	 */

	static void sortByKey() {
		SparkConf conf = new SparkConf().setAppName("sortByKey").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		@SuppressWarnings("unchecked")
		List<Tuple2<String, String>> list = Arrays.asList(new Tuple2<String, String>("90", "德华"),
				new Tuple2<String, String>("98", "贾玲"), new Tuple2<String, String>("58", "学友"),
				new Tuple2<String, String>("38", "沈腾"), new Tuple2<String, String>("95", "黎明"),
				new Tuple2<String, String>("99", "徐慧"), new Tuple2<String, String>("68", "星驰"),
				new Tuple2<String, String>("92", "艳玲")

		);
		JavaPairRDD<String, String> rdd = sc.parallelizePairs(list);
		/**
		 * 参数为false为降序，默认 升序，还可以传入Comparator进行定制排序
		 */
		JavaPairRDD<String, String> sortByKey = rdd.sortByKey(false);
		sortByKey.foreach(new VoidFunction<Tuple2<String, String>>() {

			public void call(Tuple2<String, String> t) throws Exception {
				System.out.println(t._2 + ":" + t._1);
			}
		});
	}

	/**
	 * reduceByKey 统计相同key的总分
	 */
	static void reduceByKey() {
		SparkConf conf = new SparkConf().setAppName("reduceByKey").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		@SuppressWarnings("unchecked")
		List<Tuple2<String, Integer>> list = Arrays.asList(new Tuple2<String, Integer>("class1", 90),
				new Tuple2<String, Integer>("class2", 98), new Tuple2<String, Integer>("class3", 58),
				new Tuple2<String, Integer>("class1", 38), new Tuple2<String, Integer>("class3", 95),
				new Tuple2<String, Integer>("class2", 38), new Tuple2<String, Integer>("class1", 68),
				new Tuple2<String, Integer>("class3", 28)

		);
		JavaPairRDD<String, Integer> rdd = sc.parallelizePairs(list);
		JavaPairRDD<String, Integer> reduce = rdd.reduceByKey(new Function2<Integer, Integer, Integer>() {
			private static final long serialVersionUID = 1L;

			public Integer call(Integer v1, Integer v2) throws Exception {

				return v1 + v2;
			}
		});
		reduce.foreach(new VoidFunction<Tuple2<String, Integer>>() {

			private static final long serialVersionUID = 1L;

			public void call(Tuple2<String, Integer> t) throws Exception {
				System.out.println("class:" + t._1 + ",score:" + t._2);

			}

		});
	}

	/**
	 * groupByKey 按key进行分组
	 */
	static void groupByKey() {
		SparkConf conf = new SparkConf().setAppName("groupByKey").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		@SuppressWarnings("unchecked")
		List<Tuple2<String, Integer>> list = Arrays.asList(new Tuple2<String, Integer>("class1", 90),
				new Tuple2<String, Integer>("class2", 98), new Tuple2<String, Integer>("class3", 58),
				new Tuple2<String, Integer>("class1", 38), new Tuple2<String, Integer>("class3", 95),
				new Tuple2<String, Integer>("class2", 38), new Tuple2<String, Integer>("class1", 68),
				new Tuple2<String, Integer>("class3", 28)

		);
		JavaPairRDD<String, Integer> rdd = sc.parallelizePairs(list);
		JavaPairRDD<String, Iterable<Integer>> groupByKey = rdd.groupByKey();
		groupByKey.foreach(new VoidFunction<Tuple2<String, Iterable<Integer>>>() {

			private static final long serialVersionUID = 1L;

			public void call(Tuple2<String, Iterable<Integer>> t) throws Exception {
				System.out.println("class：" + t._1);
				Iterable<Integer> iterable = t._2;
				for (Integer score : iterable) {
					System.out.println(score);
				}
				System.out.println("===================");
			}
		});

		sc.close();

	}

	/**
	 * filter 过滤结果为奇数的结果
	 */
	static void filter() {
		SparkConf conf = new SparkConf().setAppName("filter").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		JavaRDD<Integer> rdd = sc.parallelize(list);
		JavaRDD<Integer> filter = rdd.filter(new Function<Integer, Boolean>() {

			public Boolean call(Integer v1) throws Exception {

				return v1 % 2 == 0;
			}
		});
		filter.foreach(new VoidFunction<Integer>() {

			public void call(Integer t) throws Exception {
				System.out.println(t);

			}
		});
		sc.close();
	}

	/**
	 * map 对集合中的 值*2
	 */
	static void map() {
		SparkConf conf = new SparkConf().setAppName("map").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

		JavaRDD<Integer> rdd = sc.parallelize(list);
		JavaRDD<Integer> map = rdd.map(new Function<Integer, Integer>() {

			private static final long serialVersionUID = 1L;

			public Integer call(Integer v1) throws Exception {
				return v1 * 2;
			}
		});
		map.foreach(new VoidFunction<Integer>() {

			public void call(Integer t) throws Exception {
				System.out.println(t);

			}
		});
		sc.close();
	}
}