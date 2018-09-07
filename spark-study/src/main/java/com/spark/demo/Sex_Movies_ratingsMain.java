package com.spark.demo;

import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.json.JSONObject;

import com.google.common.collect.Lists;
import com.spark.demo.POJO.MoviesPOJO;
import com.spark.demo.POJO.RatingsPOJO;
import com.spark.demo.POJO.UserPOJO;

import parquet.it.unimi.dsi.fastutil.ints.IntIterators;
import scala.Tuple2;

//2、分别求男性，女性当中评分最高的 2 部电影（性别，电影名，影评分）
public class Sex_Movies_ratingsMain {
	public static void main(String[] args) {
		JavaPairRDD<Long, UserPOJO> userInfoRdd = Utils.getUserInfoRdd();
		JavaPairRDD<Long, RatingsPOJO> ratingInfoRdd = Utils.getRatingInfoRdd();
		JavaPairRDD<Long, MoviesPOJO> movieInfoRdd = Utils.getMovieInfoRdd();
		JavaPairRDD<Long, Tuple2<UserPOJO, RatingsPOJO>> user_ratingRdd = userInfoRdd.join(ratingInfoRdd);
		JavaPairRDD<Long, String> movieId_sex = user_ratingRdd.mapToPair(new PairFunction<Tuple2<Long,Tuple2<UserPOJO,RatingsPOJO>>, Long, String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Tuple2<Long, String> call(Tuple2<Long, Tuple2<UserPOJO, RatingsPOJO>> t) throws Exception {
				String sex = t._2._1.getSex();
				Double rating = t._2._2.getRating();
				Long movieId = t._2._2.getMovieId();
				JSONObject json = new JSONObject();
				json.put("sex", sex);
				json.put("rating", rating);
				return new Tuple2<Long, String>(movieId,json.toString());
			}
		});
		JavaPairRDD<Long, Tuple2<String, MoviesPOJO>> fullRdd = movieId_sex.join(movieInfoRdd);
		JavaPairRDD<String, Tuple2<String, Double>> mapToPair = fullRdd.mapToPair(new PairFunction<Tuple2<Long,Tuple2<String,MoviesPOJO>>, String, Tuple2<String,Double>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Tuple2<String, Tuple2<String, Double>> call(Tuple2<Long, Tuple2<String, MoviesPOJO>> t)
					throws Exception {
				String movieName = t._2._2.getMovieName();
				String info= t._2._1;
				JSONObject json = new JSONObject(info);
				String sex=json.getString("sex");
				double rating = json.getDouble("rating");
				Tuple2<String, Double> movieName_rating = new Tuple2<String,Double>(movieName,rating);
				return new Tuple2<String, Tuple2<String, Double>>(sex,movieName_rating);
			}
		});
		JavaPairRDD<String, Iterable<Tuple2<String, Double>>> sexGroup = mapToPair.groupByKey();
		sexGroup.foreach(new VoidFunction<Tuple2<String,Iterable<Tuple2<String,Double>>>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void call(Tuple2<String, Iterable<Tuple2<String, Double>>> t) throws Exception {
				if("F".equals(t._1)){
					List<Tuple2<String, Double>> list = IteratorUtils.toList(t._2.iterator());
					list.sort(new Comparator<Tuple2<String, Double>>() {

						@Override
						public int compare(Tuple2<String, Double> o1, Tuple2<String, Double> o2) {
							Double rating1=o1._2;
							Double rating2=o2._2;
							
							return -(int)(rating1-rating2);
						}
					});
					List<Tuple2<String, Double>> newList = list.subList(0, 2);
					for (Tuple2<String, Double> tuple:newList) {
						System.out.println("F--"+tuple._1+"--"+tuple._2);
					}
				}else if("M".equals(t._1)){
					List<Tuple2<String, Double>> list = IteratorUtils.toList(t._2.iterator());
					list.sort(new Comparator<Tuple2<String, Double>>() {

						@Override
						public int compare(Tuple2<String, Double> o1, Tuple2<String, Double> o2) {
							Double rating1=o1._2;
							Double rating2=o2._2;
							
							return -(int)(rating1-rating2);
						}
					});
					List<Tuple2<String, Double>> newList = list.subList(0, 2);
					for (Tuple2<String, Double> tuple:newList) {
						System.out.println("M--"+tuple._1+"--"+tuple._2);
					}
				}
				
			}
		});
	}
}
