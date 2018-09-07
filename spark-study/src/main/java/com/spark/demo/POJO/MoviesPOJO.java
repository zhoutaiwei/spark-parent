package com.spark.demo.POJO;

import java.io.Serializable;

/**
 *  2::Jumanji (1995)::Adventure|Children's|Fantasy
 *  电影 ID，电影名字，电影类型
 * @author Administrator
 *
 */
public class MoviesPOJO implements Serializable{
	Long movieId;
	String movieName;
	String movieType;
	public Long getMovieId() {
		return movieId;
	}
	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getMovieType() {
		return movieType;
	}
	public void setMovieType(String movieType) {
		this.movieType = movieType;
	}
	public MoviesPOJO(Long movieId, String movieName, String movieType) {
		super();
		this.movieId = movieId;
		this.movieName = movieName;
		this.movieType = movieType;
	}
	public MoviesPOJO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "MoviesPOJO [movieId=" + movieId + ", movieName=" + movieName + ", movieType=" + movieType + "]";
	}
	
}
