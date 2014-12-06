package net.wecash.server.analyzer.score.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import net.wecash.common.service.Collections;
import net.wecash.common.service.MongoService;
import net.wecash.server.analyzer.score.AnalyzerScoreBean;

/**
 * 
 * @author franklin.li
 *
 */
@Service
public class UserScoreService extends MongoService implements UserScoreDAO{
	private String collectionName = Collections.USER_SCORE;
	
	@Override
	public List<AnalyzerScoreBean> getScores(Long userId, Boolean flag, int cursor, int limit){
		Query query = Query.query(Criteria.where("selfId").is(userId));
		if(flag != null){
			query.addCriteria(Criteria.where("flag").is(flag));
		}
		query.skip(cursor);
		query.limit(limit);
		query.with(new Sort(Sort.Direction.DESC, "total"));
		return factory.getDefaultMongoTemplate().find(query, AnalyzerScoreBean.class, collectionName);
	}
	
	@Override
	public Long getScoresCount(Long userId){
		return factory.getDefaultMongoTemplate().count(Query.query(Criteria.where("selfId").is(userId)), collectionName);
	}
	
	@Override
	public void addScores(Long userId, List<AnalyzerScoreBean> asbs){
		MongoTemplate template = factory.getDefaultMongoTemplate();
		template.remove(Query.query(Criteria.where("selfId").is(userId)), collectionName);
		template.insert(asbs, collectionName);
	}
	
	@Override
	public AnalyzerScoreBean getScore(Long userId, Long targetId){
		return factory.getDefaultMongoTemplate().findOne(Query.query(Criteria.where("selfId").is(userId).and("targetId").is(targetId)), AnalyzerScoreBean.class, collectionName);
	}
	
	@Override
	public void readScoresList(Long userId, List<Long> targetIds){
		factory.getDefaultMongoTemplate().updateMulti(Query.query(Criteria.where("selfId").is(userId).and("targetId").in(targetIds)), Update.update("flag", true), collectionName);
	}
	
	@Override
	public boolean needReMatch(Long userId){
		boolean needReMatch = false;
		long count = factory.getDefaultMongoTemplate().count(Query.query(Criteria.where("selfId").is(userId).and("flag").is(true)), collectionName);
		if(count > 0 && count< 8){
			needReMatch = true;
		}
		return needReMatch;
	}
}
