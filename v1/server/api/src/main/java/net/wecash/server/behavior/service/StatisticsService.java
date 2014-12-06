package net.wecash.server.behavior.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.wecash.common.service.Collections;
import net.wecash.common.service.MongoService;
import net.wecash.common.util.DateUtils;
import net.wecash.server.behavior.dao.StatisticsDAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

@Service
public class StatisticsService extends MongoService implements StatisticsDAO {
	private String collectionName = Collections.USER_BEHAVIOR;
	@Autowired
	protected SessionFactory sessionFacotry;

	@Override
	public Map<String, Object> getStatistics() {
		Map<String, Object> map = new HashMap<String, Object>();
		Long userNum = getUserNum();
		DateUtils DateUtils = new DateUtils();
		Date startTime=DateUtils.getCurrentDayStartTime();
		Date endTime=DateUtils.getCurrentDayEndTime();
		Date monthStartTime=DateUtils.getCurrentMonthStartTime();
		Date monthEndTime=DateUtils.getCurrentMonthEndTime();
		Date yearStartTime=DateUtils.getCurrentYearStartTime();
		Date yearEndTime=DateUtils.getCurrentYearEndTime();
		
		Long todayNum=getActiveUserNum(startTime,endTime);
		Long monthNum=getActiveUserNum(monthStartTime,monthEndTime);
		Long yearNum=getActiveUserNum(yearStartTime,yearEndTime);
		map.put("countNum", userNum);
		map.put("todayNum", todayNum);
		map.put("monthNum", monthNum);
		map.put("yearNum", yearNum);
		return map;
	}

	public Long getActiveUserNum(Date start, Date end) {
		MongoTemplate template = factory.getDefaultMongoTemplate();
		DBCollection userSColl = template.getCollection(collectionName);
		BasicDBObject key = new BasicDBObject("userId",true);
		BasicDBObject cond = new BasicDBObject("updateTime",new BasicDBObject("$gt",start).append("$lt", end));
		BasicDBObject initial = new BasicDBObject("cou",0);
		String reduce = "function(obj,pre){pre.cou++}";
		BasicDBList returnList = (BasicDBList)userSColl.group(key, cond, initial, reduce);
		Long num = (long) returnList.size();
		return num;
	}

	public Long getUserNum() {
		Session session = sessionFacotry.getCurrentSession();
		Long num=0l;
		try {
			session.beginTransaction();
			String sql = "select * from t_user";
			org.hibernate.Query query = session.createSQLQuery(sql);
			num = (long) query.list().size();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return num;
	}

}
