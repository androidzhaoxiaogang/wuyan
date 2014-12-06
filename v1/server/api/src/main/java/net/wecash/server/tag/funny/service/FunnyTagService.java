package net.wecash.server.tag.funny.service;

import java.util.List;

import net.wecash.common.service.Collections;
import net.wecash.common.service.MongoService;
import net.wecash.server.tag.funny.FunnyTag;
import net.wecash.server.tag.funny.dao.FunnyTagDAO;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * @author franklin.li
 * 
 */
@Service
public class FunnyTagService extends MongoService implements FunnyTagDAO{
	private String collectionName = Collections.FUNNY_TAG;
	
	public List<FunnyTag> getFunnyTags(){
		List<FunnyTag> fts = factory.getDefaultMongoTemplate().find(new Query(), FunnyTag.class, collectionName);
		return fts;
	}
	
	public List<FunnyTag> getFunnyTags(Integer clazz, Integer type, Integer value){
		Query query = Query.query(Criteria.where("type").is(type).and("clazz").is(clazz));
		if(value != null){
			query.addCriteria(Criteria.where("value").is(value));
		}
		return factory.getDefaultMongoTemplate().find(query, FunnyTag.class, collectionName);
	}
	
	public List<FunnyTag> getFunnyTags(Integer clazz, List<Integer> types, Integer value){
		Query query = Query.query(Criteria.where("type").in(types).and("clazz").is(clazz));
		if(value != null){
			query.addCriteria(Criteria.where("value").is(value));
		}
		return factory.getDefaultMongoTemplate().find(query, FunnyTag.class, collectionName);
	}
	
	public void addFunnyTag(FunnyTag funnyTag){
		factory.getDefaultMongoTemplate().save(funnyTag, collectionName);
	}
	
	public void deleteFunnyTag(ObjectId id){
		factory.getDefaultMongoTemplate().remove(Query.query(Criteria.where("_id").is(id)), collectionName);
	}
}
