package net.wecash.server.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import net.wecash.common.bean.OnlyResultBean;
import net.wecash.common.exception.Error;
import net.wecash.common.exception.ErrorCode;
import net.wecash.common.exception.ErrorCodeException;
import net.wecash.common.exception.HandleExceptionController;
import net.wecash.server.auth.AuthValidater;
import net.wecash.server.bean.PushCreateBean;
import net.wecash.server.facade.PushFacade;
import net.wecash.server.mysql.model.Push;
import net.wecash.server.room.dao.RoomDAO;

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
public class PushController extends HandleExceptionController {

	@Autowired
	AuthValidater authValidater;
	@Autowired
	ObjectMapper mapper;
	@Autowired
	PushFacade pushFacade;
	@Autowired
	RoomDAO roomDAO;

	@RequestMapping(value = "/push/{id}/save", method = RequestMethod.POST)
	public @ResponseBody
	Object savePush(@PathVariable Long id,
			@RequestParam(required = true) String access_token,
			@Valid @RequestBody PushCreateBean pushCreateBean) {
		authValidater.validateToken(id, access_token);
		Push push = pushFacade.getPush(id);
		if (push == null) {
			Push newpush = mapper.convertValue(pushCreateBean, Push.class);
			newpush.setUserId(id);
			pushFacade.savePush(newpush);
		} else {
			throw new ErrorCodeException(new Error(
					ErrorCode.RESOURCE_ALREADY_EXISTS, "push"));
		}
		return new OnlyResultBean("ok");
	}

	@RequestMapping(value = "/push/getAll", method = RequestMethod.GET)
	public @ResponseBody
	Object getPush(@RequestParam(required = true) String access_token) {
		authValidater.validateToken(access_token);
		List<Push> push = pushFacade.getPush();
		return new OnlyResultBean(push);
	}

	@RequestMapping(value = "/push", method = RequestMethod.GET)
	public @ResponseBody
	Object pushtUser(@RequestParam Long state,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String city,
			@RequestParam(required = false) String area,
			@RequestParam(required = true) String access_token,
			@RequestParam String content) {
		authValidater.validateToken(access_token);
		authValidater.validateSystemToken(access_token);
		List<Long> ids = new ArrayList<Long>();
		if (state == 0) {
			List<Push> pushs = pushFacade.getPush();
			for (Push push : pushs) {
				ids.add(push.getUserId());
			}
		} else {
			ids = roomDAO.getUserId(province, city, area);
		}
		if (ids != null) {
			List<Push> pushs = pushFacade.getPushById(ids);
			if (pushs != null && pushs.size() > 0) {
				pushFacade.pushUser(pushs, content);
			} else {
				throw new ErrorCodeException(new Error(
						ErrorCode.RESOURCE_DOES_NOT_EXISTS, "push"));
			}
		}
		return new OnlyResultBean("ok");
	}
}
