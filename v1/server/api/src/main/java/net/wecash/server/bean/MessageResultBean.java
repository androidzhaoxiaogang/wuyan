package net.wecash.server.bean;

import java.util.Date;

public class MessageResultBean {

	private Integer id;
	private Integer fromUserId;
	private String content;
	private Date time;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "LeaveMessageRequestBean [id=" + id + ", fromUserId="
				+ fromUserId + ", content=" + content + ", time=" + time + "]";
	}
	
}
