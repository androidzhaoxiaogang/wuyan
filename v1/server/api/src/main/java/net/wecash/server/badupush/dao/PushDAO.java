package net.wecash.server.badupush.dao;

import java.util.List;

import net.wecash.server.mysql.model.Push;

public interface PushDAO {

	public void savePush(Push newpush);

	public Push getPush(Long userId);

	public List<Push> getPushById(List<Long> ids);

	public void pushUser(List<Push> push,String content);

	public void savePush(String channelPushId, String userPushId, int clientType, Long userId);

	public void deletePushInfo(Long userId);
	public List<Push> getPush();
	
}
