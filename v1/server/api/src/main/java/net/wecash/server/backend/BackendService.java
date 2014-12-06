/**
 * 
 */
package net.wecash.server.backend;

import net.wecash.server.mysql.model.AdminInfo;
import net.wecash.server.service.basic.MysqlBasicService;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

/**
 * @author franklin.li
 * 
 */
@Service
public class BackendService extends MysqlBasicService implements BackendDAO{
	
	@Override
	public boolean validateAdmin(String username, String password){
		String sql = "select count(*) from " + AdminInfo.class.getName() + " a where a.username='" + username + "' and a.password='"+ password + "'";
		return isValueExists(sql);
	}
	
	@Override
	public AdminInfo getAdminInfo(String username){
		Session session = sessionFacotry.getCurrentSession();
		AdminInfo info=null;
		try {
			session.beginTransaction();
			Criteria c = session.createCriteria(AdminInfo.class);
			c.add(Restrictions.eq("username", username));
			Object o = c.uniqueResult();
			session.getTransaction().commit();
			info = null;
			if(o != null){
				info = (AdminInfo)o;
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return info;
	}
}
