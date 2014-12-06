package net.wecash.server.contact.dao;

import java.util.List;

import net.wecash.server.bean.UserContactRequestBean;
import net.wecash.server.mysql.model.Contact;


public interface ContactDAO {

	void saveUserContact(Long id, Long toUserId);

	void updateUserContact(Long id, Long toUserId, int permit);

	List<UserContactRequestBean> getUserContact(Long id,Integer first,Integer max);
	
	List<Integer> getUserId(Long id);

	Contact getContact(Long id, Long toUserId);

	boolean isFirend(Long fromUserId, Long toUserId);
}
