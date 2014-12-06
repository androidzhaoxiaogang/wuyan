package net.wecash.server.service.basic;

import java.util.List;

import net.wecash.common.util.MysqlQueryUtil;
import net.wecash.server.mysql.model.Tag;
import net.wecash.server.mysql.model.UserTag;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class MysqlBasicAtomService {

	@Autowired
	protected  SessionFactory sessionFacotry;
	
	public void save(Object o){
		Session session = sessionFacotry.getCurrentSession();
		session.save(o);
	}
	public void delete(Object object){
		Session session = sessionFacotry.getCurrentSession();
		session.delete(object);
	}
	@SuppressWarnings("unchecked")
	public <T> T get(String hql){
		Session session = sessionFacotry.getCurrentSession();
		session.beginTransaction();
		T t = (T)session.createQuery(hql).uniqueResult();
		return t;
	}
	
	public void updateByField(Object object,String whereStr){
		String hql = MysqlQueryUtil.getSingleUpdateHql(object, whereStr);
		update(hql);
	}
	
	public void update(String hql){
		Session session = sessionFacotry.getCurrentSession();
		session.createQuery(hql).executeUpdate();
	}
	public void save(Long id, List<Long> tags) {
		for (Long l : tags) {
			UserTag userTag = new UserTag();
			userTag.setTagId(l);
			userTag.setUserId(id);
			save(userTag);
		}
	}
	public Tag getTagByName(String name) {
		Tag tag = null;
		Session session = sessionFacotry.getCurrentSession();
		Criteria c = session.createCriteria(Tag.class);
		c.add(Restrictions.eq("name", name));
		Object o = c.uniqueResult();
		if (o != null) {
			tag = (Tag) o;
		}
		return tag;
	}
}
