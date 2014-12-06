package net.wecash.server.badupush.service;

import java.util.List;

import net.wecash.common.util.MysqlQueryUtil;
import net.wecash.server.badupush.IosPushNotificationSample;
import net.wecash.server.badupush.PushType;
import net.wecash.server.badupush.dao.PushDAO;
import net.wecash.server.mysql.model.Push;
import net.wecash.server.mysql.model.UserInfo;
import net.wecash.server.service.basic.MysqlBasicService;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushService extends MysqlBasicService implements PushDAO {

	@Autowired
	IosPushNotificationSample iosPush;
	@Override
	public void savePush(Push push) {
		add(push);
	}

	@Override
	public Push getPush(Long userId) {
		Session session = sessionFacotry.getCurrentSession();
		Push push = null;
		try {
			session.beginTransaction();
			Criteria c = session.createCriteria(Push.class);
			c.add(Restrictions.eq("userId", userId));
			Object o = c.uniqueResult();
			if (o != null) {
				push = (Push) o;
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return push;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Push> getPushById(List<Long> ids) {
		Session session = sessionFacotry.getCurrentSession();
		String newids=MysqlQueryUtil.getInIntegerList(ids);
		List<Push> list=null;
		try {
			session.beginTransaction();
			String sql="select * from t_push where user_id in ("+newids+") group by channel_id";
			Query query=session.createSQLQuery(sql).addEntity(Push.class);
			list=query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<Push> getPush() {
		Session session = sessionFacotry.getCurrentSession();
		List<Push> list=null;
		try {
			session.beginTransaction();
			Criteria c = session.createCriteria(Push.class);
			list = c.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public void pushUser(List<Push> pushs,String content) {
		for(Push push:pushs){
			String newcontent=getContent(push,content);
			if(push.getDeviceType() == PushType.IOS){
				iosPush.push(push.getChannelId(), push.getUserPushId(), push.getDeviceType(), newcontent);
			}else if(push.getDeviceType() == PushType.ANDROID){
				//TODO:
			}
			
		}
	}
	
	public String getContent(Push push,String content){
		//TODO 操作发送内容
		String newcontent=null;
		if(push.getDeviceType()==PushType.IOS){
			newcontent="{\"aps\":{\"alert\":\""+content+"\"}}";
		}
		if(push.getDeviceType()==PushType.ANDROID){
			newcontent="{\"title\":\"wuyan\",\"description\":\""+content+"\"}";
		}
		return newcontent;
	}

	@Override
	public void savePush(String channelPushId, String userPushId, int clientType, Long userId) {
		if(channelPushId != null && userPushId != null){
			Push push= getPush(userId);
			if(push == null){
				Push newPush = new Push(channelPushId, userPushId, clientType, userId);
				savePush(newPush);
			}else{
				updatePush(push, channelPushId, userPushId, clientType, userId);
			}
		}
	}
	public void updatePush(Push push, String channelPushId, String userPushId, int clientType, Long userId){
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			push.setChannelId(channelPushId);
			push.setDeviceType(clientType);
			push.setUserPushId(userPushId);
			push.setUserId(userId);
			String hql = MysqlQueryUtil.getSingleUpdateHql(push, "userId");
			session.createQuery(hql).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void deletePushInfo(Long userId) {
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String sql = "delete from t_push  where user_id=" + userId;
			session.createSQLQuery(sql).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

}
