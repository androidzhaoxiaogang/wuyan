package net.wecash.server.gis.region.service;

import java.util.List;

import net.wecash.server.bean.AreaBean;
import net.wecash.server.bean.CityBean;
import net.wecash.server.bean.LandmarkAllBean;
import net.wecash.server.bean.LandmarkBean;
import net.wecash.server.bean.provinceCodeBean;
import net.wecash.server.bean.region.zh.bean.RegionBean;
import net.wecash.server.gis.region.dao.RegionDAO;
import net.wecash.server.service.basic.MysqlBasicService;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

@Service
public class RegionService extends MysqlBasicService implements RegionDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<provinceCodeBean> getProvince() {
		Session session = sessionFacotry.getCurrentSession();
		List<provinceCodeBean> list = null;
		try {
			session.beginTransaction();
			String sql = "select code, name  from t_province ";
			Query query = session.createSQLQuery(sql);
			query.setResultTransformer(Transformers.aliasToBean(provinceCodeBean.class));
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityBean> getCity(String provinceCode) {
		Session session = sessionFacotry.getCurrentSession();
		List<CityBean> list = null;
		try {
			session.beginTransaction();
			String sql = "select code, name  from t_city where provincecode="
					+ provinceCode;
			Query query = session.createSQLQuery(sql);
			query.setResultTransformer(Transformers.aliasToBean(CityBean.class));
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AreaBean> getArea(String citycode) {
		Session session = sessionFacotry.getCurrentSession();
		List<AreaBean> list = null;
		try {
			session.beginTransaction();
			String sql = "select code, name  from t_area where citycode="
					+ citycode;
			Query query = session.createSQLQuery(sql);
			query.setResultTransformer(Transformers.aliasToBean(AreaBean.class));
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LandmarkBean> getLandmark(String areacode) {
		Session session = sessionFacotry.getCurrentSession();
		List<LandmarkBean> list = null;
		try {
			session.beginTransaction();
			String sql = "select code, name  from t_landmark where areacode="
					+ areacode;
			Query query = session.createSQLQuery(sql);
			query.setResultTransformer(Transformers
					.aliasToBean(LandmarkBean.class));
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LandmarkAllBean> getAll(String code) {
		List<LandmarkAllBean> list = null;
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String sql = "select p.`code` as pcode,p.`name` as pname,c.`code` as ccode,c.`name` as cname,a.`code` as acode,"
					+ "a.`name` as aname,l.`code` as lcode ,l.`name` as lname "
					+ "from t_province p,t_city c,t_area a,t_landmark l where p.`code`=c.provincecode and c.`code`=a.citycode "
					+ "and a.`code`=l.areacode and p.code=" + code;
			Query query = session.createSQLQuery(sql);
			query.setResultTransformer(Transformers.aliasToBean(LandmarkAllBean.class));
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.beginTransaction().rollback();
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RegionBean getName(String province, String city, String area,
			String landmark) {
		RegionBean region = null;
		if (province != null && city != null && area != null) {
			Session session = sessionFacotry.getCurrentSession();
			String sql ="";
			if (landmark != null && !"".equals(landmark.trim())) {
				 sql = "select p.code as province ,c.code as city,a.code as area ,l.code as landmark from t_province p,t_city c,t_area a,t_landmark l "
							+ "where p.code =c.provincecode and c.code=a.citycode and a.code=l.areacode "
							+ "and p.name='"+ province+ "' and c.name='"+ city+ "' and a.name='" + area + "'"+" and l.name='"+landmark+"'";
			}else{
				 sql = "select p.code as province ,c.code as city,a.code as area from t_province p,t_city c,t_area a "
							+ "where p.code =c.provincecode and c.code=a.citycode "
							+ "and p.name='"+ province+ "' and c.name='"+ city+ "' and a.name='" + area + "'";
			}
			try {
				session.beginTransaction();
				SQLQuery query = session.createSQLQuery(sql);
				query.setResultTransformer(Transformers.aliasToBean(RegionBean.class));
				List<RegionBean> list = query.list();
				if(list!=null&&list.size()>0){
					region=list.get(0);
					if (region.getLandmark()==null) {
						region.setLandmark("");
					}
				}
				session.getTransaction().commit();
			} catch (Exception e) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}

		}
		return region;
	}
}
