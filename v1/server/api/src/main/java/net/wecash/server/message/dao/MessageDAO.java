package net.wecash.server.message.dao;

import java.util.List;

import net.wecash.server.bean.MessageResultBean;
import net.wecash.server.mysql.model.Message;

public interface MessageDAO {

	public void save(Message message);

	public List<MessageResultBean> getMessage(Long id ,Integer cursor, Integer limit, String start_time, String end_time);

	public void deleteMessage(Long id, Long userId);

	public Message getMessageById(Long id, Long userId);

}
