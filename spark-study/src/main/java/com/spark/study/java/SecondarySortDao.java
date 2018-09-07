package com.spark.study.java;

import java.io.Serializable;

import java.io.Serializable;

import scala.math.Ordered;

public class SecondarySortDao implements Ordered<SecondarySortDao>,Serializable{
	private int first;
	private int second;
	public boolean $greater(SecondarySortDao ss) {
		if(this.getFirst()>ss.getFirst()){
			return true;
		}else if(this.getFirst()==ss.getFirst()&&this.getSecond()>ss.getSecond()){
			return true;
		}
		return false;
	}
	public boolean $greater$eq(SecondarySortDao ss) {
		if($greater(ss)==true){
			return true;
		}else if(this.getFirst()==ss.getFirst()&&this.getSecond()==ss.getSecond()){
			return true;
		}
		return false;
	}
	public boolean $less(SecondarySortDao ss) {
		if(this.getFirst()<ss.getFirst()){
			return true;
		}else if(this.getFirst()==ss.getFirst()&&this.getSecond()<ss.getSecond()){
			return true;
		}
		return false;
	}
	public boolean $less$eq(SecondarySortDao ss) {
		if($less(ss)==true){
			return true;
		}else if(this.getFirst()==ss.getFirst()&&this.getSecond()==ss.getSecond()){
			return true;
		}
		return false;
	}
	public int compare(SecondarySortDao ss) {
		int first=this.getFirst()-ss.getFirst();
		if(first==0){
			return this.getSecond()-ss.getSecond();
		}else{
			return first;
		}
	}
	public int compareTo(SecondarySortDao ss) {
		int first=this.getFirst()-ss.getFirst();
		if(first==0){
			return this.getSecond()-ss.getSecond();
		}else{
			return first;
		}
	}

	public SecondarySortDao(int first, int second) {
		super();
		this.first = first;
		this.second = second;
	}
	public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	public SecondarySortDao() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "SecondarySortDao [first=" + first + ", second=" + second + "]";
	}
	
}
