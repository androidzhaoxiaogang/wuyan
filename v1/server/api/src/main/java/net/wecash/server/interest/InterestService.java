package net.wecash.server.interest;

import java.util.Date;
import java.util.List;

import net.wecash.common.service.Collections;
import net.wecash.common.service.MongoService;
import net.wecash.common.util.UpdateUtils;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class InterestService extends MongoService implements InterestDAO{
	private String collectionName = Collections.USER_INTEREST;
	
	@Override
	public void add(InterestEncodeBean ib){
		Assert.notNull(ib);
		Assert.notNull(ib.getUserId());
		Assert.notNull(ib.getInterestEncode());
		Assert.notNull(ib);
		ib.setTimestamp(new Date());
		MongoTemplate template = factory.getDefaultMongoTemplate();
		Query query = Query.query(Criteria.where("userId").is(ib.getUserId()));
		if(template.count(query, collectionName) > 0){
			template.remove(query, collectionName);
		}
		template.save(ib, collectionName);
	}
	
	@Override
	public void update(InterestEncodeBean ib){
		Assert.notNull(ib);
		factory.getDefaultMongoTemplate().updateFirst(Query.query(Criteria.where("_id").is(ib.getId())), UpdateUtils.convertBeanToUpdate(ib, "_id"), collectionName);
	} 
	
	@Override
	public InterestEncodeBean get(Long userId){
		Assert.notNull(userId);
		return factory.getDefaultMongoTemplate().findOne(Query.query(Criteria.where("userId").is(userId)), InterestEncodeBean.class, collectionName);
	}
	
	@Override
	public void delete(Long userId){
		factory.getDefaultMongoTemplate().remove(Query.query(Criteria.where("userId").is(userId)), collectionName);
	}
	
	@Override
	public List<InterestEncodeBean> gets(List<Long> userIds, String interestEncodeQueryRegex, Integer limit){
		Assert.notNull(userIds);
		Query query = new Query();
		query.limit(limit);
		query.with(new Sort(Sort.Direction.DESC, "timestamp"));
		query.addCriteria(Criteria.where("userId").in(userIds));
		if(interestEncodeQueryRegex != null){
			query.addCriteria(Criteria.where("interestEncode").regex(interestEncodeQueryRegex));
		}
		return factory.getDefaultMongoTemplate().find(query, InterestEncodeBean.class, collectionName);
	}
	
	@Override
	public List<InterestEncodeBean> gets(List<Long> userIds){
		Assert.notNull(userIds);
		Query query = Query.query(Criteria.where("userId").in(userIds));
		return factory.getDefaultMongoTemplate().find(query, InterestEncodeBean.class, collectionName);
	}
}
