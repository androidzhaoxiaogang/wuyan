package net.wecash.server.behavior.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.wecash.common.service.Collections;
import net.wecash.common.service.MongoService;
import net.wecash.server.behavior.dao.BehaviorDAO;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class BehaviorService extends MongoService implements BehaviorDAO{
	private String collectionName = Collections.USER_BEHAVIOR;
	
	//add update portrait record -------------------------------
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addUpdatePortraitRecord(Long userId, Map map, boolean trigger){
		MongoTemplate template = factory.getDefaultMongoTemplate();
		Map record = new HashMap();
		record.put("record", map);
		record.put("count", map.size());
		record.put("updateTime", new Date());
		record.put("userId", userId);
		record.put("isTrigger", trigger);
		template.save(record, collectionName);
	} 
	
	@Override
	@SuppressWarnings("rawtypes")
	public Map getLastUpdatePortraitRecord(Long userId){
		MongoTemplate template = factory.getDefaultMongoTemplate();
		Query q = Query.query(Criteria.where("userId").is(userId));
		q.with(new Sort(Sort.Direction.DESC, "updateTime"));
		q.limit(1);
		return template.findOne(q, Map.class, collectionName);
	}
}
