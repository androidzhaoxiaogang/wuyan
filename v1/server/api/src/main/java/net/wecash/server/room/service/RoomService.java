package net.wecash.server.room.service;

import java.util.List;

import net.wecash.server.bean.RoomQueryBean;
import net.wecash.server.gis.region.dao.RegionDAO;
import net.wecash.server.mysql.model.RoomInfo;
import net.wecash.server.room.dao.RoomDAO;
import net.wecash.server.service.basic.MysqlBasicService;
import net.wecash.server.user.dao.UserDAO;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService extends MysqlBasicService implements RoomDAO {

	@Autowired
	UserDAO userDao;
	@Autowired
	RegionDAO regionDAO;

	@Override
	public void saveRoom(RoomInfo roomInfo) {
/*		User user = userDao.getUser(roomInfo.getUserId());
		if (user.getState() == 2) {
			roomInfo.setMaxPrice(roomInfo.getMinPrice());
		}
		RegionBean regionbean=regionDAO.getName(roomInfo.getProvince(), roomInfo.getCity(), roomInfo.getArea(), roomInfo.getLandmark());
		roomInfo.setLandmark(regionbean.getLandmark());
		roomInfo.setArea(regionbean.getArea());
		roomInfo.setCity(regionbean.getCity());
		roomInfo.setProvince(regionbean.getProvince());*/
		add(roomInfo);
	}
	
	@Override
	public RoomInfo getSrcRoomInfoByUserId(Long userId){
		Session session = sessionFacotry.getCurrentSession();
		RoomInfo room=null;
		try {
			session.beginTransaction();
			Criteria c = session.createCriteria(RoomInfo.class);
			c.add(Restrictions.eq("userId", userId));
			Object o= c.uniqueResult();
			if(o!=null){
				room=(RoomInfo)o;
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return room;
	}
	
	@Override
	public void deleteRoom(Long userId){
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String sql = "delete from t_room_info  where user_id=" + userId;
			session.createSQLQuery(sql).executeUpdate();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void updateRoom(RoomInfo roomInfo) {
		/*User user = userDao.getUser(roomInfo.getUserId());
		if (user.getState() == 2) {
			roomInfo.setMaxPrice(roomInfo.getMinPrice());
		}
		RegionBean regionbean=regionDAO.getName(roomInfo.getProvince(), roomInfo.getCity(), roomInfo.getArea(), roomInfo.getLandmark());
		roomInfo.setLandmark(regionbean.getLandmark());
		roomInfo.setArea(regionbean.getArea());
		roomInfo.setCity(regionbean.getCity());
		roomInfo.setProvince(regionbean.getProvince());*/
		updateByField(roomInfo, "userId");
	}


	@Override
	public RoomQueryBean getRoomByUserId(Long id) {
		Session session = sessionFacotry.getCurrentSession();
		RoomQueryBean bean=null;
		try {
			session.beginTransaction();
			String sql = "select u.id as userId,u.name as useName,u.gender,r.min_price as minPrice,r.max_price as maxPrice,"
					+ "r.description,a.name as area,p.name as province,c.name as city,r.line_num as lineNum,r.line_station as lineStation "
					+ "from t_room_info r,t_user u,t_area a, t_city c ,t_province p where r.area=a.code and  "
					+ " r.city=c.code and r.province=p.code and r.user_id=u.id and r.user_id=" + id;
			SQLQuery query = session.createSQLQuery(sql);
			query.setResultTransformer(Transformers.aliasToBean(RoomQueryBean.class));
			Object o =  query.uniqueResult();
			if(o!=null){
				bean=(RoomQueryBean)o;
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return bean;
	}

	@Override
	public RoomQueryBean getRoomCodeById(Long id) {
		Session session = sessionFacotry.getCurrentSession();
		RoomQueryBean bean = null;
		try {
			session.beginTransaction();
			String sql = "";
			sql += "select u.id as userId,u.name as useName,u.gender,r.min_price as minPrice,"
					+ "r.max_price as maxPrice,r.description,r.province,r.city,r.area,r.landmark "
					+ " from t_room_info r,t_user u where r.user_id=u.id and r.user_id=" + id;
			SQLQuery query = session.createSQLQuery(sql);
			query.setResultTransformer(Transformers.aliasToBean(RoomQueryBean.class));
			Object o = query.uniqueResult();
			if(o!=null){
				bean=(RoomQueryBean)o;
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return bean;
	}
	
	@Override
	public boolean isRoomExists(Long userId){
		return isValueExists(RoomInfo.class.getName(), "userId", userId);
	}
	@Override
	public List<Long> getUserId(String province,String city ,String area){
		List<Long> list=null;
		Session session =sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String hql="select userId from RoomInfo where province='"+province+"' and city='"+city+"' and area='"+area+"'";
			list=session.createQuery(hql).list();
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return list;
	}
}
