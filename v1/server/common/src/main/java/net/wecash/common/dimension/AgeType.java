package net.wecash.common.dimension;

public class AgeType {
	public static final Integer SAME_AGE = 0;
	public static final Integer VARIANT = 1;
	public static final Integer GENERATIONS = 2;
	public static int getAgeScore(Integer self, Integer target){
		int score = 0;
		if(self == null || self < 0){
			self = 0;
		}
		if(target == null || target < 0){
			target = 0;
		}
		int i = Math.abs(self - target);
		if(i <= 4){
			score = 100;
		}else if(i <=8 && i > 4){
			score = 50;
		}
		return score;
	}
}
