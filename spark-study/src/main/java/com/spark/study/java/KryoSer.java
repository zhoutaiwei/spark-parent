package com.spark.study.java;

import java.util.Arrays;
import java.util.List;

import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.objenesis.strategy.StdInstantiatorStrategy;

import com.codahale.metrics.Counter;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.ClassSerializer;
import com.twitter.chill.ObjectSerializer;

import akka.serialization.Serializer;
import scala.Option;

public class KryoSer {
	public static void main(String[] args)  {
		SparkConf conf = new SparkConf().setAppName("kryo").setMaster("local");
		conf.registerKryoClasses(new Class[]{com.codahale.metrics.Counter.class});
		Kryo kryo = new Kryo();  
        kryo.setReferences(false);  
        kryo.setRegistrationRequired(false);  
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy()); 
        kryo.register(Counter.class);
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<String> list=Arrays.asList("c","b","c");
		JavaRDD<String> rdd = sc.parallelize(list);

		JavaRDD<Counter> map = rdd.map(new Function<String, Counter>() {
	
			@Override
			public Counter call(String v1) throws Exception {
				 Counter counter = new Counter();
				counter.inc(2222);
				return counter;
			}
		});
		long count = map.count();
		System.out.println(count);
	}
}
class Student{
	String  id;
	String name;
	public Student() {
		super();
	}
	public Student(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}