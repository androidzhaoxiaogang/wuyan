package net.wecash.server.controller;

import java.util.List;

import net.wecash.common.bean.OnlyResultBean;
import net.wecash.common.exception.HandleExceptionController;
import net.wecash.server.auth.AuthValidater;
import net.wecash.server.facade.SubwayFacade;
import net.wecash.server.mysql.model.Subway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SubwayController extends HandleExceptionController{

	@Autowired
	AuthValidater authValidater;
	@Autowired
	SubwayFacade subwayFacade;
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
	@RequestMapping(value="/subway",method=RequestMethod.GET)
	public @ResponseBody Object getSubway(@RequestParam(required = false) String access_token,
    		@RequestParam(required =false) String city_code,
    		@RequestParam(required =false) String line_num){
		List<Subway> list=subwayFacade.getAll(city_code,line_num);
		return new OnlyResultBean(list);
	}
}
