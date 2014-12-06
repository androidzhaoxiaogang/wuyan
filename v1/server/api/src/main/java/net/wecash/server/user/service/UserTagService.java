package net.wecash.server.user.service;



import java.util.List;

import net.wecash.server.bean.UserTagReturnBean;
import net.wecash.server.mysql.model.UserTag;
import net.wecash.server.service.basic.MysqlBasicService;
import net.wecash.server.user.dao.UserTagDao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

@Service
public class UserTagService extends MysqlBasicService implements UserTagDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<UserTagReturnBean> getTagInfo(Long userid) {
		Session session = sessionFacotry.getCurrentSession();
		List<UserTagReturnBean> list=null;
		try {
			session.beginTransaction();
			String sql="select t.name as tagName,t.weight,t.id,t.type from r_user_tag r INNER JOIN t_tag t on t.id=r.tag_id and r.user_id="+userid;
			SQLQuery query=session.createSQLQuery(sql);
			query.setResultTransformer(Transformers.aliasToBean(UserTagReturnBean.class));
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			
		}
		return list;
	}

	@Override
	public void saveUserTag(UserTag userTag) {
		String hql="from UserTag where userId="+userTag.getUserId()+" and tagId in("+userTag.getTagId()+")";
		UserTag user_tag=get(hql);
		Session session=sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			if(user_tag!=null){
			}else{
				session.save(userTag);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			
		}
	}

	@Override
	public void delete(UserTag userTag) {
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String hql = "delete from UserTag u where u.userId='" +userTag.getUserId() + "' and tagId in("+userTag.getTagId()+")";
			session.createQuery(hql).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}
}
