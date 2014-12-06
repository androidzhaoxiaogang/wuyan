/**
 * 
 */
package net.wecash.server.tag.funny;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.wecash.server.tag.funny.dao.FunnyTagDAO;

import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author franklin.li
 * 
 */
@Component
public class FunnyTagFactory {
	@Autowired
	FunnyTagDAO funnyTagDAO; 
	
	@SuppressWarnings("unchecked")
	public Map<String, Integer> getFunnyTag(FunnyTagCalBean ftcb1, FunnyTagCalBean ftcb2){
		List<FunnyTag> fTagList =null;
		List<FunnyTag> fList = new ArrayList<>();
		Map<String, Integer> resultMap = new HashMap<>();
		try {
			if(ftcb1.getHabit() == ftcb2.getHabit()){
				fTagList = funnyTagDAO.getFunnyTags(FunnyTagType.CLAZZ_HABIT, 0, ftcb1.getHabit());
				fList.addAll(fTagList);
			}
			if(ftcb1.getLandmark() != null && ftcb2.getLandmark() != null){
				if(ftcb1.getLandmark().equals(ftcb2.getLandmark())){
					fTagList = funnyTagDAO.getFunnyTags(FunnyTagType.CLAZZ_LOCATION, 0, null);
					fList.addAll(fTagList);
				}
			}
			int personalityValue = 0;
			if(ftcb1 != null && ftcb1.getPersonality() != null){
				if(ftcb1.getPersonality() < 0.4f){
					personalityValue = 0;
				}else if(ftcb1.getPersonality() >= 0.4f && ftcb1.getPersonality() <= 0.6){
					personalityValue = 1;
				}else{
					personalityValue = 2;
				}
			}

			fTagList = funnyTagDAO.getFunnyTags(FunnyTagType.CLAZZ_PERSONALITY, 0, personalityValue);
			fList.addAll(fTagList);
			
			if(ftcb1.getOccupation() == ftcb2.getOccupation()){
				fTagList = funnyTagDAO.getFunnyTags(FunnyTagType.CLAZZ_OCCUPATION, 0, null);
				fList.addAll(fTagList);
			}
			if(ftcb1.getInterestList() != null && ftcb2.getInterestList() != null){
				List<Integer> interectionList = ListUtils.intersection(ftcb1.getInterestList(), ftcb2.getInterestList());
				fTagList = funnyTagDAO.getFunnyTags(FunnyTagType.CLAZZ_INTEREST, interectionList, null);
				fList.addAll(fTagList);
			}
			setFunnyTagMap(fList, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	private void setFunnyTagMap(List<FunnyTag> fTagList, Map<String, Integer> fTags){
		if(fTagList != null){
			for(FunnyTag ft : fTagList){
				fTags.put(ft.getName(), ft.getCategory());
			}
		}
		int remain = FunnyTagType.MAX_RETRUN - fTags.size(); 
		if(remain > 0){
			for(int i = 0; i < remain; i++){
				fTags.put(FunnyTagType.defaultFunnyTag[i], FunnyTagType.FUNNY_TAG_TYPE_DEFAULT);
			}
		}
	}
}
