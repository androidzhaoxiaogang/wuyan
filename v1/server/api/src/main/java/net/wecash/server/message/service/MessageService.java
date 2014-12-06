package net.wecash.server.message.service;

import java.util.Date;
import java.util.List;

import net.wecash.server.bean.MessageResultBean;
import net.wecash.server.message.dao.MessageDAO;
import net.wecash.server.mysql.model.Message;
import net.wecash.server.service.basic.MysqlBasicService;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

@Service
public class MessageService extends MysqlBasicService implements MessageDAO {

	@Override
	public void save(Message leaveMessage) {
		leaveMessage.setTime(new Date());
		leaveMessage.setState(0);
		add(leaveMessage);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageResultBean> getMessage(Long id, Integer cursor,
			Integer limit, String start_time, String end_time) {
		Session session = sessionFacotry.getCurrentSession();
		List<MessageResultBean> list=null;
		try {
			session.beginTransaction();
			String sql = "select id,from_user_id as fromUserId,content,time from t_leave_message where state = 0 and to_user_id="
					+ id;
			if (start_time != null && end_time != null) {
				sql += " and time>='" + start_time + "' and time<='" + start_time+"'";
			} else {
				if (start_time != null) {
					sql+=" and time>='"+start_time+"'";
				}
				if (end_time != null) {
					sql+=" and time<='"+end_time+"'";
				}
			}
			Query query = session.createSQLQuery(sql);
			query.setResultTransformer(Transformers.aliasToBean(MessageResultBean.class));
			query.setFirstResult(cursor);
			query.setMaxResults(limit);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.beginTransaction().rollback();
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void deleteMessage(Long id, Long userId) {
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String sql = "update t_leave_message set state=1 where  to_user_id="
					+ userId + " and id=" + id;
			Query query = session.createSQLQuery(sql);
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.beginTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Override
	public Message getMessageById(Long id, Long userId) {
		Session session = sessionFacotry.getCurrentSession();
		Message leaveMessage=null;
		try {
			session.beginTransaction();
			String sql = "select * from t_leave_message where to_user_id=" + userId
					+ " and id=" + id;
			Query query = session.createSQLQuery(sql).addEntity(Message.class);
			Object o = query.uniqueResult();
			if(o!=null){
				leaveMessage=(Message)o;
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			session.beginTransaction().rollback();
			e.printStackTrace();
		}
		return leaveMessage;
	}
}
