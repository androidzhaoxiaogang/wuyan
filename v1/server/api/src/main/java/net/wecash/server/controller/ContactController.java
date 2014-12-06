package net.wecash.server.controller;

import java.util.List;

import net.wecash.common.bean.OnlyResultBean;
import net.wecash.common.exception.ErrorCode;
import net.wecash.common.exception.ErrorCodeException;
import net.wecash.common.exception.HandleExceptionController;
import net.wecash.server.auth.AuthValidater;
import net.wecash.server.bean.UserContactRequestBean;
import net.wecash.server.contact.dao.ContactDAO;
import net.wecash.server.mysql.model.Contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ContactController extends HandleExceptionController {

	@Autowired
	AuthValidater authValidater;
	@Autowired
	ContactDAO contactDAO;

	@RequestMapping(value = "/contact/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Object getUserContact(@PathVariable Long id,
			@RequestParam(required = true) String access_token,
			@RequestParam(required = false, defaultValue = "1") Integer type,
			@RequestParam(required = false, defaultValue = "1") Integer cursor,
			@RequestParam(required = false, defaultValue = "20") Integer limit) {
		authValidater.validateToken(id, access_token);
		List<UserContactRequestBean> list = contactDAO.getUserContact(id,
				cursor, limit > 20 ? 20 : limit);
		return new OnlyResultBean(list);
	}

	@RequestMapping(value = "/contact/{id}", method = RequestMethod.POST)
	public @ResponseBody
	Object saveUserContact(@PathVariable Long id,
			@RequestParam(required = true) String access_token,
			@RequestParam(required = false) Long contact_id) {
		authValidater.validateToken(id, access_token);
		Contact contact = contactDAO.getContact(id, contact_id);
		if (contact == null) {
			contactDAO.saveUserContact(id, contact_id);
		} else {
			throw new ErrorCodeException(ErrorCode.RESOURCE_ALREADY_EXISTS, "contact(" + id + ")");
		}
		return new OnlyResultBean("ok");
	}

	@RequestMapping(value = "/contact/{id}/update", method = RequestMethod.POST)
	public @ResponseBody
	Object updateUserContact(@PathVariable Long id,
			@RequestParam(required = true) String access_token,
			@RequestParam(required = false) Long contact_id,
			@RequestParam(required = false, defaultValue = "1") int permit) {
		authValidater.validateToken(id, access_token);
		contactDAO.updateUserContact(id, contact_id, permit == 1 ? 1 : 0);
		return new OnlyResultBean("ok");
	}
}
