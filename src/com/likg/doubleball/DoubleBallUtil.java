package com.likg.doubleball;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class DoubleBallUtil {

	public static final int RED_BALL_COUNT = 33;
	public static final int BULE_BALL_COUNT = 16;
	private static Random r = new Random();
	
	public static void main(String[] args) throws IOException {
		
		
		/*
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
		*/
		
		int totalRedBall = 0;
		List<DoubleBall> doubleBallList = getHistoryLottery();
		for(DoubleBall ball : doubleBallList){
			totalRedBall += ball.getRedBallSum();
		}
		System.out.println("总平均值："+(totalRedBall*1.0 / doubleBallList.size()));
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
	
	
	
	
	public static List<DoubleBall> getHistoryLottery() throws IOException {
		List<DoubleBall> doubleBallList = new ArrayList<DoubleBall>();
		InputStream input = DoubleBallUtil.class.getClassLoader().getResourceAsStream("historyLottery.txt");
		List<String> lineList = IOUtils.readLines(input);
		for(String line : lineList){
			//空行
			if(StringUtils.isBlank(line)){
				continue;
			}
			
			//2015013    08,09,24,25,26,29|01    2015-01-29
			String[] result = line.split("\\s+");
			DoubleBall doubleBall = new DoubleBall();
			doubleBall.setPhaseCode(result[0]);
			doubleBall.setLotteryDate(result[2]);
			String[] codes = result[1].split("\\|");
			
			for(String redBall : codes[0].split(",")){
				doubleBall.getRedBallSet().add(Integer.valueOf(redBall));
			}
			doubleBall.setBuleBall(Integer.valueOf(codes[1]));
			
			System.out.println(doubleBall);
			doubleBallList.add(doubleBall);
		}
		
		return doubleBallList;
	}
	
}




