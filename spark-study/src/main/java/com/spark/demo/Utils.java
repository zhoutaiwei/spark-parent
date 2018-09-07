package com.spark.demo;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import com.spark.demo.POJO.MoviesPOJO;
import com.spark.demo.POJO.RatingsPOJO;
import com.spark.demo.POJO.UserPOJO;

import scala.Tuple2;

public class Utils {
	static SparkConf conf = new SparkConf().setAppName("Movies_ratingsMain").setMaster("local");
	static JavaSparkContext sc = new JavaSparkContext(conf);
	static JavaRDD<String> userRdd = sc.textFile("user.txt");
	static JavaRDD<String> moviesRdd = sc.textFile("movies.txt");
	static JavaRDD<String> ratingRdd = sc.textFile("ratings.txt");

	public static JavaPairRDD<Long, MoviesPOJO> getMovieInfoRdd() {
		// 封装电影信息
		JavaPairRDD<Long, MoviesPOJO> movieInfoRdd = moviesRdd.mapToPair(new PairFunction<String, Long, MoviesPOJO>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Tuple2<Long, MoviesPOJO> call(String t) throws Exception {
				String[] line = t.split("::");
				Long movieId = Long.parseLong(line[0]);
				String movieName = line[1];
				String movieType = line[2];
				MoviesPOJO moviesPOJO = new MoviesPOJO(movieId, movieName, movieType);
				return new Tuple2<Long, MoviesPOJO>(movieId, moviesPOJO);
			}
		});
		return movieInfoRdd;
	}

	public static JavaPairRDD<Long, UserPOJO> getUserInfoRdd() {

		// 封装用户信息
		JavaPairRDD<Long, UserPOJO> userInfoRdd = userRdd.mapToPair(new PairFunction<String, Long, UserPOJO>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Tuple2<Long, UserPOJO> call(String lines) throws Exception {
				String[] words = lines.split("::");
				Long userId = Long.parseLong(words[0]);
				String sex = words[1];
				int age = Integer.parseInt(words[2]);
				int occupation = Integer.parseInt(words[3]);
				Long zipCode = Long.parseLong(words[4]);
				UserPOJO userPOJO = new UserPOJO();
				userPOJO.setUserId(userId);
				userPOJO.setSex(sex);
				userPOJO.setAge(age);
				userPOJO.setOccupation(occupation);
				userPOJO.setZipcode(zipCode);

				return new Tuple2<Long, UserPOJO>(userId, userPOJO);
			}
		});
		return userInfoRdd;
	}
	//封装了影评信息，<user,ratingPOJO>
	public static JavaPairRDD<Long, RatingsPOJO> getRatingInfoRdd(){
		JavaPairRDD<Long, RatingsPOJO> user_ratingRdd = ratingRdd.mapToPair(new PairFunction<String, Long, RatingsPOJO>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Tuple2<Long, RatingsPOJO> call(String t) throws Exception {
				String[] words = t.split("::");
				long userId = Long.parseLong(words[0]);
				long movieId = Long.parseLong(words[1]);
				Double rating = Double.parseDouble(words[2]);
				String timestamped = words[3];
				RatingsPOJO ratingsPOJO = new RatingsPOJO();
				ratingsPOJO.setUserId(userId);
				ratingsPOJO.setMovieId(movieId);
				ratingsPOJO.setRating(rating);
				ratingsPOJO.setTimestamped(timestamped);
				return new Tuple2<Long, RatingsPOJO>(userId,ratingsPOJO);
			}
		});
		return user_ratingRdd;
		
	}
}
