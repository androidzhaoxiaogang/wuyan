package net.wecash.common.dimension;

public class HabitType {
	public static final Integer SEVERE_CLEANLINESS = 3;
	public static final Integer Mild_cleanliness = 2;
	public static final Integer CLEAN = 1;
	public static final Integer DIRTY = 0;

	public static int getScore(Integer self, Integer target){
		int score = 0;
		if(self == null || self < 0){
			self = 0;
		}
		if(target == null || target < 0){
			target = 0;
		}
		int i = Math.abs(self - target);
		switch (i) {
		case 0:
			score = 100;
			break;
		case 1:
			score = 66;
			break;
		case 2:
			score = 33;
			break;
		case 3:
			score = 0;
			break;
		default:
			break;
		}
		return score;
	}
}
