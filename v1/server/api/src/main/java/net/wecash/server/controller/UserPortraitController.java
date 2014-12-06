package net.wecash.server.controller;

import java.util.List;
import java.util.Map;

import net.wecash.common.bean.MatchResultBean;
import net.wecash.common.bean.OnlyResultBean;
import net.wecash.common.exception.HandleExceptionController;
import net.wecash.server.analyzer.score.service.UserScoreDAO;
import net.wecash.server.auth.AuthValidater;
import net.wecash.server.bean.UserDetail;
import net.wecash.server.facade.FavoriteFacade;
import net.wecash.server.facade.MatchFacade;
import net.wecash.server.facade.UserFacade;
import net.wecash.server.mysql.model.Token;
import net.wecash.server.util.BoutiqueUserUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author xkk
 * 
 */
@Controller
public class UserPortraitController extends HandleExceptionController {

	@Autowired
	AuthValidater authValidater;
	@Autowired
	UserScoreDAO userScoreDAO;
	@Autowired
	UserFacade userFacade;
	@Autowired
	MatchFacade matchFacade;
	@Autowired
	FavoriteFacade favoriteFacade;
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(UserPortraitController.class);

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/user/{id}/match", method = RequestMethod.GET)
	public @ResponseBody
	Object match(@PathVariable Long id,
			@RequestParam(required = false, defaultValue = "0") Integer cursor,
			@RequestParam(required = false, defaultValue = "9") Integer limit,
			@RequestParam(required = false) Boolean flag,
			@RequestParam(required = true) String access_token) {
		authValidater.validateToken(id, access_token);
		List<Map> requestList = matchFacade.addMatchedUsers(id, flag, limit, cursor);
		float userILevel = userFacade.getUserInfoIntegritylevel(id);
		long total = userScoreDAO.getScoresCount(id);
		return new MatchResultBean(requestList, userILevel, total);
	}

	@RequestMapping(value = "/user/{id}/match/{targetId}", method = RequestMethod.GET)
	public @ResponseBody
	Object matchUsers(@PathVariable Long id, 
			@PathVariable Long targetId,
			@RequestParam(required = false, defaultValue = "1") Integer cursor,
			@RequestParam(required = false, defaultValue = "10") Integer limit,
			@RequestParam(required = true) String access_token) {
		Token token = authValidater.validateToken(id, access_token);
		Object o = null;
		if(targetId == (long)BoutiqueUserUtil.userId1
				|| targetId == (long) BoutiqueUserUtil.userId2){
			Map<String, Object> map = BoutiqueUserUtil.getBoutiqueUser(targetId);
			boolean isFavorite = favoriteFacade.isFavorite(token.getUserId(), targetId);
			map.put("isFavorite", isFavorite);
			o = map;
		}else{
			UserDetail user = matchFacade.addUserDetailMatchInfo(id, targetId);
			o = user;
		}
		return new OnlyResultBean(o);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/boutique", method = RequestMethod.GET)
	public @ResponseBody
	Object Boutique(@RequestParam(required = true) String access_token) {
		authValidater.validateToken(access_token);
		List<Map> list = BoutiqueUserUtil.getBoutiqueUsers();
		return new MatchResultBean(list, 0f, 2L);
	}
	
	@RequestMapping(value = "/boutique/{targetId}", method = RequestMethod.GET)
	public @ResponseBody
	Object BoutiqueDetail(@PathVariable Long targetId,
			@RequestParam(required = true) String access_token) {
		Token token = authValidater.validateToken(access_token);
		Map<String, Object> map = BoutiqueUserUtil.getBoutiqueUser(targetId);
		boolean isFavorite = favoriteFacade.isFavorite(token.getUserId(), targetId);
		map.put("isFavorite", isFavorite);
		return new OnlyResultBean(map);
	}
}
