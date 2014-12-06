package net.wecash.server.controller;

import java.io.IOException;
import java.util.Map;

import net.wecash.common.bean.OnlyResultBean;
import net.wecash.common.exception.HandleExceptionController;
import net.wecash.server.auth.AuthValidater;
import net.wecash.server.bean.RoomCreateBean;
import net.wecash.server.bean.RoomQueryBean;
import net.wecash.server.behavior.service.BehaviorRecorder;
import net.wecash.server.facade.RoomFacade;
import net.wecash.server.gis.region.dao.RegionDAO;
import net.wecash.server.mysql.model.RoomInfo;
import net.wecash.server.util.CRUDUtil;
import net.wecash.server.util.ReturnUtil;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class RoomController extends HandleExceptionController {

	@Autowired
	AuthValidater authValidater;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	RoomFacade roomFacade;
	@Autowired
	BehaviorRecorder behaviorRecorder;
	@Autowired
	RegionDAO regionDAO;

	@RequestMapping(value = "/user/{id}/room", method = RequestMethod.GET)
	public @ResponseBody
	Object get(@PathVariable Long id,
			@RequestParam(required = true) String access_token)
			throws IOException, JSONException {
		authValidater.validateToken(access_token);
		RoomQueryBean  bean=roomFacade.getRoom(id);
		return new OnlyResultBean(ReturnUtil.formatNullObj(bean));
	}
	
	@RequestMapping(value = "/user/{id}/room", method = RequestMethod.POST)
	public @ResponseBody
	Object save(@PathVariable Long id,
			@RequestParam(required = true) String access_token,
			@Validated @RequestBody RoomCreateBean roomCreateBean) {
		authValidater.validateToken(id, access_token);
		RoomInfo oldRoomInfo = roomFacade.getSrcRoomInfo(id);
		RoomInfo roomInfo = null;
		if(oldRoomInfo != null){
			roomInfo = CRUDUtil.checkUpdateValues(roomCreateBean, oldRoomInfo, RoomInfo.class);
			if(CRUDUtil.canUpdate(roomInfo)){
				roomFacade.convertRegionInfo(roomInfo);
				roomInfo.setUserId(id);
				roomFacade.updateRoom(CRUDUtil.intersectionUpdateValues(roomInfo, oldRoomInfo, RoomInfo.class));
			}
		}else{
			roomInfo = mapper.convertValue(roomCreateBean, RoomInfo.class);
			roomFacade.convertRegionInfo(roomInfo);
			roomInfo.setUserId(id);
			roomFacade.saveRoom(roomInfo);
		}
		behaviorRecorder.triggerAnalyzerRoomAddr(id, mapper.convertValue(roomInfo, Map.class));
		return new OnlyResultBean("ok");
	}
/*
	@RequestMapping(value = "/user/{id}/room/update", method = RequestMethod.POST)
	public @ResponseBody
	Object update(@PathVariable Long id,
			@RequestParam(required = true) String access_token,
			@Validated @RequestBody RoomUpdateBean roomUpdateBean) {
		authValidater.validateToken(id, access_token);
		if(UpdateBodyUtil.canUpdate(roomUpdateBean)){
			RoomInfo roomInfo = mapper.convertValue(roomUpdateBean, RoomInfo.class);
			roomInfo.setUserId(id);
			roomFacade.updateRoom(roomInfo);
			behaviorRecorder.triggerAnalyzerRoomAddr(id, mapper.convertValue(roomInfo, Map.class));
		}else{
			throw new ErrorCodeException(ErrorCode.REQUEST_BODY_IS_ILLEGAL);
		}
		return new OnlyResultBean("ok");
	}*/

}
