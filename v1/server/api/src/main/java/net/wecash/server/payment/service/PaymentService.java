package net.wecash.server.payment.service;

import net.wecash.common.service.Collections;
import net.wecash.server.mysql.model.Payment;
import net.wecash.server.payment.dao.PaymentDAO;
import net.wecash.server.service.basic.MysqlBasicService;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

@Service
public class PaymentService extends MysqlBasicService implements PaymentDAO{
	
	/**
	 * if Owing money return false
	 */
	@Override
	public boolean validateUserBill(Long userId){
		return true;
	}
	
	public void addPayment(Payment payment){
		add(payment);
	}
	
	public void updatePayment(Payment payment){
		updateByField(payment, "id");
	}
	
	@Override
	public Payment getPayment(Long userId) {
		Payment payment = null;
		Session session =sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String sql="select * from "+Collections.T_PAYMENT+" where user_id="+userId;
			SQLQuery query=session.createSQLQuery(sql);
			Object o=query.uniqueResult();
			if(o!=null){
				payment=(Payment)o;
			}
			session.beginTransaction().commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			session.beginTransaction().rollback();
			e.printStackTrace();
		}
		return payment;
	}
	
}
