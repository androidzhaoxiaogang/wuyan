package net.wecash.server.controller;

import java.util.List;

import net.wecash.common.bean.OnlyResultBean;
import net.wecash.server.auth.AuthValidater;
import net.wecash.server.bean.UserCollectBean;
import net.wecash.server.facade.FavoriteFacade;
import net.wecash.server.mysql.model.Favorite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FavoriteController {

	@Autowired
	AuthValidater authValidater;
	@Autowired
	FavoriteFacade favoriteFacade;
	
	@RequestMapping(value = "/favorite/{id}/add/{target}", method = RequestMethod.GET)
	public @ResponseBody
	Object save(
				@PathVariable Long id,
				@RequestParam String access_token,
				@PathVariable Long target) {
		authValidater.validateToken(id,access_token);
		boolean state=favoriteFacade.getById(id, target);
		if(!state){
			favoriteFacade.save(id,target);
		}
		return new OnlyResultBean("ok");
	}

	@RequestMapping(value = "/favorites/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Object get(@PathVariable Long id,
			@RequestParam String access_token,
			@RequestParam(required = false, defaultValue = "0") Integer cursor,
			@RequestParam(required = false, defaultValue = "10") Integer limit){
		authValidater.validateToken(id, access_token);
		List<UserCollectBean> list = favoriteFacade.getAll(id, cursor,
				limit > 10 ? 10 : limit);
		return new OnlyResultBean(list);
	}

	@RequestMapping(value = "/favorite/{id}/cancel/{target}", method = RequestMethod.GET)
	public @ResponseBody
	Object del(@PathVariable Long id, 
			@PathVariable Long target,
			@RequestParam String access_token) {
		authValidater.validateToken(access_token);
		boolean state = favoriteFacade.getById(id, target);
		if (state) {
			favoriteFacade.delete(id, target);
		}
		return new OnlyResultBean("ok");
	}
	
}
