package net.wecash.server.behavior.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.wecash.server.analyzer.UserMatchCalculator;
import net.wecash.server.analyzer.bean.ScoreTriggerBean;
import net.wecash.server.bean.MatchRequireBean;
import net.wecash.server.bean.RoomQueryBean;
import net.wecash.server.behavior.dao.BehaviorDAO;
import net.wecash.server.mysql.model.User;
import net.wecash.server.room.dao.RoomDAO;
import net.wecash.server.user.dao.PortraitDAO;
import net.wecash.server.user.dao.UserDAO;
import net.wecash.server.util.PortraitUtil;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author franklin.li
 *
 */
@Component
public class BehaviorRecorder {
	
	private ExecutorService executor;
	@Autowired
	ObjectMapper mapper;
	@Autowired
	RoomDAO roomDAO;
	@Autowired
	PortraitDAO portraitDAO;
	@Autowired 
	BehaviorDAO behaviorDAO;
	@Autowired
	UserDAO userDAO;
    @Autowired
    private UserMatchCalculator userMatchCalculator;
    private int limit = 300;
	
	private static final Logger logger = LoggerFactory.getLogger(BehaviorRecorder.class);
	public BehaviorRecorder(){
		this.executor = Executors.newSingleThreadExecutor();
	}
	
	@SuppressWarnings("rawtypes")
	public void triggerAnalyzerPortrait(final Long userId, final Map map){
		if(!matchRequired(userId)){
			logger.error("trigger analyzer error: information incomplete");
			return;
		}
        this.executor.execute(new Runnable() {
			@Override
            public void run() {
            	try{
            		Map portraitMap = PortraitUtil.getCalPortrait(map);
            		if(portraitMap.size() > 0){
                			publishTriggerMsg(userId, map);
            		}
            	}catch(Exception e){
            		logger.error("trigger analyzer error:" + e.getMessage());
            	}
            }
        });
	}
	
	@SuppressWarnings("rawtypes")
	public void triggerAnalyzerRoomAddr(final Long userId,final Map map){
		if(!matchRequired(userId)){
			logger.error("trigger analyzer error: information incomplete");
			return;
		}
		this.executor.execute(new Runnable() {
            @Override
            public void run() {
            	try{
            		Map roomAddrMap = PortraitUtil.getCalRoomAddr(map);
            		if(roomAddrMap.size() > 0){
            			publishTriggerMsg(userId, map);
            		}
            	}catch(Exception e){
            		logger.error("trigger analyzer error:" + e.getMessage());
            	}
            }
        });	
	}
	
	@SuppressWarnings("rawtypes")
	public void triggerAnalyzerAll(final Long userId, final Map map){
		if(!matchRequired(userId)){
			logger.error("trigger analyzer error: information incomplete");
			return;
		}
		this.executor.execute(new Runnable() {
            @Override
            public void run() {
            	try{
            		Map roomAddrMap = PortraitUtil.getCalRoomAddr(map);
            		Map portraitMap = PortraitUtil.getCalPortrait(map);
            		boolean publishTriggermsg = false;
            		if(roomAddrMap.size() > 0){
            			publishTriggermsg = true;
            		}

            		if(!publishTriggermsg && portraitMap.size() > 0){
            			Map lastRecord = behaviorDAO.getLastUpdatePortraitRecord(userId);
                		if(lastRecord != null){
                			int count = MapUtils.getIntValue(lastRecord, "count");
                			boolean lastTrigger = MapUtils.getBooleanValue(lastRecord, "isTrigger");
                			if(!lastTrigger){
                				publishTriggermsg = true;
                			}else if(count >= 2){
                				publishTriggermsg = true;
                			}
                		}else{
                			publishTriggermsg = true;
                		}
            		}
            		if (publishTriggermsg) {
            			publishTriggerMsg(userId, map);
            		}
            	}catch(Exception e){
            		logger.error("trigger analyzer error:" + e.getMessage());
            	}
            }
        });	
	}
	
	@SuppressWarnings("rawtypes")
	public void asyncPublishTriggerMsg(final Long userId, final Map record){
		this.executor.execute(new Runnable() {
            @Override
            public void run() {
            	publishTriggerMsg(userId, record);
            }
		});
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void publishTriggerMsg(Long userId, Map record){
		try {
			RoomQueryBean rqb = roomDAO.getRoomCodeById(userId);
			if(rqb != null){
				if(record == null){
					record = new HashMap();
					record.put("reAnalyzer", 1);
				}
				behaviorDAO.addUpdatePortraitRecord(userId, record, true);
				User user = userDAO.getUser(userId);
				List list = portraitDAO.getUserId(rqb.getProvince(), rqb.getCity(), rqb.getArea(),
						rqb.getLandmark(), rqb.getMinPrice(), rqb.getMaxPrice(),user.getState(), limit);
				if(list.size() <= 10){
					List enlargeList = portraitDAO.getUserId(rqb.getCity(), user.getState(), limit);
					if(enlargeList != null){
						list.addAll(enlargeList);
					}
				}
				if(list.size() > 0){
					ScoreTriggerBean stb = new ScoreTriggerBean(userId, list);
					userMatchCalculator.analyzer(stb);
					logger.info("trigger match:" + record+" userId(" + userId + ")");
				}
			}else{
				logger.info("failed to trigger portrait:" + record + " no room info");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	private Boolean matchRequired(Long userId){
		MatchRequireBean mrb = userDAO.getMatchRequire(userId);
		Map mrbMap = mapper.convertValue(mrb, Map.class);
		if(mrbMap != null && mrbMap.size() > 6){
			return true;
		}else{
			return false;
		}
	}
}
