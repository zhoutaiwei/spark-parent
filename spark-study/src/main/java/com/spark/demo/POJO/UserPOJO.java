package com.spark.demo.POJO;

import java.io.Serializable;

//1::M::16::12::70072
public class UserPOJO implements Serializable {
	private Long userId;
	private String sex;
	private Integer age;
	private Integer occupation ;//职业
	private Long zipcode ;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getOccupation() {
		return occupation;
	}
	public void setOccupation(Integer occupation) {
		this.occupation = occupation;
	}
	public Long getZipcode() {
		return zipcode;
	}
	public void setZipcode(Long zipcode) {
		this.zipcode = zipcode;
	}
	public UserPOJO(Long userId, String sex, Integer age, Integer occupation, Long zipcode) {
		super();
		this.userId = userId;
		this.sex = sex;
		this.age = age;
		this.occupation = occupation;
		this.zipcode = zipcode;
	}
	public UserPOJO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "UserPOJO [userId=" + userId + ", sex=" + sex + ", age=" + age + ", occupation=" + occupation
				+ ", zipcode=" + zipcode + "]";
	}
	
}
