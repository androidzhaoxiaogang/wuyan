package net.wecash.server.controller;

import java.util.Map;

import net.wecash.common.bean.OnlyResultBean;
import net.wecash.server.auth.AuthValidater;
import net.wecash.server.behavior.service.StatisticsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StatisticsController {

	@Autowired
	AuthValidater authValidater;
	@Autowired
	StatisticsService statisticsService;
	
	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	public @ResponseBody
	Object getActivity(
			@RequestParam String access_token) {
		authValidater.validateToken(access_token);
		authValidater.validateSystemToken(access_token);
		Map<String, Object> map=statisticsService.getStatistics();
		return new OnlyResultBean(map);
	}
}
