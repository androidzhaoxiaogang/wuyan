package net.wecash.server.tag.service;

import java.util.List;

import net.wecash.common.exception.Error;
import net.wecash.common.exception.ErrorCode;
import net.wecash.common.exception.ErrorCodeException;
import net.wecash.common.service.Collections;
import net.wecash.common.util.MysqlQueryUtil;
import net.wecash.server.mysql.model.Tag;
import net.wecash.server.mysql.model.UserTag;
import net.wecash.server.service.basic.MysqlBasicService;
import net.wecash.server.tag.dao.TagDAO;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

@Service
public class TagService extends MysqlBasicService implements TagDAO {

	@Override
	public void addTag(Tag tag) {
		add(tag);
	}

	@Override
	public boolean isTagNameExists(String name) {
		return isValueExists(Tag.class.getName(), "name", name);
	}

	@Override
	public void updateTag(Tag tag) {
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			Criteria c = session.createCriteria(Tag.class);
			c.add(Restrictions.eq("id", tag.getId()));
			Tag t = (Tag) c.uniqueResult();
			if (tag.getName() != null) {
				t.setName(tag.getName());
			}
			if (tag.getWeight() != null) {
				t.setWeight(tag.getWeight());
			}
			session.update(t);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Override
	public Tag getTag(Long id) {
		Tag tag = null;
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			Criteria c = session.createCriteria(Tag.class);
			c.add(Restrictions.eq("id", id));
			Object o = c.uniqueResult();
			if (o != null) {
				tag = (Tag) o;
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return tag;
	}

	@Override
	public Tag getTagByName(String name) {
		Tag tag = null;
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			Criteria c = session.createCriteria(Tag.class);
			c.add(Restrictions.eq("name", name));
			Object o = c.uniqueResult();
			if (o != null) {
				tag = (Tag) o;
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return tag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tag> getTagsByIdsAndType(List<Long> ids,String name, Integer type,
			int first, int Max) {
		List<Tag> tags = null;
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			Criteria c = session.createCriteria(Tag.class);
			if (ids != null) {
				c.add(Restrictions.in("id", ids));
			}
			if (type != null) {
				c.add(Restrictions.eq("type", type));
			}
			if(name!=null&&!"".equals(name.trim())){
				c.add(Restrictions.ilike("name", "%"+name+"%"));
			}
			c.setFirstResult(first);
			c.setMaxResults(Max);
			tags = c.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return tags;
	}

	@Override
	public void deleteTag(Long id) {
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			Criteria c = session.createCriteria(UserTag.class);
			c.add(Restrictions.eq("tagId", id));
			if(c.list().size()<=0){
				String sql = "delete from t_tag where id='" + id
						+ "'";
				SQLQuery query = session.createSQLQuery(sql);
				query.executeUpdate();
				session.getTransaction().commit();
			}else{
				session.getTransaction().rollback();
				throw new ErrorCodeException(new Error(ErrorCode.TAG_REFERENCE_ERROR));
			}
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		
	}

	@Override
	public Object groupTypesByNames(List<String> names) {
		Session session = sessionFacotry.getCurrentSession();
		Object type=null;
		try {
			session.beginTransaction();
			String sql = "select GROUP_CONCAT(type)as type from t_tag";
			sql = MysqlQueryUtil.getMutiLikeSql(sql, "name", names);
			SQLQuery query = session.createSQLQuery(sql);
			type = query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return type;
	}
	
	@Override
	public Object groupTypesByUserId(Long userId) {
		Session session = sessionFacotry.getCurrentSession();
		Object type=null;
		try {
			session.beginTransaction();
			String sql = "select GROUP_CONCAT(type)as type from t_tag t, r_user_tag u where u.user_id="+userId+" and t.id=u.tag_id";
			SQLQuery query = session.createSQLQuery(sql);
			type = query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return type;
	}

	@Override
	public long getAll(String name, Integer type) {
		Session session =sessionFacotry.getCurrentSession();
		long count=0;
		try {
			session.beginTransaction();
			String hql="select count(*) from Tag where 1=1 ";
			if(name!=null&&!"".equals(name.trim())){
				hql+=" and name like '%"+name+"%'";
			}
			if(type!=null){
				hql+=" and type="+type;
			}
			Query query=session.createQuery(hql);
			count = (Long) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return count;
	}
}
