package net.wecash.common.util;

import java.util.ArrayList;
import java.util.List;

public class MatchUtil {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List uniq(List l){
		List tempList= new ArrayList();  
		for(Object i:l){  
			if(!tempList.contains(i) && Integer.valueOf(i.toString()) != 0){  
				tempList.add(i);  
			}  
		}  
		return tempList;
	}

	public static List<String> filteLengthSeg(List<String> segList){
		if(segList == null){
			return null;
		}
		List<String> filteList = new ArrayList<String>();
		for(String seg : segList){
			if(seg.length() >= 2){
				filteList.add(seg);
			}
		}
		return filteList;
	}
	
	public static List<Integer> formateTagType(String type){
		List<Integer> typesList = new ArrayList<Integer>();
		if(type != null){
			try{
				if(type.contains(",")){
					String[] types = type.split(",");
					for(String s : types){
						typesList.add(Integer.valueOf(s));
					}
				}else{
					typesList.add(Integer.valueOf(type));
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return typesList;
	}
	public static boolean isChinese(String s){
		char[] ch = s.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
	}
	private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
}
