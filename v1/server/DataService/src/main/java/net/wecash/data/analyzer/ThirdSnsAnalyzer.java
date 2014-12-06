/**
 * 
 */
package net.wecash.data.analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.wecash.common.mongo.model.SnsDataBean;
import net.wecash.data.analyzer.bean.SnsAuthBean;
import net.wecash.data.analyzer.corpus.CorpusFactory;
import net.wecash.data.analyzer.interest.service.InterestCalculator;
import net.wecash.data.segment.SegmentAnalyzer;
import net.wecash.data.segment.util.MatchUtil;
import net.wecash.data.sns.service.SnsDataDAO;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author franklin.li
 * 
 */
@Component
public class ThirdSnsAnalyzer {
	@Autowired
	CorpusFactory corpusFactory;
	@Autowired
	SegmentAnalyzer segmentAnalyzer;
	@Autowired
	SnsDataDAO snsDataDAO;
	@Autowired
	InterestCalculator interestCalculator;
	@Autowired
	ObjectMapper mapper;
	private static Logger logger = LoggerFactory.getLogger(ThirdSnsAnalyzer.class);
	
	public List<String> getTimelinesCorpus(SnsAuthBean sab){
		List<String> segList = corpusFactory.getSnsTimelinesCorpus(sab.getUid(), sab.getToken(), sab.getType());
		return MatchUtil.filteringShortTerm(segList);
	}
	
	public List<String> getFavoritesCorpus(SnsAuthBean sab){
		List<String> segList = corpusFactory.getSnsFavoritesCorpus(sab.getUid(), sab.getToken(), sab.getType());
		return MatchUtil.filteringShortTerm(segList);
	}
	
	public List<String> getTags(SnsAuthBean sab){
		List<String> tagList = corpusFactory.getSnsTags(sab.getUid(), sab.getToken(), sab.getType());
		return MatchUtil.filteringShortTerm(tagList);
	}
	
	public Map<String, Object> getUserInfo(SnsAuthBean sab){
		return corpusFactory.getSnsUserInfo(sab.getUid(), sab.getToken(), sab.getType());
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map> getFriends(SnsAuthBean sab){
		return corpusFactory.getSnsFriends(sab.getUid(), sab.getToken(), sab.getType());
	}
	
	public void analyzer(SnsAuthBean sab){
		try {
			List<String> timelineTexts = getTimelinesCorpus(sab);
			List<String> favoritesTexts = getFavoritesCorpus(sab);
			List<String> tags = getTags(sab);
			Map<String, Object> user = getUserInfo(sab);
			List<Map> friends = getFriends(sab);
			List<String> timelineTerm = null;
			List<String> favoriteTerm = null;
			if(timelineTexts != null){
				timelineTerm = segmentAnalyzer.getSegedList(segmentAnalyzer.segText(timelineTexts.toString()));
			}
			if(favoritesTexts != null){
				favoriteTerm = segmentAnalyzer.getSegedList(segmentAnalyzer.segText(favoritesTexts.toString()));
			}
			SnsDataBean sdb = new SnsDataBean(
					sab.getUid(), 
					sab.getUserId(), 
					sab.getToken(),
					sab.getType(),
					MapUtils.getString(user, "name"), 
					tags, 
					timelineTerm, 
					favoriteTerm,
					friends, 
					MapUtils.getString(user, "profile_image_url"));
			logger.info("add sns data:" + mapper.writeValueAsString(sdb));
			snsDataDAO.addSnsData(sdb);
			interestCalculator.analyzerSelfInterest(sdb, sab.getUserId());
		} catch (JsonProcessingException e) {
			logger.error("analyzer sns data error :" + e.getMessage());
		}
	}
}
