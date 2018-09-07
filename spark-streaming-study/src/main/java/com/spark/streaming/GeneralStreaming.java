package com.spark.streaming;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.Seconds;
import org.apache.spark.streaming.StreamingContext;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.dstream.ReceiverInputDStream;

import scala.Tuple2;

public class GeneralStreaming {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("GeneralStreaming").setMaster("local[2]");
		JavaStreamingContext ssc = new JavaStreamingContext(conf, Durations.seconds(2));
		JavaReceiverInputDStream<String> lines = ssc.socketTextStream("hadoop102", 6666);
		
		 JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
			private static final long serialVersionUID = 1L;

			public Iterable<String> call(String text) throws Exception {
				List<String> asList = Arrays.asList(text.split(" "));
				return asList;
			}
		});
	 JavaPairDStream<String, Integer> mapToPair = words.mapToPair(new PairFunction<String, String, Integer>() {
			public Tuple2<String, Integer> call(String words) throws Exception {
				Tuple2<String, Integer> tuple2 = new Tuple2<String, Integer>(words, 1);
				return tuple2;
			}
		});
		JavaPairDStream<String, Integer> reduceByKey = mapToPair.reduceByKey(new Function2<Integer, Integer, Integer>() {

			public Integer call(Integer arg0, Integer arg1) throws Exception {

				return arg0 + arg1;
			}
		});
		reduceByKey.print();
		ssc.start();
		ssc.awaitTermination();
	}
}
