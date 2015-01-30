package com.likg.doubleball;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class DoubleBallUtil {

	public static final int RED_BALL_COUNT = 33;
	public static final int BULE_BALL_COUNT = 16;
	private static Random r = new Random();
	
	public static void main(String[] args) {
		int totalRedBall = 0;
		int count = 100000;
		for(int i=0; i<count; i++){
			Set<Integer> redSet = createRedBall();
			int buleBall = createBlueBall();
			DoubleBall doubleBall = new DoubleBall(redSet, buleBall);
			System.out.println(doubleBall);
			
			totalRedBall += doubleBall.getRedBallSum();
		}
		
		System.out.println("总平均值："+(totalRedBall*1.0 / count));
	}
	
	public static Set<Integer> createRedBall() {
		Set<Integer> redSet = new TreeSet<Integer>();
		while(redSet.size() < 6){
			int redBall = createOneRedBall();
			redSet.add(redBall);
		}
		return redSet;
	}
	private static int createOneRedBall() {
		return r.nextInt(RED_BALL_COUNT) + 1;
	}
	
	public static int createBlueBall() {
		return r.nextInt(BULE_BALL_COUNT) + 1;
	}
	
}

class DoubleBall {
	private Set<Integer> redSet = new TreeSet<Integer>();
	private int buleBall;
	//private int redBallSum;
	
	public DoubleBall() {
	}
	public DoubleBall(Set<Integer> redSet, int buleBall) {
		this.redSet = redSet;
		this.buleBall = buleBall;
	}
	public Set<Integer> getRedSet() {
		return redSet;
	}
	public void setRedSet(Set<Integer> redSet) {
		this.redSet = redSet;
	}
	public int getBuleBall() {
		return buleBall;
	}
	public void setBuleBall(int buleBall) {
		this.buleBall = buleBall;
	}
	public int getRedBallSum() {
		int redBallSum = 0;
		for(int red : redSet){
			redBallSum += red;
		}
		return redBallSum;
	}
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(int red : redSet){
			str.append(red).append("\t");
		}
		str.append(": ").append(this.buleBall);
		
		str.append("\t").append(this.getRedBallSum());
		
		return str.toString();
	}
}



