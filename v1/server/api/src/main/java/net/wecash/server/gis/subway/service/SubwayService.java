package net.wecash.server.gis.subway.service;

import java.util.List;

import net.wecash.server.gis.subway.dao.SubwayDAO;
import net.wecash.server.mysql.model.Subway;
import net.wecash.server.service.basic.MysqlBasicService;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

@Service
public class SubwayService extends MysqlBasicService implements SubwayDAO {

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Subway> getAll(String citycode, String lineStation) {
		Session session =sessionFacotry.getCurrentSession();
		List<Subway> list=null;
		try {
			session.beginTransaction();
			String hql="from Subway where citycode="+citycode+" and lineNum="+lineStation;
			Query query= session.createQuery(hql);
			list = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			session.beginTransaction().rollback();
			e.printStackTrace();
		}
		return list;
	}

}
