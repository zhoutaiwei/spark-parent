package com.spark.demo;

import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import com.spark.demo.POJO.MoviesPOJO;

import scala.Tuple2;
//求被评分次数最多的 5 部电影，并给出评分次数（电影名，评分次数）
public class Movies_ratingsMain {
	public static void main(String[] args) {
		
		//从影评中获取电影id
		JavaRDD<Long> movieIds = Utils.ratingRdd.map(new Function<String,Long>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Long call(String v1) throws Exception {
				String[] line = v1.split("::");
				
				return Long.parseLong(line[1]);
			}
		});
		
		JavaPairRDD<Long, Integer> mapToPair = movieIds.mapToPair(new PairFunction<Long, Long, Integer>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Tuple2<Long, Integer> call(Long t) throws Exception {
				return new Tuple2<Long, Integer>(t,1);
			}
		});
		JavaPairRDD<Long, Integer> movieNumRdd = mapToPair.reduceByKey(new Function2<Integer, Integer, Integer>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Integer call(Integer v1, Integer v2) throws Exception {
				// TODO Auto-generated method stub
				return v1+v2;
			}
		});
		
		JavaPairRDD<Long, Tuple2<Integer, MoviesPOJO>> rating_movie = movieNumRdd.join(Utils.getMovieInfoRdd());
		JavaPairRDD<Integer, String> ratingNum_MovieNameRdd = rating_movie.mapToPair(new PairFunction<Tuple2<Long,Tuple2<Integer,MoviesPOJO>>, Integer, String>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Tuple2<Integer, String> call(Tuple2<Long, Tuple2<Integer, MoviesPOJO>> t) throws Exception {
				int ratingNum=t._2._1;
				String movieName = t._2._2.getMovieName();
				return new Tuple2<Integer, String>(ratingNum,movieName);
			}
		});
		JavaPairRDD<Integer, String> sortByKey = ratingNum_MovieNameRdd.sortByKey(false);
		List<Tuple2<Integer, String>> take = sortByKey.take(5);
		for(Tuple2<Integer, String> t:take){
			System.out.println(t._2+"--"+t._1);
		}
	}
}
