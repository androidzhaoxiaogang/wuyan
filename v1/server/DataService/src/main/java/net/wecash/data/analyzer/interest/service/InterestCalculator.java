/**
 * 
 */
package net.wecash.data.analyzer.interest.service;

import java.util.ArrayList;
import java.util.List;

import net.wecash.common.dimension.InterestType;
import net.wecash.common.mongo.model.SnsDataBean;
import net.wecash.common.util.SetOperationUtil;
import net.wecash.data.segment.util.MatchUtil;
import net.wecash.data.sns.service.SnsDataDAO;

import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author franklin.li
 * 
 */
@Component
public class InterestCalculator {
	@Autowired
	InterestTagDAO interestTagDAO;
	@Autowired
	InterestDAO interestDAO;
	@Autowired
	SnsDataDAO snsDataDAO;
	
	public void analyzerSelfInterest(Long userId){
		SnsDataBean sdb = snsDataDAO.get(userId);
		analyzerSelfInterest(sdb, userId);
	}
	@SuppressWarnings("unchecked")
	public void analyzerSelfInterest(SnsDataBean sdb, Long userId){
		List<Integer> unionList = null;
		InterestEncodeBean ieb = new InterestEncodeBean();
		if(sdb != null){
			List<String> timelineSubjectTerm = sdb.getTimelineSubjectTerm();
			List<String> favoriteSubjectTerm = sdb.getFavoriteSubjectTerm();
			List<String> subjectTerm = new ArrayList<>();
			subjectTerm.addAll(MatchUtil.unionTerm(timelineSubjectTerm, favoriteSubjectTerm));
			List<String> tags = sdb.getTags();
			String types1 = tagTypeFilter(interestTagDAO.groupTypesByNames(subjectTerm));
			String types2 = tagTypeFilter(interestTagDAO.groupTypesByNames(tags));
			unionList = ListUtils.union(MatchUtil.formateTagType(types1), MatchUtil.formateTagType(types2));
		}else{
			String types = tagTypeFilter(interestTagDAO.groupTypesByUserId(userId));
			unionList = MatchUtil.formateTagType(types);
		}
		List<Integer> interestList=  MatchUtil.uniq(unionList);
		ieb.setUserId(userId);
		ieb.setInterest(interestList);
		String interestBloomCode = InterestType.encodeInterestTypes(interestList);
		ieb.setInterestEncode(interestBloomCode);
		interestDAO.add(ieb);
	}
	
	private String tagTypeFilter(Object o){
		String type = null;
		if(o != null){
			type = o.toString();
		}
		return type;
	}
}
