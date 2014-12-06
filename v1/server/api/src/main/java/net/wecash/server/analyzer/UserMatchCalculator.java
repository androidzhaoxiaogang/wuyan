package net.wecash.server.analyzer;

import java.util.List;
import java.util.Map;

import net.wecash.common.dimension.DegreeType;
import net.wecash.common.dimension.InterestType;
import net.wecash.common.dimension.OccupationType;
import net.wecash.common.dimension.RelationType;
import net.wecash.common.mongo.model.SnsDataBean;
import net.wecash.server.analyzer.bean.ScoreTriggerBean;
import net.wecash.server.analyzer.score.AnalyzerScoreBean;
import net.wecash.server.analyzer.score.ScoreCalculator;
import net.wecash.server.analyzer.score.service.UserScoreDAO;
import net.wecash.server.bean.UserPortraitResultBean;
import net.wecash.server.facade.UserFacade;
import net.wecash.server.interest.InterestDAO;
import net.wecash.server.interest.InterestEncodeBean;
import net.wecash.server.mysql.model.User;
import net.wecash.server.sns.dao.SnsDataDAO;
import net.wecash.server.user.dao.PortraitDAO;
import net.wecash.server.user.dao.UserDAO;
import net.wecash.server.util.PortraitUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 
 * @author franklin.li
 *
 */
@Component
public class UserMatchCalculator {
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	PortraitDAO portraitDAO;
	
	@Autowired
	InterestDAO interestDAO;
	
	@Autowired
	UserScoreDAO userScoreDAO;
	
	@Autowired
	SnsDataDAO snsDataDAO;
	
	private static Logger logger = LoggerFactory.getLogger(UserMatchCalculator.class);
	

	public void analyzer(ScoreTriggerBean atb){
		Assert.notNull(atb);
		try {
			Long userId = atb.getUserId();
			User user = userDAO.getUser(userId);
			SnsDataBean sdb = snsDataDAO.get(userId);
			List<Integer> degree = DegreeType.getMatchedDegree(user.getDegree());
			List<Integer> occupation = OccupationType.getMatchedOccupation(user.getOccupation());
			InterestEncodeBean ieb = interestDAO.get(userId);
			String interestBloomCode = null;
			if(ieb != null){
				interestBloomCode = ieb.getInterestEncode();
			}
			List<UserPortraitResultBean> uprbs = portraitDAO.getPortrait(userId, atb.getTargetUserIds(), degree, occupation, user.getPersonality(), user.getState(), 300);
			if(uprbs == null || uprbs.size() == 0){
				logger.info("user id("+userId+")" + "degree("+degree+") occupation("+occupation+") personality("+user.getPersonality()+") match failed ");
			}else{
				List<Long> reFilterUserIds = PortraitUtil.getUserId(uprbs);
				List<InterestEncodeBean> iebs = interestDAO.gets(reFilterUserIds, InterestType.generateInterestRegex(interestBloomCode), 500);
				List<SnsDataBean> sdbs = snsDataDAO.gets(reFilterUserIds);
				Map<Long, List<String>> relationMap = RelationType.getRelations(reFilterUserIds, sdbs);
				List<String> selfRelation = RelationType.getRelation(sdb);
				List<AnalyzerScoreBean> asbs = ScoreCalculator.getTotalScore(reFilterUserIds, user, interestBloomCode, uprbs, iebs, selfRelation, relationMap);
				userScoreDAO.addScores(userId, asbs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
