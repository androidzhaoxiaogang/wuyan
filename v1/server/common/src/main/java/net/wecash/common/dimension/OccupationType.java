package net.wecash.common.dimension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OccupationType {
	public static final Integer IT_FINANCIAL = 6;
	public static final Integer STUDENT = 5;
	public static final Integer EDUCATION_CULTURE_MEDIA_ENTERTAINMENT = 4;
	public static final Integer ESTATE = 3;
	public static final Integer MANUFACTURING_LOGISTICS_PEDDLE  = 2;
	public static final Integer SERVICE = 1;
	public static final Integer OTHER = 0;
	private static final Integer[] occupation = 
		{OTHER, SERVICE, MANUFACTURING_LOGISTICS_PEDDLE, ESTATE, EDUCATION_CULTURE_MEDIA_ENTERTAINMENT, STUDENT, IT_FINANCIAL};
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
		}else if( i == 4 || i == 3){
			score = 25;
		}else{
			score = 0;
		}
		return score;
	}
	
	public static List<Integer> getMatchedOccupation(Integer self){
		if(self == null){
			return null;
		}else if(self < 0){
			self = 0;
		}
		List<Integer> list = new ArrayList<Integer>();
		List<Integer> occupationList = Arrays.asList(occupation);
		for(int i = 0; i < 2; i++){
			int under = self - i;
			int under1 = self - i -1;
			int upper = self + i;
			if(self > MANUFACTURING_LOGISTICS_PEDDLE){
				if(occupationList.contains(under) && !list.contains(under) && under >= (self - 1)){
					list.add(under);
				}
				if(occupationList.contains(upper) && !list.contains(upper)){
					list.add(upper);
				}
				if(occupationList.contains(under1) && !list.contains(under1)){
					list.add(under1);
				}
			}else{
				if(occupationList.contains(under) && !list.contains(under)){
					list.add(under);
				}
				if(occupationList.contains(upper) && !list.contains(upper) && upper <= self + 1){
					list.add(upper);
				}
			}
		}
		return list;
	}
}
