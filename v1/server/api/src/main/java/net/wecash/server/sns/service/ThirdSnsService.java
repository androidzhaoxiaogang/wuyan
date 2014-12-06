package net.wecash.server.sns.service;

import net.wecash.server.mysql.model.SnsAuthInfo;
import net.wecash.server.service.basic.MysqlBasicService;
import net.wecash.server.sns.dao.ThirdSnsDAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

@Service
public class ThirdSnsService extends MysqlBasicService implements ThirdSnsDAO{

	@Override
	public SnsAuthInfo getSnsInfo(String uid) {
		Session session = sessionFacotry.getCurrentSession();
		SnsAuthInfo sai=null;
		try {
			session.beginTransaction();
			String hql = "select s from SnsAuthInfo s where s.thirdId="+uid;
			Object o = session.createQuery(hql).uniqueResult();
			sai = null;
			if(o != null){
				sai = (SnsAuthInfo)o;
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return sai;
	}
	
	@Override
	public boolean isThirdIdExists(String uid) {
		return isValueExists(SnsAuthInfo.class.getName(), "thirdId", uid);
	}
	
	@Override
	public void deleteSnsInfo(String uid, int type) {
		// TODO Auto-generated method stub
	}
	
	

	@Override
	public void updateSnsInfo(String uid, String token) {
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String hql = "update SnsAuthInfo s set s.thirdToken='" + token + "' where s.thirdId='" + uid + "'";
			session.createQuery(hql).executeUpdate();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}
	
	@Override
	public Long getUserIdByUidFromSnsAuthInfo(String uid){
		Long userId = null;
		SnsAuthInfo sai = getSnsInfo(uid);
		if(sai != null){
			userId = sai.getUserId();
		}
		return userId;
	}

	@Override
	public void addSnsInfo(SnsAuthInfo sai) {
		Session session = sessionFacotry.getCurrentSession();  
        try {
			session.beginTransaction();  
			session.save(sai);  
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			session.getTransaction().rollback();
			e.printStackTrace();
		}  
	}
	@Override
	public void deleteSnsInfoByUserId(Long userId){
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String hql = "delete from SnsAuthInfo s where s.userId='" + userId + "'";
			session.createQuery(hql).executeUpdate();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}
}
