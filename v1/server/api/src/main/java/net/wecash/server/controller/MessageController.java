package net.wecash.server.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.validation.Valid;

import net.wecash.common.bean.OnlyResultBean;
import net.wecash.common.exception.Error;
import net.wecash.common.exception.ErrorCode;
import net.wecash.common.exception.ErrorCodeException;
import net.wecash.server.auth.AuthValidater;
import net.wecash.server.bean.MessageCreateBean;
import net.wecash.server.bean.MessageResultBean;
import net.wecash.server.contact.dao.ContactDAO;
import net.wecash.server.message.dao.MessageDAO;
import net.wecash.server.mysql.model.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MessageController {

	@Autowired
	MessageDAO messageDAO;
	@Autowired
	ContactDAO contactDAO;
	@Autowired
	AuthValidater authValidater;
	@Autowired
	ObjectMapper mapper;

	@RequestMapping(value = "/message", method = RequestMethod.POST)
	public @ResponseBody
	Object save(@RequestParam String access_token,
			@Valid @RequestBody MessageCreateBean messageCreateBean) {
		authValidater.validateToken(access_token);
		Message message = mapper.convertValue(messageCreateBean, Message.class);
		boolean state = contactDAO.isFirend(message.getFromUserId(), message.getToUserId());
		if (state) {
			messageDAO.save(message);
		} else {
			throw new ErrorCodeException(new Error(ErrorCode.PERMISSION_DENIED));
		}
		return new OnlyResultBean("ok");
	}

	@RequestMapping(value = "/message/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Object get(@PathVariable Long id,
			@RequestParam String access_token,
			@RequestParam (required =false) Long start_time,
			@RequestParam (required =false) Long end_time,
			@RequestParam(required = false, defaultValue = "0") Integer cursor,
			@RequestParam(required = false, defaultValue = "5") Integer limit){
		authValidater.validateToken(id, access_token);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String startTimeStr =null;
		String endTimeStr =null;
		if(start_time!=null){
			startTimeStr=sdf.format(start_time);
		}
		if(end_time!=null){
			endTimeStr=sdf.format(end_time);
		}
		List<MessageResultBean> list = messageDAO.getMessage(id, cursor,
				limit > 5 ? 5 : limit,startTimeStr,endTimeStr);
		return new OnlyResultBean(list);
	}

	@RequestMapping(value = "/message/{id}/destroy", method = RequestMethod.POST)
	public @ResponseBody
	Object del(@PathVariable Long id, 
			@RequestParam Long user_id,
			@RequestParam String access_token) {
		authValidater.validateToken(access_token);
		Message message = messageDAO.getMessageById(id, user_id);
		if (message != null) {
			messageDAO.deleteMessage(id, user_id);
		} else {
			throw new ErrorCodeException(new Error(
					ErrorCode.RESOURCE_DOES_NOT_EXISTS, "message"));
		}
		return new OnlyResultBean("ok");
	}
}
