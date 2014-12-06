package net.wecash.server.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.validation.Valid;

import net.wecash.common.auth.oauth2.TokenInfo;
import net.wecash.common.bean.OnlyResultBean;
import net.wecash.common.dimension.InterestType;
import net.wecash.common.exception.Error;
import net.wecash.common.exception.ErrorCode;
import net.wecash.common.exception.ErrorCodeException;
import net.wecash.common.exception.HandleExceptionController;
import net.wecash.common.mongo.model.SnsDataBean;
import net.wecash.common.third.SnsAuthBean;
import net.wecash.common.util.DateUtils;
import net.wecash.common.util.MatchUtil;
import net.wecash.server.amqp.AmqpMessageSender;
import net.wecash.server.auth.AccessTokenGenerator;
import net.wecash.server.auth.AuthValidater;
import net.wecash.server.backend.BackendDAO;
import net.wecash.server.bean.BackendLoginBean;
import net.wecash.server.bean.LandmarkAllBean;
import net.wecash.server.bean.ThirdLoginBean;
import net.wecash.server.bean.UserLoginResultBean;
import net.wecash.server.behavior.service.BehaviorRecorder;
import net.wecash.server.dao.FileDAO;
import net.wecash.server.easemob.EasemobApi;
import net.wecash.server.facade.PushFacade;
import net.wecash.server.facade.ThirdLoginFacade;
import net.wecash.server.facade.TokenFacade;
import net.wecash.server.facade.UserFacade;
import net.wecash.server.gis.region.dao.RegionDAO;
import net.wecash.server.interest.InterestDAO;
import net.wecash.server.interest.InterestEncodeBean;
import net.wecash.server.mysql.model.AdminInfo;
import net.wecash.server.mysql.model.RoomInfo;
import net.wecash.server.mysql.model.SnsAuthInfo;
import net.wecash.server.mysql.model.Token;
import net.wecash.server.mysql.model.User;
import net.wecash.server.mysql.model.UserImg;
import net.wecash.server.room.dao.RoomDAO;
import net.wecash.server.sns.dao.SnsDataDAO;
import net.wecash.server.sns.dao.ThirdSnsDAO;
import net.wecash.server.sns.dao.ThirdSnsRequestDAO;
import net.wecash.server.sns.service.ThirdSnsService;
import net.wecash.server.tag.dao.TagDAO;
import net.wecash.server.user.dao.UserImageDAO;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("")
public class LoginController extends HandleExceptionController {
	@Autowired
	ThirdSnsRequestDAO loginRequestDAO;
	@Autowired
	ThirdSnsDAO thirdSnsDAO;
	@Autowired
	UserFacade userFacade;
	@Autowired
	TokenFacade tokenFacade;
	@Autowired
	ThirdLoginFacade thirdLoginFacade;
	@Autowired
	AmqpMessageSender amqpMessageSender;
	@Autowired
	ThirdSnsService thirdSnsService;
	@Autowired
	AuthValidater authValidater;
	@Autowired
	FileDAO imageDAO;
	@Autowired
	UserImageDAO userImageDAO;
	@Autowired
	SnsDataDAO snsDataDAO;
	@Autowired
	InterestDAO interestDAO;
	@Autowired
	TagDAO tagDAO;
	@Autowired
	BackendDAO backendDAO;
	@Autowired
	RoomDAO roomDAO;
	@Autowired
	RegionDAO regionDAO;
	@Autowired
	ObjectMapper mapper;
	@Autowired
	PushFacade pushFacade;
	@Autowired
	BehaviorRecorder behaviorRecorder;
	@Autowired
	EasemobApi easemobApi;

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(TagController.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody
	Object login(@Valid @RequestBody ThirdLoginBean loginBean) throws Exception {
		int clientType = authValidater.validateClient(loginBean.getClient_id(), loginBean.getClient_secret());
		String uid = loginBean.getUid();
		String access_token = loginBean.getThird_token();
		String channelPushId=loginBean.getChannel_push_id();
		String userPushId=loginBean.getUser_push_id();
		Integer type = loginBean.getType();
		if (type == 1) {
			if (!loginRequestDAO.validateWeiboToken(uid, access_token)) {
				throw new ErrorCodeException(new Error(ErrorCode.UID_OR_ACCESS_TOKEN_ERROR));
			}
		} else if (type == 2) {
			// TODO:人人授权验证
		} else {
			throw new ErrorCodeException(new Error(ErrorCode.NONSUPPORT_SNS_AUTH_TYPE));
		}
		User u = null;
		
		boolean firstLogin = false;
		if (thirdSnsDAO.isThirdIdExists(uid)) {
			Long userId = null;
			userId = thirdSnsDAO.getUserIdByUidFromSnsAuthInfo(uid);
			thirdSnsDAO.updateSnsInfo(uid, access_token);
			u = userFacade.getUser(userId);
		} else {
			try {
				firstLogin = true;
				Map<String, Object> weiboInfo = loginRequestDAO.getWeiboInfo(uid, access_token);
				u = thirdLoginFacade.TransactionManage(uid, access_token, weiboInfo, loginBean);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ErrorCodeException(new Error(ErrorCode.SYSTEM_ERROR));
			}
		}
		Token token = new Token();
		Token oldToken = tokenFacade.getToken(u.getId());
		if(oldToken != null && oldToken.getToken() != null){
			token.setToken(oldToken.getToken());
		}else{
			token.setToken(AccessTokenGenerator.getInstance().getAccessToken());
		}
		if(channelPushId != null && userPushId != null){
			pushFacade.savePush(channelPushId, userPushId, clientType, u.getId());
		}
		token.setExpiresIn(TokenInfo.TokenExpiresIn);
		token.setUserId(u.getId());
		token.setType(u.getType());
		tokenFacade.addToken(token);
		float userILevel = userFacade.getUserInfoIntegritylevel(u.getId());
		UserLoginResultBean urb = new UserLoginResultBean(token.getToken(),
				token.getExpiresIn(), token.getUserId(), loginBean.getClient_id(), firstLogin, userILevel);
		amqpMessageSender.publishTriggerSnsDataCollectorMessage(new SnsAuthBean(uid, access_token, u.getId(), type));
		if(userILevel > 0.6){
			behaviorRecorder.asyncPublishTriggerMsg(u.getId(), null);
		}
		return new OnlyResultBean(urb);
	}

	@RequestMapping(value = "/login/simulate", method = RequestMethod.POST)
	public @ResponseBody
	Object loginSimulate(@Valid @RequestBody ThirdLoginBean loginBean,
			@RequestParam String code,
			@RequestParam String gender,
			@RequestParam int count,
			@RequestParam String room_des,
			@RequestParam int state
			) throws Exception {
		int clientType = authValidater.validateClient(loginBean.getClient_id(), loginBean.getClient_secret());
		String uid = loginBean.getUid();
		String access_token = loginBean.getThird_token();
		if(!loginBean.getUid().startsWith("wuyan")){
			throw new ErrorCodeException(new Error(ErrorCode.UID_OR_ACCESS_TOKEN_ERROR));
		}
		if(thirdSnsService.isThirdIdExists(uid)){
			throw new ErrorCodeException(new Error(ErrorCode.USER_ALREADY_EXISTS));
		}
		User u = new User();
		Random r = new Random();
		u.setGender(gender);
		u.setName(uid);
		String[] rYear = {"1990","1991","1992","1993","1994","1989","1988","1987"};
		String[] rMonth = {"01","02","04","06","08","09","11","12"};
		String[] rDay = {"01","04","07","09","11","14","17","19","22","25","27"};
		int y = r.nextInt(rYear.length);
		int m = r.nextInt(rMonth.length);
		int d = r.nextInt(rDay.length);
		u.setBirthday(DateUtils.formateDate(rYear[y]+"-"+rMonth[m]+"-"+rDay[d]));
		u.setDegree(r.nextInt(3));
		u.setHabit(r.nextInt(4));
		u.setOccupation(r.nextInt(6));
		u.setPersonality(0.5f);
		u.setState(state);
		u.setType(1);
		u.setDescription("");
		userFacade.addUser(u);
		User user = new User();
		user.setId(u.getId());
		user.setState(state);
		user.setName("wuyan"+u.getId());
		userFacade.updateUser(user);
		for(int i=1 ;i<=4;i++){
			String file = "C:\\Users\\franklin.li\\Desktop\\pic\\" +gender+"\\"+count+"\\"+i+".jpg";
			try {
				File fileF = new File(file);
				if(fileF.exists()){
					InputStream is = new FileInputStream(fileF);
					if(is != null){
						String filename = ObjectId.get().toString()+".jpg";
						if(i == 1){
							Object imgIndex = imageDAO.addFile(filename, is);
							UserImg ui = new UserImg(u.getId(), imgIndex.toString(), 0);
							userImageDAO.addUserImage(ui);
						}else{
							Object imgIndex1 = imageDAO.addFile(filename, is);
							UserImg ui1 = new UserImg(u.getId(), imgIndex1.toString(), i - 1);
							userImageDAO.addUserImage(ui1);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//String code="110000";
		//北京市:110000,天津市:120000,上海：310000,广东:440000,四川:510000
		//湖北：420000  江苏：320000 浙江：330000 深圳：重庆 500000
		List<LandmarkAllBean> landmarkList=regionDAO.getAll(code);
		int num=r.nextInt(landmarkList.size());
		RoomInfo roomInfo=new RoomInfo();
		LandmarkAllBean landmarkAllBean=landmarkList.get(num);
		roomInfo.setProvince(landmarkAllBean.getPcode());
		roomInfo.setCity(landmarkAllBean.getCcode());
		roomInfo.setArea(landmarkAllBean.getAcode());
		roomInfo.setLandmark(landmarkAllBean.getLcode());
		roomInfo.setUserId(u.getId());
		int rr = r.nextInt(5);
		roomInfo.setMinPrice(900 + rr * 100);
		roomInfo.setMaxPrice(1800 + rr * 100);
		roomInfo.setDescription(room_des);
		roomDAO.saveRoom(roomInfo);
		
		String[] rTags = {"音乐","宠物","运动","摄影","绘画","艺术","动漫","游戏","科技","财经","美食","旅行","公益","阅读","影视"};
		String[] subTerm = {"音乐达人","网球爱好者","萌宠","火影忍者","篮球","NBA","游泳","美容","健身","美拍","电子商务","料理","吃货","在路上","慈善","网络文学","韩剧","综艺"};
		List<String> timelineSubjectTerm = new ArrayList<>();
		List<String> tags = new ArrayList<>();
		for(int j =1; j<=2;j++){
			String s=  rTags[r.nextInt(rTags.length)];
			if(!tags.contains(s)){
				tags.add(s);
			}
		}
		for(int j =1; j<=3;j++){
			String s=  subTerm[r.nextInt(subTerm.length)];
			if(!timelineSubjectTerm.contains(s)){
				timelineSubjectTerm.add(s);
			}
		}
		List<Map> friends = new ArrayList<>();
/*		Map friend = new HashMap();
		friend.put("uid", Utilities.randomString(3));
		friend.put("name", Utilities.randomStr(9));
		friend.put("profileImage", "http://tp1.sinaimg.cn/1404376560/50/0/1");*/
//		friends.add(friend);
		SnsDataBean sdb = new SnsDataBean(uid, u.getId(), null, 1, u.getId().toString(), tags, timelineSubjectTerm, null, friends, null);
		sdb.setCreateTime(new Date());
		snsDataDAO.add(sdb);
		
		tags.addAll(timelineSubjectTerm);
		List<Integer> inList = MatchUtil.uniq(MatchUtil.formateTagType(tagDAO.groupTypesByNames(tags).toString()));
		InterestEncodeBean ib = new InterestEncodeBean();
		ib.setUserId(u.getId());
		ib.setInterestEncode(InterestType.encodeInterestTypes(inList));
		ib.setInterest(inList);
		interestDAO.add(ib);
		
		Token token = new Token();
		token.setToken(access_token);
		token.setExpiresIn(TokenInfo.TokenExpiresIn);
		token.setUserId(u.getId());
		token.setType(u.getType());
		tokenFacade.addToken(token);
		easemobApi.createNewUser(""+u.getId());
		SnsAuthInfo sai = new SnsAuthInfo();
		sai.setThirdId(uid);
		sai.setThirdToken(access_token);
		sai.setType(1);
		sai.setUserId(u.getId());
		thirdSnsService.addSnsInfo(sai);
		UserLoginResultBean urb = new UserLoginResultBean(token.getToken(),
				token.getExpiresIn(), token.getUserId(),loginBean.getClient_id(), true);
		return new OnlyResultBean(urb);
	}
	
	@RequestMapping(value = "/login/backend", method = RequestMethod.POST)
	public @ResponseBody
	Object loginAdmin(@Valid @RequestBody BackendLoginBean backendLoginBean) throws Exception {
		int clientType = authValidater.validateClient(backendLoginBean.getClient_id(), backendLoginBean.getClient_secret());
		if(backendDAO.validateAdmin(backendLoginBean.getUsername(), backendLoginBean.getPassword())){
			AdminInfo ai = backendDAO.getAdminInfo(backendLoginBean.getUsername());
			Token token = new Token();
			Token oldToken = tokenFacade.getToken(ai.getId());
			if(oldToken != null && oldToken.getToken() != null){
				token.setToken(oldToken.getToken());
			}else{
				token.setToken(AccessTokenGenerator.getInstance().getAccessToken());
			}
			token.setToken(AccessTokenGenerator.getInstance().getAccessToken());
			token.setExpiresIn(TokenInfo.TokenExpiresIn);
			token.setUserId(ai.getId());
			token.setType(0);
			tokenFacade.addToken(token);
			UserLoginResultBean urb = new UserLoginResultBean(token.getToken(),
					token.getExpiresIn(), token.getUserId(), backendLoginBean.getClient_id());
			return new OnlyResultBean(urb);
		}else{
			throw new ErrorCodeException(new Error(ErrorCode.USERNAME_OR_PASSWORD_ERROR));
		}
	}
	
	@RequestMapping(value = "/oauth2/get_token_info", method = RequestMethod.GET)
	public @ResponseBody
	Object authToken(
			@RequestParam(required = false) String access_token) throws OAuthSystemException {
		Token token = authValidater.validateToken(access_token);
		UserLoginResultBean urb = new UserLoginResultBean(token.getToken(),
				token.getExpiresIn(), token.getUserId(),token.getClientId());
		return new OnlyResultBean(urb);
	}
}
