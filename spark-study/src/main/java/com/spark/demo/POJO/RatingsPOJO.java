package com.spark.demo.POJO;

import java.io.Serializable;

/**
 * package com.spark.demo.POJO;
 * @author Administrator
 *1::1193::5::978300760
 *用户 ID，电影 ID，评分，评分时间戳
 */

public class RatingsPOJO implements Serializable{
	private Long userId;
	private Long movieId;
	private Double rating ;
	private String Timestamped ;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getMovieId() {
		return movieId;
	}
	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public String getTimestamped() {
		return Timestamped;
	}
	public void setTimestamped(String timestamped) {
		Timestamped = timestamped;
	}
	public RatingsPOJO(Long userId, Long movieId, Double rating, String timestamped) {
		super();
		this.userId = userId;
		this.movieId = movieId;
		this.rating = rating;
		Timestamped = timestamped;
	}
	public RatingsPOJO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "RatingsPOJO [userId=" + userId + ", movieId=" + movieId + ", tating=" + rating + ", Timestamped="
				+ Timestamped + "]";
	}
	
}
