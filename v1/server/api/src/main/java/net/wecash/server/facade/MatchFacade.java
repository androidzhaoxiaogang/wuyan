/**
 * 
 */
package net.wecash.server.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.wecash.common.exception.ErrorCode;
import net.wecash.common.exception.ErrorCodeException;
import net.wecash.common.mongo.model.SnsDataBean;
import net.wecash.common.util.SetOperationUtil;
import net.wecash.server.analyzer.score.AnalyzerScoreBean;
import net.wecash.server.analyzer.score.ScoreCalculator;
import net.wecash.server.analyzer.score.service.UserScoreDAO;
import net.wecash.server.bean.UserDetail;
import net.wecash.server.bean.UserImgBean;
import net.wecash.server.interest.InterestDAO;
import net.wecash.server.interest.InterestEncodeBean;
import net.wecash.server.mysql.model.UserImg;
import net.wecash.server.sns.dao.SnsDataDAO;
import net.wecash.server.tag.funny.FunnyTagCalBean;
import net.wecash.server.tag.funny.FunnyTagFactory;
import net.wecash.server.user.dao.UserDAO;
import net.wecash.server.user.dao.UserImageDAO;
import net.wecash.server.util.PortraitUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author franklin.li
 * 
 */
@Service
public class MatchFacade {
	@Autowired
	UserScoreDAO userScoreDAO;
	@Autowired
	InterestDAO interestDAO;
	@Autowired
	UserDAO userDAO;
	@Autowired
	UserFacade userFacade;
	@Autowired
	SnsDataDAO snsDataDAO;
	@Autowired
	UserImageDAO userImageDAO;
	@Autowired
	FunnyTagFactory funnyTagFactory;
	@Autowired
	FavoriteFacade favoriteFacade;
	@Autowired
	ObjectMapper mapper;
	
	@SuppressWarnings("rawtypes")
	public List<Map> addMatchedUsers(long id, Boolean flag, int limit, int cursor){
		List<AnalyzerScoreBean> scoreList = userScoreDAO.getScores(id, flag, cursor, limit > 10 ? 10 : limit);
		List<Map> requestList = new ArrayList<Map>();
		if(scoreList != null && scoreList.size() > 0) {
			List<Long> targetIds = PortraitUtil.getTargetIds(scoreList);
			List<UserImgBean> userImgs = userImageDAO.getAllNormalImgs(targetIds);
			List<UserImgBean> userIcons = userImageDAO.getAllIconImgs(targetIds);
			List<UserDetail> uds = userDAO.getUserDetails(targetIds);
			InterestEncodeBean ieb = interestDAO.get(id);
			List<InterestEncodeBean> iebs = interestDAO.gets(targetIds);
			UserDetail selfDetailInfo = userDAO.getUserDetail(id);
			Map<Long, List<String>> userImgsMap = PortraitUtil.getUserImgMap(userImgs);
			Map<Long, String> userIconsMap = PortraitUtil.getUserIcon(userIcons);
			Map<Long, InterestEncodeBean> iebsMap = PortraitUtil.getUserInterests(iebs);
			FunnyTagCalBean selfFunnyTagCalBean = new FunnyTagCalBean(selfDetailInfo.getHabit(), selfDetailInfo.getLandmark(), 
						selfDetailInfo.getPersonality(), selfDetailInfo.getOccupation(), ieb.getInterest());
			for(UserDetail ud : uds){
				Long targetId = (long)ud.getUserId();
				Map<String, Object> userMap = new HashMap<String, Object>();
				InterestEncodeBean targetIeb = iebsMap.get(targetId);
				FunnyTagCalBean targetFunnyTagCalBean = new FunnyTagCalBean(ud.getHabit(), ud.getLandmark(),
						ud.getPersonality(), ud.getOccupation(), targetIeb.getInterest());
				Map<String, Integer> funnytags = funnyTagFactory.getFunnyTag(selfFunnyTagCalBean, targetFunnyTagCalBean);
				userMap.put("funnyTags", funnytags);
				userMap.put("name", ud.getName());
				if(ud.getDescription() != null){
					userMap.put("description", ud.getDescription());
				}else{
					userMap.put("description", "");
				}
				userMap.put("userId", ud.getUserId());
				userMap.put("gender", ud.getGender());
				userMap.put("state", ud.getState());
				if(userImgsMap.get(targetId) != null){
					userMap.put("image", userImgsMap.get(targetId));
				}else{
					userMap.put("image", new ArrayList<>());
				}
				if(userIconsMap.get(targetId) != null){
					userMap.put("icon", userIconsMap.get(targetId));
				}
				requestList.add(userMap);
			}
			userScoreDAO.readScoresList(id, targetIds);
		}
		return requestList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserDetail addUserDetailMatchInfo(Long id, Long targetId){
		
		UserDetail target = userFacade.getUserDetailInfo(targetId);
		if(target != null){
			boolean isFavorite = favoriteFacade.isFavorite(id, targetId);
			target.setIsFavorite(isFavorite);
			SnsDataBean targetSnsDataBean = snsDataDAO.get(targetId);
			SnsDataBean selfSnsDataBean = snsDataDAO.get(id);
			AnalyzerScoreBean scorebean = userScoreDAO.getScore(id, targetId);
			//Collect c = collectDAO.getCollectById(id, targetId);
			if(isFavorite){
				UserDetail self = userFacade.getUserDetailInfo(id);
				InterestEncodeBean iebSelf = interestDAO.get(id);
				InterestEncodeBean iebTarget = interestDAO.get(targetId);
				scorebean = ScoreCalculator.getScore(self, target, iebSelf.getInterestEncode(), 
						iebTarget.getInterestEncode(), 
						PortraitUtil.getSnsDataFriensNameList(selfSnsDataBean.getBilateralFriends()), 
						PortraitUtil.getSnsDataFriensNameList(targetSnsDataBean.getBilateralFriends()));
			}else{
				target.setScore(mapper.convertValue(scorebean, Map.class));
				scorebean = userScoreDAO.getScore(id, targetId);
			}
			
			if(scorebean == null){
				throw new ErrorCodeException(ErrorCode.PERMISSION_DENIED);
			}else{
				if (target != null) {
					// 添加分数
					target.setScore(mapper.convertValue(scorebean, Map.class));
					// 添加标签
					List<String> tagList = targetSnsDataBean.getTags();
					if(tagList != null && tagList.size() > 6){
						tagList = tagList.subList(0, 5);
					}
					target.setTags(tagList);
					// 添加共同好友
					Map<String, Map> targetFriends = PortraitUtil.getSnsDataFriensMap(targetSnsDataBean.getBilateralFriends());
					Map<String, Map> selfFriends = PortraitUtil.getSnsDataFriensMap(selfSnsDataBean.getBilateralFriends());
					List<String> sameFriends = new ArrayList(SetOperationUtil.union(targetFriends.keySet(), selfFriends.keySet()));
					List<Map> friends = new ArrayList<Map>();
					for (String uid : sameFriends) {
						if(selfFriends.containsKey(uid)){
							friends.add(selfFriends.get(uid));
						}
					}
					if(friends.size() > 5){
						friends = friends.subList(0, 4);
					}
					target.setFriends(friends);
					UserImg ui = userImageDAO.getUserIconInfo(targetId);
					if(ui.getImage() != null){
						target.setIcon(ui.getImage());
					}
				}
			}
		}else {
			throw new ErrorCodeException(ErrorCode.USER_DOSE_NOT_EXISTS);
		}
		return target;
	}
}
