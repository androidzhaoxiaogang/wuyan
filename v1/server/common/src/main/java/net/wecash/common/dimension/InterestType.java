package net.wecash.common.dimension;

import java.util.List;
import java.util.Random;

public class InterestType {
	public static final int UNCONFIRMED = 0;
	public static final int MUSIC = 1;
	public static final int SPORT = 2;
	public static final int PHOTOGRAPHY_DRAWING_ART = 3;
	public static final int ANIMATION_GAME = 4;
	public static final int CATE = 5;
	public static final int TRAVEL = 6;
	public static final int READ = 7;
	public static final int FILM_TELEVISION = 8;
	public static final int BENEFIT_ACTIVITIES = 9;
	public static final int TECHNOLOGY_FINANCE = 10;
	public static final int PET = 11;
	
	public static final int[] types = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
	public static String encodeInterestTypes(List<Integer> interests){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < types.length; i++){
			int bloom = 0;
			if(interests.contains(types[i])){
				bloom = 1;
			}
			sb.append(bloom);
		}
		return sb.toString();
	}
	public static String generateInterestRegex(String interestBloomCode){
		StringBuffer sb = new StringBuffer();
		if(interestBloomCode != null){
			char[] list = interestBloomCode.toCharArray();
			for(char c : list){
				if(c == '1'){
					sb.append("\\d");
				}else{
					if(new Random().nextBoolean()){
						sb.append("\\d");
					}else{
						sb.append("0");
					}
				}
			}
			return sb.toString();
		}else{
			return null;
		}
		
	}
	
	public static int getScore(String selfEncodeInterest, String targetEncodeIntereset){
		int score = 0;
		if(targetEncodeIntereset != null && selfEncodeInterest != null){
			char[] selfEncode = selfEncodeInterest.toCharArray();
			char[] targetEncode = targetEncodeIntereset.toCharArray();
			/*char True = '1';*/
			int count = 0;
			for(int i = 0; i < selfEncode.length && i < targetEncode.length; i++){
				if(selfEncode[i] == targetEncode[i]/* && selfEncode[i] == True*/){
					count++;
				}
			}
			float f = count/11f + 0.05f;
			score = (int)(f * 100.0);
			if(score > 100){
				score = 100;
			}else if(score < 0 || count == 0){
				score = 0;
			}
		}
		return score;
	}
}
