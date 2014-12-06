package net.wecash.server.facade;

import java.util.List;

import net.wecash.server.badupush.dao.PushDAO;
import net.wecash.server.mysql.model.Push;
import net.wecash.server.mysql.model.UserInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushFacade {

	@Autowired
	PushDAO pushDAO;
	
	public void savePush(Push newpush){
		pushDAO.savePush(newpush);
	}
	
	public Push getPush(Long userId){
		return pushDAO.getPush(userId);
	}

	public List<Push> getPushById(List<Long> id) {
		return pushDAO.getPushById(id);
	}

	public void pushUser(List<Push> push,String content) {
		pushDAO.pushUser(push,content);
	}
	public void savePush(String channelPushId, String userPushId, int clientType, Long userId){
		pushDAO.savePush(channelPushId,userPushId,clientType,userId);
	}

	public List<Push> getPush() {
		return pushDAO.getPush();
	}
}
