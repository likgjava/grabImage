package com.likg.doubleball;

import java.util.Set;
import java.util.TreeSet;

public class DoubleBall {
	private String phaseCode; //期号
	private Set<Integer> redBallSet = new TreeSet<Integer>();
	private int buleBall;
	private String lotteryDate; //开奖日期
	
	public DoubleBall() {
	}
	public DoubleBall(Set<Integer> redBallSet, int buleBall) {
		this.redBallSet = redBallSet;
		this.buleBall = buleBall;
	}
	
	public String getPhaseCode() {
		return phaseCode;
	}
	public void setPhaseCode(String phaseCode) {
		this.phaseCode = phaseCode;
	}
	public Set<Integer> getRedBallSet() {
		return redBallSet;
	}
	public void setRedBallSet(Set<Integer> redBallSet) {
		this.redBallSet = redBallSet;
	}
	public int getBuleBall() {
		return buleBall;
	}
	public void setBuleBall(int buleBall) {
		this.buleBall = buleBall;
	}
	public String getLotteryDate() {
		return lotteryDate;
	}
	public void setLotteryDate(String lotteryDate) {
		this.lotteryDate = lotteryDate;
	}
	public int getRedBallSum() {
		int redBallSum = 0;
		for(int red : redBallSet){
			redBallSum += red;
		}
		return redBallSum;
	}
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(int red : redBallSet){
			str.append(red).append("\t");
		}
		str.append(": ").append(this.buleBall);
		
		str.append("\t").append(this.getRedBallSum());
		
		return str.toString();
	}


}
