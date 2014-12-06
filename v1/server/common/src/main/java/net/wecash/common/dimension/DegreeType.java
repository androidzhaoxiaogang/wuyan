package net.wecash.common.dimension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DegreeType {
	public static final Integer DOCTOR = 4;
	public static final Integer MASTER = 3;
	public static final Integer BACHELOR = 2;
	public static final Integer SPECIALIST = 1;
	public static final Integer OTHER = 0;
	private static final Integer[] degree = {OTHER, SPECIALIST, BACHELOR, MASTER, DOCTOR};
	
	public static int getScore(Integer self, Integer target){
		int score = 0;
		if(self == null || self < 0){
			self = 0;
		}
		if(target == null || target < 0){
			target = 0;
		}
		int i = Math.abs(self - target);
		if(i == 0){
			score = 100;
		}else if(i == 1){
			score = 75;
		}else if(i == 2){
			score = 50;
		}else if(i == 3){
			score = 25;
		}else{
			score = 0;
		}
		return score;
	}
	
	/**
	 * 学历差别不大
	 * @param self
	 * @return
	 */
	public static List<Integer> getMatchedDegree(Integer self){
		if(self == null){
			return null;
		}else if(self < 0){
			self = 0;
		}
		List<Integer> list = new ArrayList<Integer>();
		List<Integer> degreeList = Arrays.asList(degree);
		for(int i = 0; i < 2; i++){
			int under = self - i;
			int upper = self + i;
			if(degreeList.contains(under) && !list.contains(under)){
				list.add(under);
			}
			if(degreeList.contains(upper) && !list.contains(upper)){
				list.add(upper);
			}
		}
		return list;
	}
	public static void main(String[]args){
		Integer i = null;
		System.out.println(getMatchedDegree(i));
	}
}
