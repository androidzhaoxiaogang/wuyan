package net.wecash.server.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import net.wecash.common.bean.OnlyResultBean;
import net.wecash.common.exception.ErrorCode;
import net.wecash.common.exception.ErrorCodeException;
import net.wecash.common.exception.HandleExceptionController;
import net.wecash.common.mongo.model.SnsDataBean;
import net.wecash.server.auth.AuthValidater;
import net.wecash.server.badupush.dao.PushDAO;
import net.wecash.server.bean.UserAllBean;
import net.wecash.server.bean.UserDetail;
import net.wecash.server.bean.UserUpdateBean;
import net.wecash.server.behavior.service.BehaviorRecorder;
import net.wecash.server.dao.FileDAO;
import net.wecash.server.facade.FavoriteFacade;
import net.wecash.server.facade.RoomFacade;
import net.wecash.server.facade.TokenFacade;
import net.wecash.server.facade.UserFacade;
import net.wecash.server.interest.InterestDAO;
import net.wecash.server.mysql.model.User;
import net.wecash.server.mysql.model.UserImg;
import net.wecash.server.mysql.model.UserInfo;
import net.wecash.server.room.dao.RoomDAO;
import net.wecash.server.sns.dao.SnsDataDAO;
import net.wecash.server.sns.dao.ThirdSnsDAO;
import net.wecash.server.user.dao.UserImageDAO;
import net.wecash.server.util.CRUDUtil;
import net.wecash.server.util.ReturnUtil;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author xkk
 * 
 */
@Controller
public class UserController extends HandleExceptionController {

	@Autowired
	ObjectMapper mapper;

	@Autowired
	UserFacade userFacade;

	@Autowired
	TokenFacade tokenFacade;

	@Autowired
	AuthValidater authValidater;

	@Autowired
	ThirdSnsDAO thirdSnsDAO;
	@Autowired
	InterestDAO interestDAO;
	@Autowired
	SnsDataDAO snsDataDAO;
	@Autowired
	UserImageDAO userImageDAO;
	@Autowired
	FileDAO fileDAO;
	@Autowired
	RoomDAO roomDAO;
	@Autowired
	BehaviorRecorder behaviorRecorder;
	@Autowired
	RoomFacade roomFacade;
	@Autowired
	PushDAO pushDAO;
	@Autowired
	FavoriteFacade favoriteFacade;

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Object get(@PathVariable Long id,
			@RequestParam(required = false) String access_token) {
		authValidater.validateToken(access_token);
		User user = userFacade.getUser(id);
		return new OnlyResultBean(user);
	}

	@RequestMapping(value = "/user/{id}/detail", method = RequestMethod.GET)
	public @ResponseBody
	Object getDetail(@PathVariable Long id,
			@RequestParam(required = true) String access_token) {
		authValidater.validateToken(access_token);
		UserDetail user = userFacade.getUserDetailInfo(id);
		if(user == null){
			throw new ErrorCodeException(ErrorCode.USER_DOSE_NOT_EXISTS);
		}
		SnsDataBean sdb = snsDataDAO.get(id);
		List<String> tags = sdb.getTags();
		UserImg icon = userImageDAO.getUserIconInfo(id);
		List<String> imgs = userImageDAO.getAllUserImg(id);
		user.setIcon(icon.getImage());
		user.setImage(imgs);
		if(tags != null && tags.size() > 6){
			tags = tags.subList(0, 5);
		}
		user.setTags(tags);
		return new OnlyResultBean(ReturnUtil.formatNullObj(user));
	}

	@RequestMapping(value = "/user/{id}/update", method = RequestMethod.POST)
	public @ResponseBody
	Object update(@PathVariable Long id,
			@RequestParam(required = true) String access_token,
			@Valid @RequestBody UserUpdateBean userUpdateBean) {
		try {
			logger.info("userUpdateBean"+mapper.writeValueAsString(userUpdateBean));
		} catch (Exception e) {
			// TODO: handle exception
		}
		authValidater.validateToken(id, access_token);
		User oldUser = userFacade.getUser(id);
		User user = CRUDUtil.checkUpdateValues(userUpdateBean, oldUser, User.class);
		if (CRUDUtil.canUpdate(user)) {
			user.setId(id);
			userFacade.updateUser(user);
 			behaviorRecorder.triggerAnalyzerPortrait(id, mapper.convertValue(user, Map.class));
		}
		return new OnlyResultBean("ok");
	}

	@RequestMapping(value = "/user/{id}/update/all", method = RequestMethod.POST)
	public @ResponseBody
	Object updateAll(@PathVariable Long id,
			@RequestParam(required = true) String access_token,
			@Valid @RequestBody UserAllBean userAllBean) {
		authValidater.validateToken(id, access_token);
		try {
			logger.info("userUpdateBean"+mapper.writeValueAsString(userAllBean));
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(CRUDUtil.canUpdate(userAllBean)){
			boolean canUpdate = false;
			canUpdate = roomFacade.updateOrsaveRoomByUserAllBean(id, userAllBean);
			User oldUser = userFacade.getUser(id);
			User user = CRUDUtil.checkUpdateValues(userAllBean, oldUser, User.class);
			if (CRUDUtil.canUpdate(user)) {
				user.setId(id);
				userFacade.updateUser(user);
				canUpdate = true;
			}
			if (canUpdate){
				behaviorRecorder.triggerAnalyzerAll(id, mapper.convertValue(userAllBean, Map.class));
			}
		}
		return new OnlyResultBean("ok");
	}

	@RequestMapping(value = "/user/{id}/destroy", method = RequestMethod.POST)
	public @ResponseBody
	Object delete(@PathVariable Long id,
			@RequestParam(required = true) String access_token) {
		authValidater.validateToken(id, access_token);
		userFacade.deleteUser(id);
		tokenFacade.deleteToken(id);
		thirdSnsDAO.deleteSnsInfoByUserId(id);
		snsDataDAO.delete(id);
		interestDAO.delete(id);
		roomDAO.deleteRoom(id);
		pushDAO.deletePushInfo(id);
		favoriteFacade.delete(id);
		List<UserImg> images = userImageDAO.getUserImages(id);
		for (UserImg ui : images) {
			userImageDAO.deleteUserImg(id, ui.getImage());
			fileDAO.deleteFile(new ObjectId(ui.getImage()));
		}
		return new OnlyResultBean("ok");
	}
	
	@RequestMapping(value = "/user/{id}/logout", method = RequestMethod.GET)
	public @ResponseBody
	Object logout(@PathVariable Long id,
			@RequestParam String access_token) throws Exception {
		authValidater.validateToken(id, access_token);
		tokenFacade.deleteToken(access_token);
		return new OnlyResultBean("ok");
	}
	
	@RequestMapping(value = "/user/{id}/info", method = RequestMethod.GET)
	public @ResponseBody
	Object getUser(@PathVariable Long id,
			@RequestParam String access_token) {
		authValidater.validateToken(access_token);
		UserInfo userInfo =userFacade.getUserInfo(id);
		return new OnlyResultBean(userInfo);
	}
}
