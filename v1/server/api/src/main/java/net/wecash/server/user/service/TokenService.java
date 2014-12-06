package net.wecash.server.user.service;

import net.wecash.server.mysql.model.Token;
import net.wecash.server.service.basic.MysqlBasicService;
import net.wecash.server.user.dao.TokenDAO;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

@Service
public class TokenService extends MysqlBasicService implements TokenDAO{
	
	@Override
	public Token getToken(String token) {
		Session session = sessionFacotry.getCurrentSession();
		Object o=null;
		try {
			session.beginTransaction();
			Criteria c = session.createCriteria(Token.class);
			c.add(Restrictions.eq("token", token));
			o = c.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		Token tokenBean = null;
		if(o != null){
			tokenBean = (Token)o;
		}
		return tokenBean;
	}
	@Override
	public Token getToken(Long userId, Integer type) {
		Session session = sessionFacotry.getCurrentSession();
		Object o=null;
		try {
			session.beginTransaction();
			Criteria c = session.createCriteria(Token.class);
			c.add(Restrictions.eq("userId", userId));
			if(type != null){
				c.add(Restrictions.eq("type", type));
			}
			o = c.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		Token tokenBean = null;
		if(o != null){
			tokenBean = (Token)o;
		}
		return tokenBean;
	}

	@Override
	public void deleteToken(String token) {
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String hql = "delete from Token t where t.token='" + token + "'";
			session.createQuery(hql).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}
	
	@Override
	public void deleteToken(Long id) {
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String hql = "delete from Token t where t.userId='" + id + "'";
			session.createQuery(hql).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void addToken(Token token) {
		Token oldToken=getToken(token.getUserId(), token.getType());
		if(oldToken!=null){
			token.setId(oldToken.getId());
			updateToken(token);
		}else{
			add(token);
		}
		
	}
	
	public void updateToken(Token token){
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			session.update(token);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}
}
