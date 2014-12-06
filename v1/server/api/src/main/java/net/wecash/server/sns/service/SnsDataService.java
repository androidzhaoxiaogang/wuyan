/**
 * 
 */
package net.wecash.server.sns.service;

import java.util.List;

import net.wecash.common.mongo.model.SnsDataBean;
import net.wecash.common.service.Collections;
import net.wecash.common.service.MongoService;
import net.wecash.server.sns.dao.SnsDataDAO;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * @author franklin.li
 * 
 */
@Service
public class SnsDataService extends MongoService implements SnsDataDAO{
	private String collectionName = Collections.SNS_DATA;
	
	@Override
	public SnsDataBean get(Long userId){
		Query q = new Query();
		q.addCriteria(Criteria.where("userId").is(userId));
//		q.with(new Sort(Sort.Direction.DESC, "createTime"));
		return factory.getDefaultMongoTemplate().findOne(q, SnsDataBean.class, collectionName);
	}
	
	@Override
	public List<SnsDataBean> gets(List<Long> userIds){
		Query q = Query.query(Criteria.where("userId").in(userIds));
		return factory.getDefaultMongoTemplate().find(q, SnsDataBean.class, collectionName);
	}
	
	@Override
	public void add(SnsDataBean s){
		factory.getDefaultMongoTemplate().save(s, collectionName);
	}
	
	@Override
	public void delete(Long userId){
		factory.getDefaultMongoTemplate().remove(Query.query(Criteria.where("userId").is(userId)), collectionName);
	}
}
