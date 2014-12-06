package net.wecash.server.activity.service;

import java.util.Date;
import java.util.List;

import net.wecash.common.util.DateUtils;
import net.wecash.server.activity.dao.ActivityDAO;
import net.wecash.server.dao.FileDAO;
import net.wecash.server.mysql.model.Activity;
import net.wecash.server.service.basic.MysqlBasicService;

import org.bson.types.ObjectId;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService extends MysqlBasicService implements ActivityDAO {

	@Autowired
	FileDAO imageDAO;

	@SuppressWarnings("unchecked")
	@Override
	public List<Activity> getActivity(Integer cursor, Integer limit) {
		Session session = sessionFacotry.getCurrentSession();
		List<Activity> list=null;
		try {
			session.beginTransaction();
			String hql = "from Activity where expireTime > '"+DateUtils.formatDate(new Date())+"'";
			Query query = session.createQuery(hql);
			query.setFirstResult(cursor);
			query.setMaxResults(limit);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public Long getActivityAll() {
		Session session = sessionFacotry.getCurrentSession();
		Long count=0l;
		try {
			session.beginTransaction();
			String hql = "select count(id) from Activity where expireTime > '"+DateUtils.formatDate(new Date())+"'";
			Query query = session.createQuery(hql);
			count = (long) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public void deleteActivity(Long id) {
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String sql = "delete from t_activity  where id=" + id;
			session.createSQLQuery(sql).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Override
	public Activity getActivity(Long id) {
		Session session = sessionFacotry.getCurrentSession();
		Activity mu=null;
		try {
			session.beginTransaction();
			Criteria c = session.createCriteria(Activity.class);
			c.add(Restrictions.eq("id", id));
			c.add(Restrictions.gt("expireTime", new Date()));
			Object o = c.uniqueResult();
			if(o!=null){
				mu=(Activity)o;
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return mu;
	}

	@Override
	public void saveActivity(Date overdueTime,  String describe, byte[] body) {
		Object imageIndex = imageDAO.addFile(body);
		Activity mu = new Activity();
		mu.setExpireTime(overdueTime);
		mu.setCreateTime(new Date());
		mu.setCover(imageIndex.toString());
		mu.setDescribes(describe);
		add(mu);

	}

	@Override
	public void updateActivity(Activity activity, byte[] body) {
		if (body != null && body.length > 0) {
			imageDAO.deleteFile(new ObjectId(activity.getCover()));
			Object imageIndex = imageDAO.addFile(body);
			activity.setCover(imageIndex.toString());
		}

		updateByField(activity, "id");
	}
	@Override
	public void updateActivityDetail(String filename,Activity activity, byte[] body) {
		if (body != null && body.length > 0) {
			imageDAO.deleteFile(new ObjectId(activity.getCover()));
			Object imageIndex = imageDAO.addFile(filename, body);
			activity.setDetails(imageIndex.toString());
		}

		updateByField(activity, "id");
	}
	@Override
	public void addActivityDetail(String filename,Activity activity, byte[] body) {
		Object imageIndex = imageDAO.addFile(filename, body);
		String oldDetail = activity.getDetails();
		activity.setDetails(imageIndex.toString());
		updateBean(activity);
		if(oldDetail != null){
			imageDAO.deleteFile(new ObjectId(oldDetail));
		}
	}


}
