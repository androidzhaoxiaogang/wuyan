package net.wecash.data.sns.service;

import java.util.Date;
import java.util.List;

import net.wecash.common.mongo.model.SnsDataBean;
import net.wecash.common.service.Collections;
import net.wecash.common.service.MongoService;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author franklin.li
 * 
 */
@Service
public class SnsDataService extends MongoService implements SnsDataDAO{
	private String collectionName = Collections.SNS_DATA;
	
	@Override
	public void addSnsData(SnsDataBean sdb){
		Assert.notNull(sdb);
		Assert.notNull(sdb.getUserId());
		sdb.setCreateTime(new Date());
		MongoTemplate template = factory.getDefaultMongoTemplate();
		Long count = template.count(Query.query(Criteria.where("userId").is(sdb.getUserId())), collectionName);
		if(count > 0){
			template.remove(Query.query(Criteria.where("userId").is(sdb.getUserId())), collectionName);
		}
		template.save(sdb, collectionName);
	}
	
	@Override
	public SnsDataBean get(Long userId){
		Query q = new Query();
		q.addCriteria(Criteria.where("userId").is(userId));
		return factory.getDefaultMongoTemplate().findOne(q, SnsDataBean.class, collectionName);
	}
	
	@Override
	public List<SnsDataBean> gets(List<Long> userIds){
		Query q = Query.query(Criteria.where("userId").in(userIds));
		return factory.getDefaultMongoTemplate().find(q, SnsDataBean.class, collectionName);
	}
}
