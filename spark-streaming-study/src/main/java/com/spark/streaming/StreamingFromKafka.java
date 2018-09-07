package com.spark.streaming;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import scala.Tuple2;

public class StreamingFromKafka {
	// 指定消费者组
	static String group = "consumer-group";
	// 指定topic
	static String topic ="calllog";
	//指定zk-quorum
	static String zk_quorum="hadoop102:2181,hadoop103:2181,hadoop104:2181";
	
	

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("StreamingFromKafkav").setMaster("local[3]");
		JavaStreamingContext jssc = new JavaStreamingContext(conf,new Duration(1000));//每两秒读取一次卡夫卡
		Map<String, Integer> topicMap=new HashMap<String, Integer>();
		String[] split = topic.split(",");
		//指定每个主题的分片数
		int topicNum=3;
		for (int i = 0; i < split.length; i++) {
			topicMap.put(split[i], topicNum);
		}
		//从kafka中获取数据转换成rdd
		JavaPairReceiverInputDStream<String, String> lines = KafkaUtils.createStream(jssc, zk_quorum, group, topicMap);
		
		JavaDStream<String> words = lines.flatMap(new FlatMapFunction<Tuple2<String,String>, String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Iterable<String> call(Tuple2<String, String> t) throws Exception {
				System.out.println("t._1="+t._2);
				return Arrays.asList(t._2.split(" "));
			}
		});
		JavaPairDStream<String, Integer> wordCount = words.mapToPair(new PairFunction<String, String, Integer>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Tuple2<String, Integer> call(String t) throws Exception {
				
				return new Tuple2<String, Integer>(t, 1);
			}
		});
		JavaDStream<Integer> map = wordCount.map(new Function<Tuple2<String,Integer>, Integer>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Integer call(Tuple2<String, Integer> v1) throws Exception {
				return v1._2;
			}
		});
		JavaDStream<Integer> wordSum = map.reduce(new Function2<Integer, Integer, Integer>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Integer call(Integer v1, Integer v2) throws Exception {
			
				return v1+v2;
			}
		});
		wordSum.print();
		jssc.start();
		jssc.awaitTermination();
	}
}
