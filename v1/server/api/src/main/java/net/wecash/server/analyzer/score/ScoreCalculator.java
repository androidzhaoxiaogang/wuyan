/**
 * 
 */
package net.wecash.server.analyzer.score;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.wecash.common.dimension.AgeType;
import net.wecash.common.dimension.DegreeType;
import net.wecash.common.dimension.HabitType;
import net.wecash.common.dimension.InterestType;
import net.wecash.common.dimension.OccupationType;
import net.wecash.common.dimension.RelationType;
import net.wecash.common.util.DateUtils;
import net.wecash.server.bean.UserDetail;
import net.wecash.server.bean.UserPortraitResultBean;
import net.wecash.server.interest.InterestEncodeBean;
import net.wecash.server.mysql.model.User;
import net.wecash.server.util.PortraitUtil;

/**
 * @author franklin.li
 * 
 */
public class ScoreCalculator {
	public static List<AnalyzerScoreBean> getTotalScore(List<Long> userIds, 
			User user, 
			String interestBloomCode,  
			List<UserPortraitResultBean> uprbs, 
			List<InterestEncodeBean> iebs,
			List<String> selfRelation,
			Map<Long, List<String>> relationMap){
		Map<Long, UserPortraitResultBean> uprbMap = PortraitUtil.getUserPortraitMap(uprbs);
		Map<Long, InterestEncodeBean> iebMap = PortraitUtil.getUserInterests(iebs);
		List<AnalyzerScoreBean> asbs = new ArrayList<>();
		for(Long l : userIds){
			if(l >= 0 && l != user.getId()){
				UserPortraitResultBean u = uprbMap.get(l);
				InterestEncodeBean i = iebMap.get(l);
				AnalyzerScoreBean asb = new AnalyzerScoreBean();
				//计算习惯
				asb.setHabit(HabitType.getScore(user.getHabit(), u.getHabit()));
				//计算年龄
				Date targetBirthday = u.getBirthday();
				asb.setBirthday(AgeType.getAgeScore(DateUtils.getYear(user.getBirthday()), DateUtils.getYear(targetBirthday)));
				//计算兴趣
				String tempInterestEncode = null;
				if(i != null && i.getInterestEncode() != null){
					tempInterestEncode = i.getInterestEncode();
				}
				asb.setInterest(InterestType.getScore(interestBloomCode, tempInterestEncode));
				//计算行业
				asb.setOccupation(OccupationType.getScore(user.getOccupation(), u.getOccupation()));
				//计算教育背景
				asb.setDegree(DegreeType.getScore(user.getDegree(), u.getDegree()));
				//计算人脉
				List<String> relation = relationMap.get(l);
				asb.setSocialRelation(RelationType.getScore(selfRelation, relation));
				
				asb.setTotal((int)(asb.getHabit()*0.3 + asb.getBirthday() * 0.3 + asb.getInterest() * 0.2 
						+ asb.getDegree() * 0.1 + asb.getOccupation() * 0.1/* + asb.getSocialRelation() * 0.1*/));
				asb.setSelfId(user.getId());
				asb.setTargetId(l);
				asb.setFlag(false);
				asbs.add(asb);
			}
		}
		return asbs;
	}
	
	public static AnalyzerScoreBean getScore(UserDetail self, UserDetail target, 
			String selfEncodeInterest, String targetEncodeIntereset,
			List<String> selfRelation, List<String> targetRelation){
		AnalyzerScoreBean asb = new AnalyzerScoreBean();
		//计算习惯
		asb.setHabit(HabitType.getScore(self.getHabit(), target.getHabit()));
		//计算年龄
		asb.setBirthday(AgeType.getAgeScore(DateUtils.getYear(self.getBirthday()), DateUtils.getYear(target.getBirthday())));
		//计算兴趣
		asb.setInterest(InterestType.getScore(selfEncodeInterest, targetEncodeIntereset));
		//计算行业
		asb.setOccupation(OccupationType.getScore(self.getOccupation(), target.getOccupation()));
		//计算教育背景
		asb.setDegree(DegreeType.getScore(self.getDegree(), target.getDegree()));
		//计算人脉
		asb.setSocialRelation(RelationType.getScore(selfRelation, targetRelation));
		
		asb.setTotal((int)(asb.getHabit()*0.3 + asb.getBirthday() * 0.3 + asb.getInterest() * 0.2 
				+ asb.getDegree() * 0.1 + asb.getOccupation() * 0.1/* + asb.getSocialRelation() * 0.1*/));
		asb.setSelfId((long)self.getUserId());
		asb.setTargetId((long)target.getUserId());
		asb.setFlag(false);
		return asb;
	}
}
