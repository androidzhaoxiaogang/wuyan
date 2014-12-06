/**
 * 
 */
package net.wecash.data.analyzer.interest.service;

import java.util.List;

import net.wecash.common.util.MysqlQueryUtil;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author franklin.li
 * 
 */
@Service
public class InterestTagService implements InterestTagDAO{
	@Autowired
	protected  SessionFactory sessionFacotry;
	
	@Override
	public Object groupTypesByNames(List<String> names) {
		Object type = null;
		if(names != null && names.size() > 0){
			Session session = sessionFacotry.getCurrentSession();
			try {
				session.beginTransaction();
				String sql = "select GROUP_CONCAT(type)as type from t_tag";
				sql = MysqlQueryUtil.getMutiSql(sql, "name", names);
				SQLQuery query = session.createSQLQuery(sql);
				type = query.uniqueResult();
				session.getTransaction().commit();
			} catch (Exception e) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}
		}
		return type;
	}
	
	@Override
	public Object groupTypesByUserId(Long userId) {
		Object type = null;
		if(userId != null){
			Session session = sessionFacotry.getCurrentSession();
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
		}
		return type;
	}
}
